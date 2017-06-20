package icm.censo.a3_code.com.censoicm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import model.Censo;
import util.DBEsquema;
import util.RecycleViewAdapter;

public class ListaCensoActivity extends AppCompatActivity {

    private List<Censo> listaCenso;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter adapter;
    private FloatingActionButton reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_censo);

        try{
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            listaCenso = (List<Censo>) getIntent().getSerializableExtra(DBEsquema.TABLE.getValor());
            RecycleViewAdapter adapter = new RecycleViewAdapter(this,listaCenso);
            recyclerView.setAdapter(adapter);

            reportBtn = (FloatingActionButton) findViewById(R.id.reportButton);
            reportBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent relatorioMesActivity = new Intent(ListaCensoActivity.this, RelatorioMesActivity.class);
                    relatorioMesActivity.putExtra(DBEsquema.TABLE.getValor(), (Serializable) listaCenso);
                    startActivity(relatorioMesActivity);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(ListaCensoActivity.this, getString(R.string.erro_generico)+": "+e.getMessage(),
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

}
