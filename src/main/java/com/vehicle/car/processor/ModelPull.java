package com.vehicle.car.processor;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarSeries;
import com.vehicle.car.repository.CarModelRepository;
import com.vehicle.car.repository.CarSeriesRepository;
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
 * 车型数据拉取
 */
@Service
public class ModelPull implements PageProcessor {

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarSeriesRepository carSeriesRepository;

    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Date nowDate = new Date();
        Html html = page.getHtml();

        if (page.getUrl().regex("https://www.autohome.com.cn/spec").match()) {
            System.out.println("标题");

            String url = page.getUrl().toString();
            CarModel carModel = carModelRepository.findCarModelByTitleUrlEquals(url);

            String pictureUrl = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[1]/div[2]/div[1]/div[1]/a/@href").replace("//", "https://").get();
            carModel.setPictureUrl(pictureUrl);
            carModelRepository.save(carModel);

        } else {
            System.out.println("总品牌型号");
            String url = page.getUrl().toString();
            CarSeries carSeries = carSeriesRepository.findCarSeriesByUrlEquals(url);
            Integer seriesId = carSeries.getId();
            List<String> title = html.xpath("//*[@id=\"specWrap-2\"]/dl/dd/div[1]/div/p/a[1]/text()").all();
            List<String> titleUrl = html.xpath("//*[@id=\"specWrap-2\"]/dl/dd/div[1]/div/p/a[1]/@href").replace("/spec", "https://www.autohome.com.cn/spec").all();
            List<String> guidancePrice = html.xpath("//*[@id=\"specWrap-2\"]/dl/dd/div[3]/p/span/text()").all();
            System.out.println(title);
            System.out.println(titleUrl);
            System.out.println(guidancePrice);
            for (int i = 0; i < title.size(); i++) {
                String tu = titleUrl.get(i);
                CarModel carModel = new CarModel();
                carModel.setTitle(title.get(i));
                carModel.setTitleUrl(tu);
                carModel.setPrice(guidancePrice.get(i));
                carModel.setSeriesId(seriesId);
                carModel.setCreateTime(nowDate);
                carModelRepository.save(carModel);
                page.addTargetRequest(tu);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ModelPull()).setDownloader(new HttpClientDownloader())
//                .addUrl("https://www.autohome.com.cn/692/#levelsource=000000000_0&pvareaid=101594")
                .addUrl("https://www.autohome.com.cn/spec/37101/#pvareaid=3454492")
                .thread(5)
                .run();
    }
}
