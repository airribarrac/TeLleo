package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PasajeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero);
    }

    public void crearViaje(View v){
        Intent intent = new Intent(this, crearviaje.class);

        intent.putExtra("username", "test1");
        startActivity(intent);
    }
}

