package udec.telleo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;

public class crearviaje extends AppCompatActivity {
    private EditText fecha,hora;
    private PlaceAutocompleteFragment pafo,pafd;
    private Place[] orDes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearviaje);
        orDes = new Place[2];
        orDes[0]=orDes[1]=null;
        fecha = this.findViewById(R.id.textFecha);
        fecha.setOnClickListener(new crearviaje.FechaListener(fecha));
        hora = this.findViewById(R.id.textHoraS);
        hora.setOnClickListener(new crearviaje.HoraListener(hora));
        pafo = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.forigen);
        AutocompleteFilter typeFilter1 = new AutocompleteFilter.Builder().setCountry("CL")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
        pafo.setFilter(typeFilter1);
        pafo.getView().findViewById(R.id.place_autocomplete_clear_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pafo.setText("");
                        view.setVisibility(View.GONE);
                        orDes[0]=null;
                    }
                }
        );
        pafo.setOnPlaceSelectedListener(new crearviaje.TextoListener(orDes,true));
        ((EditText)pafo.getView().findViewById(R.id.place_autocomplete_search_input)).setHint("Origen");



        //Boton de Confirmar
        Button conf = findViewById(R.id.confirmar);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Para ver si lee.
                if(orDes[0]==null){
                    Toast.makeText(getBaseContext(),"Ingrese direccion",Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if(hora.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Ingrese hora",Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if(fecha.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(),"Ingrese fecha",Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                final String[] partes = orDes[0].getAddress().toString().split(",");
                int largo = partes.length;
                if(largo<4){
                    Toast.makeText(getBaseContext(),"Ingrese direccion completa",Toast.LENGTH_LONG)
                            .show();
                    return;
                }


                Log.d("Fecha: ",getFecha());
                Log.d("Hora: ",getHora());
                Log.d("Origen: ",getOrigen());
                Log.d("Destino: ",getDestino());
                //Agregar datos a un viaje.
                Viaje v = new Viaje();
                v.setOrigen(getOrigen());
                v.setDestino(getDestino());
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");// yyyy-MM-dd'T'HH:mm:ssZ");
                String[] fecha1 = getFecha().split("/");

                String dateInString = getFecha() + " " + getHora();//fecha1[2] + "-" + fecha1[1] + "-" + fecha1[0] + "T" + getHora() + ":00" +  "Z";;
                Log.d("FECHA PARSEADA", dateInString);
                try {

                    Date date = formatter.parse(dateInString);
                    v.setFecha(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SharedPreferences sp = getSharedPreferences("datos", MODE_PRIVATE);
                Call<Viaje> call = TeLleoService.getService(getApplicationContext()).postViaje(v, sp.getString("usuario", ""));
                call.enqueue(new Callback<Viaje>() {
                    @Override
                    public void onResponse(Call<Viaje> call, Response<Viaje> response) {
                        if(response.code() == 200 && response.body() != null) {
                            Intent i = new Intent(crearviaje.this, AgregarParadas.class);
                            i.putExtra("direccion", partes[0]);
                            i.putExtra("ciudad", partes[1]);
                            i.putExtra("hora", hora.getText().toString());
                            i.putExtra("fecha", fecha.getText().toString());
                            i.putExtra("viaje", response.body());
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(crearviaje.this,"Hubo un error al crear el viaje",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Viaje> call, Throwable t) {

                    }
                });
                /*
                v.setDestino(getDestino());

                v.setOrigen(getOrigen());
                v.setEquipajeMaximo(5);
                //Transformar la fecha a Date.
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                 */
                /*
                String dateInString = getFecha() + "T" + getHora() + "Z";
                try {

                   Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
                    v.setFecha(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Hacer el post por la API.
                Call<ResponseBody> call = TeLleoService.getService(crearviaje.this).
                        postViaje(v,"dgatica");
                Log.v("call",call.request().url().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.v("alo", String.valueOf(response.code()));
                        }
                        Toast.makeText(crearviaje.this,"Viaje creado",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("ERROR", t.toString());
                    }
                });*/
            }
        });
        //El boton que accede al mapa.

    }

    private String getFecha()
    {
        return ((EditText)findViewById(R.id.textFecha)).getText().toString();
    }
    private String getHora()
    {
        return ((EditText)findViewById(R.id.textHoraS)).getText().toString();
    }
    private String getOrigen()
    {
        return "origen";
    }
    private String getDestino()
    {
        return "destino";
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
            DatePickerDialog dpd = new  DatePickerDialog(crearviaje.this,
                    AlertDialog.THEME_HOLO_LIGHT,
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
            TimePickerDialog tpd = new TimePickerDialog(crearviaje.this,
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
    private class TextoListener implements PlaceSelectionListener{
        private Place[] orDes;
        private boolean esOr;
        TextoListener(Place[] par,boolean v){
            orDes=par;
            esOr=v;
        }
        @Override
        public void onPlaceSelected(Place place) {

            if(esOr) {
                orDes[0]=place;
            } else {
                orDes[1]=place;
            }
            Log.v("ALO","LO CAMBIE A "+place.getName() );
        }

        @Override
        public void onError(Status status) {
            Log.e("Error",status.getStatusMessage());
        }
    }
}
