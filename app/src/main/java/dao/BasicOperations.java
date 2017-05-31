package dao;

/**
 * Assinatura de metodos usados na persistencia de dados
 *
 * Created by luucasAlbuq on 30/05/2017.
 */

public interface BasicOperations {

    /* Basic crud */
    public <T> void save( T object);
    public <T> T update( T object, T objectId);
    public <T> boolean delete( T object);
    public <T> T read( T objectId);

}
