package udec.telleo;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usuario,contrasenia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.textusuario);
        contrasenia = findViewById(R.id.textcontra);
    }

    public void loguear(View view) {

        if(usuario.getText().toString().equals("")){
            Toast.makeText(this,"Ingrese usuario",Toast.LENGTH_LONG).show();
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.agitar);
            usuario.startAnimation(shake);
            return;
        }
        if(contrasenia.getText().toString().equals("")){
            Toast.makeText(this,"Ingrese contraseña",Toast.LENGTH_LONG).show();
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.agitar);
            contrasenia.startAnimation(shake);
            return;
        }
    /** TO DO
    revisar si el qlo esta en la base o se quiere hacer el vio
     **/
        SharedPreferences sp = getSharedPreferences("datos",MODE_PRIVATE);
        sp.edit().putString("usuario",usuario.getText().toString());
        sp.edit().putString("contraseña",contrasenia.getText().toString());
        Intent i = new Intent(this,ElegirViaje.class);
        startActivity(i);
    }


    public void verViajesCreadosClick(View view) {
        Intent intent = new Intent(this, ViajesCreadosActivity.class);

        intent.putExtra("username", "test1");
        startActivity(intent);

    }
    public void registrarse(View view){
        Intent intent = new Intent(this, Registro.class);

        intent.putExtra("username", "test1");
        startActivity(intent);

    }
}
