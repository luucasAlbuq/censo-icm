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

    private CensoService censoService = new CensoServiceImpl();

    public boolean cadastrar(Censo censo) throws Exception {
        return censoService.cadastrar(censo);
    }

    public boolean atualizar(Censo censo) throws Exception {
        return censoService.atualizar(censo, censo.getId());
    }

    public List<Censo> getCensoByDate(Date data) throws Exception {
        return censoService.getCensoByData(data);
    }

    public List<Censo> getCensoBetweenDates(Date from, Date to) throws Exception {
        return censoService.getCensoBetweenDates(from,to);
    }
}
