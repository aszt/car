package com.vehicle.car.service.impl;

import com.vehicle.car.pojo.CarBrand;
import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.pojo.CarSeries;
import com.vehicle.car.repository.CarBrandRepository;
import com.vehicle.car.repository.CarModelRepository;
import com.vehicle.car.repository.CarPictureRepository;
import com.vehicle.car.repository.CarSeriesRepository;
import com.vehicle.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarSeriesRepository carSeriesRepository;

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarPictureRepository carPictureRepository;

    @Override
    public List<CarBrand> queryBrand() {
        List<CarBrand> carBrands = (List<CarBrand>) carBrandRepository.findAll();
        return carBrands;
    }

    @Override
    public List<CarSeries> querySeries(Integer brandId) {
        List<CarSeries> carSeriesList = carSeriesRepository.findCarSeriesByBrandIdEquals(brandId);
        return carSeriesList;
    }

    @Override
    public List<CarModel> queryModel(Integer seriesId) {
        List<CarModel> carModelList = carModelRepository.findCarModelBySeriesIdEquals(seriesId);
        return carModelList;
    }

    @Override
    public List<CarPicture> queryPicture(Integer modelId) {
        List<CarPicture> carPictureList = carPictureRepository.findCarPicturesByModelIdEquals(modelId);
        return carPictureList;
    }
}
