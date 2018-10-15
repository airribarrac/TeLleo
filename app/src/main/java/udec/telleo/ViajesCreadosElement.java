package udec.telleo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import udec.telleo.apiclient.AsyncCall;
import udec.telleo.apiclient.Client;
import udec.telleo.model.Viaje;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViajesCreadosElement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViajesCreadosElement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViajesCreadosElement extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int idViaje;

    private OnFragmentInteractionListener mListener;

    public ViajesCreadosElement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViajesCreadosElement.
     */
    // TODO: Rename and change types and number of parameters
    public static ViajesCreadosElement newInstance(int idViaje) {
        ViajesCreadosElement fragment = new ViajesCreadosElement();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, idViaje);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idViaje = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_viajes_creados_element, container, false);
        view.findViewById(R.id.element_main_layout).setOnClickListener(this);
        // se que no deberia, perdon :(
        Client.getViaje(idViaje, this.getContext(), new AsyncCall<Viaje>() {
            @Override
            public void onSuccess(Viaje viaje) {
                if(viaje != null){
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    ((TextView)view.findViewById(R.id.origen)).setText(viaje.getOrigen());
                    ((TextView)view.findViewById(R.id.destino)).setText(viaje.getDestino());
                    ((TextView)view.findViewById(R.id.fecha)).setText(df.format(viaje.getFecha()));
                    ((TextView)view.findViewById(R.id.vehiculo)).setText(viaje.getVehiculo().getMarca() + " " + viaje.getVehiculo().getModelo());
                    ((TextView)view.findViewById(R.id.equipajemax)).setText("Equipaje maximo: " + viaje.getEquipajeMaximo());
                }
            }

            @Override
            public void onFailure(Throwable err) {

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
