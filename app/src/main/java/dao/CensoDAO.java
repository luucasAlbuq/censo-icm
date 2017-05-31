package dao;

import java.util.Date;
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
     * @return Set<Censo> colecao de objectos
     */
    public Set<Censo> getCensoFromTo(Date datInicio, Date dataFim);

    /**
     * Recupera todos os @{@link Censo} de um mes
     * @param int mes
     * @return Set<Censo> colecao de objectos
     */
    public Set<Censo> getCensoMes(int mes);

    /**
     * Recupera todos os @{@link Censo} de um ano
     * @param int ano
     * @return Set<Censo> colecao de objectos
     */
    public Set<Censo> getCensoAno(int ano);
}
