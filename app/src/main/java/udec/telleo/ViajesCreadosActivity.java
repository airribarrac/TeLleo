package udec.telleo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;
import udec.telleo.viewadapters.ViajesAdapter;

public class ViajesCreadosActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_test);
        ViajesAdapter adapter = new ViajesAdapter(this, R.id.fragment_container);
        SharedPreferences preferences = getSharedPreferences("datos", MODE_PRIVATE);

        String usuario = preferences.getString("usuario", "");
        Log.d("VIAJES CREADOS", "usuario: " + usuario);
        actualizarViajesAdapter(usuario, adapter);
        ListView lv = findViewById(R.id.fragment_container);
        lv.setAdapter(adapter);
    }

    private void actualizarViajesAdapter(String username, final ViajesAdapter adapter){
        Call<List<Viaje>> call = TeLleoService.getService(getApplicationContext()).getViajesDeConductor(username);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> res) {
                adapter.setCollection(res.body());
                Log.d("VIAJES CREADOS", "Cargados exitosamente");
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Context context = getApplicationContext();
                CharSequence text = "No se pudo obtener los viajes creados :(";
                int duration = Toast.LENGTH_LONG;
                Log.e("VIAJES CREADOS", "NO SE CARGARON", t);
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}
