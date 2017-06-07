package service;

import android.util.Log;

import java.util.Date;
import java.util.Set;

import dao.CensoDAO;
import dao.CensoDAOImpl;
import model.Censo;
import validator.CensoValidator;
import validator.CensoValidatorImpl;

/**
 * Created by luucasAlbuq on 06/06/2017.
 */
public class CensoServiceImpl implements CensoService {

    private CensoValidator censoValidator = new CensoValidatorImpl();
    private CensoDAO censoDAO = CensoDAOImpl.getInstance();

    @Override
    public boolean cadastrar(Censo censo) throws Exception {
        boolean success = false;
        try {
            censoValidator.isCensoValidForSave(censo);
            success = censoDAO.save(censo);
        } catch (Exception e) {
            Log.e("Controller:",e.getMessage());
            throw new Exception(e.getMessage());
        }
        return success;
    }

    @Override
    public boolean atualizar(Censo censo, String censoID) throws Exception {
        boolean success = false;
        try {
            censoValidator.isCensoValidForUpdate(censo, censoID);
            success = censoDAO.update(censo, censoID);
        } catch (Exception e) {
            Log.e("Controller:",e.getMessage());
            throw new Exception(e.getMessage());
        }
        return success;
    }

    @Override
    public Censo getCensoByData(Date data) {
        return null;
    }

    @Override
    public Censo getCensoById(String censoId) {
        return null;
    }

    @Override
    public Set<Censo> getCensoByMes(int mes) {
        return null;
    }

    @Override
    public Set<Censo> getCensoByAno(int ano) {
        return null;
    }
}
