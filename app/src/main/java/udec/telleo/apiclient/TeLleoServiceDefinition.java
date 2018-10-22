package udec.telleo.apiclient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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
}
