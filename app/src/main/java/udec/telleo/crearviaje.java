package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearviaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearviaje);
        Button conf = (Button) findViewById(R.id.confirmar);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Fecha: ",getFecha());
                Log.d("Hora: ",getHora());
                Log.d("Origen: ",getOrigen());
                Log.d("Destino: ",getDestino());
            }
        });
    }

    private String getFecha()
    {
        return ((EditText)findViewById(R.id.textFecha)).getText().toString();
    }
    private String getHora()
    {
        return ((EditText)findViewById(R.id.textHora)).getText().toString();
    }
    private String getOrigen()
    {
        return ((EditText)findViewById(R.id.textOrigen)).getText().toString();
    }
    private String getDestino()
    {
        return ((EditText)findViewById(R.id.textDestino)).getText().toString();
    }
}
