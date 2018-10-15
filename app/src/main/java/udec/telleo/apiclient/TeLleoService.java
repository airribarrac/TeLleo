package udec.telleo.apiclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeLleoService {

    static final String baseUrl = "https://telleo.herokuapp.com/api/v1/";
    private static TeLleoServiceDefinition service = null;

    private static void initialize(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(TeLleoServiceDefinition.class);
    }

    public static TeLleoServiceDefinition getService() {
        if (service == null) {
            initialize();
        }
        return service;
    }
}
