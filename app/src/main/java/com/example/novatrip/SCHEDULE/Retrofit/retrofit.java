package com.example.novatrip.SCHEDULE.Retrofit;

import com.example.novatrip.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.novatrip.SCHEDULE.Retrofit.RetroBaseApi.scheduleBaseURL;

public class retrofit {

    public static retrofit2.Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder().baseUrl(scheduleBaseURL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);                }
            };
        }
    }
    

    private static retrofit2.Retrofit retrofit;
}
