package udec.telleo;

import android.app.Notification;
import android.content.Context;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.LoginResponse;
import udec.telleo.model.UserData;

public class MainActivity extends AppCompatActivity {
    private EditText usuario,contrasenia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = findViewById(R.id.textusuario);
        contrasenia = findViewById(R.id.textcontra);
    }



    public boolean validarDatos(){
        if(usuario.getText().toString().equals("")){
            Toast.makeText(this,"Ingrese usuario",Toast.LENGTH_LONG).show();
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.agitar);
            usuario.startAnimation(shake);
            return false;
        }
        if(contrasenia.getText().toString().equals("")){
            Toast.makeText(this,"Ingrese contraseña",Toast.LENGTH_LONG).show();
            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.agitar);
            contrasenia.startAnimation(shake);
            return false;
        }
        return true;
    }

    public void loguear(View view) {

        if(!validarDatos())
            return;
        /** TO DO
         revisar si el qlo esta en la base o se quiere hacer el vio
         **/
        SharedPreferences sp = getSharedPreferences("datos",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("usuario",usuario.getText().toString());
        editor.putString("contraseña",contrasenia.getText().toString());
        editor.commit();
        Log.d("LOGIN", "usuario: " + usuario.getText().toString() );
        UserData data = new UserData();
        data.setUsername(usuario.getText().toString());
        data.setPassword(contrasenia.getText().toString());
        data.setEsConductor(false);
        login(data);
    }

    public void loguearConductor(View view) {
        if(!validarDatos())
            return;
        SharedPreferences sp = getSharedPreferences("datos",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("usuario",usuario.getText().toString());
        editor.putString("contraseña",contrasenia.getText().toString());
        editor.commit();
        Log.d("LOGIN", "usuario: " + usuario.getText().toString());
        UserData data = new UserData();
        data.setUsername(usuario.getText().toString());
        data.setPassword(contrasenia.getText().toString());
        data.setEsConductor(true);
        login(data);

    }


    void login(final UserData data){
        Call<LoginResponse> call = TeLleoService.getService(this).login(data);
        final Context ctx = this;
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getValido()){
                    if(data.esConductor()) {
                        Intent intent = new Intent(ctx, InicioConductorActivity.class);
                        ctx.startActivity(intent);
                    }
                   else{
                        Intent intent = new Intent(ctx, InicioPasajero.class);
                        ctx.startActivity(intent);
                    }
                }
                else{
                    Context context = getApplicationContext();
                    CharSequence text = "Sus credenciales son incorrectas o aún no se ha registrado :(";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Log.d("LOGIN", "FALLO EL LOGIN");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Context context = getApplicationContext();
                CharSequence text = "Hubo un error en la autenticación, probablemente no" +
                        " esté conectado a internet";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.d("LOGIN", "CRASHEO EL LOGIN");
            }
        });
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
