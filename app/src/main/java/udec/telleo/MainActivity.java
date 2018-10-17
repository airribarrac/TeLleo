package udec.telleo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this,ElegirViaje.class);
        startActivity(i);
        Log.v("asdasd","afekjkhdfkfsdasdasdfsdfjhfkg");
    }
}
