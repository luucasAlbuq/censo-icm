package service;

import android.util.Log;

import java.util.Date;
import java.util.List;

import dao.CensoDAO;
import dao.CensoDAOImpl;
import model.Censo;
import validator.CensoValidator;
import validator.CensoValidatorImpl;

/**
 * Created by luucasAlbuq on 06/06/2017.
 */
public class CensoServiceImpl implements CensoService {

    private static CensoService censoService;
    private CensoValidator censoValidator = new CensoValidatorImpl();
    private CensoDAO censoDAO = CensoDAOImpl.getInstance();

    private CensoServiceImpl() {};

    public static synchronized CensoService getInstance() {
        if(censoService == null){
            censoService = new CensoServiceImpl();
        }
        return censoService;
    }

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
    public boolean delete(String id) throws Exception {
        boolean sucess = true;
        try{
            censoValidator.isCensoValidForDelete(id);
            sucess = censoDAO.delete(id);
        }catch (Exception e){
            Log.e("Controller:",e.getMessage());
            sucess = false;
            throw new Exception(e.getMessage());
        }
        return sucess;
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
    public List<Censo> getCensoByData(Date data) throws Exception {
        List<Censo> censo = null;
        try{
            censo = censoDAO.getCensoByData(data);
        }catch (Exception e){
            Log.e("Controller:",e.getMessage());
            throw new Exception(e.getMessage());
        }
        return censo;
    }

    @Override
    public Censo getCensoById(String censoId) {
        return null;
    }

    @Override
    public List<Censo> getCensoBetweenDates(Date from, Date to) throws Exception {
        List<Censo> censos = null;
        try{
            censoValidator.isCensoValidSearchBetweenDates(from,to);
            censos = censoDAO.getCensoBetweenDates(from, to);
        }catch (Exception e){
            Log.e("Controller:",e.getMessage());
            throw new Exception(e.getMessage());
        }
        return censos;
    }
}
