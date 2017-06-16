package icm.censo.a3_code.com.censoicm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import model.Censo;
import util.DBEsquema;
import util.RecycleViewAdapter;

public class ListaCensoActivity extends AppCompatActivity {

    private List<Censo> listaCenso;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapter adapter;

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

        }catch (Exception e){
            Toast.makeText(ListaCensoActivity.this, getString(R.string.erro_generico)+": "+e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

}
