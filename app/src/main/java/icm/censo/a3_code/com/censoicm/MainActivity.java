package icm.censo.a3_code.com.censoicm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private  Button cadastrarButton, pesquisarMenuButton;

    private void preparaPesquisaPopup(){
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pesquisar_popup, null);
        PopupWindow popupWindow = new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(pesquisarMenuButton, Gravity.CENTER, 0, 0);
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

        //Chamando a tela de cadastro quando clicar na opcao de pesquisa
        pesquisarMenuButton = (Button) findViewById(R.id.pesquisarButton);
        pesquisarMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preparaPesquisaPopup();
            }
        });

    }
}
