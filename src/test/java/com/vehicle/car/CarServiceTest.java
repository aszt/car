package com.vehicle.car;

import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.pojo.CarSeries;
import com.vehicle.car.service.CarService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 数据查询测试类
 */
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    /**
     * 查询品牌
     */
    @Test
    void queryBrand() {
        List<CarBrand> carBrands = carService.queryBrand();
        System.out.println("查询条数：" + carBrands.size());
        for (CarBrand carBrand : carBrands) {
            System.out.println(ReflectionToStringBuilder.toString(carBrand, ToStringStyle.MULTI_LINE_STYLE));
        }
    }

    /**
     * 查询车系
     */
    @Test
    void querySeries() {
        Integer brandId = 1;
        List<CarSeries> carSeriesList = carService.querySeries(brandId);
        System.out.println("查询条数：" + carSeriesList.size());
        for (CarSeries carSeries : carSeriesList) {
            System.out.println(ReflectionToStringBuilder.toString(carSeries, ToStringStyle.MULTI_LINE_STYLE));
        }
    }

    /**
     * 查询车型
     */
    @Test
    void queryModel() {
        Integer seriesId = 1;
        List<CarModel> carModelList = carService.queryModel(seriesId);
        System.out.println("查询条数：" + carModelList.size());
        for (CarModel carModel : carModelList) {
            System.out.println(ReflectionToStringBuilder.toString(carModel, ToStringStyle.MULTI_LINE_STYLE));
        }
    }

    @Test
    void queryPicture() {
        Integer modelId = 34;
        List<CarPicture> carPictureList = carService.queryPicture(modelId);
        System.out.println("查询条数：" + carPictureList.size());
        for (CarPicture carPicture : carPictureList) {
            System.out.println(ReflectionToStringBuilder.toString(carPicture, ToStringStyle.MULTI_LINE_STYLE));
        }

    }
}
