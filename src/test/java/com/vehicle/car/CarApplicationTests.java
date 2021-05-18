package com.vehicle.car;

import com.vehicle.car.downloader.HttpClientDownloader;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.processor.*;
import com.vehicle.car.repository.CarModelRepository;
import com.vehicle.car.repository.CarPictureRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Spider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据拉取测试类
 */
@SpringBootTest
class CarApplicationTests {

    @Autowired
    private BrandSeriesPull brandSeriesPull;

    @Autowired
    private ModelPull modelPull;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private PicturePull picturePull;

    @Autowired
    private CarPictureRepository carPictureRepository;

    @Autowired
    private IconPull iconPull;

    @Autowired
    private ModelParamPull modelParamPull;

    /**
     * 获取所有品牌车系
     */
    @Test
    void getBrandSeries() {
        List<String> urlList = new ArrayList<String>();
        for (char c = 97; c <= 122; c++) {
            String bz = String.valueOf(c).toUpperCase();
            String url = "https://www.autohome.com.cn/grade/carhtml/" + bz + ".html";
            urlList.add(url);
        }
        String[] urlLists = urlList.toArray(new String[urlList.size()]);
        Spider.create(brandSeriesPull).setDownloader(new HttpClientDownloader())
//                .addUrl("https://www.autohome.com.cn/grade/carhtml/A.html")
                .addUrl(urlLists)
                .thread(1)
                .run();
    }

    /**
     * 获取所有车型
     */
    @Test
    void getModel() {
        /*List<CarSeries> carSeriesList = (List<CarSeries>) carSeriesRepository.findAll();
        List<String> collect = carSeriesList.stream().map(carSeries -> carSeries.getUrl()).collect(Collectors.toList());
        String[] urlList = collect.toArray(new String[collect.size()]);
        Spider.create(modelPull).setDownloader(new HttpClientDownloader())
                .addUrl(urlList)
                .thread(30)
                .run();*/

        // 信息补录
        List<CarModel> carModelList = carModelRepository.findCarModelByPictureUrlIsNull();
        List<String> collect = carModelList.stream().map(carModel -> carModel.getTitleUrl()).collect(Collectors.toList());
        String[] urlList = collect.toArray(new String[collect.size()]);
        Spider.create(modelPull).setDownloader(new HttpClientDownloader())
                .addUrl(urlList)
                .thread(1)
                .run();
    }

    /**
     * 获取所有图片
     */
    @Test
    void getPicture() {
        /*List<CarModel> carModelList = (List<CarModel>) carModelRepository.findAll();
        List<String> collect = carModelList.stream().map(carModel -> carModel.getPictureUrl()).collect(Collectors.toList());
        String[] urlList = collect.toArray(new String[collect.size()]);
        Spider.create(picturePull).setDownloader(new HttpClientDownloader())
                .addUrl(urlList)
                .thread(40)
                .run();*/

        // 信息补录
        List<CarPicture> carPictures = carPictureRepository.findCarPicturesByPictureUrlIsNull();
        List<String> collect = carPictures.stream().map(carPicture -> carPicture.getPictureLink()).collect(Collectors.toList());
        String[] urlList = collect.toArray(new String[collect.size()]);
        Spider.create(picturePull).setDownloader(new HttpClientDownloader())
                .addUrl(urlList)
                .thread(1)
                .run();
    }

    // 补充图标
    @Test
    void getIcon(){
        List<String> urlList = new ArrayList<String>();
        for (char c = 97; c <= 122; c++) {
            String bz = String.valueOf(c).toUpperCase();
            String url = "https://www.autohome.com.cn/grade/carhtml/" + bz + ".html";
            urlList.add(url);
        }
        String[] urlLists = urlList.toArray(new String[urlList.size()]);
        Spider.create(iconPull).setDownloader(new HttpClientDownloader())
                .addUrl(urlLists)
                .thread(1)
                .run();
    }

    // 获取车型信息
    @Test
    void getModelParam() {
        List<CarModel> carModelList = (List<CarModel>) carModelRepository.findAll();
        System.out.println(carModelList.size());
        List<String> collect = carModelList.stream().map(carModel -> carModel.getTitleUrl()).collect(Collectors.toList());
        String[] urlList = collect.toArray(new String[collect.size()]);
        Spider.create(modelParamPull).setDownloader(new HttpClientDownloader())
                .addUrl(urlList)
                .thread(20)
                .run();
    }
}
