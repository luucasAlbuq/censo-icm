package validator;

import model.Censo;

/**
 * Implementa o validador de @{@link Censo}
 *
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoValidatorImpl implements CensoValidator {

    @Override
    public boolean isCensoValid(Censo censo) {
        return false;
    }

    @Override
    public boolean isCensoValidForUpdate(Censo censo, Object censoId) {
        return false;
    }

    @Override
    public boolean isCensoValidForSave(Censo censo) {
        return false;
    }
}
