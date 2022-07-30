package com.OpenMind.serveces.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private final String URL= "url";
    private final String PUBLIC_ID= "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CloudinaryPicture upload(MultipartFile file) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, file.getOriginalFilename());
        file.transferTo(tempFile);

        try{
           Map<String, String> result = cloudinary.uploader().upload(tempFile, Map.of());

           String url = result.getOrDefault(URL, "static/images/icon.png");
           String publicId = result.getOrDefault(PUBLIC_ID, "");

           return new CloudinaryPicture(publicId, url);

        }finally {
            tempFile.delete();
        }
    }

    @Override
    public boolean delete(String publicId) {

        try {
            this.cloudinary
                    .uploader()
                    .destroy(publicId, Map.of());
        } catch (IOException e) {
           return false; // not deleted
        }
        return true; //deleted
    }
}
