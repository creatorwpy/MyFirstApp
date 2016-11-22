package library.mlibrary.util.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * mInterceptor = new Interceptor() {
 *
 * @Override public Response intercept(Chain chain) throws IOException {
 * Response originalResponse = chain.proceed(chain.request());
 * return originalResponse.newBuilder()
 * .body(new ProgressResponseBody(originalResponse.body(), listener))
 * .build();
 * }
 * };
 * OkHttpClient client= mHttpClient.newBuilder().addNetworkInterceptor(mInterceptor).build();
 * <p>
 * Created by Harmy on 2016/5/4 0004.
 */
public class ProgressResponseBody extends ResponseBody {
    //实际的待包装响应体
    private final ResponseBody responseBody;
    //进度回调接口
    private final ProgressListener mProgressListener;
    //包装完成的BufferedSource
    private BufferedSource bufferedSource;

    /**
     * 构造函数，赋值
     *
     * @param responseBody 待包装的响应体
     */
    public ProgressResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
        mProgressListener = null;
    }

    /**
     * 构造函数，赋值
     *
     * @param responseBody     待包装的响应体
     * @param progressListener 回调接口
     */
    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.mProgressListener = progressListener;
    }


    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if (mProgressListener != null) {
                    mProgressListener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}
