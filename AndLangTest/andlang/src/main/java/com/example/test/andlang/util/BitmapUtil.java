/**
 * 
 */
package com.example.test.andlang.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.example.test.andlang.andlangutil.BaseLangApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author will.xu
 * 
 */
public class BitmapUtil {


	public static String compressImage(String filePath, String targetPath, int quality)  {
		Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
		int degree = readPictureDegree(filePath);//获取相片拍摄角度
		if(degree!=0){//旋转照片角度，防止头像横着显示
			bm=rotateBitmap(bm,degree);
		}
		File outputFile=new File(targetPath);
		try {
			if (!outputFile.exists()) {
				outputFile.getParentFile().mkdirs();
				//outputFile.createNewFile();
			}else{
				outputFile.delete();
			}
			FileOutputStream out = new FileOutputStream(outputFile);
			bm.compress(Bitmap.CompressFormat.PNG, quality, out);
		}catch (Exception e){}
		return outputFile.getPath();
	}

	/**
	 * 根据路径获得图片信息并按比例压缩，返回bitmap
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
		BitmapFactory.decodeFile(filePath, options);
		// 计算缩放比
		options.inSampleSize = calculateInSampleSize(options, 300, 400);
		// 完整解析图片返回bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 获取照片角度
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转照片
	 * @param bitmap
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap,int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		 int height = options.outHeight;
		 int width = options.outWidth;
		if(height<width){
			height=options.outWidth;
			width=options.outHeight;
		}
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static void saveBitmapFile(Context context,Bitmap bitmap){
		File file=new File(StorageUtils.getOwnCacheDirectory(
				context.getApplicationContext(), BaseLangApplication.tmpImageDir), System.currentTimeMillis()+".jpg");
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
