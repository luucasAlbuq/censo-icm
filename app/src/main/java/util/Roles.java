package util;

/**
 * Created by luucasAlbuq on 14/06/2017.
 */
public enum Roles {
    ADMIN("admin"), USER("user");

    private String valor;

    private Roles(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
