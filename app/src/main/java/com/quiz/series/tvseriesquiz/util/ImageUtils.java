package com.quiz.series.tvseriesquiz.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Some utility methods related with the Image class.
 *
 */
public class ImageUtils {
    
   public static String encodeTobase64(Bitmap image) {
       Bitmap immagex=image;
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
       byte[] b = baos.toByteArray();
       String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

       return imageEncoded;
   }

   public static Bitmap decodeBase64(String input) {
       byte[] decodedByte = Base64.decode(input, 0);
       return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
   }
}