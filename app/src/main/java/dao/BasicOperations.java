package dao;

/**
 * Assinatura de metodos usados na persistencia de dados
 *
 * Created by luucasAlbuq on 30/05/2017.
 */

public interface BasicOperations {

    /* Basic crud */
    public <T> boolean save( T object);
    public <T> boolean update( T object, T objectId);
    public <T> boolean delete( T objectId);
    public <T> Object read( T objectId);

}
