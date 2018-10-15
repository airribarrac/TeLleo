package udec.telleo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;

public class ContainerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_test);

        Call<List<Viaje>> call = TeLleoService.getService().getViajesDeConductor(getIntent().getStringExtra("username"));
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> res) {
                ((LinearLayout)findViewById(R.id.fragment_container)).removeAllViews();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(res != null) {
                    for (Viaje viaje : res.body()) {
                        Log.d("viendo", viaje.toString());
                        ViajesCreadosElement element = ViajesCreadosElement.newInstance(viaje.getId());
                        fragmentTransaction.add(R.id.fragment_container, element);
                    }
                }
                fragmentTransaction.commit();

            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {

            }
        });
    }
}
