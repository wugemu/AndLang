package com.example.test.andlang.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.test.andlang.andlangutil.BaseLangPresenter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 1 on 2016/1/18.
 */
public class HttpU {
    public static final String COOKIE = "Cookie";

    private static HttpU mInstance;
    private final Handler handler;
    private OkHttpClient mOkHttpClient;
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    private HttpU() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder().dispatcher(new Dispatcher(ExecutorServiceUtil.getInstence().getExecutorService())).readTimeout(30, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)// 设置写的超时时间
                .connectTimeout(30, TimeUnit.SECONDS);// 设置连接超时时间  ;
        handler = new Handler(Looper.getMainLooper());
    }



    public static HttpU getInstance() {
        if (mInstance == null) {
            synchronized (HttpU.class) {
                if (mInstance == null) {
                    mInstance = new HttpU();
                }
            }
        }
        return mInstance;
    }


    /**
     * 网络请求方法
     *
     * @param context
     * @param url
     * @param params
     * @param tag
     * @param callback
     */
    public void post(final Context context, final String url, Map<String, Object> params, Object tag, final HttpCallback callback) {
        if (context==null){
            callback.onAfter();
            return;
        }
        String reqmsg = "";
        //token校验参数
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry != null) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                            formBuilder.add(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }

        }
        FormBody formBody = formBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();
        final Request request = requestBuilder.url(url).post(formBody).tag(tag).build();

        Log.d(BaseLangPresenter.TAG,"请求报文Host：" + url);
        Log.d(BaseLangPresenter.TAG,"请求报文body：" + params);

        callback.onBefore(request);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(request, e, context);
                        Log.i("nyso_bbc","onError"+e);
                        callback.onAfter();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    List<String> cookies = response.headers("Set-Cookie");
                    for (String str : cookies) {
                        Log.d(BaseLangPresenter.TAG,"返回报文cookie：" + str);
                    }
                    Log.d(BaseLangPresenter.TAG,"返回报文Host：" + url);
                    Log.d(BaseLangPresenter.TAG,"返回报文body：" + result);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(result);
                            callback.onAfter();
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(request, e, context);
                            callback.onAfter();
                        }
                    });
                }
            }
        });
    }

    /**
     * 网络请求方法
     *
     * @param context
     * @param url
     * @param params
     * @param tag
     * @param callback
     */
    public void postArray(final Context context, final String url, Map<String, Object> params, Object tag, final HttpCallback callback) {
        if (context==null){
            callback.onAfter();
            return;
        }
        String reqmsg = "";
        //token校验参数
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry != null) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        if(entry.getValue().toString().startsWith("[")&&entry.getValue().toString().endsWith("]")){
                            String value=entry.getValue().toString().substring(1,entry.getValue().toString().length()-1);
                            String[] values=value.split(",");
                            for (int i=0;i<values.length;i++){
                                formBuilder.add(entry.getKey().toString(), values[i]);
                            }
                        }else {
                            formBuilder.add(entry.getKey().toString(), entry.getValue().toString());
                        }

                    }
                }
            }

        }
        FormBody formBody = formBuilder.build();
        Request.Builder requestBuilder = new Request.Builder();
        final Request request = requestBuilder.url(url).post(formBody).tag(tag).build();

        Log.d(BaseLangPresenter.TAG,"请求报文Host：" + url);
        Log.d(BaseLangPresenter.TAG,"请求报文body：" + params);

        callback.onBefore(request);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(request, e, context);
                        Log.i("nyso_bbc","onError"+e);
                        callback.onAfter();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    List<String> cookies = response.headers("Set-Cookie");
                    String cookie = null;
                    for (String str : cookies) {
                        Log.d(BaseLangPresenter.TAG,"返回报文cookie：" + str);
                    }

                    Log.d(BaseLangPresenter.TAG,"返回报文Host：" + url);
                    Log.d(BaseLangPresenter.TAG,"返回报文body：" + result);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(result);
                            callback.onAfter();
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(request, e, context);
                            callback.onAfter();
                        }
                    });
                }
            }
        });
    }
    /**
     * 网络请求方法
     *
     * @param context
     * @param url
     * @param callback
     *
     */
    public void get(final Context context, final String url, final HttpCallback callback) {

        Log.d(BaseLangPresenter.TAG,"请求报文Host：" + url);
        Request.Builder requestBuilder = new Request.Builder();

        final Request request = requestBuilder.url(url).build();


        callback.onBefore(request);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(request, e, context);
                        callback.onAfter();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String result = response.body().string();

                    Log.d(BaseLangPresenter.TAG,"返回报文Host：" + url);
                    Log.d(BaseLangPresenter.TAG,"返回报文body：" + result);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(result);
                            callback.onAfter();
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(request, e, context);
                            callback.onAfter();
                        }
                    });
                }
            }
        });
    }

    public String uploadImage(Context context, File file, String serverUrl, boolean isUpHeadImg) {
        return uploadImage(context, file, serverUrl, null, isUpHeadImg);
    }



    public String uploadImage(Context context, File file, String serverUrl, String type, boolean isUpHeadImg) {
        // file=new File(Environment.getExternalStorageDirectory(),
        // IMAGE_FILE_NAME);
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
//            if(!BBCUtil.isEmpty(type)){
//                conn.setRequestProperty("credentialsType", type);
//            }
            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"pic\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                file.getAbsoluteFile().getAbsolutePath();
                InputStream is = new FileInputStream(file);
                long length = file.length();
                int percent = 0;
//                Message msg;
                byte[] bytes = new byte[1024];
                int len = 0;
                int total = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                    total += len;
                    percent = (int) (total * 100 / length);
//                    msg = new Message();
//                    msg.what = 1;
//                    msg.arg1 = percent;
//                    handler.sendMessage(msg);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                dos.close();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();

                if (res == 200) {

                    conn.getResponseMessage();
                    StringBuilder resultData = new StringBuilder("");
                    InputStreamReader isr = new InputStreamReader(
                            conn.getInputStream());
                    // 使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader buffer = new BufferedReader(isr);
                    String inputLine = null;
                    while ((inputLine = buffer.readLine()) != null) {
                        resultData.append(inputLine);
                        resultData.append("\n");
                    }
                    buffer.close();
                    isr.close();
                    conn.disconnect();
                    String result = resultData.toString();

                    Log.d(BaseLangPresenter.TAG,"返回报文body：" + result);

                    List<String> strList = conn.getHeaderFields().get("Set-Cookie");
                    if (strList != null) {
                        for (String str : strList) {
                            Log.d(BaseLangPresenter.TAG,"返回报文cookie：" + str);
                        }
                    }

                    if (isUpHeadImg) {
                        //保存上传成功后的图片路径

                        conn.disconnect();
                        return result;
                    }
                    conn.disconnect();
                    return result;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

    public String uploadImage(Context context, File file, String serverUrl) {
        // file=new File(Environment.getExternalStorageDirectory(),
        // IMAGE_FILE_NAME);
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {

            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);

            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"pic\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                file.getAbsoluteFile().getAbsolutePath();
                InputStream is = new FileInputStream(file);
                long length = file.length();
                int percent = 0;
//                Message msg;
                byte[] bytes = new byte[1024];
                int len = 0;
                int total = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                    total += len;
                    percent = (int) (total * 100 / length);
//                    msg = new Message();
//                    msg.what = 1;
//                    msg.arg1 = percent;
//                    handler.sendMessage(msg);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                dos.close();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();

                if (res == 200) {

                    conn.getResponseMessage();
                    StringBuilder resultData = new StringBuilder("");
                    InputStreamReader isr = new InputStreamReader(
                            conn.getInputStream());
                    // 使用缓冲一行行的读入，加速InputStreamReader的速度
                    BufferedReader buffer = new BufferedReader(isr);
                    String inputLine = null;
                    while ((inputLine = buffer.readLine()) != null) {
                        resultData.append(inputLine);
                        resultData.append("\n");
                    }
                    buffer.close();
                    isr.close();
                    conn.disconnect();
                    String result = resultData.toString();
                    return result;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }



    // 批量上传图片公有方法
    public void uploadImgAndParameter(final Context context, Map<String, Object> map,
                                      String url, final HttpCallback callback) {

        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    if("images".equals(entry.getKey())){
                        List<File> list= (List<File>) entry.getValue();
                        for (int i=0;i<list.size();i++){
                            builder.addFormDataPart("image"+i, list.get(i).getName(),
                                    RequestBody.create(MEDIA_TYPE_PNG, list.get(i)));
                        }
                    }else{
                        builder.addFormDataPart(entry.getKey(), entry
                                .getValue().toString());
                    }
                }

            }
        }

        // 创建RequestBody
        RequestBody body = builder.build();
        final Request request = new Request.Builder()
                .url(url)// 地址
                .post(body)// 添加请求体
                .build();
        callback.onBefore(request);

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                    Log.i("","");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(request, e, context);
                        callback.onAfter();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String result = response.body().string();
                    String cookie = response.headers().get("Set-Cookie");
                    Log.d(BaseLangPresenter.TAG,"返回报文cookie：" + cookie);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(result);
                            callback.onAfter();
                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(request, e, context);
                            callback.onAfter();
                        }
                    });
                }

            }

        });
    }

}
