package com.vehicle.car.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "carSeries")
public class CarSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String url;

    private Integer brandId;

    private String seriesGroupName;

    private Date createTime;

    private Date updateTime;
}
