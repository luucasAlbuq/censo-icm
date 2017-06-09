package controller;

import java.util.Date;

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

    public Censo getCensoByDia(Date data) throws Exception {
        return censoService.getCensoByData(data);
    }
}
