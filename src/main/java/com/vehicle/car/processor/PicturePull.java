package com.vehicle.car.processor;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.repository.CarModelRepository;
import com.vehicle.car.repository.CarPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;

/**
 * 车型图片拉取（取9张即可）
 */
@Service
public class PicturePull implements PageProcessor {

    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarPictureRepository carPictureRepository;

    @Override
    public void process(Page page) {
        Date nowDate = new Date();
        Html html = page.getHtml();

        if (page.getUrl().regex("https://car.autohome.com.cn/photolist").match()) {
            System.out.println("列表页");
            // 列表数据
            String url = page.getUrl().toString();

            CarModel carModel = carModelRepository.findCarModelByPictureUrlEquals(url);
            Integer modelId = carModel.getId();

            List<String> title = html.xpath("//*[@id=\"imgList\"]/li/a/@title").all();
            List<String> priceLink = html.xpath("//*[@id=\"imgList\"]/li/a/@href").replace("/photo", "https://car.autohome.com.cn/photo").all();
            List<String> smallPrice1 = html.xpath("//*[@id=\"imgList\"]/li/a/img/@src").replace("//", "https://").all();
            List<String> smallPrice2 = html.xpath("//*[@id=\"imgList\"]/li/a/img/@src2").replace("//", "https://").all();

            System.out.println(title);
            System.out.println(priceLink);
            System.out.println(smallPrice1);
            System.out.println(smallPrice2);

            // 取9张即可
            for (int i = 0; i < title.size(); i++) {
                if (i < 9) {
                    CarPicture carPicture = new CarPicture();
                    carPicture.setModelId(modelId);
                    carPicture.setTitle(title.get(i));
                    carPicture.setPictureLink(priceLink.get(i));
                    carPicture.setPictureLocation(i + 1);
                    carPicture.setCreateTime(nowDate);

                    String sp1 = smallPrice1.get(i);
                    String sp2 = smallPrice2.get(i);
                    if (null == sp2 || sp2.equals("")) {
                        carPicture.setSmallPictureUrl(sp1);
                    } else {
                        carPicture.setSmallPictureUrl(sp2);
                    }
                    carPictureRepository.save(carPicture);

                    page.addTargetRequest(priceLink.get(i));
                }
            }
        } else {
            System.out.println("图片页");
            // 获取图片
            String url = page.getUrl().toString();
            CarPicture carPicture = carPictureRepository.findCarPictureByPictureLinkEquals(url);

            String pictureUrl = html.xpath("//*[@id=\"img\"]/@src").replace("//", "https://").get();
            System.out.println(pictureUrl);
            carPicture.setPictureUrl(pictureUrl);
            carPictureRepository.save(carPicture);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new PicturePull()).setDownloader(new HttpClientDownloader())
//                .addUrl("https://car.autohome.com.cn/photolist/spec/42418/p1/#pvareaid=3454554")
                .addUrl("https://car.autohome.com.cn/photolist/spec/42424/p1/#pvareaid=3454554")
                .thread(5)
                .run();
    }
}
