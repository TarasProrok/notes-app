package com.ratatui.notes.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Data
@Service
@RequiredArgsConstructor
public class QRService {
    private static final int QR_WIDTH = 320;
    private static final int QR_HEIGHT = 320;

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
