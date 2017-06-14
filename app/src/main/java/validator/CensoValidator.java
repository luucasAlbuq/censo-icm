package validator;

import java.util.Date;

import model.Censo;

/**
 * Define se um objeto censo é valido para realizar uma determinada operacao
 * Created by lucas.almeida on 30/05/2017.
 */

public interface CensoValidator {

    public boolean isCensoValid(Censo censo);
    public void isCensoValidForUpdate(Censo censo, Object censoId) throws Exception;
    public void isCensoValidForSave(Censo censo) throws Exception;
    public void isCensoValidSearchDetweenDates(Date from, Date to) throws Exception;
}
