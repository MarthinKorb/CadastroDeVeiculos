package com.liberato.mobile.cadastrodeveculos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConsultaActivity extends AppCompatActivity {
    private Button btVoltar;
    private Button btConsultar;
    private TextView cPlaca;
    private TextView cProprietario;
    private TextView cMarca;
    private TextView cModelo;
    private TextView cAno;
    private EditText placa;
    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        btVoltar = findViewById(R.id.btVoltar1Id);
        btConsultar = findViewById(R.id.btConsultarVeiculo);
        placa = findViewById(R.id.consultaId);
        cPlaca = findViewById(R.id.cPlacaId);
        cProprietario = findViewById(R.id.cProprietarioId);
        cMarca = findViewById(R.id.cMarcaId);
        cModelo = findViewById(R.id.cModeloId);
        cAno = findViewById(R.id.cAnoId);
        try {
            bancoDados =
                    openOrCreateDatabase("veiculos", MODE_PRIVATE, null);
            btConsultar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String consulta;
                    cPlaca.setText("");
                    cProprietario.setText("");
                    cMarca.setText("");
                    cModelo.setText("");
                    cAno.setText("");
                    consulta = placa.getText().toString();
                    if (consulta.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Preencha a placa para consultar",Toast.LENGTH_LONG).show();
                    }else {
                     //   Cursor cursor = bancoDados.rawQuery("SELECT * FROM veiculos where placa= '"+ consulta.toUpperCase() +"'",null);
                        Cursor cursor = bancoDados.rawQuery("SELECT * FROM veiculos where placa=?",new String[]{consulta.toUpperCase()});
                        int indexColPlaca = cursor.getColumnIndex("placa");
                        int indexColProprietario = cursor.getColumnIndex("proprietario");
                        int indexColMarca = cursor.getColumnIndex("marca");
                        int indexColModelo = cursor.getColumnIndex("modelo");
                        int indexColAno = cursor.getColumnIndex("ano");
                        int vAno;
                        if(cursor.moveToFirst()){
                            cPlaca.setText("Placa : "+cursor.getString(indexColPlaca));
                            cProprietario.setText("Proprietário : "+cursor.getString(indexColProprietario));
                            cMarca.setText("Marca : "+cursor.getString(indexColMarca));
                            cModelo.setText("Modelo : "+cursor.getString(indexColModelo));
                            vAno = cursor.getInt(indexColAno);
                            cAno.setText("Ano : "+String.valueOf(vAno));
                    }else{
                            Toast.makeText(getApplicationContext(),"Não foi encontrado veículo com esta placa",Toast.LENGTH_LONG).show();
                        }
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
    }
}
