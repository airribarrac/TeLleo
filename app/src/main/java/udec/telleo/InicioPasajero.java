package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InicioPasajero extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_pasajero);
    }

    public void verViajesReservadosClick(View view) {
        //No implementa3
        Intent intent = new Intent(this, ViajesReservados.class);
        startActivity(intent);
    }

    public void buscarViajesClick(View view) {
        Intent intent = new Intent(this, ElegirViaje.class);
        startActivity(intent);
    }
}
