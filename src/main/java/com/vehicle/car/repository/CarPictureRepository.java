package com.vehicle.car.repository;

import com.vehicle.car.pojo.CarModel;
import com.vehicle.car.pojo.CarPicture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarPictureRepository extends CrudRepository<CarPicture, Integer> {

    CarPicture findCarPictureByPictureLinkEquals(String url);

    List<CarPicture> findCarPicturesByModelIdEquals(Integer modelId);

    List<CarPicture> findCarPicturesByPictureUrlIsNull();

}
