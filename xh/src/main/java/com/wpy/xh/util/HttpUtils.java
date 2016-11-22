package com.wpy.xh.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wpy.xh.pojo.ResponseResult;
import com.wpy.xh.config.NetConfig;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import library.mlibrary.constant.Tags;
import library.mlibrary.util.log.LogDebug;
import library.mlibrary.util.okhttp.ProgressListener;
import library.mlibrary.util.okhttp.ProgressRequestBody;
import library.mlibrary.util.okhttp.ProgressResponseBody;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by harmy on 2016/8/4 0004.
 */
public class HttpUtils {
    private static OkHttpClient mClient;
    private static HttpUtils mUtils;

    private HttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(300000, TimeUnit.MILLISECONDS);
        mClient = builder.build();
    }

    public static HttpUtils getInstance() {
        if (mUtils == null || mClient == null) {
            mUtils = new HttpUtils();
        }
        return mUtils;
    }

    private String url;

    public HttpUtils url(String url) {
        this.url = url;
        return mUtils;
    }

    public HttpUtils interfaceapi(String api) {
        url = NetConfig.HOST + api;
        return mUtils;
    }

    private RequestParam params;

    public HttpUtils params(RequestParam params) {
        this.params = params;
        return mUtils;
    }

    public ExecuteResponse executePost(ProgressListener listener) {
        try {
            Response response = mClient.newCall(buildPostRequest(listener)).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                String result = new String(bytes);
                LogDebug.d(Tags.HTTP, result);
                ExecuteResponse executeResponse = new ExecuteResponse();
                executeResponse.setResponse(response);
                executeResponse.setBytes(bytes);
                executeResponse.setString(result);
                return executeResponse;
            } else return null;
        } catch (IOException e) {
            LogDebug.e(e);
            return null;
        }
    }

    public void executePost(@NonNull final HttpUtils.HttpListener listener) {
        mClient.newCall(buildPostRequest(new ProgressListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                LogDebug.d(Tags.HTTP, "responseprogress--" + progress + "/" + total + "|isdone=" + done);
                if (listener != null) {
                    listener.onRequestProgress(progress, total, done);
                }
            }
        })).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogDebug.e(e);
                if (listener != null) {
                    listener.onException(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                String result = new String(bytes);
                LogDebug.d(Tags.HTTP, result);
                if (listener != null) {
                    listener.onHttpSuccess(response);
                    listener.onHttpSuccess(bytes);
                    listener.onHttpSuccess(result);
                }
            }
        });
    }

    public void executeGet(@NonNull final HttpUtils.HttpListener listener) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), new ProgressListener() {
                            @Override
                            public void onProgress(long progress, long total, boolean done) {
                                LogDebug.d(Tags.HTTP, "requestprogress--" + progress + "/" + total + "|isdone=" + done);
                                if (listener != null) {
                                    listener.onResponseProgress(progress, total, done);
                                }
                            }
                        }))
                        .build();
            }
        };
        OkHttpClient client = mClient.newBuilder().addNetworkInterceptor(interceptor).build();
        client.newCall(buildGetRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogDebug.e(e);
                if (listener != null) {
                    listener.onException(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                String result = new String(bytes);
                LogDebug.d(Tags.HTTP, result);
                if (listener != null) {
                    listener.onHttpSuccess(response);
                    listener.onHttpSuccess(bytes);
                    listener.onHttpSuccess(result);
                }
            }
        });
    }

    public ExecuteResponse executeGet(@Nullable final ProgressListener listener) {
        try {
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), listener))
                            .build();
                }
            };
            OkHttpClient client = mClient.newBuilder().addNetworkInterceptor(interceptor).build();
            Response response = client.newCall(buildGetRequest()).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                String result = new String(bytes);
                LogDebug.d(Tags.HTTP, result);
                ExecuteResponse executeResponse = new ExecuteResponse();
                executeResponse.setResponse(response);
                executeResponse.setBytes(bytes);
                executeResponse.setString(result);
                return executeResponse;
            } else return null;
        } catch (IOException e) {
            LogDebug.e(e);
            return null;
        }
    }

    private Request buildPostRequest(ProgressListener progress) {
        Request.Builder builder = new Request.Builder();
        String url = new String(this.url);
        String request = url;
        if (params != null) {
            ArrayList<ParamKV> paramKVs = params.getParams();
            if (paramKVs.size() > 0) {
                MultipartBody.Builder multibuilder = new MultipartBody.Builder();
                multibuilder.setType(MultipartBody.FORM);
                for (ParamKV kv : paramKVs) {
                    if (kv.getTag() == ParamKV.STRING) {
                        multibuilder.addFormDataPart(kv.getKey(), kv.getValue());
                        request += kv.getKey() + "=" + kv.getValue() + "&";
                    } else if (kv.getTag() == ParamKV.FILE) {
                        multibuilder.addFormDataPart(kv.getKey(), kv.getFile().getName(), RequestBody.create(null, kv.getFile()));
                        request += kv.getKey() + "=" + kv.getFile().getName() + "&";
                    }
                }
                builder.post(new ProgressRequestBody(multibuilder.build(), progress)).url(url);
            } else {
                FormBody.Builder formbuilder = new FormBody.Builder();
                builder.post(new ProgressRequestBody(formbuilder.build(), progress)).url(url);
            }
        } else {
            FormBody.Builder formbuilder = new FormBody.Builder();
            builder.post(new ProgressRequestBody(formbuilder.build(), progress)).url(url);
        }
        params(null);
        LogDebug.d(Tags.HTTP, request);
        return builder.build();
    }

    private Request buildGetRequest() {
        Request.Builder builder = new Request.Builder();
        String url = new String(this.url);
        if (params != null) {
            ArrayList<ParamKV> paramKVs = params.getParams();
            for (ParamKV kv : paramKVs) {
                url += kv.getKey() + "=" + kv.getValue() + "&";
            }
            url.substring(0, url.length() - 1);
        }
        builder.get().url(url);
        params(null);
        LogDebug.d(Tags.HTTP, url);
        return builder.build();
    }

    public static boolean isSuccess(String json) {
        try {
            Gson gson = new Gson();
            TypeToken<ResponseResult> typeToken = new TypeToken<ResponseResult>() {
            };
            Type type = typeToken.getType();
            ResponseResult responseResult = gson.fromJson(json, type);
            if (responseResult.getCode().equals(NetConfig.CODE_SUCCESS)) {
                return true;
            } else return false;
        } catch (Exception e) {
            LogDebug.e(e);
            return false;
        }

    }

    public static class ExecuteResponse implements Serializable {
        private String string;
        private byte[] bytes;
        private Response response;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }
    }

    public static class RequestParam implements Serializable {
        private ArrayList<ParamKV> mParams;

        public RequestParam() {
            mParams = new ArrayList<>();
        }

        public void add(String key, String value) {
            mParams.add(new ParamKV(key, value));
        }

        public void add(String key, File file) {
            mParams.add(new ParamKV(key, file));
        }

        public void action(String action) {
            mParams.add(new ParamKV("action", action));
        }

        public ArrayList<ParamKV> getParams() {
            return mParams;
        }
    }

    public static class ParamKV implements Serializable {
        public static final int FILE = 0;
        public static final int STRING = 1;
        private String key;
        private String value;
        private File file;
        private int tag = STRING;

        public ParamKV() {
        }

        public ParamKV(String key, String value) {
            tag = STRING;
            setKey(key);
            setValue(value);
        }

        public ParamKV(String key, File file) {
            tag = FILE;
            setKey(key);
            setFile(file);
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public static class HttpListener {
        public void onRequestProgress(long progress, long total, boolean done) {
        }

        public void onResponseProgress(long progress, long total, boolean done) {
        }

        /**
         * 网络请求成功
         */
        public void onHttpSuccess(Response response) {
        }

        /**
         * 网络请求成功
         */
        public void onHttpSuccess(String string) {
        }

        /**
         * 网络请求成功
         */
        public void onHttpSuccess(byte[] bytes) {
        }

        /**
         * 网络请求失败
         *
         * @param e
         */
        public void onException(Throwable e) {
        }
    }
}
