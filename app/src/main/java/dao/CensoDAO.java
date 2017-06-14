package dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import model.Censo;

/**
 * Define recursos especificos para @{@link Censo} armazenadas no BD
 * Created by lucas.almeida on 30/05/2017.
 */
public interface CensoDAO extends BasicOperations {

    /**
     * Recupera todos os @{@link Censo} entre o intervelo de duas datas
     *
     * @param Date datInicio
     * @param Date dataFim
     * @return List<Censo> colecao de objectos
     */
    public List<Censo> getCensoBetweenDates(Date datInicio, Date dataFim);

    /**
     * Recupera o censo feito em um dia específico
     * @param data
     * @return Censo
     */
    public List<Censo> getCensoByData(Date data);

}
