package com.leyou.upload.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    private static final List<String> CONTENT_TYPE= Arrays.asList("image/gif","image/jpeg");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;
    public String uploadImage(MultipartFile file) {

            //校验文件类型
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
        try {
            if(!CONTENT_TYPE.contains(contentType)){
                LOGGER.info("文件类型不合法{}",originalFilename);
                return null;
            }
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage==null){
                LOGGER.info("文件内容不合法{}",originalFilename);
            }
           // file.transferTo(new File("E:\\test1\\image"+originalFilename));
            //return "http://image.leyou.com/"+originalFilename;
            String extend = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), extend,null);
            return "http://image.leyou.com/" +storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部异常{}",originalFilename);
            e.printStackTrace();
        }return null;


    }
            }
