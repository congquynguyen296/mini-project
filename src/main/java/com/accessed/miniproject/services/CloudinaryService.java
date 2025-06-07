package com.accessed.miniproject.services;

import com.accessed.miniproject.utils.CloudinaryUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryService {

    Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {

        assert file.getOriginalFilename() != null;
        String publicValue = CloudinaryUtil.generatePublicValue(file.getOriginalFilename());

        String extension = CloudinaryUtil.getFileName(file.getOriginalFilename())[1];
        File fileUpload = CloudinaryUtil.convert(file);

        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));
        CloudinaryUtil.cleanDisk(fileUpload);

        return  cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));
    }
}
