package icm.censo.a3_code.com.censoicm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import controller.CensoController;
import util.MetodoPesquisa;

public class MainActivity extends AppCompatActivity {

    private Button cadastrarButton, pesquisarMenuButton, compararButton, relatorioButton, sairButton;
    private final Calendar calendario = Calendar.getInstance();
    private CensoController controller = new CensoController();

    //Prepara o calendario
    private DatePickerDialog.OnDateSetListener preparaCalendario(final TextView dataTextView) {
        final Calendar calendario = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener data = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendario.clear(Calendar.HOUR_OF_DAY);
                calendario.clear(Calendar.AM_PM);
                calendario.clear(Calendar.MINUTE);
                calendario.clear(Calendar.HOUR);
                calendario.clear(Calendar.MILLISECOND);
                calendario.clear(Calendar.SECOND);

                Locale BRAZIL = new Locale("pt", "BR");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", BRAZIL);
                dataTextView.setText("Data: " + sdf.format(calendario.getTime()));
            }
        };

        return data;
    }

    //Prepara o selectbox do popup
    private void preparaSpinner(ViewGroup layout) {
        final String[] metodos_pesquisa = {MetodoPesquisa.POR_MES.getValor(), MetodoPesquisa.POR_DIA.getValor()};
        /**
         * Adicionando elementes no spinner
         */
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.simple_spinner_item, metodos_pesquisa);
        Spinner spinner = (Spinner) layout.findViewById(R.id.metodo_de_pesquisa);
        spinner.setAdapter(adapter);
    }


    private void preparaPesquisaPopup() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.pesquisar_popup, null);
        mBuilder.setView(view);
        final AlertDialog dialog = mBuilder.create();

        final TextView dataPesquisaInicio = (TextView) view.findViewById(R.id.dataPesquisaInicio);
        final TextView dataPesquisaFim = (TextView) view.findViewById(R.id.dataPesquisaFim);
        final Button pesquisarButton = (Button) view.findViewById(R.id.pesquisarPopupButton);
        final Button calcelarPopup = (Button) view.findViewById(R.id.cancelarPopupButton);

        preparaSpinner(view);

        // Carrega Calendario Para campo Data de Inicio
        dataPesquisaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener data = preparaCalendario(dataPesquisaInicio);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, data, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH));
                Locale.setDefault(new Locale("pt", "BR"));
                datePickerDialog.show();
            }
        });

        dataPesquisaFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener data = preparaCalendario(dataPesquisaFim);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, data, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH));
                Locale.setDefault(new Locale("pt", "BR"));
                datePickerDialog.show();
            }
        });

        pesquisarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dialog.getContext(), RelatorioDiaActivity.class);
                dialog.getContext().startActivity(intent);
                dialog.dismiss();
            }
        });

        /**
         * Necessario carregar os elementos diretamente do layout que esta sendo usado,
         * porque as instancias desses elementos nao existem fora do layout do popup
         */
        calcelarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Chamando a tela de cadastro quando clicar na opcao de cadastro
        cadastrarButton = (Button) findViewById(R.id.cadastrarButton);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastraActivity = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(cadastraActivity);
            }
        });

        //Chamando o popup de pesquisa quando clicar na opcao de pesquisa
        pesquisarMenuButton = (Button) findViewById(R.id.pesquisarButton);
        pesquisarMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparaPesquisaPopup();
            }
        });

        //Chamando o popup de pesquisa quando clicar na opcao de comparar
        compararButton = (Button) findViewById(R.id.compararButton);
        compararButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparaPesquisaPopup();
            }
        });

        //Chamando o popup de pesquisa quando clicar na opcao de gerar relatorio
        relatorioButton = (Button) findViewById(R.id.relatorioButton);
        relatorioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparaPesquisaPopup();
            }
        });

        //Deslogar
        sairButton = (Button) findViewById(R.id.sairButton);
        sairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.erro_generico), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
