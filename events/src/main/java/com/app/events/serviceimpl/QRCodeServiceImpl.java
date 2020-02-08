package com.app.events.serviceimpl;


import com.app.events.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Override
    public byte[] generateQRCodeImage(String text) throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 150, 150);

        Path path = FileSystems.getDefault().getPath("src/main/resources/QRCode.jpg");
        MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "JPG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
}
