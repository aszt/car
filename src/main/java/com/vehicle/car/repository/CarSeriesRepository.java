package com.vehicle.car.repository;

import com.vehicle.car.pojo.CarSeries;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSeriesRepository extends CrudRepository<CarSeries, Integer> {

    CarSeries findCarSeriesByUrlEquals(String url);

    CarSeries findCarSeriesByNameEquals(String name);

    List<CarSeries> findCarSeriesByBrandIdEquals(Integer brandId);

}
