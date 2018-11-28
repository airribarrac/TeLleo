package udec.telleo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData  implements Serializable {
    //region Variables
    @SerializedName("usuario")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("conductor")
    private boolean conductor;

    public void setEsConductor(boolean esConductor){
        conductor = esConductor;
    }

    public void setPassword(String pass){
        password = pass;
    }

    public void setUsername(String user){
        username = user;
    }

    public boolean esConductor(){
        return conductor;
    }
}
