package com.vehicle.car;

import com.vehicle.car.pojo.CarPicture;
import com.vehicle.car.repository.CarPictureRepository;
import com.vehicle.car.utils.PictureDownloadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

/**
 * 车辆图片保存至本地（用于图片编辑）
 */
@SpringBootTest
public class CarPictureSaveLocalTest {

    @Autowired
    private CarPictureRepository carPictureRepository;

    @Test
    void saveLocal() throws Exception {
        // 查询所有图片
        List<CarPicture> carPictureList = (List<CarPicture>) carPictureRepository.findAll();
        for (CarPicture carPicture : carPictureList) {
            String requestUrl = carPicture.getPictureUrl();
            String id = carPicture.getId().toString();

            // 下载至本地
            String basePath = "D://CarPicture/" + id;
            PictureDownloadUtil.judeDirExists(basePath);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = uuid + ".jpg";
            String wirtePath = basePath + "/" + fileName;
            PictureDownloadUtil.download(requestUrl, wirtePath);
        }
    }

}
