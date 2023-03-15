package com.reggie.reggietakeoutapi.controller;

import com.reggie.reggietakeoutapi.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.imagePath}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        // 文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")); // 后缀名
        // 生成新文件名，放置重名覆盖
        String fileName = UUID.randomUUID() + suffix;

        File dir = new File(basePath);
        if(!dir.exists()){
            // 文件不存在，创建
            dir.mkdir();
        }

        try{
            // 转存到指定路径
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(fileName);
    }

    /**
     * 文件下载，返回为jpg图片类型
     * @param fileName
     */
    @GetMapping(value = "/download",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] download(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes,0, inputStream.available());
        return bytes;
    }
}
