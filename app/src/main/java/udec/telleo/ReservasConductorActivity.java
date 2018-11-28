package udec.telleo;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Reserva;
import udec.telleo.model.Viaje;
import udec.telleo.viewadapters.ReservasAdapter;
import udec.telleo.viewadapters.ViajesAdapter;

public class ReservasConductorActivity extends AppCompatActivity {
    private Viaje viaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_conductor);
        viaje = (Viaje)getIntent().getSerializableExtra("viaje");
        ReservasAdapter adapter = new ReservasAdapter(this, R.id.reservas_viaje_container, viaje);
        String username = getSharedPreferences("datos", MODE_PRIVATE).getString("usuario", "");
        actualizarReservas(username, viaje.getId(), adapter);
        ListView view = findViewById(R.id.reservas_viaje_container);
        view.setAdapter(adapter);
    }

    private void actualizarReservas(String conductor, int idViaje, final ReservasAdapter adapter){
        Call<List<Reserva>> call = TeLleoService.getService(this).getReservasRecibidas(conductor, idViaje);
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if(response.code() == 200) {
                    adapter.setCollection(response.body());
                    Log.d("RESERVAS: ", "");
                    for (Reserva r : response.body()) {
                        Log.d("reserva:", r.toString());
                    }
                }
                else{
                    Log.d("VER RESERVAS", "NO SE CARGARON LAS RESERVAS, CODE:" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Context context = getApplicationContext();
                CharSequence text = "No se pudo obtener las reservas del viaje     :(";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.d("ERROR", t.toString());
            }
        });
    }
}
