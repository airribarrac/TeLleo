package udec.telleo.apiclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import udec.telleo.ContainerTestActivity;
import udec.telleo.model.Parada;
import udec.telleo.model.Viaje;

public class Client {
    static final String baseUrl = "https://telleo.herokuapp.com/api/v1";
    private static String urlViajesConductor(String conductor){
        return String.format("%s/conductores/%s/viajes", baseUrl, conductor);
    }

    @SuppressLint("DefaultLocale")
    private static String urlParadasViaje(int viajeId){
        return String.format("%s/viajes/%d/paradas", baseUrl, viajeId);
    }

    @SuppressLint("DefaultLocale")
    private static String urlViaje(int viajeId){
        return String.format("%s/viajes/%d", baseUrl, viajeId);
    }

    public static void listViajesDeConductor(String conductor, Context context, final AsyncCall<List<Viaje>> callback){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                urlViajesConductor(conductor),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Viaje>>(){}.getType();
                        ArrayList<Viaje> viajes = new Gson().fromJson(response, listType);
                        callback.onSuccess(viajes);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error);
                    }
                }
        );
        RequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void listParadasEnViaje(int viajeId, Context context, final AsyncCall<List<Parada>> callback){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                urlParadasViaje(viajeId),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type listType = new TypeToken<ArrayList<Parada>>(){}.getType();
                        ArrayList<Parada> paradas = new Gson().fromJson(response, listType);
                        callback.onSuccess(paradas);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error);
                    }
                }
        );
        RequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }

    public static void getViaje(int idViaje, Context context, final AsyncCall<Viaje> callback){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                urlViaje(idViaje),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Viaje viaje= new Gson().fromJson(response, Viaje.class);
                        callback.onSuccess(viaje);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error);
                    }
                }
        );
        RequestQueue.getInstance(context).addToRequestQueue(stringRequest);
    }
}
