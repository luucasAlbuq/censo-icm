package dao;

import java.util.Date;
import java.util.Set;

import model.Censo;

/**
 * Implementa as assinaturas para manipulacao de objetos @{@link Censo}
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoDAOImpl implements CensoDAO {

    @Override
    public <T> void save(T object) {

    }

    @Override
    public <T> T update(T object, T objectId) {
        return null;
    }

    @Override
    public <T> boolean delete(T object) {
        return false;
    }

    @Override
    public <T> T read(T objectId) {
        return null;
    }

    @Override
    public Set<Censo> getCensoFromTo(Date datInicio, Date dataFim) {
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
