package udec.telleo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ContainerTestActivity.class);
        // rico login ctm
        SharedPreferences.Editor preferences = getSharedPreferences("datos", MODE_PRIVATE).edit();
        preferences.putString("username", "test1");
        preferences.apply();
        intent.putExtra("username", "test1");
        startActivity(intent);
    }
}
