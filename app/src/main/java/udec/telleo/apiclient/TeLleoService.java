package udec.telleo.apiclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeLleoService {

    static final String baseUrl = "https://telleo.herokuapp.com/api/v1/";
    private static TeLleoServiceDefinition service = null;

    private static void initialize(final Context ctx){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(ctx.getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();
                        SharedPreferences preferences = ctx.getSharedPreferences("datos", Context.MODE_PRIVATE);
                        String key = preferences.getString("api_key", "");

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder();
                        if(key != "") {
                            requestBuilder.header("api_key", key); // clave de la api, por ahora solo el usuario
                        }
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(TeLleoServiceDefinition.class);
    }

    public static TeLleoServiceDefinition getService(Context context) {
        if (service == null) {
            initialize(context);
        }
        return service;
    }
}
