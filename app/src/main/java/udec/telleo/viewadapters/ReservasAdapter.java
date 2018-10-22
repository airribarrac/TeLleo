package udec.telleo.viewadapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.R;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Reserva;
import udec.telleo.model.Viaje;

public class ReservasAdapter extends CustomArrayAdapter<Reserva> {
    private Context context;
    private Viaje viaje;
    public ReservasAdapter(@NonNull Context context, int resource, Viaje viaje)
    {
        super(context, resource);
        this.context = context;
        this.viaje = viaje;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.reserva_recibida_element, null);
        Reserva reserva = getItem(i);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        ((TextView)convertView.findViewById(R.id.origen)).setText(reserva.getOrigen());
        ((TextView)convertView.findViewById(R.id.destino)).setText(reserva.getDestino());
        ((TextView)convertView.findViewById(R.id.nombre_persona)).setText(reserva.getPasajero());
        ((TextView)convertView.findViewById(R.id.maletas)).setText("Maletas: " + reserva.getMaletas());
        ((TextView)convertView.findViewById(R.id.asientos)).setText("Asientos: " + reserva.getAsientos());
        String estado = reserva.getEstado() == 1 ? "pendiente" : (reserva.getEstado() == 2 ? "aprobada" : "rechazada");
        TextView estado_tv = (TextView)convertView.findViewById(R.id.estado);
        estado_tv.setText(estado);

        convertView.findViewById(R.id.aceptar_button).setOnClickListener(new SetEstadoActionListener(estado_tv, reserva, 2));
        convertView.findViewById(R.id.rechazar_button).setOnClickListener(new SetEstadoActionListener(estado_tv, reserva, 3));
        return convertView;
    }

    class SetEstadoActionListener implements OnClickListener{
        private TextView textView;
        private int estado;
        private Reserva reserva;
        public SetEstadoActionListener(TextView textView, Reserva reserva, int estado){
            this.estado = estado;
            this.textView = textView;
            this.reserva = reserva;
        }
        @Override
        public void onClick(View view) {
            final String texto = estado == 1 ? "pendiente" : (estado == 2 ? "aprobada" : "rechazada");
            Reserva r = new Reserva();
            r.setEstado(estado);
            String username = context.getSharedPreferences("datos", Context.MODE_PRIVATE).getString("username", "");
            Call<ResponseBody> call = TeLleoService.getService(getContext()).setEstadoReserva(viaje.getVehiculo().getConductor(),viaje.getId(),reserva.getId(), r);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        reserva.setEstado(estado);
                        Context context = getContext();
                        CharSequence text = "Se cambio el estado del viaje";
                        int duration = Toast.LENGTH_LONG;
                        textView.setText(texto);
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                    else{

                        Context context = getContext();
                        CharSequence text = "No se pudo cambiar el estado del viaje :( " + response.code();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Context context = getContext();
                    CharSequence text = "No se pudo cambiar el estado del viaje creados :(";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });

        }
    }
}
