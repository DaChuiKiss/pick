package com.ergou.hailiao.mvp.http.converter;

import com.ergou.hailiao.utils.EncodeUtils;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 */

public class DecodeResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        //解密字符串
        return adapter.fromJson(new String(EncodeUtils.base64Decode(value.string()), "UTF-8"));
    }

//    @Override public T convert(ResponseBody value) throws IOException {
//        JsonReader jsonReader = gson.newJsonReader(value.charStream());
//        try {
//            return adapter.read(jsonReader);
//        } finally {
//            value.close();
//        }
//    }
}
