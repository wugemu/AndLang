package com.example.test.andlang.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

	// 计算图片的缩放值
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 判断照片角度
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

	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
		if (bitmap != null) {
			Matrix m = new Matrix();
			m.postRotate(degress);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
			return bitmap;
		}
		return bitmap;
	}

	public static File getFileimg(Uri uri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null,
				null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor
				.getString(actual_image_column_index);

		File file = new File(img_path);
		return file;
	}

	// 缩放图片
	public static Bitmap zoomImg(String img, int newWidth, int newHeight) {
		// 图片源
		Bitmap bm = BitmapFactory.decodeFile(img);
		if (null != bm) {
			return zoomImg(bm, newWidth);
		}
		return null;
	}

	// 缩放图片
	public static Bitmap zoomImg(Bitmap bm, int newWidth) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;

		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleWidth);
		// 得到新的图片
		Bitmap newb = null;
		try {
			newb = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
					true);
		}catch (OutOfMemoryError e) {
//            while(newb == null) {
			System.gc();
			System.runFinalization();
//                newb = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
//                        true);
//            }
		}
		return newb;
	}
	// 在src的基础上加水印图片
	public static Bitmap addWatermark(Bitmap src, Bitmap watermark) {
		if (src == null || watermark  == null) {
			return src;
		}
		int sWid = src.getWidth();
		int sHei = src.getHeight();
		int wWid = watermark.getWidth();
		int wHei = watermark.getHeight();
		if (sWid == 0 || sHei == 0) {
			return null;
		}

		if (sWid < wWid || sHei < wHei) {
			return src;
		}

		Bitmap bitmap = Bitmap.createBitmap(sWid, sHei, Bitmap.Config.ARGB_8888);
		try {
			Canvas cv = new Canvas(bitmap);
			cv.drawBitmap(src, 0, 0, null);
			cv.drawBitmap(watermark, (sWid - wWid) / 2, (sHei - wHei) / 2, null);
			cv.save(Canvas.ALL_SAVE_FLAG);
			cv.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}

	// 将内存中的图片保存到本地
	public static boolean saveBitmap(Bitmap bitmap, String parentDir, String fileName) {
		File file = new File(parentDir);
		if (!file.exists()) {
			file.mkdir();
		}
		File imageFile = new File(file, fileName);
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// 将内存中的图片保存到本地
	public static boolean saveBitmap(Bitmap bitmap, File imageFile) {
		if(imageFile == null) {
			return false;
		}
		if (imageFile.exists()) {
			imageFile.delete();
		}
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d("0.0", e + "");
			return false;
		} catch (IOException e) {
			Log.d("0.0", e + "");
			return false;
		}
		return true;
	}


	/**
	 * 解决小米手机上获取图片路径为null的情况
	 * @param intent
	 * @return
	 */
	public static Uri getUri(android.content.Intent intent, ContentResolver cr) {
		Uri uri = intent.getData();
		String type = intent.getType();
		if (uri.getScheme().equals("file") && (type.contains("image/"))) {
			String path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
						.append("'" + path + "'").append(")");
				Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						new String[] { MediaStore.Images.ImageColumns._ID },
						buff.toString(), null, null);
				int index = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
					// set _id value
					index = cur.getInt(index);
				}
				if (index == 0) {
					// do nothing
				} else {
					Uri uri_temp = Uri
							.parse("content://media/external/images/media/"
									+ index);
					if (uri_temp != null) {
						uri = uri_temp;
					}
				}
			}
		}
		return uri;
	}

	/**
	 * View转换成图片bitmap
	 */
	public static Bitmap getViewBitmap(View addViewContent) {

		addViewContent.setDrawingCacheEnabled(true);

		addViewContent.measure(
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		addViewContent.layout(0, 0,
				addViewContent.getMeasuredWidth(),
				addViewContent.getMeasuredHeight());

		addViewContent.buildDrawingCache();
		Bitmap cacheBitmap = addViewContent.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		return bitmap;
	}

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
	 * 根据路径获得图片信息并按比例压缩，返回bitmap
	 */
	public static Bitmap getSmallBitmap(String filePath,int wight,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
		BitmapFactory.decodeFile(filePath, options);
		// 计算缩放比
		options.inSampleSize = calculateInSampleSize(options, wight, height);
		// 完整解析图片返回bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	// 将内存中的图片保存到本地
	public static boolean saveBitmap(Bitmap bitmap) {
		File imageFile = new File(PicSelUtil.getImgFileDir(), PicSelUtil.CACHE_FILE_NAME[0]);
		if (imageFile.exists()) {
			imageFile.delete();
		}
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			Log.d("0.0", e + "");
			return false;
		} catch (IOException e) {
			Log.d("0.0", e + "");
			return false;
		}
		return true;
	}
}
