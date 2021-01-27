package com.disfarse.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddContact extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    EditText etNome, etTelefone, etEmail, etDatanasc;
    Button bt_confirmar, bt_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etNome=findViewById(R.id.et_nomeC);
        etTelefone=findViewById(R.id.et_telefoneC);
        etEmail=findViewById(R.id.et_emailC);
        etDatanasc=findViewById(R.id.et_datNasC);
        bt_confirmar=findViewById(R.id.btConfirmar);
        bt_cancelar=findViewById(R.id.btCancelar);

        bt_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome1 = etNome.getText().toString().trim();
                String telefone1 = etTelefone.getText().toString().trim();
                String email1 = etEmail.getText().toString().trim();
                String dataNasc1 = etDatanasc.getText().toString().trim();
                // a check to avoid null exception in parse Int in main activity and crash app
                if(TextUtils.isEmpty(telefone1)) { etTelefone.setError("Este campo não pode estar vazio");
                    return;
                }
                // add a check to name just because is a good way to to things
                if(TextUtils.isEmpty(nome1)) { etNome.setError("Este campo não pode estar vazio");
                    return;
                }
                if(TextUtils.isEmpty(email1)) { etEmail.setError("Este campo não pode estar vazio");
                    return;
                }


                Intent returnIntend = new Intent();
                returnIntend.putExtra("nome1", nome1);
                returnIntend.putExtra("telefone1", telefone1);
                returnIntend.putExtra("email1", email1);
                returnIntend.putExtra("dataNasc1", dataNasc1);
                setResult(RESULT_OK,returnIntend);


                finish();
            }
        });

        bt_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // updateLabel();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                etDatanasc.setText(sdf.format(myCalendar.getTime()));
            }
        };
        etDatanasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddContact.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void ShowAlertDialog (View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "We will leave", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "We will stay", Toast.LENGTH_LONG).show();
            }
        });
    }
}