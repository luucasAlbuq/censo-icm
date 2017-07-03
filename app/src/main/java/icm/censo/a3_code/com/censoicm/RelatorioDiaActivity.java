package icm.censo.a3_code.com.censoicm;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
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
    private CensoController controller = CensoController.getInstance();
    private TextView obreiroLouvor, obreiroPalavra, obreiroPorta, data, dom, louvores, textoBiblico;
    private Button baixar, delete;
    private PieChart mChart;

    private void buildChart(Censo censo) {
        mChart = (PieChart) findViewById(R.id.relatorio_graf_dia);
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

        description.setText("Total: " + censo.getTotalPessoas() + " | " + data);
        description.setTextAlign(Paint.Align.CENTER);
        description.setTextSize(10f);
        description.setTextColor(Color.WHITE);
        description.setPosition(90f, 15f);
        mChart.setDescription(description);
        setDataPie(censo, mChart);

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
                    mChart.getLegend().setTextColor(Color.BLACK);
                    mChart.getDescription().setTextColor(Color.BLACK);
                    mChart.saveToGallery("censo_icm_relatorio_diario" + Calendar.getInstance().getTimeInMillis() + ".jpg", 85); // 85 is the quality of the image
                    Toast.makeText(getApplicationContext(), "Gráfico Salvo", Toast.LENGTH_SHORT).show();
                    mChart.getLegend().setTextColor(Color.WHITE);
                    mChart.getDescription().setTextColor(Color.WHITE);
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void setDataPie(Censo censo, PieChart pieChart) {

        PieEntry qtd_jovens = new PieEntry((float) censo.getQtdJovens(), "Jovens: " + censo.getQtdJovens());
        PieEntry qtd_adolescentes = new PieEntry((float) censo.getQtdAdolescentes(), "Adolescentes: " + censo.getQtdAdolescentes());
        PieEntry qtd_criancas = new PieEntry((float) censo.getQtdCriancas(), "Crianças: " + censo.getQtdCriancas());
        PieEntry qtd_varoes = new PieEntry((float) censo.getQtdVaroes(), "Varões: " + censo.getQtdVaroes());
        PieEntry qtd_senhoras = new PieEntry((float) censo.getQtdSenhoras(), "Senhoras: " + censo.getQtdSenhoras());
        PieEntry qtd_visitanates = new PieEntry((float) censo.getQtdVisitantes(), "Visitantes: " + censo.getQtdVisitantes());

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

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + Environment.DIRECTORY_DCIM.toString() + "/" + now + ".jpg";
            Log.i("PATH", mPath);

            //Don't show the button
            baixar.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);

            TextView obreiroLouvorField = (TextView) findViewById(R.id.relatorio_obreiro_louvor);
            TextView obreiroPalavraField = (TextView) findViewById(R.id.relatorio_obreiro_palavra);
            TextView obreiroPortaField = (TextView) findViewById(R.id.relatorio_obreiro_porta);
            TextView dataField = (TextView) findViewById(R.id.relatorio_data);
            TextView domField = (TextView) findViewById(R.id.relatorio_dom);
            TextView louvoresField = (TextView) findViewById(R.id.relatorio_louvores);
            TextView textoBiblicoField = (TextView) findViewById(R.id.relatorio_texto_biblico);

            obreiroLouvorField.setTextColor(Color.BLACK);
            obreiroPalavraField.setTextColor(Color.BLACK);
            dataField.setTextColor(Color.BLACK);
            domField.setTextColor(Color.BLACK);
            louvoresField.setTextColor(Color.BLACK);
            textoBiblicoField.setTextColor(Color.BLACK);
            obreiroLouvor.setTextColor(Color.BLACK);
            obreiroPalavra.setTextColor(Color.BLACK);
            data.setTextColor(Color.BLACK);
            dom.setTextColor(Color.BLACK);
            louvores.setTextColor(Color.BLACK);
            textoBiblico.setTextColor(Color.BLACK);
            mChart.getLegend().setTextColor(Color.BLACK);
            mChart.getDescription().setTextColor(Color.BLACK);


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

            //Efeito de flash na tela quando faz o screenshot
            AlphaAnimation animation = new AlphaAnimation(1, 0);
            animation.setStartOffset(0);
            animation.setDuration(200);
            view.startAnimation(animation);

            obreiroLouvorField.setTextColor(Color.WHITE);
            obreiroPalavraField.setTextColor(Color.WHITE);
            dataField.setTextColor(Color.WHITE);
            domField.setTextColor(Color.WHITE);
            louvoresField.setTextColor(Color.WHITE);
            textoBiblicoField.setTextColor(Color.WHITE);
            obreiroLouvor.setTextColor(Color.WHITE);
            obreiroPalavra.setTextColor(Color.WHITE);
            data.setTextColor(Color.WHITE);
            dom.setTextColor(Color.WHITE);
            louvores.setTextColor(Color.WHITE);
            textoBiblico.setTextColor(Color.WHITE);
            mChart.getLegend().setTextColor(Color.WHITE);
            mChart.getDescription().setTextColor(Color.WHITE);

            baixar.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Relatório salvo em suas imagens.", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.erro_generico), Toast.LENGTH_SHORT).show();
        }
    }

    private void buildDeletePopup() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RelatorioDiaActivity.this);
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.popup_delete, null);
        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        final Button confirmaDelete = (Button) view.findViewById(R.id.confirmaRemocaoButton);
        final Button negaDelete = (Button) view.findViewById(R.id.negaRemocaoButton);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarDelete);

        confirmaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    controller.delete(censo.getId());
                    ListaCensoActivity.updateCensoListActivityWhenDeleteIsDone(censo, RelatorioDiaActivity.this);
                    dialog.dismiss();
                    finish();
                    Toast.makeText(getApplicationContext(), "Removido com Sucesso.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.erro_generico), Toast.LENGTH_SHORT).show();
                    Log.e("CENSO Delete ", e.getMessage());
                }
                progressBar.setVisibility(View.GONE);
            }
        });

        negaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_dia);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        censo = (Censo) getIntent().getSerializableExtra(DBEsquema.TABLE.getValor());

        obreiroLouvor = (TextView) findViewById(R.id.relatorio_obreiro_louvor_resposta);
        obreiroPalavra = (TextView) findViewById(R.id.relatorio_obreiro_palavra_resposta);
        obreiroPorta = (TextView) findViewById(R.id.relatorio_obreiro_porta_resposta);
        data = (TextView) findViewById(R.id.relatorio_data_resposta);
        dom = (TextView) findViewById(R.id.relatorio_dom_resposta);
        louvores = (TextView) findViewById(R.id.relatorio_louvores_resposta);
        textoBiblico = (TextView) findViewById(R.id.relatorio_texto_biblico_resposta);
        baixar = (Button) findViewById(R.id.baixarRelatorioDia);
        delete = (Button) findViewById(R.id.deleteCenso);

        try {
            buildChart(censo);

            Locale BRAZIL = new Locale("pt", "BR");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
            data.setText(sdf.format(censo.getData()));

            obreiroLouvor.setText(censo.getObreiroLouvor());
            obreiroPalavra.setText(censo.getObreiroPalavra());
            obreiroPorta.setText(censo.getObreirosPorta().toString().replace("[", "").replace("]", ""));

            dom.setText(censo.getDom());
            louvores.setText(censo.getLouvores().toString().replace("[", "").replace("]", ""));
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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Caso o usuario nao tenha role de admin objeto nao pode ser salvo
                if (!LoginActivity.hasAdminRole()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.denied_error), Toast.LENGTH_SHORT).show();
                } else {
                    buildDeletePopup();
                }
            }
        });
    }
}
