package icm.censo.a3_code.com.censoicm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import model.Censo;
import util.DBEsquema;
import util.EstatisticaMes;

public class RelatorioMesActivity extends AppCompatActivity {

    List<Censo> censoList;

    private void buildLineaChart(LineChart chart){
        LineDataSet dataSet = new LineDataSet(addValueToLineChart(censoList, DBEsquema.COL_TOTAL),"Total");
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setColor(Color.BLUE);


        LineDataSet dataSet2 = new LineDataSet(addValueToLineChart(censoList, DBEsquema.COL_QTD_VISITANTES),"Visitantes");
        dataSet2.setCircleColor(Color.RED);
        dataSet2.setColor(Color.RED);

        LineData data = new LineData();
        data.addDataSet(dataSet);
        data.addDataSet(dataSet2);

        chart.setData(data);
        chart.animateY(3000);
        chart.fitScreen();
        chart.setMinimumHeight(500);
        chart.setMaxVisibleValueCount(40);
        chart.invalidate();

        // customize legends
        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    }

    private List<Entry> addValueToLineChart(List<Censo> censoList, DBEsquema campo){
        List<Entry> entries = new ArrayList<>();
        Locale BRAZIL = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd", BRAZIL);

        for(int i=0;i<censoList.size();i++){
            //Define o valor do eixo x como sendo o dia do mes em que o censo foi feito
            //int dia = Integer.parseInt(sdf.format(censoList.get(i).getData()));
            if(campo.equals(DBEsquema.COL_QTD_VISITANTES)){
                entries.add(new Entry(i,censoList.get(i).getQtdVisitantes()));
            }else if(campo.equals(DBEsquema.COL_TOTAL)){
                entries.add(new Entry(i,censoList.get(i).getTotalPessoas()));
            }
        }
        return entries;
    }

    private void buildBarChart(BarChart chart, String type){
        List<BarEntry> barEntryList = addValuesToBARENTRY(censoList);
        BarDataSet dataSet = new BarDataSet(barEntryList, type);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(dataSet);
        chart.setData(data);
        chart.animateY(3000);
        chart.fitScreen();
        chart.setMinimumHeight(300);
        chart.setMaxVisibleValueCount(40);
        chart.invalidate();
    }

    private List<BarEntry> addValuesToBARENTRY(List<Censo> censoList){
        List<BarEntry> barEntryList = new ArrayList<>();
        for(int i=0; i<censoList.size();i++){
            barEntryList.add(new BarEntry(i+1, censoList.get(i).getTotalPessoas()));
        }
        return barEntryList;
    }

    private void buildPieChart(final PieChart mChart, EstatisticaMes estatisticaMes){
        mChart.setTransparentCircleRadius(0);
        mChart.setUsePercentValues(true);
        mChart.setDescription(null);
        mChart.setMinimumWidth(500);
        mChart.setMinimumHeight(500);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(0);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setSaveEnabled(true);

        Description description = new Description();

        description.setText("Total: "+estatisticaMes.getTotalPessoas());
        description.setTextAlign(Paint.Align.CENTER);
        description.setTextSize(10f);
        description.setPosition(85f,15f);
        mChart.setDescription(description);
        setDataPie(estatisticaMes, mChart);

        // customize legends
        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(mChart.isSaveEnabled()){
                    mChart.saveToGallery("censo_icm"+ Calendar.getInstance().getTimeInMillis()+".jpg", 85); // 85 is the quality of the image
                    Toast.makeText(getApplicationContext(), "Gráfico Salvo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {}
        });
    }

    private void setDataPie(EstatisticaMes estatisticaMes, PieChart pieChart){
        PieEntry qtd_jovens = new PieEntry((float) estatisticaMes.getTotalJovens(), "Jovens: "+estatisticaMes.getTotalAdolescentes());
        PieEntry qtd_adolescentes = new PieEntry((float) estatisticaMes.getTotalAdolescentes(), "Adolescentes: "+estatisticaMes.getTotalAdolescentes());
        PieEntry qtd_criancas = new PieEntry((float) estatisticaMes.getTotalCriancas(), "Crianças: "+estatisticaMes.getTotalCriancas());
        PieEntry qtd_varoes = new PieEntry((float) estatisticaMes.getTotalVaroes(), "Varões: "+estatisticaMes.getTotalVaroes());
        PieEntry qtd_senhoras = new PieEntry((float) estatisticaMes.getTotalSenhoras(), "Senhoras: "+estatisticaMes.getTotalSenhoras());
        PieEntry qtd_visitanates = new PieEntry((float) estatisticaMes.getTotalVisitantes(), "Visitantes: "+estatisticaMes.getTotalVisitantes());

        List<PieEntry> entries = new ArrayList<>();
        entries.add(qtd_jovens);
        entries.add(qtd_adolescentes);
        entries.add(qtd_criancas);
        entries.add(qtd_varoes);
        entries.add(qtd_senhoras);
        entries.add(qtd_visitanates);

        ArrayList<Integer> cores = new ArrayList<Integer>();
        for(int c : ColorTemplate.MATERIAL_COLORS){
            if(!cores.contains(c)) cores.add(c);
        }

        for(int c: ColorTemplate.COLORFUL_COLORS){
            if(!cores.contains(c)) cores.add(c);
        }

        PieDataSet dataSet = new PieDataSet(entries, null);
        dataSet.setSliceSpace(5);
        dataSet.setSelectionShift(5);
        dataSet.setColors(cores);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void takeScreenshot(final ScrollView view, final Button baixarBtn, final Button gerarExcellBtn) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString()+"/"+ Environment.DIRECTORY_DCIM.toString()+"/"+ now + ".jpg";
            Log.i("PATH",mPath);

            //Don't show the button
            baixarBtn.setVisibility(View.GONE);
            gerarExcellBtn.setVisibility(View.GONE);

            Bitmap bitmap = Bitmap.createBitmap(
                    view.getChildAt(0).getWidth(),
                    view.getChildAt(0).getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bitmap);
            c.drawColor(Color.WHITE);
            view.getChildAt(0).draw(c);

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //Efeito de flash na tela quando faz o screenshot
            AlphaAnimation animation = new AlphaAnimation(1,0);
            animation.setStartOffset(0);
            animation.setDuration(200);
            view.startAnimation(animation);

            baixarBtn.setVisibility(View.VISIBLE);
            gerarExcellBtn.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Relatório salvo em suas imagens.", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),getString(R.string.erro_generico), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_mes);

        try{
            censoList = (List<Censo>) getIntent().getSerializableExtra(DBEsquema.TABLE.getValor());
            EstatisticaMes estatisticaMes = calculaEstatisticaMes(censoList);
            TextView resumo = (TextView) findViewById(R.id.relatorio_resumo_mes);
            resumo.setText(estatisticaMes.toString());


            LineChart lineChartTotalMes = (LineChart) findViewById(R.id.grafico_linha_total_mes);
            PieChart pieChartGeral = (PieChart) findViewById(R.id.grafico_geral_mes);
            buildLineaChart(lineChartTotalMes);
            buildPieChart(pieChartGeral, calculaEstatisticaMes(censoList));

            final ScrollView scrollView = (ScrollView) findViewById(R.id.relatorio_geral_mes);
            final Button baixarBtn = (Button) findViewById(R.id.baixarRelatorioMes);
            final Button gerarExcellBtn = (Button) findViewById(R.id.gerarRelatorioExcell);

            baixarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takeScreenshot(scrollView, baixarBtn, gerarExcellBtn);
                }
            });

            gerarExcellBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gerarRelatorioExcell();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            Log.e("CENSO DIA ", e.getMessage());
        }
    }

    /**
     * Calcula a frequência geral de um grupo de dados
     * @param list
     * @return
     */
    public static EstatisticaMes calculaEstatisticaMes(List<Censo> list){
        EstatisticaMes estatisticaMes = new EstatisticaMes();
        int totalDiasRegistrados = list.size();
        int totalAdolescentes = 0;
        int totalJovens = 0;
        int totalCriancas = 0;
        int totalSenhoras = 0;
        int totalVaroes = 0;
        int totalVisitantes = 0;
        int totalPessoas = 0;

        for(Censo censo: list){
            totalAdolescentes += censo.getQtdAdolescentes();
            totalCriancas += censo.getQtdCriancas();
            totalJovens += censo.getQtdJovens();
            totalSenhoras += censo.getQtdSenhoras();
            totalVaroes += censo.getQtdVaroes();
            totalVisitantes += censo.getQtdVisitantes();
            totalPessoas += censo.getTotalPessoas();
        }

        float mediaPessoas = (totalPessoas/totalDiasRegistrados);
        float mediaPorcentagemJovens = (totalJovens*100)/totalPessoas;
        float mediaPorcentagemCriancas = (totalCriancas*100)/totalPessoas;
        float mediaPorcentagemAdolescentes = (totalAdolescentes*100)/totalPessoas;
        float mediaPorcentagemSenhoras = (totalSenhoras*100)/totalPessoas;
        float mediaPorcentagemVaroes = (totalVaroes*100)/totalPessoas;
        float mediaPorcentagemVisitantes = (totalVisitantes*100)/totalPessoas;

        estatisticaMes.setMediaPessoas(mediaPessoas);
        estatisticaMes.setMediaPorcentagemAdolescentes(mediaPorcentagemAdolescentes);
        estatisticaMes.setMediaPorcentagemCriancas(mediaPorcentagemCriancas);
        estatisticaMes.setMediaPorcentagemJovens(mediaPorcentagemJovens);
        estatisticaMes.setMediaPorcentagemSenhoras(mediaPorcentagemSenhoras);
        estatisticaMes.setMediaPorcentagemVaroes(mediaPorcentagemVaroes);
        estatisticaMes.setMediaPorcentagemVisitantes(mediaPorcentagemVisitantes);
        estatisticaMes.setTotalAdolescentes(totalAdolescentes);
        estatisticaMes.setTotalCriancas(totalCriancas);
        estatisticaMes.setTotalJovens(totalJovens);
        estatisticaMes.setTotalSenhoras(totalSenhoras);
        estatisticaMes.setTotalVaroes(totalVaroes);
        estatisticaMes.setTotalVisitantes(totalVisitantes);
        estatisticaMes.setTotalPessoas(totalPessoas);
        //A lista vem do banco em order descencente em relacao a data de cada censo
        estatisticaMes.setDataInicio(list.get(totalDiasRegistrados-1).getData());
        estatisticaMes.setDataFim(list.get(0).getData());
        return estatisticaMes;
    }

    /**
     * Gera o excell com as informações de vários objestos censo
     */
    private void gerarRelatorioExcell(){
        String fileName = "relatorio_censo_icm_"+Calendar.getInstance().getTimeInMillis()+".xls";
        WorkbookSettings workbookSettings = new WorkbookSettings();
        workbookSettings.setUseTemporaryFileDuringWrite(true);

        String path = Environment.getExternalStorageDirectory().toString() +"/"+ Environment.DIRECTORY_DOWNLOADS.toString()+"/"+fileName;
        //add on the your app's path
        File dir = new File(path);
        //make them in case they're not there
        dir.mkdirs();

        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(path,fileName);

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
            cellFormat.setFont(font);

            int colunaDataIndex = 0;
            int colunaLouvoresIndex = 1;
            int colunaRecpcaoIndex = 2;
            int colunaObreiroLouvorIndex = 3;
            int colunaObreiroPalavraIndex = 4;
            int colunacolunaTxtBiblicoIndex = 5;
            int colunaVaroesIndex = 6;
            int colunaSenhorasIndex = 7;
            int colunaJovensIndex = 8;
            int colunaAdolescentesIndex = 9;
            int colunaCriancasIndex = 10;
            int colunaVisitantesIndex = 11;
            int colunaTotalIndex = 12;
            int colunaDomIndex = 13;


            Label colunaData = new Label(colunaDataIndex,0,"Data",cellFormat);
            excelSheet.addCell(colunaData);

            Label colunaLouvores = new Label(colunaLouvoresIndex,0,"Lista de Louvores",cellFormat);
            excelSheet.addCell(colunaLouvores);

            Label colunaRecpcao = new Label(colunaRecpcaoIndex,0,"Recpção/Preparo",cellFormat);
            excelSheet.addCell(colunaRecpcao);

            Label colunaObreiroLouvor = new Label(colunaObreiroLouvorIndex,0,"Servo(a) No Louvor",cellFormat);
            excelSheet.addCell(colunaObreiroLouvor);

            Label colunaObreiroPalavra = new Label(colunaObreiroPalavraIndex,0,"Servo(a) Na Palavra",cellFormat);
            excelSheet.addCell(colunaObreiroPalavra);

            Label colunaTxtBiblico = new Label(colunacolunaTxtBiblicoIndex,0,"Texto Bíblico",cellFormat);
            excelSheet.addCell(colunaTxtBiblico);

            Label colunaVaroes = new Label(colunaVaroesIndex,0,"N° Varões",cellFormat);
            excelSheet.addCell(colunaVaroes);

            Label colunaSenhoras = new Label(colunaSenhorasIndex,0,"N° Senhoras",cellFormat);
            excelSheet.addCell(colunaSenhoras);

            Label colunaJovens = new Label(colunaJovensIndex,0,"N° Jovens",cellFormat);
            excelSheet.addCell(colunaJovens);

            Label colunaAdolescentes = new Label(colunaAdolescentesIndex,0,"N° Adolescentes",cellFormat);
            excelSheet.addCell(colunaAdolescentes);

            Label colunaCriancas = new Label(colunaCriancasIndex,0,"N° Crianças",cellFormat);
            excelSheet.addCell(colunaCriancas);

            Label colunaVisitantes = new Label(colunaVisitantesIndex,0,"N° Visitantes",cellFormat);
            excelSheet.addCell(colunaVisitantes);

            Label colunaTotal = new Label(colunaTotalIndex,0,"N° Total",cellFormat);
            excelSheet.addCell(colunaTotal);

            Label colunaDom = new Label(colunaDomIndex,0,"Dom",cellFormat);
            excelSheet.addCell(colunaDom);

            populaExcell(censoList, excelSheet);

            writableWorkbook.write();
            writableWorkbook.close();

            Toast.makeText(getApplicationContext(), "Relatório Gerado com Sucesso.", Toast.LENGTH_SHORT).show();

        }catch(Exception ex){
            Log.e("EXCELL",ex.getStackTrace().toString());
            Log.e("EXCELL", ex.getMessage());
            Toast.makeText(getApplicationContext(),getString(R.string.erro_generico), Toast.LENGTH_SHORT).show();
        }
    }

    private  void populaExcell(List<Censo> censoList, WritableSheet sheet) throws WriteException {
        int colunaDataIndex = 0;
        int colunaLouvoresIndex = 1;
        int colunaRecpcaoIndex = 2;
        int colunaObreiroLouvorIndex = 3;
        int colunaObreiroPalavraIndex = 4;
        int colunacolunaTxtBiblicoIndex = 5;
        int colunaVaroesIndex = 6;
        int colunaSenhorasIndex = 7;
        int colunaJovensIndex = 8;
        int colunaAdolescentesIndex = 9;
        int colunaCriancasIndex = 10;
        int colunaVisitantesIndex = 11;
        int colunaTotalIndex = 12;
        int colunaDomIndex = 13;

        if(sheet != null && censoList != null && censoList.size() > 0){
            Locale BRAZIL = new Locale("pt", "BR");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);

            for (int i=0;i<censoList.size();i++){
                Censo censo = censoList.get(i);
                int linha = i+1;
                Label dataValue = new Label(colunaDataIndex, linha,sdf.format(censo.getData()).toString());
                sheet.addCell(dataValue);

                String louvores = censo.getLouvores().toString().replace("[","").replace("]","");
                Label louvoresValues = new Label(colunaLouvoresIndex, linha, louvores);
                sheet.addCell(louvoresValues);

                String obreirosPorta = censo.getObreirosPorta().toString().replace("[","").replace("]","");
                Label obreirosPortaValue = new Label(colunaRecpcaoIndex, linha,obreirosPorta);
                sheet.addCell(obreirosPortaValue);

                Label obreiroLouvorValue = new Label(colunaObreiroLouvorIndex, linha, censo.getObreiroLouvor());
                sheet.addCell(obreiroLouvorValue);

                Label obreiroPalavraValue = new Label(colunaObreiroPalavraIndex, linha, censo.getObreiroPalavra());
                sheet.addCell(obreiroPalavraValue);

                Label txtBiblicoValue = new Label(colunacolunaTxtBiblicoIndex,linha,censo.getTextoBiblico());
                sheet.addCell(txtBiblicoValue);

                Number vaoresValue = new Number(colunaVaroesIndex,linha,censo.getQtdVaroes());
                sheet.addCell(vaoresValue);

                Number senhorasValue = new Number(colunaSenhorasIndex,linha,censo.getQtdSenhoras());
                sheet.addCell(senhorasValue);

                Number jovensValue = new Number(colunaJovensIndex, linha, censo.getQtdJovens());
                sheet.addCell(jovensValue);

                Number adolescentesValue = new Number(colunaAdolescentesIndex, linha, censo.getQtdAdolescentes());
                sheet.addCell(adolescentesValue);

                Number criancasValue = new Number(colunaCriancasIndex, linha, censo.getQtdCriancas());
                sheet.addCell(criancasValue);

                Number visitantesValue = new Number(colunaVisitantesIndex, linha, censo.getQtdVisitantes());
                sheet.addCell(visitantesValue);

                Number totalValue = new Number(colunaTotalIndex, linha, censo.getTotalPessoas());
                sheet.addCell(totalValue);

                Label domValue = new Label(colunaDomIndex, linha, censo.getDom());
                sheet.addCell(domValue);
            }
        }
    }
}
