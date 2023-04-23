package com.example.calculadoradeimc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    EditText empresaET, empregadoET;
    TextView informacao;
    Button botaoA, botaoB, botaoC, botaoD;
    ListView listView;
    ConexaoBanco conexaoBanco;
    SQLiteDatabase sqlDataBase;
    StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empresaET = findViewById(R.id.editText);
        empregadoET = findViewById(R.id.editText2);
        informacao = findViewById(R.id.informacaoID);
        botaoA = findViewById(R.id.botaoIDA);
        botaoB = findViewById(R.id.botaoIDB);
        botaoC = findViewById(R.id.botaoIDC);
        botaoD = findViewById(R.id.button2);


        conexaoBanco = new ConexaoBanco(this, "dbT", 1);
        sqlDataBase = conexaoBanco.getWritableDatabase();
        stringBuilder = new StringBuilder();

        botaoA.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String empresaS = empresaET.getText().toString();
                                          String empregadoS = empregadoET.getText().toString();

                                          if (empresaS.isEmpty() || empregadoS.isEmpty()) {
                                              Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                                          }else {
                                              ContentValues values = new ContentValues();
                                              values.put("empresa", empresaS);
                                              values.put("empregado", empregadoS);

                                              long id = sqlDataBase.insert("organizacaoEmpresa", null, values);

                                              if (id > 0) {
                                                  Toast.makeText(getApplicationContext(), "Informações armazenadas com sucesso!", Toast.LENGTH_SHORT).show();

                                                  empresaET.setText("");
                                                  empregadoET.setText("");


                                              } else {
                                                  Toast.makeText(getApplicationContext(), "Erro ao armazenar informações!", Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }
                                 });
        botaoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empresaS = empresaET.getText().toString().trim();
                String empregadoS = empregadoET.getText().toString().trim();

                Cursor cursor;

                if (empresaS.isEmpty() && empregadoS.isEmpty()) {
                    cursor = sqlDataBase.query("organizacaoEmpresa", new String[]{"empresa", "empregado"}, null, null, null, null, null);
                }else{
                    cursor = cursor = sqlDataBase.query("organizacaoEmpresa", new String[]{"empresa", "empregado"}, "empresa = ? AND empregado = ?", new String[]{empresaS, empregadoS}, null, null, null);
                }
                StringBuilder stringBuilder = new StringBuilder();

                while (cursor.moveToNext()) {
                    String empresa = cursor.getString(0);
                    String empregado = cursor.getString(1);
                    stringBuilder.append("Empresa: " + empresa + " | Empregado: " + empregado + "\n");
                }

                if (stringBuilder.length() == 0) {
                    informacao.setText("Não há registros com essas informações.");
                } else {
                    informacao.setText(stringBuilder.toString());
                }

                empresaET.setText("");
                empregadoET.setText("");
            }
        });

        botaoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empresaS = empresaET.getText().toString().trim();
                String empregadoS = empregadoET.getText().toString().trim();

                if (empresaS.isEmpty() && empregadoS.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Por favor, preencha os campos de empresa e empregado para deletar.", Toast.LENGTH_SHORT).show();
                } else{
                    int numDeletados = sqlDataBase.delete("organizacaoEmpresa", "empresa = ? AND empregado = ?", new String[]{empresaS, empregadoS});

                    if (numDeletados > 0) {
                        Toast.makeText(getApplicationContext(), "Registros deletados com sucesso!", Toast.LENGTH_SHORT).show();

                        empresaET.setText("");
                        empregadoET.setText("");


                    } else {
                        Toast.makeText(getApplicationContext(), "Não foi possível deletar os registros.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        botaoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = sqlDataBase.query("organizacaoEmpresa", new String[]{"COUNT(*)"}, null, null, null, null, null);
                cursor.moveToFirst();
                int count = cursor.getInt(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Contagem de itens no DB");
                builder.setMessage("Há " + count + " itens no banco de dados.");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });

    };

    }
