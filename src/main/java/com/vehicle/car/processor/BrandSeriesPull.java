package com.vehicle.car.processor;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.pojo.CarSeries;
import com.vehicle.car.repository.CarBrandRepository;
import com.vehicle.car.repository.CarSeriesRepository;
import com.vehicle.car.utils.PingYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Date;
import java.util.List;

/**
 * 品牌车系拉取
 */
@Service
public class BrandSeriesPull implements PageProcessor {
    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarSeriesRepository carSeriesRepository;

    @Override
    public void process(Page page) {
        Date nowDate = new Date();
        Html html = page.getHtml();
        List<String> brandIdList = html.xpath("//dl/@id").all();
        List<String> brandNameList = html.xpath("//dl/dt/div/a/text()").all();

        System.out.println("品牌id：" + brandIdList);
        System.out.println("品牌：" + brandNameList);

        int x = 0;
        for (String brandId : brandIdList) {
            String brandName = brandNameList.get(x);

            String brandInitial = brandName.substring(0, 1);
            String initial = PingYinUtil.getPYIndexStr(brandInitial, true);
            CarBrand carBrand = new CarBrand();
            carBrand.setBrandName(brandName);
            carBrand.setInitial(initial);
            carBrand.setCreateTime(nowDate);
            // 品牌id
            CarBrand save = carBrandRepository.save(carBrand);
            Integer id = save.getId();

            List<String> groupName = html.xpath("//*[@id=\"" + brandId + "\"]/dd/div/a/text()").all();
            System.out.println(brandId + "分组名称：" + groupName);

            for (int i = 0; i < groupName.size(); i++) {
                String gn = groupName.get(i);
                List<String> seriesName = html.xpath("//*[@id=\"" + brandId + "\"]/dd/ul[" + (i+1) + "]/li/h4/a/text()").all();
                List<String> serieslink = html.xpath("//*[@id=\"" + brandId + "\"]/dd/ul[" + (i+1) + "]/li/h4/a/@href").replace("//", "https://").all();
                System.out.println("车系名称：" + seriesName);
                System.out.println("车系链接：" + serieslink);
                for (int y = 0; y < seriesName.size(); y++){
                    CarSeries carSeries = new CarSeries();
                    carSeries.setName(seriesName.get(y));
                    carSeries.setUrl(serieslink.get(y));
                    carSeries.setBrandId(id);
                    carSeries.setSeriesGroupName(gn);
                    carSeries.setCreateTime(nowDate);
                    carSeriesRepository.save(carSeries);
                }
            }
            x++;
        }


    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BrandSeriesPull()).setDownloader(new HttpClientDownloader())
                .addUrl("https://www.autohome.com.cn/grade/carhtml/A.html")
                .thread(1)
                .run();
    }
}
