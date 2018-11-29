package udec.telleo.apiclient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.PUT;
import udec.telleo.model.*;

public interface TeLleoServiceDefinition {
    @GET("conductores/{conductor}/viajes")
    Call<List<Viaje>> getViajesDeConductor(@Path("conductor") String conductor);

    @GET("viajes/{viajeid}/paradas")
    Call<List<Parada>> getParadasDeViaje(@Path("viajeid")int idViaje);

    @GET("viajes/{viajeid}")
    Call<Viaje> getDatosViaje(@Path("viajeid")int idViaje);

    @GET("conductores/{conductor}/viajes/{viajeid}/reservas")
    Call<List<Reserva>> getReservasRecibidas(@Path("conductor")String conductor, @Path("viajeid") int idViaje);

    @PUT("conductores/{conductor}/viajes/{viajeid}/reservas/{idreserva}")
    Call<ResponseBody> setEstadoReserva(@Path("conductor") String conductor, @Path("viajeid") int idViaje, @Path("idreserva") int idReserva, @Body Reserva reserva);
    @GET("viajes")
    Call<List<Viaje>> getViajes(@Query("origen") String origen, @Query("destino") String destino,
            @Query("fechaminima") String fechamin, @Query("fechamaxima") String fechamax,
            @Query("asientos") int asientos, @Query("maletas") int maletas);
    @POST("viajes/{viajeid}/pasajeros")
    Call<ResponseBody> postReserva(@Body Reserva res, @Path("viajeid") int viajeid);

    @POST("/conductores/{conductor}/viajes")
    Call<ResponseBody> postViaje(@Body Viaje vi, @Path("conductor") String conductor);

    @DELETE("viajes/{viajeid}")
    Call<ResponseBody> deleteViaje(@Path("viajeid") int viajeid);

    @POST("login")
    Call<LoginResponse> login(@Body UserData data);

    @GET("pasajeros/{pasajero}/reservas")
    Call<List<Reserva>> getReservaPasajero(@Path("pasajero") String pasajero);

    @DELETE("pasajeros/{pasajero}/reservas/{reservaID}")
    Call<ResponseBody> deleteReserva(@Path("pasajero") String pasajero, @Path("reservaid") int reservaid);

}
