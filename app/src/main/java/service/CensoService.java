package service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import model.Censo;

/**
 * Assinatura de servicos
 *
 * Created by luucasAlbuq on 06/06/2017.
 */
public interface CensoService {

    public boolean cadastrar(Censo censo) throws Exception;
    public boolean atualizar(Censo censo, String censoID) throws Exception;
    public List<Censo> getCensoByData(Date data) throws Exception;
    public Censo getCensoById(String censoId);
    public List<Censo> getCensoByMes(int mes);
    public List<Censo> getCensoByAno(int ano);
    public List<Censo> getCensoBetweenDates(Date from, Date to) throws Exception;
}
