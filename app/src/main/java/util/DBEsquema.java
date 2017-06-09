package util;

/**
 * Created by luucasAlbuq on 09/06/2017.
 */
public enum DBEsquema {
    TABLE("censo"),
    COL_USER("usuario"),
    COL_QTD_JOVENS("qtd_jovens"),
    COL_QTD_CRIANCAS("qtd_criancas"),
    COL_QTD_VISITANTES("qtd_visitantes"),
    COL_QTD_SENHORAS("qtd_senhoras"),
    COL_QTD_VAROES("qtd_varoes"),
    COL_QTD_ADOLESCENTES("qtd_adolescentes"),
    COL_TOTAL("qtd_total"),
    COL_DOM("dom"),
    COL_OBRE_LOUVOR("obreiro_louvor"),
    COL_OBRE_PALAVRA("obreiro_palavra"),
    COL_TEXT_BIBLICO("texto_biblico"),
    COL_OBRE_PORTA("obreiros_porta"),
    COL_LOUVORES("louvores"),
    COL_NOME_IGREJA("nome_igreja"),
    COL_DATA("data");

    private String valor;

    private DBEsquema(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
