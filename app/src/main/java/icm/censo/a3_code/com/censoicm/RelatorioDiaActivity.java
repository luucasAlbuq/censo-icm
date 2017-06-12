package icm.censo.a3_code.com.censoicm;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import controller.CensoController;
import model.Censo;

public class RelatorioDiaActivity extends AppCompatActivity {

    private CensoController controller = new CensoController();

    private void  buildChart(Censo censo){
        final PieChart mChart = (PieChart) findViewById(R.id.relatorio_graf_dia);
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
        setDataPie(censo, mChart);

        // customize legends
        Legend l = mChart.getLegend();
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(mChart.isSaveEnabled()){
                    mChart.saveToGallery("censo_icm.jpg", 85); // 85 is the quality of the image
                    Toast.makeText(getApplicationContext(), "Gráfico Salvo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setDataPie(Censo censo, PieChart pieChart){

        PieEntry qtd_jovens = new PieEntry((float) censo.getQtdJovens(), "Jovens: "+censo.getQtdJovens());
        PieEntry qtd_adolescentes = new PieEntry((float) censo.getQtdAdolescentes(), "Adolescentes: "+censo.getQtdAdolescentes());
        PieEntry qtd_criancas = new PieEntry((float) censo.getQtdCriancas(), "Crianças: "+censo.getQtdCriancas());
        PieEntry qtd_varoes = new PieEntry((float) censo.getQtdVaroes(), "Varões: "+censo.getQtdVaroes());
        PieEntry qtd_senhoras = new PieEntry((float) censo.getQtdSenhoras(), "Senhoras: "+censo.getQtdSenhoras());
        PieEntry qtd_visitanates = new PieEntry((float) censo.getQtdVisitantes(), "Visitantes: "+censo.getQtdVisitantes());

        List<PieEntry> entries = new ArrayList<>();
        entries.add(qtd_jovens);
        entries.add(qtd_adolescentes);
        entries.add(qtd_criancas);
        entries.add(qtd_varoes);
        entries.add(qtd_senhoras);
        entries.add(qtd_visitanates);

        ArrayList<Integer> cores = new ArrayList<Integer>();
        for(int c : ColorTemplate.MATERIAL_COLORS){
            cores.add(c);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_dia);

        TextView obreiroLouvor = (TextView) findViewById(R.id.relatorio_obreiro_louvor_resposta);
        TextView obreiroPalavra = (TextView) findViewById(R.id.relatorio_obreiro_palavra_resposta);
        TextView obreiroPorta = (TextView) findViewById(R.id.relatorio_obreiro_porta_resposta);
        TextView data = (TextView) findViewById(R.id.relatorio_data_resposta);
        TextView dom = (TextView) findViewById(R.id.relatorio_dom_resposta);
        TextView louvores = (TextView) findViewById(R.id.relatorio_louvores_resposta);
        TextView textoBiblico = (TextView) findViewById(R.id.relatorio_texto_biblico_resposta);

        List<Censo> lista = null;
        try {
            Calendar calendario = Calendar.getInstance();
            calendario.clear(Calendar.HOUR_OF_DAY);
            calendario.clear(Calendar.AM_PM);
            calendario.clear(Calendar.MINUTE);
            calendario.clear(Calendar.HOUR);
            calendario.clear(Calendar.MILLISECOND);
            calendario.clear(Calendar.SECOND);
            lista = controller.getCensoByDia(calendario.getTime());
            Censo censo = lista.get(0);
            buildChart(censo);

            Locale BRAZIL = new Locale("pt", "BR");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
            data.setText(sdf.format(censo.getData()));

            obreiroLouvor.setText(censo.getObreiroLouvor());
            obreiroPalavra.setText(censo.getObreiroPalavra());
            obreiroPorta.setText(censo.getObreirosPorta().toString().replace("[","").replace("]",""));

            dom.setText(censo.getDom());
            louvores.setText(censo.getLouvores().toString().replace("[","").replace("]",""));
            textoBiblico.setText(censo.getTextoBiblico());


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CENSO DIA ", e.getMessage());
        }


    }
}
