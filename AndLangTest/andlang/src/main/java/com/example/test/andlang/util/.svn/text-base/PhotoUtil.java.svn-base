package com.example.test.andlang.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.channels.FileChannel;

/**
 * Created By lhp
 * on 2019/3/21
 */
public class PhotoUtil {
    public  static final int REQUESTCODE_TAKE=1000;//拍照
    public static final int REQUESTCODE_PICK=1001;//本地
    public static final int REQUESTCODE_PICK_CROP=2001;//取本地照片，需要裁剪
    public static final int REQUESTCODE_TAKE_CROP=2000;//拍照,需要裁剪
    private static final int CROP_PHOTO = 1003;// 剪裁
    private static File imageDir=null;
    private static String tmpImageDir = "sdplay";
    // 权限检查相关
    private static final int TAKE_PHOTO_CODE = 100; // 请求码
    private static final int SELECT_PHOTO_CODE = 200; // 请求码
    // 所需的全部权限——摄像
    private static final String[] TAKE_PHOTO_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String[] WRITE_EXTERNAL_STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private PhotoCallback photoCallback;
    private WeakReference<Activity> mContext;
    // 图片格式的URI对象
    private Uri imageUri;
    private String tmpPath;
    // 临时保存头像的缓存文件
    private File imageCache;
    //是否需要裁剪
    private boolean needCrop;
    private Intent data;
    private PhotoUtil(){
    }

    public static PhotoUtil getInstance(){
        return Singleton.INSTANCE.getInstance();
    }
    private enum Singleton{
        INSTANCE;
        private PhotoUtil singleton;
        //JVM会保证此方法绝对只调用一次
         Singleton(){
            singleton = new PhotoUtil();
        }
        public PhotoUtil getInstance(){
            return singleton;
        }
    }


    public interface PhotoCallback{
        void ReceiveFile(File file);

    }

    public void execute(Activity activity, int requestCode, PhotoCallback callback){
        switch (requestCode){
            case REQUESTCODE_TAKE:
                takePhoto(activity,false,callback);
                break;
            case REQUESTCODE_PICK:
                selectLocalPhoto(activity,false,callback);
                break;
            case REQUESTCODE_TAKE_CROP:
                takePhoto(activity,true,callback);
                break;
            case REQUESTCODE_PICK_CROP:
                selectLocalPhoto(activity,true,callback);
                break;

        }
    }


    private void takePhoto(Activity activity, boolean needCrop, PhotoCallback photoCallback){
        mContext=new WeakReference<>(activity);
        this.needCrop=needCrop;
        this.photoCallback=photoCallback;
        // 系统相机
        // 缺少权限时, 进入权限配置页面
        if (lacksPermissions(mContext.get(), TAKE_PHOTO_PERMISSIONS)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(mContext.get(), TAKE_PHOTO_PERMISSIONS,
                    TAKE_PHOTO_CODE);
        } else {
            startCemara();
        }
    }

    private void selectLocalPhoto(Activity activity, boolean needCrop, PhotoCallback photoCallback){
        mContext=new WeakReference<>(activity);
        this.needCrop=needCrop;
        this.photoCallback=photoCallback;
        // 缺少权限时, 进入权限配置页面
        if (lacksPermissions(activity, WRITE_EXTERNAL_STORAGE_PERMISSIONS)) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, WRITE_EXTERNAL_STORAGE_PERMISSIONS,
                    SELECT_PHOTO_CODE);
        } else {
            selectPhoto();
        }
    }

    private void selectPhoto(){
        // 本地图册 消除No Activity found to handle Intent 错误
        try {
            Intent intent = new Intent(
                    Intent.ACTION_PICK, null);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");

            if (needCrop) {
                mContext.get().startActivityForResult(intent, REQUESTCODE_PICK_CROP);
            } else {
                mContext.get().startActivityForResult(intent, REQUESTCODE_PICK);
            }
        }catch (Exception e){

        }
    }

    private void startCemara() {
        // 系统相机
        try {
            Intent intent2 = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tmpPath = System.currentTimeMillis() + ".jpg";
            imageUri = getImageCacheUri(mContext.get(), tmpPath);
            intent2.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    imageUri);
            if (needCrop) {
                mContext.get().startActivityForResult(intent2, REQUESTCODE_TAKE_CROP);
            } else {
                mContext.get().startActivityForResult(intent2, REQUESTCODE_TAKE);
            }
        }catch (Exception e){

        }
    }

    private void crop(){

        // 调用剪裁程序活动
       Intent intent = new Intent("com.android.camera.action.CROP");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(imageUri, "image/*");
//                    intent.putExtra("scale", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mContext.get().startActivityForResult(intent, CROP_PHOTO);
    }

    public void onActivityResult(int requestCode, Intent data){
        this.data=data;
        Message message=handler.obtainMessage();
        message.what=requestCode;
        handler.sendMessage(message);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUESTCODE_PICK_CROP://直接从相册获取,需要裁剪
                    // 相册
                    try {
                        Uri uri = getUri(data, mContext.get().getContentResolver());//解决小米手机上获取图片路径为null的情况\
                        if(uri != null) {
                            // 取得SDCard图片路径做显示
                            String path = getFilePathFromContentUri(uri, mContext.get().getContentResolver());
                            File imgSrc = new File(path);
                            tmpPath= System.currentTimeMillis()+".jpg";
                            File copyimg= copyFile(imgSrc,tmpPath);//复制原图
                            if(copyimg==null){
                                return;
                            }
                            // 获得uri
                            imageUri =file2Uri(mContext.get(), copyimg);
                            // 打开剪裁程序
                            crop();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();// 用户点击取消操作，其他错误
                    }

                    break;
                case REQUESTCODE_PICK:// 直接从相册获取
                    // 相册
                    try {
                        Uri uri = getUri(data, mContext.get().getContentResolver());//解决小米手机上获取图片路径为null的情况\
                        if (uri != null) {
                            // 取得SDCard图片路径做显示
                            String path = getFilePathFromContentUri(uri, mContext.get().getContentResolver());
                            imageCache = new File(path);
//                            // 获得uri
                            if (imageCache != null&&photoCallback!=null) {
                                photoCallback.ReceiveFile(imageCache);
                            }else {
                                ToastUtil.show(mContext.get(),"操作失败");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();// 用户点击取消操作，其他错误
                    }

                break;
                case REQUESTCODE_TAKE_CROP:
                    // 相机,需要裁剪
                    crop();
                    break;
                case REQUESTCODE_TAKE:
                    // 相机
                    try {
                        imageCache = getFileFromUri(mContext.get(),imageUri,tmpPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (imageCache != null&&photoCallback!=null) {
                        photoCallback.ReceiveFile(imageCache);
                    }else {
                        ToastUtil.show(mContext.get(),"操作失败");
                    }
                    break;
                case CROP_PHOTO:
                    // 剪裁程序返回
                    // 当剪裁程序返回的结果是正确的时候
                    // 获取图片
                    try {
                        imageCache = getFileFromUri(mContext.get(),imageUri,tmpPath);
                        if (imageCache != null&&photoCallback!=null) {
                            photoCallback.ReceiveFile(imageCache);
                        }else {
                            ToastUtil.show(mContext.get(),"操作失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };


    //根据requestCode和grantResults(授权结果)做相应的后续处理
    public void doNext(int requestCode, int[] grantResults) {

        if (requestCode == SELECT_PHOTO_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 开启读写权限
                selectPhoto();
            } else {
                ToastUtil.show(mContext.get(), "请开启SD卡读写权限");
            }
        }
        if (requestCode == TAKE_PHOTO_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // 开启拍照
                startCemara();
            } else {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.show(mContext.get(), "请开启拍照权限");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.show(mContext.get(), "请开启SD卡读写权限");
                } else {
                    ToastUtil.show(mContext.get(), "请开启拍照和SD卡读写权限");
                }
            }
        }

    }

    // 判断权限集合
    private  boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    private Uri getImageCacheUri(Context context, String fileName) {
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        File outputImage = new File(getImgFileDir(), fileName);
        try {
            // 如果存在则删除
            if (outputImage.exists()) {
                outputImage.delete();
            }
            // 创建一个新的文件
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file2Uri(context,outputImage);
    }


    //读写本地外置sd卡
    public static File getImgFileDir()
    {
        if(imageDir==null)
        {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)){
                imageDir = new File(Environment.getExternalStorageDirectory(), tmpImageDir);
            }else {
                imageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), tmpImageDir);
            }
        }
        return imageDir;
    }


    /**
     * 根据文件转换成对应的Uri
     *
     * @param ctx
     * @param file
     * @return
     */
    private Uri file2Uri(Context ctx, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(ctx, ctx.getApplicationInfo().processName + ".fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 从拍照得到uri中获取文件对象
     * @param ctx
     * @param uri
     * @param name
     * @return
     * @throws Exception
     */
    private File getFileFromUri(Context ctx, Uri uri, String name) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri));
                if (bitmap != null &&saveBitmap(bitmap,name)) {
                    return new File(getImgFileDir(),name);
                } else {
                    return null;
                }
            }catch (Exception e){
                //内存溢出 缩小图片为原来的1/4
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, options);
                if (bitmap != null && saveBitmap(bitmap,name)) {
                    return new File(getImgFileDir(), name);
                } else {
                    return null;
                }
            }
        } else {
            return new File(uri.getPath());
        }
    }

    /**
     * 保存bitmap到本地
     * @param bitmap
     * @param name
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String name) {
        File imageFile = new File(getImgFileDir(), name);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
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
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    private String getFilePathFromContentUri(Uri selectedVideoUri,
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


    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    private Uri getUri(Intent intent, ContentResolver cr) {
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
     * 根据文件路径拷贝文件
     * @param src 源文件
     * @return boolean 成功true、失败false
     */
    private File copyFile(File src, String name) {
        File copyFile=null;
        if ((src == null)) {
            return null;
        }
        // 如果不存在目录，则创建
        if (!getImgFileDir().exists()) {
            getImgFileDir().mkdir();
        }
        // 创建File对象，用于存储牌照后的图片
        copyFile = new File(getImgFileDir(), name);
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

    public static File getVideoFrame(String path, long time) {
        try {
            long timeUs = time * 1000;
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            Bitmap bitmap= mmr.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST);
            if(bitmap!=null&&saveBitmap(bitmap,"videoImg.jpg")){
                return new File(getImgFileDir(),"videoImg.jpg");
            }
        }catch (Exception e){

        }
        return null;
    }

}
