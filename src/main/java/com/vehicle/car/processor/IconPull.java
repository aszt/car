package com.vehicle.car.processor;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.repository.CarBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 品牌小图标拉取
 */
@Service
public class IconPull implements PageProcessor {

    @Autowired
    private CarBrandRepository carBrandRepository;

    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> iconPullList = html.xpath("//dl/dt/a/img/@src").replace("//", "https://").all();
        List<String> brandNameList = html.xpath("//dl/dt/div/a/text()").all();
        for (int i = 0; i < brandNameList.size(); i++) {
            String brandName = brandNameList.get(i);
            CarBrand carBrand = carBrandRepository.findCarBrandByBrandNameEquals(brandName);
            carBrand.setBrandPictureUrl(iconPullList.get(i));
            carBrandRepository.save(carBrand);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new IconPull()).setDownloader(new HttpClientDownloader())
                .addUrl("https://www.autohome.com.cn/grade/carhtml/A.html")
                .thread(1)
                .run();
    }
}
