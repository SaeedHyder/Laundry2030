package com.ingic.mylaundry.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InputHelper {

    public static File persistImage(Bitmap bitmap, String name, Context context) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Error", "Error writing bitmap", e);
        }

        return imageFile;
    }

    public static File persistVideo(Bitmap newBitmap, String name, Context context) throws IOException {
        File filesDir = context.getFilesDir();
        File videoFile = new File(filesDir, name + ".mp4");

        OutputStream os;
        try {
            os = new FileOutputStream(videoFile);
            newBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("Error", "Error writing bitmap", e);
        }

        return videoFile;
    }
}
