package com.vehicle.car.service;

import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.pojo.CarSeries;

import java.util.List;

public interface CarService {

    List<CarBrand> queryBrand();

    List<CarSeries> querySeries(Integer brandId);

    List<CarModel> queryModel(Integer seriesId);

    List<CarPicture> queryPicture(Integer modelId);
}
