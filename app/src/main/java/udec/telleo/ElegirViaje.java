package udec.telleo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;

import java.util.Calendar;

public class ElegirViaje extends AppCompatActivity {
    private EditText ti,tf;
    private PlaceAutocompleteFragment pafo,pafd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_viaje);
        ti = this.findViewById(R.id.fechainicio);
        ti.setOnClickListener(new FechaListener(ti));
        tf = this.findViewById(R.id.fechafinal);
        tf.setOnClickListener(new FechaListener(tf));
        pafo = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.forigen);
        pafd = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fdestino);
        AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder().setCountry("CL")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        AutocompleteFilter typeFilter2 = new AutocompleteFilter.Builder().setCountry("CL")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        pafo.setFilter(typeFilter1);
        pafd.setFilter(typeFilter2);

        ((EditText)pafo.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("Origen");

        ((EditText)pafd.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("Destino");
    }

    private class FechaListener implements View.OnClickListener{
        private EditText t;
        FechaListener(EditText et){
            t=et;
        }
        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            int mm = calendar.get(Calendar.MONTH);
            DatePickerDialog dpd = new  DatePickerDialog(ElegirViaje.this,
                    AlertDialog.THEME_HOLO_DARK,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                            String day = String.format("%02d",d);
                            String year = String.format("%d",y);
                            String month = String.format("%02d",m+1);
                            t.setText(day+"/"+month+"/"+year);
                        }
                    }
            ,yy,mm,dd);
            dpd.show();
        }
    }
}
