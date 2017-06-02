package dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Set;

import model.Censo;

/**
 * Implementa as assinaturas para manipulacao de objetos @{@link Censo}
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoDAOImpl implements CensoDAO {

    private  DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    @Override
    public <T> void save(T object) {
        //Gerando Id
        String censoId = db.push().getKey();
        db.child(censoId).setValue(object);
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
