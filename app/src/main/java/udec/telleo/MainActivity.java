package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import udec.telleo.model.*;
import udec.telleo.apiclient.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ContainerTestActivity.class);
        intent.putExtra("username", "test1");
        startActivity(intent);
        Log.e("holi", "asdas");
        
    }
}
