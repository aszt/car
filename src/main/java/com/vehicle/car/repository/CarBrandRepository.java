package com.vehicle.car.repository;

import com.vehicle.car.pojo.CarBrand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarBrandRepository extends CrudRepository<CarBrand, Integer> {

    CarBrand findCarBrandByBrandNameEquals(String brandName);

}
