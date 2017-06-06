package validator;

import model.Censo;

/**
 * Implementa o validador de @{@link Censo}
 *
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoValidatorImpl implements CensoValidator {

    //Verifica se uma string possui apenas letras
    private boolean isAlpth(String name){
        return name.matches("[a-zA-Z]+");
    }

    @Override
    public boolean isCensoValid(Censo censo) {
        boolean isValid = true;

        if(censo == null) return false;
        if(censo.getData() == null) return false;
        if(censo.getObreiroPalavra() == null) return false;
        if(!isAlpth(censo.getObreiroPalavra())) return false;
        if(censo.getObreiroLouvor() == null) return false;
        if(!isAlpth(censo.getObreiroLouvor())) return false;
        if(censo.getTotalPessoas() <= 0) return false;
        if((censo.getQtdAdolescentes() + censo.getQtdCriancas() + censo.getQtdJovens()
                + censo.getQtdSenhoras() + censo.getQtdVaroes() + censo.getQtdVisitantes())
                != censo.getTotalPessoas()){
            return false;
        }

        for(String porta: censo.getObreirosPorta()){
            if(!isAlpth(porta)) return false;
        }

        return isValid;
    }

    @Override
    public void isCensoValidForUpdate(Censo censo, Object censoId) throws Exception {
        if(!censo.getId().equals(censoId) || !isCensoValid(censo)){
            throw new Exception("Dados Inválidos");
        }
    }

    @Override
    public void isCensoValidForSave(Censo censo) throws Exception {
        if(!isCensoValid(censo)){
            throw new Exception("Dados Inválidos");
        }
    }
}
