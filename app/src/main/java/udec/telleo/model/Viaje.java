package udec.telleo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Viaje {
    //region Variables
    @SerializedName("id")
    private int id;

    @SerializedName("origen")
    private String origen;

    @SerializedName("destino")
    private String destino;

    @SerializedName("fecha")
    private Date fecha;

    @SerializedName("equipajeMaximo")
    private int equipajeMaximo;

    @SerializedName("vehiculo")
    private Vehiculo vehiculo;
    //endregion

    //region Accessors/Mutators
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEquipajeMaximo() {
        return equipajeMaximo;
    }

    public void setEquipajeMaximo(int equipajeMaximo) {
        this.equipajeMaximo = equipajeMaximo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    //endregion

    @Override
    public String toString(){
        return (String.format("%s, %s, %s, %s, %s, Vehiculo: %s",
                String.valueOf(id),
                origen,
                destino,
                fecha.toString(),
                String.valueOf(equipajeMaximo),
                vehiculo.toString()));
    }
}
