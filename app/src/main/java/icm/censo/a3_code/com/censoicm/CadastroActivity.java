package icm.censo.a3_code.com.censoicm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import model.Censo;

public class CadastroActivity extends AppCompatActivity {

    private int varoesValue, senhorasValue, jovensValue, adolescentesValue, criancasValue, visitantesValue, totalValue;
    private TextView totalTextView;
    //Campos de frequencia
    private EditText varoesField, senhorasField, jovensField, adolescentesField, criancasField, visitantesField;
    //Campos de informacoes textuais
    private EditText portaField, palavraField, louvorField, domField;

    private Button botaoSalvar;

    /**
     * Carrega os valores dos campos editText para as variaveis correspondentes.
     */
    private void carregaFrequencia() {
        try{
            if(!varoesField.getText().toString().isEmpty()){
                varoesValue = Integer.parseInt(varoesField.getText().toString());
            }if(!senhorasField.getText().toString().isEmpty()){
                senhorasValue = Integer.parseInt(senhorasField.getText().toString());
            }if(!jovensField.getText().toString().isEmpty()){
                jovensValue = Integer.parseInt(jovensField.getText().toString());
            }if(!adolescentesField.getText().toString().isEmpty()){
                adolescentesValue = Integer.parseInt(adolescentesField.getText().toString());
            }if(!criancasField.getText().toString().isEmpty()){
                criancasValue = Integer.parseInt(criancasField.getText().toString());
            }if(!visitantesField.getText().toString().isEmpty()){
                visitantesValue = Integer.parseInt(visitantesField.getText().toString());
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Dados inválidos", Toast.LENGTH_SHORT).show();
            Log.e("formValues",e.getMessage());
        }
    }

    /**
     * Calcula o total de pessoas
     * @return total de pessoas
     */
    private int calcularTotal() {
        totalValue =  varoesValue + senhorasValue + jovensValue + adolescentesValue + criancasValue + visitantesValue;
        return totalValue;
    }

    /**
     * Adiciona evento de onChange aos campos de frequencia para atualizar o total dinamicamente
     */
    private void addTextChangedListener(){
        varoesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
        senhorasField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
        jovensField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
        adolescentesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
        criancasField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
        visitantesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                carregaFrequencia();
                calcularTotal();
                totalTextView.setText("Total: "+totalValue);
            }
        });
    }

    private Censo buildCenso(){
        Censo censo = new Censo();

        censo.setQtdVaroes(varoesValue);
        censo.setQtdSenhoras(senhorasValue);
        censo.setQtdJovens(jovensValue);
        censo.setQtdCriancas(criancasValue);
        censo.setQtdVisitantes(visitantesValue);
        censo.setQtdAdolescentes(adolescentesValue);
        censo.setTotalPessoas(totalValue);

        String palavra = palavraField.getText().toString();
        String louvor = louvorField.getText().toString();
        String dom  = domField.getText().toString();
        String[] porta = portaField.getText().toString().split(" ");

        censo.setObreiroPalavra(palavra);
        censo.setObreiroLouvor(louvor);
        censo.setObreirosPorta(new HashSet<String>(Arrays.asList(porta)));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        censo.setData(new Date());

        return censo;
    }

    /**
     * Verifica se todos os campos obrigatorios foram preenchidos
     * @return boolean
     */
    public boolean verificaCamposObrigatorios(){
        boolean valido = true;

        if(palavraField.getText().toString().isEmpty()){
            palavraField.setHintTextColor(Color.RED);
            valido = false;
        }if(louvorField.getText().toString().isEmpty()){
            louvorField.setHintTextColor(Color.RED);
            valido = false;
        }if(totalValue <= 0){
            totalTextView.setTextColor(Color.RED);
            valido = false;
        }

        return  valido;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Carregando valores dos campos de edicao
        varoesField = (EditText) findViewById(R.id.varoes);
        senhorasField = (EditText) findViewById(R.id.senhoras);
        jovensField = (EditText) findViewById(R.id.jovens);
        adolescentesField = (EditText) findViewById(R.id.adolescentes);
        criancasField = (EditText) findViewById(R.id.criancas);
        visitantesField = (EditText) findViewById(R.id.visitantes);
        totalTextView = (TextView) findViewById(R.id.total);
        portaField = (EditText) findViewById(R.id.porta);
        palavraField = (EditText) findViewById(R.id.palavra);
        louvorField = (EditText) findViewById(R.id.louvor);
        domField = (EditText) findViewById(R.id.dom);
        botaoSalvar = (Button) findViewById(R.id.salvar);

        addTextChangedListener();

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCamposObrigatorios()){
                    buildCenso();
                    Toast.makeText(getApplicationContext(), "Dados Salvos", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
