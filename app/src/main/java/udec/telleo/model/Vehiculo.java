package udec.telleo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Vehiculo implements Serializable {
    //region Variables
    @SerializedName("patente")
    private String patente;

    @SerializedName("marca")
    private String marca;

    @SerializedName("modelo")
    private String modelo;

    @SerializedName("conductor")
    private String conductor;

    @SerializedName("aptoSillaBebe")
    private boolean aptoParaSillaBebe;

    @SerializedName("dosPasajerosAtras")
    private boolean dosPasajerosAtras;

    @SerializedName("capacidadEquipaje")
    private int capacidadEquipaje;

    @SerializedName("numeroPlazas")
    private int numeroPlazas;
    //endregion

    //region Accessors/Mutators
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public boolean isAptoParaSillaBebe() {
        return aptoParaSillaBebe;
    }

    public void setAptoParaSillaBebe(boolean aptoParaSillaBebe) {
        this.aptoParaSillaBebe = aptoParaSillaBebe;
    }

    public boolean isDosPasajerosAtras() {
        return dosPasajerosAtras;
    }

    public void setDosPasajerosAtras(boolean dosPasajerosAtras) {
        this.dosPasajerosAtras = dosPasajerosAtras;
    }

    public int getCapacidadEquipaje() {
        return capacidadEquipaje;
    }

    public void setCapacidadEquipaje(int capacidadEquipaje) {
        this.capacidadEquipaje = capacidadEquipaje;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }
    //endregion

    @Override
    public String toString(){
        return String.format("%s %s %s %s %s %s %s %s",
                patente,
                marca,
                modelo,
                conductor,
                String.valueOf(aptoParaSillaBebe),
                String.valueOf(dosPasajerosAtras),
                String.valueOf(capacidadEquipaje),
                String.valueOf(numeroPlazas));
    }
}