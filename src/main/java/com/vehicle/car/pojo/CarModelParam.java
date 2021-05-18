package com.vehicle.car.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "carModelParam")
public class CarModelParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer modelId;

    private String level;

    private String displacement;

    private String maximumPower;

    private String maximumTorque;

    private String transmissionCase;

    private String oilConsumption;

    private String EnvironmentalStandards;

    private Date createTime;

    private Date updateTime;
}
