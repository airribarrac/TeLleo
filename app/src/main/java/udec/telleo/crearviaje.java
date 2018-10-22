package udec.telleo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udec.telleo.apiclient.TeLleoService;
import udec.telleo.model.Viaje;

public class crearviaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearviaje);
        //Boton de Confirmar
        Button conf = findViewById(R.id.confirmar);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Para ver si lee.
                Log.d("Fecha: ",getFecha());
                Log.d("Hora: ",getHora());
                Log.d("Origen: ",getOrigen());
                Log.d("Destino: ",getDestino());
                //Agregar datos a un viaje.
                Viaje v = new Viaje();
                v.setDestino(getDestino());
                v.setOrigen(getOrigen());
                v.setEquipajeMaximo(5);
                //Transformar la fecha a Date.
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                String dateInString = getFecha() + "T" + getHora() + "Z";
                try {

                   Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
                    v.setFecha(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Hacer el post por la API.
                Call<ResponseBody> call = TeLleoService.getService(crearviaje.this).
                        postViaje(v,"ficoqlo");
                Log.v("call",call.request().url().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(crearviaje.this,"Viaje creado",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("ERROR", t.toString());
                    }
                });
            }
        });
        //El boton que accede al mapa.
        Button mapa = findViewById(R.id.mapita);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* LatitudeAndLongitudeWithPincode lt = new LatitudeAndLongitudeWithPincode();
                try {
                    String[] st = LatitudeAndLongitudeWithPincode.getLatLongPositions("Concepcion, Chile");
                    System.out.println(st[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                Intent i = new Intent(crearviaje.this, MapsActivity.class);
                i.putExtra("origen", getOrigen());
                i.putExtra("destino",getDestino());
                startActivity(i);
            }
        });
    }

    private String getFecha()
    {
        return ((EditText)findViewById(R.id.textFecha)).getText().toString();
    }
    private String getHora()
    {
        return ((EditText)findViewById(R.id.textHora)).getText().toString();
    }
    private String getOrigen()
    {
        return ((EditText)findViewById(R.id.textOrigen)).getText().toString();
    }
    private String getDestino()
    {
        return ((EditText)findViewById(R.id.textDestino)).getText().toString();
    }
}

/**
 * This class will get the lat long values.
 */
class LatitudeAndLongitudeWithPincode
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("java.net.useSystemProxies", "true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter a location:");
        String postcode=reader.readLine();
        String latLongs[] = getLatLongPositions(postcode);
        System.out.println("Latitude: "+latLongs[0]+" and Longitude: "+latLongs[1]);
    }

    public static String[] getLatLongPositions(String address) throws Exception
    {
        int responseCode = 0;
        String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
        System.out.println("URL : "+api);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if(responseCode == 200)
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
            Document document = builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String)expr.evaluate(document, XPathConstants.STRING);
            if(status.equals("OK"))
            {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
                return new String[] {latitude, longitude};
            }
            else
            {
                throw new Exception("Error from the API - response status: "+status);
            }
        }
        return null;
    }
}