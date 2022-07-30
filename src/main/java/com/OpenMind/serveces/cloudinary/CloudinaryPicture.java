package com.OpenMind.serveces.cloudinary;

public class CloudinaryPicture {

    private String url;
    private String publicId;

    public CloudinaryPicture(){}

    public CloudinaryPicture(String publicId, String url) {
        this.publicId = publicId;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
