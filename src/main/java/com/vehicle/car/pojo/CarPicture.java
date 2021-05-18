package com.vehicle.car.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "carPicture")
public class CarPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer modelId;

    private String title;

    private String smallPictureUrl;

    private String pictureLink;

    private String pictureUrl;

    private String oosUrl;

    private Integer pictureLocation;

    private Date createTime;

    private Date updateTime;
}
