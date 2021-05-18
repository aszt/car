package com.vehicle.car.processor;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarModelParam;
import com.vehicle.car.repository.CarModelParamRepository;
import com.vehicle.car.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;

/**
 * 车型详情数据拉取
 */
@Service
public class ModelParamPull implements PageProcessor {

    private Site site = Site.me().setRetryTimes(10).setSleepTime(1000);

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarModelParamRepository carModelParamRepository;

    @Override
    public void process(Page page) {
        Date nowDate = new Date();
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        String level = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[1]/p/text()").get();
        String displacement = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[2]/p/text()").get();
        String maximumPower = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[3]/p/text()").get();
        String maximumTorque = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[4]/p/text()").get();
        String transmissionCase = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[5]/p/text()").get();
        String oilConsumption = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[6]/p/text()").get();
        String environmentalStandards = html.xpath("/html/body/div[1]/div[3]/div[4]/div[1]/div[2]/div[2]/div/div[7]/p/text()").get();

        System.out.println(level);
        System.out.println(displacement);
        System.out.println(maximumPower);
        System.out.println(maximumTorque);
        System.out.println(transmissionCase);
        System.out.println(oilConsumption);
        System.out.println(environmentalStandards);
        CarModel carModel = carModelRepository.findCarModelByTitleUrlEquals(url);
        Integer modelId = carModel.getId();
        CarModelParam carModelParam = new CarModelParam();
        carModelParam.setModelId(modelId);
        carModelParam.setLevel(level);
        carModelParam.setDisplacement(displacement);
        carModelParam.setMaximumPower(maximumPower);
        carModelParam.setMaximumTorque(maximumTorque);
        carModelParam.setTransmissionCase(transmissionCase);
        carModelParam.setOilConsumption(oilConsumption);
        carModelParam.setEnvironmentalStandards(environmentalStandards);
        carModelParam.setCreateTime(nowDate);
        carModelParamRepository.save(carModelParam);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ModelParamPull()).setDownloader(new HttpClientDownloader())
                .addUrl("https://www.autohome.com.cn/spec/27632/#pvareaid=3454492")
                .thread(1)
                .run();
    }
}
