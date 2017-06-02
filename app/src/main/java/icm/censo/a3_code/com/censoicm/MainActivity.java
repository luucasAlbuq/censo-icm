package icm.censo.a3_code.com.censoicm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import model.Censo;
import util.MetodoPesquisa;

public class MainActivity extends AppCompatActivity {

    private  Button cadastrarButton, pesquisarMenuButton, compararButton, relatorioButton;
    private final Calendar calendario = Calendar.getInstance();

    //Prepara o calendario
    private DatePickerDialog.OnDateSetListener preparaCalendario(final TextView dataTextView){
        final Calendar calendario = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener data = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Locale BRAZIL = new Locale("pt","BR");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",BRAZIL);
                dataTextView.setText("Data: "+sdf.format(calendario.getTime()));
            }
        };

        return data;
    }

    //Prepara o selectbox do popup
    private void preparaSpinner(ViewGroup layout){
        /**
         * Adicionando elementes no spinner
         */
        String[] metodos_pesquisa = {MetodoPesquisa.POR_MES.getValor(), MetodoPesquisa.POR_DIA.getValor()};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.simple_spinner_item, metodos_pesquisa);
        Spinner spinner = (Spinner) layout.findViewById(R.id.metodo_de_pesquisa);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void preparaPesquisaPopup(){
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pesquisar_popup, null);
        final PopupWindow popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(pesquisarMenuButton, Gravity.CENTER, 0, 0);

        preparaSpinner(layout);

        // Carrega Calendario Para campo Data de Inicio
        final TextView dataPesquisaInicio = (TextView) layout.findViewById(R.id.dataPesquisaInicio);
        dataPesquisaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener data = preparaCalendario(dataPesquisaInicio);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, data, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH));
                Locale.setDefault(new Locale("pt","BR"));
                datePickerDialog.show();
            }
        });

        final TextView dataPesquisaFim = (TextView) layout.findViewById(R.id.dataPesquisaFim);
        dataPesquisaFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener data = preparaCalendario(dataPesquisaFim);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, data, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH));
                Locale.setDefault(new Locale("pt","BR"));
                datePickerDialog.show();
            }
        });

        /**
         * Necessario carregar os elementos diretamente do layout que esta sendo usado,
         * porque as instancias desses elementos nao existem fora do layout do popup
         */
        Button calcelarPopup = (Button) layout.findViewById(R.id.cancelarPopupButton);
        calcelarPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Chamando a tela de cadastro quando clicar na opcao de cadastro
        cadastrarButton  = (Button) findViewById(R.id.cadastrarButton);
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
    }
}
