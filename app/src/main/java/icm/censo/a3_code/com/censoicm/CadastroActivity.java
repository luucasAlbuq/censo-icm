package icm.censo.a3_code.com.censoicm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Censo;

public class CadastroActivity extends AppCompatActivity {

    private Censo censo;
    private int varoesValue, senhorasValue, jovensValue, adolescentesValue, criancasValue, visitantesValue, totalValue;
    private TextView totalTextView;
    private EditText varoesField, senhorasField, jovensField, adolescentesField, criancasField, visitantesField;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Inicializando objeto censo
        censo = new Censo();

        //Carregando valores dos campos de edicao
        varoesField = (EditText) findViewById(R.id.varoes);
        senhorasField = (EditText) findViewById(R.id.senhoras);
        jovensField = (EditText) findViewById(R.id.jovens);
        adolescentesField = (EditText) findViewById(R.id.adolescentes);
        criancasField = (EditText) findViewById(R.id.criancas);
        visitantesField = (EditText) findViewById(R.id.visitantes);
        totalTextView = (TextView) findViewById(R.id.total);

        addTextChangedListener();
    }

}
