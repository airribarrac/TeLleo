package udec.telleo;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Integer.parseInt;

public class AgregarParadas extends AppCompatActivity {
    private LinearLayout ll;
    private DialogAgregar dialogAgregar;
    private ArrayList<View> viewList;
    private ArrayList<LatLng> placeList;
    private String fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i= getIntent();
        viewList = new ArrayList<>();
        placeList = new ArrayList<>();
        fecha = i.getStringExtra("fecha");
        String hora = i.getStringExtra("hora");
        String direccion = i.getStringExtra("direccion");
        String ciudad = i.getStringExtra("ciudad");
        setContentView(R.layout.activity_agregar_paradas);
        ll = findViewById(R.id.llayoutparadas);
        dialogAgregar = new DialogAgregar(this);
        agregarParada(ciudad,hora,-1,direccion);

    }
    public void clickParada(View v){
        dialogAgregar.mostrar();

    }
    public void agregarParada(String ciudad,String hora,int tarifa,String direccion){
        View v = getLayoutInflater().inflate(R.layout.fragment_paradas_element, null);
        ImageButton cerrar = v.findViewById(R.id.removeButton);
        cerrar.setOnClickListener(new CerrarListener(v));
        TextView eciudad = v.findViewById(R.id.textCiudad);
        eciudad.setText(ciudad);
        TextView ehora = v.findViewById(R.id.textHoraS);
        ehora.setText(hora);
        TextView etarifa = v.findViewById(R.id.textTarifa);
        DecimalFormat format = new DecimalFormat("###,###,###");
        String str;
        str = format.format(tarifa);
        etarifa.setText("$"+str.replaceAll(",","."));
        TextView dir = v.findViewById(R.id.textDireccion);
        dir.setText(direccion);
        if(tarifa==-1){
            ImageButton eliminar = v.findViewById(R.id.removeButton);
            eliminar.setClickable(false);
            eliminar.setAlpha(0.0f);
            etarifa.setAlpha(0.0f);
        }
        ll.addView(v);
        ll.removeAllViews();
        viewList.add(v);
        Collections.sort(viewList,new Comparador());
        for(View x : viewList){
            ll.addView(x);
        }
    }
    private class CerrarListener implements View.OnClickListener{
        private View v;
        public CerrarListener(View v){
            this.v=v;
        }
        @Override
        public void onClick(View view) {
            viewList.remove(v);
            ll.removeView(v);
        }
    }
    private class HoraListener implements View.OnClickListener{
        private EditText t;
        HoraListener(EditText et){
            t=et;
        }
        @Override
        public void onClick(View view) {

            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            TimePickerDialog tpd = new TimePickerDialog(AgregarParadas.this,
                    TimePickerDialog.THEME_HOLO_LIGHT,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            t.setText(String.format("%02d",selectedHour )+ ":" +
                                    String.format("%02d",selectedMinute  ));
                        }
                    }
                    , hour, minute,true);
            tpd.show();
        }
    }
    private class DialogAgregar extends Dialog{
        private Button cancelar,continuar;
        private PlaceAutocompleteFragment paf;
        Place lugar;
        EditText tarifa,hora;
        public DialogAgregar(@NonNull Context context) {
            super(context);
        }

        public void mostrar(){
            show();
            lugar=null;
            tarifa.setText("");
            hora.setText("");
            ((EditText)paf.getView().findViewById(R.id.place_autocomplete_search_input)).setText("");
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_agregarparada);
            paf = (PlaceAutocompleteFragment) getFragmentManager().
                    findFragmentById(R.id.fparada);
            AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder().setCountry("CL")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
            paf.setFilter(typeFilter1);
            ((EditText)paf.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("Ciudad");
            paf.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    lugar=place;

                }

                @Override
                public void onError(Status status) {

                }
            });
            hora = findViewById(R.id.textHoraS);
            hora.setOnClickListener(new HoraListener(hora));
            tarifa = findViewById(R.id.textTarifa);
            tarifa.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    try {
                        int input = Integer.parseInt(dest.toString().replaceAll("[^0-9]","")
                                + source.toString().replaceAll("[^0-9]",""));
                        if (input>0 && input<100000 )
                            return null;
                    } catch (NumberFormatException nfe) { }
                    return "";
                }
            }});
            tarifa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        Log.v("focuschange","gane");
                        String str = tarifa.getText().toString().replaceAll("[^0-9]","");
                        Log.v("focuschange",str);
                        tarifa.setText(str);
                    }else{
                        Log.v("focuschange","perdi");
                        DecimalFormat format = new DecimalFormat("###,###,###");
                        String str;
                        if(!tarifa.getText().toString().equals("")) {
                            str = format.format(parseInt(tarifa.getText().toString()));
                            Log.v("focuschange","ENTRE AQUI "+str);
                        }else{
                            str="0";
                        }

                        tarifa.setText("$"+str);
                    }
                }
            });
            cancelar = findViewById(R.id.botonCancelar);
            continuar = findViewById(R.id.botonContinuar);
            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hide();
                }
            });
            continuar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lugar==null){
                        Toast.makeText(getContext(),"Ingrese ciudad",Toast.LENGTH_LONG).show();
                        return;
                    }
                    placeList.add(lugar.getLatLng());
                    String[] partes = lugar.getAddress().toString().split(",");
                    int largo = partes.length;
                    if(largo<4){
                        Toast.makeText(getContext(),"Ingrese direccion completa",Toast.LENGTH_LONG)
                        .show();
                        return;
                    }
                    String tar = tarifa.getText().toString().replaceAll("[^0-9]","");
                    if(tar.equals("")){
                        Toast.makeText(getContext(),"Ingrese tarifa",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(hora.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Ingrese hora",Toast.LENGTH_LONG).show();
                        return;
                    }
                    int tari = Integer.parseInt(tar);

                    agregarParada(partes[1],hora.getText().toString(),tari,partes[0]);
                    hide();
                }
            });
        }
    }
    public void subir(View v){
        //subir a la base de datos
    }

    private class Comparador implements Comparator<View>{
        @Override
        public int compare(View view, View t1) {
            return ((TextView)view.findViewById(R.id.textHoraS)).getText().toString().compareTo(
                    ((TextView)t1.findViewById(R.id.textHoraS)).getText().toString()
            );
        }
    }
}
