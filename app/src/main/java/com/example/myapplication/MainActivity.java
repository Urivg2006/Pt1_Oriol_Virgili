package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerIdiomes;
    private boolean isUserAction = false;
    Button btnl0, btnl1, btnl2, btnl3, btnl4, btnl5, btnl6, btnl7, btnl8, btnl9, btnlcoma, btnldel, btnlsum, btnlrest, btnlmult, btnldiv, btnlresult;
    TextView textResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

            spinnerIdiomes = findViewById(R.id.spinnerIdiomes);
        String[] languages = {"Català", "Español", "English"};
        String[] langCodes = {"ca", "es", "en"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                languages
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdiomes.setAdapter(adapter);

        String currentLang = AppCompatDelegate.getApplicationLocales().toLanguageTags();
        if (currentLang.startsWith("es")) {
            spinnerIdiomes.setSelection(1);
        } else if (currentLang.startsWith("en")) {
            spinnerIdiomes.setSelection(2);
        } else {
            spinnerIdiomes.setSelection(0);
        }
        spinnerIdiomes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isUserAction) {
                    isUserAction = true;
                    return;
                }

                String selectedLang = langCodes[position];

                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(selectedLang));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnl0 = findViewById(R.id.button0);
        btnl1 = findViewById(R.id.button1);
        btnl2 = findViewById(R.id.button2);
        btnl3 = findViewById(R.id.button3);
        btnl4 = findViewById(R.id.button4);
        btnl5 = findViewById(R.id.button5);
        btnl6 = findViewById(R.id.button6);
        btnl7 = findViewById(R.id.button7);
        btnl8 = findViewById(R.id.button8);
        btnl9 = findViewById(R.id.button9);
        btnlcoma = findViewById(R.id.buttoncoma);
        btnldel = findViewById(R.id.buttondel);
        btnlsum = findViewById(R.id.buttonsum);
        btnlrest = findViewById(R.id.buttonrest);
        btnlmult = findViewById(R.id.buttonmult);
        btnldiv = findViewById(R.id.buttondiv);
        btnlresult = findViewById(R.id.buttonresult);
        textResultat = findViewById(R.id.textView);

        btnlcoma.setOnClickListener(this);
        btnldel.setOnClickListener(this);
        btnlsum.setOnClickListener(this);
        btnlrest.setOnClickListener(this);
        btnlmult.setOnClickListener(this);
        btnldiv.setOnClickListener(this);
        btnlresult.setOnClickListener(this);


        for (int i = 0; i <= 9; i++) {
            String nomBoto = "button" + i;
            int id = getResources().getIdentifier(nomBoto, "id", getPackageName());

            Button b = findViewById(id);
            if (b != null) {
                b.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonresult) {
            double resultat = calcul();
            textResultat.setText(String.valueOf(resultat));
            return;
        }
        if (v instanceof Button) {
            if (v.getId() == R.id.buttondel) {
                textResultat.setText("");
            } else {
                Button botoPremut = (Button) v;
                String textActual = textResultat.getText().toString();
                textResultat.setText(textActual + botoPremut.getText().toString());
            }

            //calcul();
        }

    }

    public double calcul() {
        String calcul = textResultat.getText().toString().trim();
        if (calcul.isEmpty()) return 0;

        calcul = calcul.replace("+", " + ").replace("-", " - ").replace("x", " * ").replace("/", " / ");
        String[] tokens = calcul.trim().split("\\s+");

        if (tokens.length == 0) return 0;

        double resultat = 0;
        try {
            resultat = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i += 2) {
                if (i + 1 >= tokens.length) break;

                String operador = tokens[i];
                double seguentNumero = Double.parseDouble(tokens[i + 1]);

                switch (operador) {
                    case "+":
                        resultat += seguentNumero;
                        break;
                    case "-":
                        resultat -= seguentNumero;
                        break;
                    case "*":
                        resultat *= seguentNumero;
                        break;
                    case "/":
                        resultat /= seguentNumero;
                        break;
                }
            }
        } catch (NumberFormatException e) {
            return 0;
        }

        return resultat;
    }
}
