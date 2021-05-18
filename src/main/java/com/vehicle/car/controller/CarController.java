package com.vehicle.car.controller;

import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.pojo.CarSeries;
import com.vehicle.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * 获取品牌
     *
     * @return
     */
    @GetMapping("/brand")
    public Map<String, Object> getBrand() {
        Map<String, Object> map = new HashMap<>();
        List<CarBrand> carBrands = carService.queryBrand();
        map.put("code", 10000);
        map.put("msg", "成功");
        map.put("result", carBrands);
        return map;
    }

    /**
     * 获取车系
     * @param brandId
     * @return
     */
    @GetMapping("/series/{brandId}")
    public Map<String, Object> getSeries(@PathVariable Integer brandId) {
        Map<String, Object> map = new HashMap<>();
        List<CarSeries> carSeriesList = carService.querySeries(brandId);
        map.put("code", 10000);
        map.put("msg", "成功");
        map.put("result", carSeriesList);
        return map;
    }

    /**
     * 获取车型
     * @param seriesId
     * @return
     */
    @GetMapping("/model/{seriesId}")
    public Map<String, Object> getModel(@PathVariable Integer seriesId) {
        Map<String, Object> map = new HashMap<>();
        List<CarModel> carModelList = carService.queryModel(seriesId);
        map.put("code", 10000);
        map.put("msg", "成功");
        map.put("result", carModelList);
        return map;
    }

    /**
     * 获取图片
     * @param modelId
     * @return
     */
    @GetMapping("/picture/{modelId}")
    public Map<String, Object> getPicture(@PathVariable Integer modelId) {
        Map<String, Object> map = new HashMap<>();
        List<CarPicture> carPictureList = carService.queryPicture(modelId);
        map.put("code", 10000);
        map.put("msg", "成功");
        map.put("result", carPictureList);
        return map;
    }
}
