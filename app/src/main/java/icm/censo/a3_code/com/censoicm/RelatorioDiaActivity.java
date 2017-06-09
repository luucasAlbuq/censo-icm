package icm.censo.a3_code.com.censoicm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controller.CensoController;
import model.Censo;

public class RelatorioDiaActivity extends AppCompatActivity {

    private CensoController controller = new CensoController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_dia);

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
            Log.i("CENSO DIA ", String.valueOf(lista.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
