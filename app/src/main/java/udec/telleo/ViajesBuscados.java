package udec.telleo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Precio;
import udec.telleo.model.Reserva;
import udec.telleo.model.Viaje;

public class ViajesBuscados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_buscados);
        Intent i = getIntent();
        String origen = i.getStringExtra("origen");
        String destino = i.getStringExtra("destino");
        String finicio = i.getStringExtra("finicio");
        String ffinal = i.getStringExtra("ffinal");
        Call<List<Viaje>> call = TeLleoService.getService(ViajesBuscados.this).
                getViajes(origen,destino,finicio,ffinal,1,0);
        Log.v("call",call.request().url().toString());
        call.enqueue(new Callback<List<Viaje>>() {
            @Override
            public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
                if(!response.isSuccessful()){
                    Log.e("response","fallo "+response.code());

                }
                Log.d("respuesta","  "+response.toString());
                LinearLayout ll = findViewById(R.id.llayout);
                findViewById(R.id.progressBar).setClickable(false);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                findViewById(R.id.textobuscando).setClickable(false);
                findViewById(R.id.textobuscando).setVisibility(View.GONE);
                boolean first=true;
                for(final Viaje r : response.body()){
                    if(!first){
                        View v = new View(ViajesBuscados.this);
                        v.setLayoutParams(
                                new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        5
                                )
                        );
                        v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                    }
                    first = false;
                    Log.d("reserva:", r.toString());
                    final View child = getLayoutInflater().inflate(R.layout.fragmentviajebuscado,null);
                    ((TextView)child.findViewById(R.id.origen)).setText(r.getOrigen());
                    ((TextView)child.findViewById(R.id.destino)).setText(r.getDestino());
                    ((TextView)child.findViewById(R.id.fecha)).setText(r.getFecha().toLocaleString());
                    ((TextView)child.findViewById(R.id.vehiculo)).
                            setText(r.getVehiculo().getMarca()+" "+r.getVehiculo().getModelo());
                    Reserva res = new Reserva();
                    res.setAsientos(1);
                    res.setOrigen(r.getOrigen());
                    res.setDestino(r.getDestino());
                    res.setMaletas(1);
                    SharedPreferences sp = getSharedPreferences("datos", MODE_PRIVATE);
                    String usuario = sp.getString("usuario", "");
                    res.setPasajero(usuario);
                    res.setIdViaje(r.getId());
                    Call<Precio> call2 = TeLleoService.getService(ViajesBuscados.this).getPrecio(r.getId(), r.getOrigen(), r.getDestino());
                    call2.enqueue(new Callback<Precio>() {
                        @Override
                        public void onResponse(Call<Precio> call, Response<Precio> response) {
                            if(response.code() == 200 && response.body() != null)
                            ((TextView)child.findViewById(R.id.precio)).setText("$" + response.body().getPrecio());
                        }

                        @Override
                        public void onFailure(Call<Precio> call, Throwable t) {

                        }
                    });
                    ((Button)child.findViewById(R.id.botonreservar))
                            .setOnClickListener(new ReservaClickListener(res,r.getId()));
                    ll.addView(child);
                }
            }

            @Override
            public void onFailure(Call<List<Viaje>> call, Throwable t) {
                Log.d("ERROR", t.toString());
            }
        });

    }
    private class ReservaClickListener implements View.OnClickListener{
        private Reserva r;
        private int id;
        public ReservaClickListener(Reserva rr,int idviaje){
            r=rr;
            id=idviaje;
        }
        @Override
        public void onClick(View view) {
            //mandar reserva a api
            Call<ResponseBody> call = TeLleoService.getService(ViajesBuscados.this).
                    postReserva(r,id);
            Log.v("call",call.request().url().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200)
                        Toast.makeText(ViajesBuscados.this,"Reserva realizada",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(ViajesBuscados.this,"No puedes solicitar mas reservas para este viaje",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("ERROR", t.toString());
                }
            });

        }
    }
}
