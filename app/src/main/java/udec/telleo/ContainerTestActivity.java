package udec.telleo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;
import udec.telleo.viewadapters.ViajesAdapter;

public class ContainerTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_test);
        ViajesAdapter adapter = new ViajesAdapter(this, R.id.fragment_container);
        actualizarViajesAdapter(getIntent().getStringExtra("username"), adapter);
        ListView lv = findViewById(R.id.fragment_container);
        lv.setAdapter(adapter);
    }

    private void actualizarViajesAdapter(String username, final ViajesAdapter adapter){
        Call<List<Viaje>> call = TeLleoService.getService(getApplicationContext()).getViajesDeConductor(username);
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> res) {
                adapter.setCollection(res.body());
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {

            }
        });
    }
}
