package com.competitionapp.nrgscouting;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by valli on 10/12/17.
 */

public class QRCodeGenerator {
    public static Bitmap qrCodeMapFor(String content) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode("text2Qr", BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);

        } catch (WriterException we) {

        }
        return null;
    }
}
