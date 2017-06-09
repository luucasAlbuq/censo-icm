package dao;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.Set;

import model.Censo;
import util.DBEsquema;

/**
 * Implementa as assinaturas para manipulacao de objetos @{@link Censo}
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoDAOImpl implements CensoDAO {

    private static CensoDAO censoDAO;
    private CensoDAOImpl(){};

    public static synchronized CensoDAO getInstance() {
        if(censoDAO == null){
            censoDAO = new CensoDAOImpl();
        }
        return censoDAO;
    }

    private ParseObject buildParseObj(Censo censo){
        ParseObject object = new ParseObject(DBEsquema.TABLE.getValor());
        object.put(DBEsquema.COL_NOME_IGREJA.getValor(), "icm-azenha");
        object.put(DBEsquema.COL_USER.getValor(),ParseUser.getCurrentUser().getUsername());

        object.put(DBEsquema.COL_DATA.getValor(), censo.getData());
        object.put(DBEsquema.COL_QTD_JOVENS.getValor(), censo.getQtdJovens());
        object.put(DBEsquema.COL_QTD_ADOLESCENTES.getValor(), censo.getQtdAdolescentes());
        object.put(DBEsquema.COL_QTD_CRIANCAS.getValor(), censo.getQtdCriancas());
        object.put(DBEsquema.COL_QTD_VISITANTES.getValor(), censo.getQtdVisitantes());
        object.put(DBEsquema.COL_QTD_SENHORAS.getValor(), censo.getQtdSenhoras());
        object.put(DBEsquema.COL_QTD_VAROES.getValor(), censo.getQtdVaroes());
        object.put(DBEsquema.COL_TOTAL.getValor(), censo.getTotalPessoas());
        object.put(DBEsquema.COL_DOM.getValor(), censo.getDom());
        object.put(DBEsquema.COL_TEXT_BIBLICO.getValor(), censo.getTextoBiblico());
        object.put(DBEsquema.COL_LOUVORES.getValor(), censo.getLouvores());
        object.put(DBEsquema.COL_OBRE_LOUVOR.getValor(), censo.getObreiroLouvor());
        object.put(DBEsquema.COL_OBRE_PALAVRA.getValor(), censo.getObreiroPalavra());
        object.put(DBEsquema.COL_OBRE_PORTA.getValor(), censo.getObreirosPorta());
        return object;
    }

    @Override
    public <T> boolean save(final T object) {
        boolean isSaved = false;
        try{
            ParseObject censo = buildParseObj((Censo) object);
            censo.save();
            isSaved = true;
        }catch (Exception e){
            Log.e("DB: save: ",e.getMessage());
        }
        return isSaved;
    }

    @Override
    public <T> boolean update(T object, T objectId) {
        return false;
    }


    @Override
    public <T> boolean delete(T objectId) {
        return false;
    }

    @Override
    public <T> Censo read(T objectId) {
        return null;
    }

    @Override
    public Set<Censo> getCensoFromTo(Date dataInicio, Date dataFim) {
        return null;
    }

    @Override
    public Censo getCensoByData(Date data) {
        return null;
    }

    @Override
    public Set<Censo> getCensoMes(int mes) {
        return null;
    }

    @Override
    public Set<Censo> getCensoAno(int ano) {
        return null;
    }
}

