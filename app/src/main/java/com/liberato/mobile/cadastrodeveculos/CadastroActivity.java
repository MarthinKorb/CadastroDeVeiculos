package com.liberato.mobile.cadastrodeveculos;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {
    private Button btVoltar;
    private Button btLimpar;
    private Button btGravar;
    private EditText placa;
    private EditText proprietario;
    private EditText marca;
    private EditText modelo;
    private EditText ano;
    SQLiteDatabase bancoDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btVoltar = findViewById(R.id.btVoltarId);
        btLimpar = findViewById(R.id.btLimparId);
        btGravar = findViewById(R.id.btGravarId);
        placa = findViewById(R.id.placaId);
        proprietario = findViewById(R.id.proprietarioID);
        marca = findViewById(R.id.marcaID);
        modelo = findViewById(R.id.modeloID);
        ano = findViewById(R.id.anoId);
        try {
            bancoDados =
                    openOrCreateDatabase("veiculos", MODE_PRIVATE, null);
            bancoDados.execSQL
                    ("CREATE TABLE IF NOT EXISTS veiculos ( id  integer primary key autoincrement," +
                            "                               placa text," +
                            "                               proprietario text," +
                            "                               marca text," +
                            "                               modelo text," +
                            "                               ano integer);");
            btGravar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vPlaca = placa.getText().toString();
                    String vDono = proprietario.getText().toString();
                    String vMarca = marca.getText().toString();
                    String vModelo = modelo.getText().toString();
                    String vAno = ano.getText().toString();
                    if(vPlaca.isEmpty()||vDono.isEmpty()||vMarca.isEmpty()||vModelo.isEmpty()|| vAno.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Preencha Todos os Campos!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        bancoDados.execSQL("insert into veiculos (placa, proprietario, marca, modelo, ano) values ('" + vPlaca.toUpperCase() + "','" + vDono + "','" + vMarca + "','" + vModelo + "','" + vAno + "')");
                        Toast.makeText(getApplicationContext(), "Registro incluido!", Toast.LENGTH_SHORT).show();
                        limpar();
                    }
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpar();
            }
        });
    }
    private void limpar(){
        placa.setText("");
        proprietario.setText("");
        marca.setText("");
        modelo.setText("");
        ano.setText("");
        placa.requestFocus();
    }
}
