package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Registro extends AppCompatActivity {
    private EditText usuario,mail,contra,contra2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        usuario = findViewById(R.id.userText);
        mail = findViewById(R.id.mailText);
        contra = findViewById(R.id.passwordText);
        contra2 = findViewById(R.id.password2Text);
    }
    public void registrar(View v){
        //validar si las weas están bien
        if(!contra.getText().toString().equals(contra2.getText().toString())){
            Log.v("password","no coinciden");
            return;
        }
        Intent intent = new Intent(this, Registro2.class);
        intent.putExtra("username", usuario.getText().toString());
        intent.putExtra("mail",mail.getText().toString());
        intent.putExtra("contra",contra.getText().toString());
        startActivity(intent);
    }
}
