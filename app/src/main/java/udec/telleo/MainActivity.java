package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //boton de mi wea
        Button miboton = (Button) findViewById(R.id.miboton);
        miboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nueva = new Intent(MainActivity.this, crearviaje.class);
                startActivity(nueva);
            }
        });
        Log.v("asdasd","afekjkhdfkfsdasdasdfsdfjhfkg");
    }

    public void abrirElegirViaje(View view) {
        Intent i = new Intent(this,ElegirViaje.class);
        startActivity(i);

    }
}
