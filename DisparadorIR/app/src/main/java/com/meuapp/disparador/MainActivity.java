package com.meuapp.disparador;

import android.app.Activity;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ConsumerIrManager irManager;
    private final Handler handler = new Handler();
    private Runnable loopSinal;
    private Runnable repetirManual;
    private boolean enviando = false;

    private Button btnComecar, btnParar;
    private EditText etIntervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        irManager = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

        etIntervalo = findViewById(R.id.etIntervalo);
        Button btnManual = findViewById(R.id.btnManual);
        btnComecar = findViewById(R.id.btnComecar);
        btnParar = findViewById(R.id.btnParar);

        // Botão azul – toque contínuo + troca visual
        repetirManual = new Runnable() {
            @Override
            public void run() {
                enviarIR();
                handler.postDelayed(this, 100);
            }
        };

        btnManual.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setPressed(true); // mostra visual pressionado
                    handler.post(repetirManual);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setPressed(false);
                    handler.removeCallbacks(repetirManual);
                    v.performClick();
                    return true;
            }
            return false;
        });

        desativarComecar();
        desativarParar();

        // Input digitado
        etIntervalo.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                atualizarEstadoBotaoComecar();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnComecar.setOnClickListener(view -> {
            String texto = etIntervalo.getText().toString().trim();
            if (texto.isEmpty() || texto.equals("0")) return;

            int segundos = Integer.parseInt(texto);
            int intervaloMs = segundos * 1000;

            enviando = true;
            desativarComecar();
            ativarParar();

            loopSinal = new Runnable() {
                @Override
                public void run() {
                    if (enviando) {
                        enviarIR();
                        handler.postDelayed(this, intervaloMs);
                    }
                }
            };

            handler.post(loopSinal);
            Toast.makeText(this, "Disparo automático iniciado", Toast.LENGTH_SHORT).show();
        });

        btnParar.setOnClickListener(view -> {
            enviando = false;
            handler.removeCallbacks(loopSinal);
            desativarParar();
            atualizarEstadoBotaoComecar();
            Toast.makeText(this, "Disparo automático parado", Toast.LENGTH_SHORT).show();
        });

        atualizarEstadoBotaoComecar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarEstadoBotaoComecar();
    }

    private void enviarIR() {
        if (irManager != null && irManager.hasIrEmitter()) {
            int frequency = 38000;
            int[] pattern = {
                    9000, 4500,
                    560, 1690, 560, 560, 560, 1690, 560, 560,
                    560, 1690, 560, 560, 560, 1690, 560, 560,
                    560, 560, 560, 1690, 560, 560, 560, 560,
                    560
            };
            irManager.transmit(frequency, pattern);
        }
    }

    private void ativarComecar() {
        btnComecar.setEnabled(true);
        btnComecar.setBackgroundResource(R.drawable.button_verde);
    }

    private void desativarComecar() {
        btnComecar.setEnabled(false);
        btnComecar.setBackgroundResource(R.drawable.button_verde_disabled);
    }

    private void ativarParar() {
        btnParar.setEnabled(true);
        btnParar.setBackgroundResource(R.drawable.button_laranja);
    }

    private void desativarParar() {
        btnParar.setEnabled(false);
        btnParar.setBackgroundResource(R.drawable.button_laranja_disabled);
    }

    private void atualizarEstadoBotaoComecar() {
        String texto = etIntervalo.getText().toString().trim();
        if (!enviando && !texto.isEmpty() && !texto.equals("0")) {
            ativarComecar();
        } else {
            desativarComecar();
        }
    }
}
