package controller;

import java.util.Date;
import java.util.List;

import model.Censo;
import service.CensoService;
import service.CensoServiceImpl;

/**
 * Created by luucasAlbuq on 06/06/2017.
 */
public class CensoController {

    private static CensoController controller;
    private CensoService censoService = CensoServiceImpl.getInstance();

    private CensoController(){};

    public static synchronized CensoController getInstance() {
        if(controller == null){
            controller = new CensoController();
        }
        return controller;
    }

    public boolean cadastrar(Censo censo) throws Exception {
        return censoService.cadastrar(censo);
    }

    public boolean atualizar(Censo censo) throws Exception {
        return censoService.atualizar(censo, censo.getId());
    }

    public boolean delete(String id) throws Exception{
        return censoService.delete(id);
    }

    public List<Censo> getCensoByDate(Date data) throws Exception {
        return censoService.getCensoByData(data);
    }

    public List<Censo> getCensoBetweenDates(Date from, Date to) throws Exception {
        return censoService.getCensoBetweenDates(from,to);
    }
}
