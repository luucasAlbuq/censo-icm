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
import android.widget.Button;
import android.widget.ScrollView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import controller.CensoController;
import model.Censo;
import util.DBEsquema;

public class RelatorioDiaActivity extends AppCompatActivity {

    private Censo censo;

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

        Description description = new Description();

        Locale BRAZIL = new Locale("pt", "BR");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
        String data = sdf.format(censo.getData());

        description.setText("Total: "+censo.getTotalPessoas()+" | "+data);
        description.setTextAlign(Paint.Align.CENTER);
        description.setTextSize(10f);
        description.setPosition(85f,15f);
        mChart.setDescription(description);
        setDataPie(censo, mChart);

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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString()+"/"+ Environment.DIRECTORY_DCIM.toString()+"/"+ now + ".jpg";
            Log.i("PATH",mPath);


            //Don't show the button
            Button baixarBtn = (Button) findViewById(R.id.baixarRelatorioDia);
            baixarBtn.setVisibility(View.GONE);


            ScrollView view = (ScrollView) findViewById(R.id.relatorio_dia_id);
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
            baixarBtn.setVisibility(View.VISIBLE);
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
        setContentView(R.layout.activity_relatorio_dia);

        censo = (Censo) getIntent().getSerializableExtra(DBEsquema.TABLE.getValor());

        TextView obreiroLouvor = (TextView) findViewById(R.id.relatorio_obreiro_louvor_resposta);
        TextView obreiroPalavra = (TextView) findViewById(R.id.relatorio_obreiro_palavra_resposta);
        TextView obreiroPorta = (TextView) findViewById(R.id.relatorio_obreiro_porta_resposta);
        TextView data = (TextView) findViewById(R.id.relatorio_data_resposta);
        TextView dom = (TextView) findViewById(R.id.relatorio_dom_resposta);
        TextView louvores = (TextView) findViewById(R.id.relatorio_louvores_resposta);
        TextView textoBiblico = (TextView) findViewById(R.id.relatorio_texto_biblico_resposta);
        Button baixar = (Button) findViewById(R.id.baixarRelatorioDia);

        try {
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


        baixar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot();
            }
        });
    }
}
