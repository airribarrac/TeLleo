package udec.telleo;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.List;

import udec.telleo.apiclient.AsyncCall;
import udec.telleo.apiclient.Client;
import udec.telleo.model.Viaje;

public class ContainerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_test);

        Client.listViajesDeConductor(getIntent().getStringExtra("username"), this, new AsyncCall<List<Viaje>>() {
            @Override
            public void onSuccess(List<Viaje> res) {
                ((LinearLayout)findViewById(R.id.fragment_container)).removeAllViews();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(res != null) {
                    for (Viaje viaje : res) {
                        Log.d("viendo", viaje.toString());
                        ViajesCreadosElement element = ViajesCreadosElement.newInstance(viaje.getId());
                        fragmentTransaction.add(R.id.fragment_container, element);
                    }
                }
                fragmentTransaction.commit();

            }

            @Override
            public void onFailure(Throwable err) {

            }
        });

    }
}
