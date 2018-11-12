package udec.telleo;

import android.content.Intent;
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
import udec.telleo.model.Reserva;
import udec.telleo.model.Viaje;

public class ViajesReservados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_reservados);
        Intent i = getIntent();
        String origen = i.getStringExtra("origen");
        String destino = i.getStringExtra("destino");
        String finicio = i.getStringExtra("finicio");
        String ffinal = i.getStringExtra("ffinal");
        Call<List<Reserva>> call = TeLleoService.getService(ViajesReservados.this).
                getReservaPasajero("test1");
        Log.v("call",call.request().url().toString());
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
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
                for(Reserva r : response.body()){
                    if(!first){
                        View v = new View(ViajesReservados.this);
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
                    View child = getLayoutInflater().inflate(R.layout.fragmentviajebuscado,null);
                    ((TextView)child.findViewById(R.id.origen)).setText(r.getOrigen());
                    ((TextView)child.findViewById(R.id.destino)).setText(r.getDestino());
                    Reserva res = new Reserva();
                    res.setAsientos(1);
                    res.setOrigen(r.getOrigen());
                    res.setDestino(r.getDestino());
                    res.setMaletas(1);
                    //agregar parte de login (shared preferences?)
                    res.setPasajero("dongato");
                    res.setIdViaje(r.getId());
                    ((Button)child.findViewById(R.id.botonreservar))
                            .setOnClickListener(new ViajesReservados.ReservaClickListener(res,r.getId()));
                    ll.addView(child);
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
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
            Call<ResponseBody> call = TeLleoService.getService(ViajesReservados.this).
                    deleteReserva(r,id);
            Log.v("call",call.request().url().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ViajesReservados.this,"Reserva cancelada",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("ERROR", t.toString());
                }
            });

        }
    }
}
