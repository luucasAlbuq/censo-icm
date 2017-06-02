package model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.Set;

/**
 * Entidade que armazena dados sobre o censo de um culto
 * Created by luucasAlbuq on 30/05/2017.
 */
@IgnoreExtraProperties
public class Censo {

    private int qtdJovens;
    private int qtdCriancas;
    private int qtdVisitantes;
    private int qtdSenhoras;
    private int qtdVaroes;
    private int qtdAdolescentes;
    private int totalPessoas;
    private String dom;
    private Set<String> obreirosPorta;
    private String obreiroLouvor;
    private String obreiroPalavra;
    private Date data;
    private Object id;

    public int getQtdAdolescentes() {
        return qtdAdolescentes;
    }

    public void setQtdAdolescentes(int qtdAdolescentes) {
        this.qtdAdolescentes = qtdAdolescentes;
    }

    public int getQtdJovens() {
        return qtdJovens;
    }

    public void setQtdJovens(int qtdJovens) {
        this.qtdJovens = qtdJovens;
    }

    public int getQtdCriancas() {
        return qtdCriancas;
    }

    public void setQtdCriancas(int qtdCriancas) {
        this.qtdCriancas = qtdCriancas;
    }

    public int getQtdVisitantes() {
        return qtdVisitantes;
    }

    public void setQtdVisitantes(int qtdVisitantes) {
        this.qtdVisitantes = qtdVisitantes;
    }

    public int getQtdSenhoras() {
        return qtdSenhoras;
    }

    public void setQtdSenhoras(int qtdSenhoras) {
        this.qtdSenhoras = qtdSenhoras;
    }

    public int getQtdVaroes() {
        return qtdVaroes;
    }

    public void setQtdVaroes(int qtdVaroes) {
        this.qtdVaroes = qtdVaroes;
    }

    public int getTotalPessoas() {
        return totalPessoas;
    }

    public void setTotalPessoas(int totalPessoas) {
        this.totalPessoas = totalPessoas;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public Set<String> getObreirosPorta() {
        return obreirosPorta;
    }

    public void setObreirosPorta(Set<String> obreirosPorta) {
        this.obreirosPorta = obreirosPorta;
    }

    public String getObreiroLouvor() {
        return obreiroLouvor;
    }

    public void setObreiroLouvor(String obreiroLouvor) {
        this.obreiroLouvor = obreiroLouvor;
    }

    public String getObreiroPalavra() {
        return obreiroPalavra;
    }

    public void setObreiroPalavra(String obreiroPalavra) {
        this.obreiroPalavra = obreiroPalavra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Censo censo = (Censo) o;

        if (!getData().equals(censo.getData())) return false;
        return getId().equals(censo.getId());

    }

    @Override
    public int hashCode() {
        int result = getData().hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Censo{" +
                "qtdJovens=" + qtdJovens +
                ", qtdCriancas=" + qtdCriancas +
                ", qtdVisitantes=" + qtdVisitantes +
                ", qtdSenhoras=" + qtdSenhoras +
                ", qtdVaroes=" + qtdVaroes +
                ", qtdAdolescentes=" + qtdAdolescentes +
                ", totalPessoas=" + totalPessoas +
                ", dom='" + dom + '\'' +
                ", obreirosPorta=" + obreirosPorta +
                ", obreiroLouvor='" + obreiroLouvor + '\'' +
                ", obreiroPalavra='" + obreiroPalavra + '\'' +
                ", data=" + data +
                ", id=" + id +
                '}';
    }
}
