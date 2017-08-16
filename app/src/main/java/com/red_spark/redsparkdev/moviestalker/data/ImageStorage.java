package com.red_spark.redsparkdev.moviestalker.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import com.red_spark.redsparkdev.moviestalker.LogHelp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Red_Spark on 16-Aug-17.
 * Handle the storing and getting of images from the EXTERNAL_STORAGE
 */

public class ImageStorage {
    private static final String TAG = ImageStorage.class.getSimpleName();
    private static final String thumbnailFolder = ".thumbnail";
    private static final String backdropFolder = ".backdrop";
    private static final String tempFolder = ".tempImages";

    private static final String STORAGE_DIR = Environment.getExternalStorageDirectory().toString();

    public enum ImageType {
        THUMBNAIL(thumbnailFolder),
        BACKDROP(backdropFolder),
        TEMP(tempFolder);
        String folder;
        ImageType(String folder){
            this.folder = folder;
        }
        @Override
        public String toString() {
            return folder;
        }
    }
    private static String getDir(ImageType imageType){

        File storageDir = new File(STORAGE_DIR, imageType.toString());
        if(!storageDir.exists()){
            if(!storageDir.mkdir()){
                LogHelp.print(TAG, storageDir.toString());
                Log.d(TAG, ":Failed to Created image folder");
                return null;
            }
        }
        return storageDir.toString();
    }
    public static Bitmap convertToBitmap(Drawable image){
        Bitmap bitmap = null;

        if (image instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) image;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(image.getIntrinsicWidth() <= 0 || image.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap
            // will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(image.getIntrinsicWidth(), image.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        image.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        image.draw(canvas);
        return bitmap;

    }
    public static void saveImage(ImageType imageType, Drawable image, String id) {
        Bitmap bitImage = convertToBitmap(image);
        if (bitImage == null) {
            Log.d(TAG, ":Failed to convert image");
        }
        File file = new File(getDir(imageType), id + ".png");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static File getImage(ImageType imageType, String id){
        File file = new File(getDir(imageType), id+".png");
        if(file.exists()){
            return file;
        }
        Log.v(TAG, ":File not found");
        return null;
    }
    public static String getImagePath(ImageType imageType, String id){

        LogHelp.print(TAG, getDir(imageType)+"/"+ id+".png");
        return getDir(imageType)+"/"+ id+".png";
    }
    public static void deleteImage(ImageType imageType, String id) {
        File file = new File(getDir(imageType), id+".png");
        if(file.exists()){
            file.delete();
        }
    }
}
