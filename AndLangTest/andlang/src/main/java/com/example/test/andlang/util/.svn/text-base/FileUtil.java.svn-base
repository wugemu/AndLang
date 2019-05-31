package com.example.test.andlang.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Bill56 on 2018-1-31.
 */

public class FileUtil {

    /**
     * 根据文件转换成对应的Uri
     *
     * @param ctx
     * @param file
     * @return
     */
    public static Uri file2Uri(Context ctx, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(ctx, ctx.getApplicationInfo().processName + ".fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static File getFileFromUri(Context ctx,Uri uri) throws Exception{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri));
                if (bitmap != null && ImageUtil.saveBitmap(bitmap)) {
                    return new File(PicSelUtil.getImgFileDir(), PicSelUtil.CACHE_FILE_NAME[0]);
                } else {
                    return null;
                }
            }catch (Exception e){
                //内存溢出 缩小图片为原来的1/4
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, options);
                if (bitmap != null && ImageUtil.saveBitmap(bitmap)) {
                    return new File(PicSelUtil.getImgFileDir(), PicSelUtil.CACHE_FILE_NAME[0]);
                } else {
                    return null;
                }
            }
        } else {
            return new File(uri.getPath());
        }
    }

    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    public static String saveHttpImageToLocal(Context context,Bitmap bitmap,String fileName) {
        if(bitmap==null){
            return null;
        }
        File file = PicSelUtil.getImageFile(fileName);
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.EMPTY.fromFile(file)));
            return file.getAbsolutePath();
        }
        return null;
    }

    public static File getWeexFile(String fileName){
        File file = PicSelUtil.getImageFile(fileName);
        return file;
    }

    public static String getFilePath(String fileName){
        // 如果不存在目录，则创建
        if (!PicSelUtil.getImgFileDir().exists()) {
            PicSelUtil.getImgFileDir().mkdir();
        }
        File outputImage = new File(PicSelUtil.getImgFileDir(), fileName);
        if (outputImage.exists()) {
            return "file://"+outputImage.getAbsolutePath();
        }
        return "";
    }

    public static String saveHttpImage(Context context, Bitmap bitmap,String fileName,String root) {
        File file = null;
        try {
            file = createStableImageFile(context, fileName,root);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static File createStableImageFile(Context context, String IMAGE_NAME,String root) throws IOException {
        String imageFileName = IMAGE_NAME + ".jpg";
        File image = new File(StorageUtils.getOwnCacheDirectory(
                context.getApplicationContext(),root), imageFileName);
        return image;
    }


    /**
     * 根据文件路径拷贝文件
     * @param src 源文件
     * @return boolean 成功true、失败false
     */
    public static File copyFile(File src) {
        File copyFile=null;
        if ((src == null)) {
            return null;
        }
        // 如果不存在目录，则创建
        if (!PicSelUtil.getImgFileDir().exists()) {
            PicSelUtil.getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        copyFile = new File(PicSelUtil.getImgFileDir(), PicSelUtil.CACHE_FILE_NAME[0]);
        if (copyFile!= null && copyFile.exists()) {
            copyFile.delete(); // delete file
        }
        try {
            copyFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(copyFile).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyFile;
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
