package validator;

import model.Censo;

/**
 * Define se um objeto censo é valido para realizar uma determinada operacao
 * Created by lucas.almeida on 30/05/2017.
 */

public interface CensoValidator {

    public boolean isCensoValid(Censo censo);
    public boolean isCensoValidForUpdate(Censo censo, Object censoId);
    public boolean isCensoValidForSave(Censo censo);
}
