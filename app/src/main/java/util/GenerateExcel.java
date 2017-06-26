package util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import icm.censo.a3_code.com.censoicm.R;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.Censo;

/**
 * Gera o arquivo excel
 * Created by luucasAlbuq on 22/06/2017.
 */

public class GenerateExcel {

    private static int colunaDataIndex = 0;
    private static int colunaLouvoresIndex = 1;
    private static  int colunaRecpcaoIndex = 2;
    private static  int colunaObreiroLouvorIndex = 3;
    private static  int colunaObreiroPalavraIndex = 4;
    private static  int colunacolunaTxtBiblicoIndex = 5;
    private static  int colunaVaroesIndex = 6;
    private static  int colunaSenhorasIndex = 7;
    private static  int colunaJovensIndex = 8;
    private static  int colunaAdolescentesIndex = 9;
    private static  int colunaCriancasIndex = 10;
    private static  int colunaVisitantesIndex = 11;
    private static  int colunaTotalIndex = 12;
    private static  int colunaDomIndex = 13;

    private static String path = Environment.getExternalStorageDirectory().toString() +"/"+ Environment.DIRECTORY_DOWNLOADS.toString()+"/censo_icm_app";;

    public static void gerarRelatorioExcell(List<Censo> censoList, Context context){
        String fileName = "relatorio_censo_icm_"+ Calendar.getInstance().getTimeInMillis()+".xls";
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setUseTemporaryFileDuringWrite(true);

        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(path,fileName);
        wbfile.mkdir();

        Log.i("Excel Path:",wbfile.getPath());

        WritableWorkbook writableWorkbook = null;

        try{
            //create a new WritableWorkbook using the java.io.File and
            //WorkbookSettings from above
            writableWorkbook = Workbook.createWorkbook(wbfile,workbookSettings);

            // create an Excel sheet
            WritableSheet excelSheet = writableWorkbook.createSheet("Censo Icm", 0);

            //Formt Cell
            WritableCellFormat cellFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            font.setColour(Colour.WHITE);
            cellFormat.setBackground(Colour.LIGHT_BLUE);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setFont(font);

            Label colunaData = new Label(colunaDataIndex,0,"Data",cellFormat);
            excelSheet.setColumnView(colunaDataIndex, colunaData.getString().length()*3);
            excelSheet.addCell(colunaData);

            Label colunaLouvores = new Label(colunaLouvoresIndex,0,"Lista de Louvores",cellFormat);
            excelSheet.setColumnView(colunaLouvoresIndex, colunaLouvores.getString().length()*2);
            excelSheet.addCell(colunaLouvores);

            Label colunaRecpcao = new Label(colunaRecpcaoIndex,0,"Recepção/Preparo",cellFormat);
            excelSheet.setColumnView(colunaRecpcaoIndex, colunaRecpcao.getString().length()*2);
            excelSheet.addCell(colunaRecpcao);

            Label colunaObreiroLouvor = new Label(colunaObreiroLouvorIndex,0,"Servo(a) No Louvor",cellFormat);
            excelSheet.setColumnView(colunaObreiroLouvorIndex, colunaObreiroLouvor.getString().length()*2);
            excelSheet.addCell(colunaObreiroLouvor);

            Label colunaObreiroPalavra = new Label(colunaObreiroPalavraIndex,0,"Servo(a) Na Palavra",cellFormat);
            excelSheet.setColumnView(colunaObreiroPalavraIndex, colunaObreiroPalavra.getString().length()*2);
            excelSheet.addCell(colunaObreiroPalavra);

            Label colunaTxtBiblico = new Label(colunacolunaTxtBiblicoIndex,0,"Texto Bíblico",cellFormat);
            excelSheet.setColumnView(colunacolunaTxtBiblicoIndex, colunaTxtBiblico.getString().length()*2);
            excelSheet.addCell(colunaTxtBiblico);

            Label colunaVaroes = new Label(colunaVaroesIndex,0,"N° Varões",cellFormat);
            excelSheet.setColumnView(colunaVaroesIndex, colunaVaroes.getString().length()*2);
            excelSheet.addCell(colunaVaroes);

            Label colunaSenhoras = new Label(colunaSenhorasIndex,0,"N° Senhoras",cellFormat);
            excelSheet.setColumnView(colunaSenhorasIndex, colunaSenhoras.getString().length()*2);
            excelSheet.addCell(colunaSenhoras);

            Label colunaJovens = new Label(colunaJovensIndex,0,"N° Jovens",cellFormat);
            excelSheet.setColumnView(colunaJovensIndex, colunaJovens.getString().length()*2);
            excelSheet.addCell(colunaJovens);

            Label colunaAdolescentes = new Label(colunaAdolescentesIndex,0,"N° Adolescentes",cellFormat);
            excelSheet.setColumnView(colunaAdolescentesIndex, colunaAdolescentes.getString().length()*2);
            excelSheet.addCell(colunaAdolescentes);

            Label colunaCriancas = new Label(colunaCriancasIndex,0,"N° Crianças",cellFormat);
            excelSheet.setColumnView(colunaCriancasIndex, colunaCriancas.getString().length()*2);
            excelSheet.addCell(colunaCriancas);

            Label colunaVisitantes = new Label(colunaVisitantesIndex,0,"N° Visitantes",cellFormat);
            excelSheet.setColumnView(colunaVisitantesIndex, colunaVisitantes.getString().length()*2);
            excelSheet.addCell(colunaVisitantes);

            Label colunaTotal = new Label(colunaTotalIndex,0,"N° Total",cellFormat);
            excelSheet.setColumnView(colunaTotalIndex, colunaTotal.getString().length()*2);
            excelSheet.addCell(colunaTotal);

            Label colunaDom = new Label(colunaDomIndex,0,"Dom",cellFormat);
            excelSheet.setColumnView(colunaDomIndex, colunaDom.getString().length()*20);
            excelSheet.addCell(colunaDom);

            excelSheet.setColumnGroup(colunaVaroesIndex, colunaTotalIndex, false);
            populaExcell(censoList, excelSheet);

            writableWorkbook.write();
            writableWorkbook.close();

            Toast.makeText(context, "Relatório Gerado com Sucesso.", Toast.LENGTH_SHORT).show();

        }catch(Exception ex){
            Log.e("EXCELL",ex.getStackTrace().toString());
            Log.e("EXCELL", ex.getMessage());
            Toast.makeText(context,"Desculpe, ocorreu um erro.", Toast.LENGTH_SHORT).show();
        }
    }

    private static void populaExcell(List<Censo> censoList, WritableSheet sheet) throws WriteException {
        if(sheet != null && censoList != null && censoList.size() > 0){
            Locale BRAZIL = new Locale("pt", "BR");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);

            WritableCellFormat cellFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
            font.setColour(Colour.BLACK);
            cellFormat.setAlignment(Alignment.JUSTIFY);
            cellFormat.setFont(font);

            for (int i=0;i<censoList.size();i++){
                Censo censo = censoList.get(i);
                int linha = i+1;
                Label dataValue = new Label(colunaDataIndex, linha,sdf.format(censo.getData()).toString(), cellFormat);
                sheet.addCell(dataValue);

                String louvores = censo.getLouvores().toString().replace("[","").replace("]","");
                Label louvoresValues = new Label(colunaLouvoresIndex, linha, louvores, cellFormat);
                sheet.addCell(louvoresValues);

                String obreirosPorta = censo.getObreirosPorta().toString().replace("[","").replace("]","");
                Label obreirosPortaValue = new Label(colunaRecpcaoIndex, linha,obreirosPorta, cellFormat);
                sheet.addCell(obreirosPortaValue);

                Label obreiroLouvorValue = new Label(colunaObreiroLouvorIndex, linha, censo.getObreiroLouvor(), cellFormat);
                sheet.addCell(obreiroLouvorValue);

                Label obreiroPalavraValue = new Label(colunaObreiroPalavraIndex, linha, censo.getObreiroPalavra(), cellFormat);
                sheet.addCell(obreiroPalavraValue);

                Label txtBiblicoValue = new Label(colunacolunaTxtBiblicoIndex,linha,censo.getTextoBiblico(), cellFormat);
                sheet.addCell(txtBiblicoValue);

                Number vaoresValue = new Number(colunaVaroesIndex,linha,censo.getQtdVaroes(), cellFormat);
                sheet.addCell(vaoresValue);

                Number senhorasValue = new Number(colunaSenhorasIndex,linha,censo.getQtdSenhoras(), cellFormat);
                sheet.addCell(senhorasValue);

                Number jovensValue = new Number(colunaJovensIndex, linha, censo.getQtdJovens(), cellFormat);
                sheet.addCell(jovensValue);

                Number adolescentesValue = new Number(colunaAdolescentesIndex, linha, censo.getQtdAdolescentes(), cellFormat);
                sheet.addCell(adolescentesValue);

                Number criancasValue = new Number(colunaCriancasIndex, linha, censo.getQtdCriancas(), cellFormat);
                sheet.addCell(criancasValue);

                Number visitantesValue = new Number(colunaVisitantesIndex, linha, censo.getQtdVisitantes(), cellFormat);
                sheet.addCell(visitantesValue);

                Number totalValue = new Number(colunaTotalIndex, linha, censo.getTotalPessoas(), cellFormat);
                sheet.addCell(totalValue);

                Label domValue = new Label(colunaDomIndex, linha, censo.getDom(), cellFormat);
                sheet.addCell(domValue);

                sheet.getRowView(linha).setAutosize(true);
            }
        }
    }
}
