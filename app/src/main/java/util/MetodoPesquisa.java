package util;

/**
 * Define os métodos de pesquisa disponiveis
 */
public enum MetodoPesquisa {
    POR_MES("Por Mês"),
    POR_DIA("Por Dia");

    private String valor;

    private MetodoPesquisa(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
