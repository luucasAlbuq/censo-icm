package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Censo;

/**
 * Created by lucas.almeida on 19/06/2017.
 */

public class EstatisticaMes {
    private Date dataInicio;
    private Date dataFim;
    private int totalPessoas;
    private int totalAdolescentes = 0;
    private int totalJovens = 0;
    private int totalCriancas = 0;
    private int totalSenhoras = 0;
    private int totalVaroes = 0;
    private int totalVisitantes = 0;
    private float mediaPessoas;
    private float mediaPorcentagemJovens;
    private float mediaPorcentagemCriancas;
    private float mediaPorcentagemAdolescentes;
    private float mediaPorcentagemSenhoras;
    private float mediaPorcentagemVaroes;
    private float mediaPorcentagemVisitantes;
    private List<Censo> diasMaisVisitados;

    public EstatisticaMes(){};


    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public int getTotalPessoas() {
        return totalPessoas;
    }

    public void setTotalPessoas(int totalPessoas) {
        this.totalPessoas = totalPessoas;
    }

    public float getMediaPessoas() {
        return mediaPessoas;
    }

    public void setMediaPessoas(float mediaPessoas) {
        this.mediaPessoas = mediaPessoas;
    }

    public float getMediaPorcentagemJovens() {
        return mediaPorcentagemJovens;
    }

    public void setMediaPorcentagemJovens(float mediaPorcentagemJovens) {
        this.mediaPorcentagemJovens = mediaPorcentagemJovens;
    }

    public float getMediaPorcentagemCriancas() {
        return mediaPorcentagemCriancas;
    }

    public void setMediaPorcentagemCriancas(float mediaPorcentagemCriancas) {
        this.mediaPorcentagemCriancas = mediaPorcentagemCriancas;
    }

    public float getMediaPorcentagemAdolescentes() {
        return mediaPorcentagemAdolescentes;
    }

    public void setMediaPorcentagemAdolescentes(float mediaPorcentagemAdolescentes) {
        this.mediaPorcentagemAdolescentes = mediaPorcentagemAdolescentes;
    }

    public float getMediaPorcentagemSenhoras() {
        return mediaPorcentagemSenhoras;
    }

    public void setMediaPorcentagemSenhoras(float mediaPorcentagemSenhoras) {
        this.mediaPorcentagemSenhoras = mediaPorcentagemSenhoras;
    }

    public float getMediaPorcentagemVaroes() {
        return mediaPorcentagemVaroes;
    }

    public void setMediaPorcentagemVaroes(float mediaPorcentagemVaroes) {
        this.mediaPorcentagemVaroes = mediaPorcentagemVaroes;
    }

    public float getMediaPorcentagemVisitantes() {
        return mediaPorcentagemVisitantes;
    }

    public void setMediaPorcentagemVisitantes(float mediaPorcentagemVisitantes) {
        this.mediaPorcentagemVisitantes = mediaPorcentagemVisitantes;
    }

    public List<Censo> getDiasMaisVisitados() {
        return diasMaisVisitados;
    }

    public void setDiasMaisVisitados(List<Censo> diasMaisVisitados) {
        this.diasMaisVisitados = diasMaisVisitados;
    }

    public int getTotalAdolescentes() {
        return totalAdolescentes;
    }

    public void setTotalAdolescentes(int totalAdolescentes) {
        this.totalAdolescentes = totalAdolescentes;
    }

    public int getTotalJovens() {
        return totalJovens;
    }

    public void setTotalJovens(int totalJovens) {
        this.totalJovens = totalJovens;
    }

    public int getTotalCriancas() {
        return totalCriancas;
    }

    public void setTotalCriancas(int totalCriancas) {
        this.totalCriancas = totalCriancas;
    }

    public int getTotalSenhoras() {
        return totalSenhoras;
    }

    public void setTotalSenhoras(int totalSenhoras) {
        this.totalSenhoras = totalSenhoras;
    }

    public int getTotalVaroes() {
        return totalVaroes;
    }

    public void setTotalVaroes(int totalVaroes) {
        this.totalVaroes = totalVaroes;
    }

    public int getTotalVisitantes() {
        return totalVisitantes;
    }

    public void setTotalVisitantes(int totalVisitantes) {
        this.totalVisitantes = totalVisitantes;
    }


    @Override
    public String toString() {
        Locale BRAZIL = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);

        String report = "Entre os dias "+sdf.format(dataInicio)+" e "+sdf.format(dataFim)+" tivemos uma média diária de "+
                String.format("%.1f", mediaPessoas) +" pessoas, contabilizando um total de "+totalPessoas+" pessoas que assistiram a pelo menos um de nossos cultos entre essas datas, sendo desses: "+
                String.format("%.1f", mediaPorcentagemJovens)+"%"+"(jovens), "+String.format("%.1f", mediaPorcentagemAdolescentes)+"%"+"(adolescentes), "+String.format("%.1f", mediaPorcentagemCriancas)+"%"+"(crianças), "+
                String.format("%.1f", mediaPorcentagemSenhoras)+"%"+"(senhoras), "+String.format("%.1f", mediaPorcentagemVaroes)+"%"+"(varões) e "+String.format("%.1f", mediaPorcentagemVisitantes)+"%"+"(visitantes).";


        if(diasMaisVisitados != null ){
            report += " O dias com maior frequência foram: ";
            for(int i=0;i<diasMaisVisitados.size();i++){
                Censo censo = diasMaisVisitados.get(i);
                if(i>0){
                    report += ", "+sdf.format(censo.getData())+"(total: "+censo.getTotalPessoas()+")";
                }else{
                    report += sdf.format(censo.getData())+"(total: "+censo.getTotalPessoas()+")";
                }
            }
        }

        return report;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstatisticaMes that = (EstatisticaMes) o;

        if (getTotalPessoas() != that.getTotalPessoas()) return false;
        if (totalAdolescentes != that.totalAdolescentes) return false;
        if (totalJovens != that.totalJovens) return false;
        if (totalCriancas != that.totalCriancas) return false;
        if (totalSenhoras != that.totalSenhoras) return false;
        if (totalVaroes != that.totalVaroes) return false;
        if (totalVisitantes != that.totalVisitantes) return false;
        if (Float.compare(that.getMediaPessoas(), getMediaPessoas()) != 0) return false;
        if (Float.compare(that.getMediaPorcentagemJovens(), getMediaPorcentagemJovens()) != 0)
            return false;
        if (Float.compare(that.getMediaPorcentagemCriancas(), getMediaPorcentagemCriancas()) != 0)
            return false;
        if (Float.compare(that.getMediaPorcentagemAdolescentes(), getMediaPorcentagemAdolescentes()) != 0)
            return false;
        if (Float.compare(that.getMediaPorcentagemSenhoras(), getMediaPorcentagemSenhoras()) != 0)
            return false;
        if (Float.compare(that.getMediaPorcentagemVaroes(), getMediaPorcentagemVaroes()) != 0)
            return false;
        if (Float.compare(that.getMediaPorcentagemVisitantes(), getMediaPorcentagemVisitantes()) != 0)
            return false;
        if (getDataInicio() != null ? !getDataInicio().equals(that.getDataInicio()) : that.getDataInicio() != null)
            return false;
        if (getDataFim() != null ? !getDataFim().equals(that.getDataFim()) : that.getDataFim() != null)
            return false;
        return getDiasMaisVisitados() != null ? getDiasMaisVisitados().equals(that.getDiasMaisVisitados()) : that.getDiasMaisVisitados() == null;

    }

    @Override
    public int hashCode() {
        int result = getDataInicio() != null ? getDataInicio().hashCode() : 0;
        result = 31 * result + (getDataFim() != null ? getDataFim().hashCode() : 0);
        result = 31 * result + getTotalPessoas();
        result = 31 * result + totalAdolescentes;
        result = 31 * result + totalJovens;
        result = 31 * result + totalCriancas;
        result = 31 * result + totalSenhoras;
        result = 31 * result + totalVaroes;
        result = 31 * result + totalVisitantes;
        result = 31 * result + (getMediaPessoas() != +0.0f ? Float.floatToIntBits(getMediaPessoas()) : 0);
        result = 31 * result + (getMediaPorcentagemJovens() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemJovens()) : 0);
        result = 31 * result + (getMediaPorcentagemCriancas() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemCriancas()) : 0);
        result = 31 * result + (getMediaPorcentagemAdolescentes() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemAdolescentes()) : 0);
        result = 31 * result + (getMediaPorcentagemSenhoras() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemSenhoras()) : 0);
        result = 31 * result + (getMediaPorcentagemVaroes() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemVaroes()) : 0);
        result = 31 * result + (getMediaPorcentagemVisitantes() != +0.0f ? Float.floatToIntBits(getMediaPorcentagemVisitantes()) : 0);
        result = 31 * result + (getDiasMaisVisitados() != null ? getDiasMaisVisitados().hashCode() : 0);
        return result;
    }
}
