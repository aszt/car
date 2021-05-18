package com.vehicle.car.repository;

import com.vehicle.car.pojo.CarModelParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelParamRepository extends CrudRepository<CarModelParam, Integer> {
}
