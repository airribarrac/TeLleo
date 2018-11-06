package udec.telleo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Registro2 extends AppCompatActivity {
    private EditText nombre,apellido,rut,celular;
    private String user,contra,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);
        Intent i = getIntent();
        user = i.getStringExtra("username");
        mail = i.getStringExtra("mail");
        contra = i.getStringExtra("contra");
    }

    public void completar(View v){
        //validar
        DialogFragment pcd = new PreguntaConductorDialog();
        //pcd.show(getSupportFragmentManager(),"alo");
    }

}
