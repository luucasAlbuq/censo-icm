package validator;

import java.util.Date;

import model.Censo;

/**
 * Implementa o validador de @{@link Censo}
 *
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoValidatorImpl implements CensoValidator {

    //Verifica se uma string possui apenas letras
    private boolean isAlpth(String name){
        return name.trim().matches("[a-zA-Z]+");
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
            if(!isAlpth(porta)){
                return false;
            }
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

    @Override
    public void isCensoValidForDelete(String id) throws Exception {
        if(id == null || id.isEmpty()){
            throw new Exception("ID Inválido");
        }
    }

    @Override
    public void isCensoValidSearchBetweenDates(Date from, Date to) throws Exception {
        if(from.after(to)){
            throw new Exception("Dados de Pesquisa Inválidos");
        }
    }
}
