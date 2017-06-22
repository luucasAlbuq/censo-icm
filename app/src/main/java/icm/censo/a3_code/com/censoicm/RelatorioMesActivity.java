package icm.censo.a3_code.com.censoicm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Censo;
import util.DBEsquema;
import util.EstatisticaMes;
import util.GenerateExcel;

public class RelatorioMesActivity extends AppCompatActivity {

    private List<Censo> censoList;
    private LineChart lineChartTotalMes;
    private PieChart pieChartGeral;
    private TextView resumo;

    private void buildLineaChart(LineChart chart) {
        LineDataSet dataSet = new LineDataSet(addValueToLineChart(censoList, DBEsquema.COL_TOTAL), "Total");
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setColor(Color.BLUE);


        LineDataSet dataSet2 = new LineDataSet(addValueToLineChart(censoList, DBEsquema.COL_QTD_VISITANTES), "Visitantes");
        dataSet2.setCircleColor(Color.RED);
        dataSet2.setColor(Color.RED);

        LineData data = new LineData();
        data.addDataSet(dataSet);
        data.addDataSet(dataSet2);

        Description description = new Description();
        description.setText("Frequência Geral");
        description.setTextColor(Color.WHITE);
        chart.setDescription(description);

        chart.setData(data);
        chart.animateY(3000);
        chart.fitScreen();
        chart.setMinimumHeight(500);
        chart.setMaxVisibleValueCount(40);
        chart.invalidate();

        // customize legends
        Legend l = chart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
    }

    private List<Entry> addValueToLineChart(List<Censo> censoList, DBEsquema campo) {
        List<Entry> entries = new ArrayList<>();
        Locale BRAZIL = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd", BRAZIL);

        for (int i = 0; i < censoList.size(); i++) {
            //Define o valor do eixo x como sendo o dia do mes em que o censo foi feito
            //int dia = Integer.parseInt(sdf.format(censoList.get(i).getData()));
            if (campo.equals(DBEsquema.COL_QTD_VISITANTES)) {
                entries.add(new Entry(i, censoList.get(i).getQtdVisitantes()));
            } else if (campo.equals(DBEsquema.COL_TOTAL)) {
                entries.add(new Entry(i, censoList.get(i).getTotalPessoas()));
            }
        }
        return entries;
    }

    private void buildBarChart(BarChart chart, String type) {
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

    private List<BarEntry> addValuesToBARENTRY(List<Censo> censoList) {
        List<BarEntry> barEntryList = new ArrayList<>();
        for (int i = 0; i < censoList.size(); i++) {
            barEntryList.add(new BarEntry(i + 1, censoList.get(i).getTotalPessoas()));
        }
        return barEntryList;
    }

    private void buildPieChart(final PieChart mChart, EstatisticaMes estatisticaMes) {
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

        description.setText("Total: " + estatisticaMes.getTotalPessoas());
        description.setTextAlign(Paint.Align.CENTER);
        description.setTextColor(Color.WHITE);
        description.setTextSize(10f);
        description.setPosition(85f, 15f);
        mChart.setDescription(description);
        setDataPie(estatisticaMes, mChart);

        // customize legends
        Legend l = mChart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (mChart.isSaveEnabled()) {
                    mChart.saveToGallery("censo_icm" + Calendar.getInstance().getTimeInMillis() + ".jpg", 85); // 85 is the quality of the image
                    Toast.makeText(getApplicationContext(), "Gráfico Salvo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void setDataPie(EstatisticaMes estatisticaMes, PieChart pieChart) {
        PieEntry qtd_jovens = new PieEntry((float) estatisticaMes.getTotalJovens(), "Jovens: " + estatisticaMes.getTotalAdolescentes());
        PieEntry qtd_adolescentes = new PieEntry((float) estatisticaMes.getTotalAdolescentes(), "Adolescentes: " + estatisticaMes.getTotalAdolescentes());
        PieEntry qtd_criancas = new PieEntry((float) estatisticaMes.getTotalCriancas(), "Crianças: " + estatisticaMes.getTotalCriancas());
        PieEntry qtd_varoes = new PieEntry((float) estatisticaMes.getTotalVaroes(), "Varões: " + estatisticaMes.getTotalVaroes());
        PieEntry qtd_senhoras = new PieEntry((float) estatisticaMes.getTotalSenhoras(), "Senhoras: " + estatisticaMes.getTotalSenhoras());
        PieEntry qtd_visitanates = new PieEntry((float) estatisticaMes.getTotalVisitantes(), "Visitantes: " + estatisticaMes.getTotalVisitantes());

        List<PieEntry> entries = new ArrayList<>();
        entries.add(qtd_jovens);
        entries.add(qtd_adolescentes);
        entries.add(qtd_criancas);
        entries.add(qtd_varoes);
        entries.add(qtd_senhoras);
        entries.add(qtd_visitanates);

        ArrayList<Integer> cores = new ArrayList<Integer>();
        for (int c : ColorTemplate.MATERIAL_COLORS) {
            if (!cores.contains(c)) cores.add(c);
        }

        for (int c : ColorTemplate.COLORFUL_COLORS) {
            if (!cores.contains(c)) cores.add(c);
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
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DCIM.toString() + "/" + now + ".jpg";
            Log.i("PATH", mPath);

            //Don't show the button
            baixarBtn.setVisibility(View.GONE);
            gerarExcellBtn.setVisibility(View.GONE);

            pieChartGeral.getDescription().setTextColor(Color.BLACK);
            pieChartGeral.getLegend().setTextColor(Color.BLACK);
            lineChartTotalMes.getDescription().setTextColor(Color.BLACK);
            lineChartTotalMes.getLegend().setTextColor(Color.BLACK);
            resumo.setTextColor(Color.BLACK);

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
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setStartOffset(0);
            animation.setDuration(200);
            view.startAnimation(animation);

            baixarBtn.setVisibility(View.VISIBLE);
            gerarExcellBtn.setVisibility(View.VISIBLE);

            pieChartGeral.getDescription().setTextColor(Color.WHITE);
            pieChartGeral.getLegend().setTextColor(Color.WHITE);
            lineChartTotalMes.getDescription().setTextColor(Color.WHITE);
            lineChartTotalMes.getLegend().setTextColor(Color.WHITE);
            resumo.setTextColor(Color.WHITE);

            Toast.makeText(getApplicationContext(), "Relatório salvo em suas imagens.", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.erro_generico), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_mes);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        try {
            censoList = (List<Censo>) getIntent().getSerializableExtra(DBEsquema.TABLE.getValor());
            EstatisticaMes estatisticaMes = EstatisticaMes.calculaEstatisticaMes(censoList);
            resumo = (TextView) findViewById(R.id.relatorio_resumo_mes);
            resumo.setText(estatisticaMes.toString());


            lineChartTotalMes = (LineChart) findViewById(R.id.grafico_linha_total_mes);
            pieChartGeral = (PieChart) findViewById(R.id.grafico_geral_mes);
            buildLineaChart(lineChartTotalMes);
            buildPieChart(pieChartGeral, EstatisticaMes.calculaEstatisticaMes(censoList));

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
                    GenerateExcel.gerarRelatorioExcell(censoList, getApplicationContext());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CENSO DIA ", e.getMessage());
        }
    }
}
