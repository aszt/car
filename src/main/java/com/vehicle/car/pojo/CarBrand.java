package com.vehicle.car.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "carBrand")
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String initial;

    private String brandName;

    private String brandPictureUrl;

    private String brandPictureOosUrl;

    private Date createTime;

    private Date updateTime;

}
