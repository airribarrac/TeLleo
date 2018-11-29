package udec.telleo.viewadapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.R;
import udec.telleo.ReservasConductorActivity;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;

public class ViajesAdapter extends CustomArrayAdapter<Viaje>{
    public ViajesAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.fragment_viajes_creados_element, null);
        Viaje viaje = getItem(i);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        ((TextView)convertView.findViewById(R.id.origen)).setText(viaje.getOrigen());
        ((TextView)convertView.findViewById(R.id.destino)).setText(viaje.getDestino());
        ((TextView)convertView.findViewById(R.id.fecha)).setText(df.format(viaje.getFecha()));
        ((TextView)convertView.findViewById(R.id.vehiculo)).setText(viaje.getVehiculo().getMarca() + " " + viaje.getVehiculo().getModelo());
        ((TextView)convertView.findViewById(R.id.equipajemax)).setText("Equipaje maximo: " + viaje.getEquipajeMaximo());
        ((Button)convertView.findViewById(R.id.cancelar_viaje)).setOnClickListener(new CancelarViajeClickListener(viaje, parent.getContext()));
        convertView.setOnClickListener(new ViajeClickListener(viaje, parent.getContext()));
        return convertView;
    }

    class ViajeClickListener implements View.OnClickListener{
        private Context context;
        private Viaje viaje;
        ViajeClickListener(Viaje viaje, Context context){
            this.context = context;
            this.viaje = viaje;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ReservasConductorActivity.class);
            intent.putExtra("viaje",viaje);
            context.startActivity(intent);
        }
    }

    class CancelarViajeClickListener implements View.OnClickListener{
        private Context context;
        private Viaje viaje;
        CancelarViajeClickListener(Viaje viaje, Context context){
            this.context = context;
            this.viaje = viaje;
        }
        @Override
        public void onClick(View view) {
            Call<ResponseBody> call = TeLleoService.getService(this.context).deleteViaje(viaje.getId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        deleteItem(viaje);
                    }
                    else{
                        Log.d("DeleteViaje", response.message());
                        Log.d("DeleteViaje", "code: " + response.code());
                        Log.d("DeleteViaje", response.raw().toString());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("DeleteViaje", "fallo llamada api", t);
                }
            });
        }
    }
}
