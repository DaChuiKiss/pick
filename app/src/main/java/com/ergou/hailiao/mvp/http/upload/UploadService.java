package com.ergou.hailiao.mvp.http.upload;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;


/**
 * 上传文件接口
 */
public interface UploadService {

    @POST("upload")
    @Multipart
    Observable<ResponseBody> uploadFileInfo(@QueryMap Map<String, String> options, @PartMap Map<String, RequestBody> externalFileParameters);

}
