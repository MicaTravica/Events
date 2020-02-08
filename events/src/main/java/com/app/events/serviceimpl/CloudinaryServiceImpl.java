package com.app.events.serviceimpl;

import com.app.events.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(String path) throws Exception {
        Map result = cloudinary.uploader().upload(new File(path), ObjectUtils.emptyMap());
        String imageUrl = (String) result.get("url");
        return imageUrl;
    }

    public String uploadImage(byte[] image) throws Exception {
        Map options = new ObjectUtils().asMap("resource_type", "image");
        Map result = cloudinary.uploader().upload(image, options);
        String imageUrl = (String) result.get("url");
        return imageUrl;
    }

}
