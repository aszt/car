package com.vehicle.car.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "carModel")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer seriesId;

    private String title;

    private String titleUrl;

    private String pictureUrl;

    private String price;

    private Date createTime;

    private Date updateTime;
}
