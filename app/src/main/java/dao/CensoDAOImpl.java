package dao;

import android.util.Log;

import java.util.Date;
import java.util.Set;

import model.Censo;

/**
 * Implementa as assinaturas para manipulacao de objetos @{@link Censo}
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoDAOImpl implements CensoDAO {

    private static CensoDAO censoDAO;
    private CensoDAOImpl(){};

    public static synchronized CensoDAO getInstance() {
        if(censoDAO == null){
            censoDAO = new CensoDAOImpl();
        }
        return censoDAO;
    }

    @Override
    public <T> boolean save(final T object) {
        boolean isSaved = false;
        try{

        }catch (Exception e){
            Log.e("DB: save: ",e.getMessage());
        }finally {
            return isSaved;
        }
    }

    @Override
    public <T> boolean update(T object, T objectId) {
        return false;
    }


    @Override
    public <T> boolean delete(T objectId) {
        return false;
    }

    @Override
    public <T> Censo read(T objectId) {
        return null;
    }

    @Override
    public Set<Censo> getCensoFromTo(Date dataInicio, Date dataFim) {
        return null;
    }

    @Override
    public Censo getCensoByData(Date data) {
        return null;
    }

    @Override
    public Set<Censo> getCensoMes(int mes) {
        return null;
    }

    @Override
    public Set<Censo> getCensoAno(int ano) {
        return null;
    }
}
