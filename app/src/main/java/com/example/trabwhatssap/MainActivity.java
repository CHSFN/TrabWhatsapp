package com.example.trabwhatssap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText telefone, mensagem;
    Button btnEnviar;
    String mensagemStr = "";
    String telefonestr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryCodePicker = findViewById(R.id.CountryCode);
        telefone = findViewById(R.id.Telefone);
        mensagem = findViewById(R.id.Mensagem);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mensagemStr = mensagem.getText().toString();
                telefonestr = telefone.getText().toString();

                if (!mensagemStr.isEmpty() && !telefonestr.isEmpty()){

                    countryCodePicker.registerCarrierNumberEditText(telefone);
                    telefonestr = countryCodePicker.getFullNumber();

                    if (isWhatappInstalled()){

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/"+telefonestr+
                                "/?text="+mensagemStr));
                        startActivity(i);
                        mensagem.setText("");
                        telefone.setText("");
                    }
                    else{
                        Toast.makeText(MainActivity.this,"O Whatsapp não está instalado no seu celular!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Coloque o numero de telefone e mensagem para funcionar!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean isWhatappInstalled(){

        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {

            packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;


        }catch (PackageManager.NameNotFoundException e){

            whatsappInstalled = false;

        }

        return whatsappInstalled;

    }
}