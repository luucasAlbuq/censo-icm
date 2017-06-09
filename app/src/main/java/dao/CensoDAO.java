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
    public List<Censo> getCensoFromTo(Date datInicio, Date dataFim);

    /**
     * Recupera o censo feito em um dia específico
     * @param data
     * @return Censo
     */
    public List<Censo> getCensoByData(Date data);

    /**
     * Recupera todos os @{@link Censo} de um mes
     * @param int mes
     * @return List<Censo> colecao de objectos
     */
    public List<Censo> getCensoMes(int mes);

    /**
     * Recupera todos os @{@link Censo} de um ano
     * @param int ano
     * @return List<Censo> colecao de objectos
     */
    public List<Censo> getCensoAno(int ano);
}
