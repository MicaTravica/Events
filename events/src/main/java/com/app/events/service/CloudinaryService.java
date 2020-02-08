package com.app.events.service;

public interface CloudinaryService {

    String uploadImage(String path) throws Exception;

    String uploadImage(byte[] image) throws Exception;
}
