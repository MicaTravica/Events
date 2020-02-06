package com.app.events.service;

public interface QRCodeService {

     byte[] generateQRCodeImage(String text) throws Exception;
}
