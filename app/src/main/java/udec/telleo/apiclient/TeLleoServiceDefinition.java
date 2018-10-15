package udec.telleo.apiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import udec.telleo.model.*;

public interface TeLleoServiceDefinition {
    @GET("conductores/{conductor}/viajes")
    Call<List<Viaje>> getViajesDeConductor(@Path("conductor") String conductor);

    @GET("viajes/{viajeid}/paradas")
    Call<List<Parada>> getParadasDeViaje(@Path("viajeid")int idViaje);

    @GET("viajes/{viajeid}")
    Call<Viaje> getDatosViaje(@Path("viajeid")int idViaje);
}
