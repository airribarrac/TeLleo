package udec.telleo.viewadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import udec.telleo.R;
import udec.telleo.model.Reserva;
import udec.telleo.model.Viaje;

public class ReservasAdapter extends CustomArrayAdapter<Reserva> {
    public ReservasAdapter(@NonNull Context context, int resource) {
        super(context, resource);
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
        ((TextView)convertView.findViewById(R.id.estado)).setText(estado);
        return convertView;
    }
}
