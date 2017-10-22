package com.competitionapp.nrgscouting;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Created by valli on 10/12/17. Approved by Acchin 10/22/17.
 */

public class QRCodeGenerator {
    public static Bitmap qrCodeMapFor(String content) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(matrix);

        } catch (WriterException we) {

        }
        return null;
    }
}
