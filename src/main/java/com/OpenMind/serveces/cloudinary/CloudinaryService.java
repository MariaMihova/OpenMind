package com.OpenMind.serveces.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryPicture upload(MultipartFile file) throws IOException;
    boolean delete(String publicId);
}
