package com.vehicle.car.repository;

import com.vehicle.car.pojo.CarModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepository extends CrudRepository<CarModel, Integer> {

    CarModel findCarModelByTitleUrlEquals(String url);

    CarModel findCarModelByPictureUrlEquals(String url);

    List<CarModel> findCarModelByPictureUrlIsNull();

    CarModel findCarModelBySeriesIdEqualsAndTitleEqualsAndPriceEquals(Integer seriesId, String title, String price);

    CarModel findCarModelByTitleEqualsAndPriceEquals(String title, String price);

    List<CarModel> findCarModelBySeriesIdEqualsAndPriceEquals(Integer seriesId, String price);

    List<CarModel> findCarModelBySeriesIdEquals(Integer seriesId);
}
