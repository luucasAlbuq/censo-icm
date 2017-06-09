package service;

import java.util.Date;
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
    public Censo getCensoByData(Date data) throws Exception;
    public Censo getCensoById(String censoId);
    public Set<Censo> getCensoByMes(int mes);
    public Set<Censo> getCensoByAno(int ano);
}
