package com.example.jgarciaamor.juegocolores;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.empezar);
        Button azul = (Button) findViewById(R.id.azul);
        azul.setEnabled(false);
        Button rojo = (Button) findViewById(R.id.rojo);
        rojo.setEnabled(false);
        Button amarillo = (Button) findViewById(R.id.amarillo);
        amarillo.setEnabled(false);
        Button verde = (Button) findViewById(R.id.verde);
        verde.setEnabled(false);
        TextView nivel = (TextView) findViewById(R.id.nivel);
    }

    int [] botones= {R.id.azul,R.id.rojo,R.id.amarillo,R.id.verde};
    String [] colorBoton={"#00B0FF","#E57373","#FFFF99","#8BC34A"};
    int [] colorClaro={Color.BLUE,Color.RED,Color.parseColor("#FFFF00"),Color.GREEN};

    int [] arrayAudio ={R.raw.azul,R.raw.rojo,R.raw.amarillo,R.raw.verde};

    TimerTask tiempoTarea;
    Timer tiempo;
    ArrayList<Integer> colores = new ArrayList();
    ArrayList<Integer> jugador = new ArrayList();

    protected static int CONTADOR = 0;
    protected static int CONTTIEMPO=0;
    protected static int DIFICULT=4;
    protected static int NIVEL=1;

    void empezar(View e) {
        findViewById(R.id.azul).setEnabled(true);
        findViewById(R.id.rojo).setEnabled(true);
        findViewById(R.id.amarillo).setEnabled(true);
        findViewById(R.id.verde).setEnabled(true);
        CONTADOR = 0;
        empezarTimer();
        e.setEnabled(false);
    }

    void eventoAzul(View a) {
        CONTADOR++;
        jugador.add(0);
        parpadear(0);
        restablecer();
    }

    void eventoRojo(View r) {
        CONTADOR++;
        jugador.add(1);
        parpadear(1);
        restablecer();
    }

    void eventoAmarillo(View am) {
        CONTADOR++;
        jugador.add(2);
        parpadear(2);
        restablecer();
    }

    void eventoVerde(View a) {
        CONTADOR++;
        jugador.add(3);
        parpadear(3);
        restablecer();
    }


    int aleatorio() {

        int valorDado = (int) Math.floor(Math.random()*4);
        return valorDado;

    }

    void comprobar() {
        TextView nivel = (TextView) findViewById(R.id.nivel);
        if (colores.toString().equals(jugador.toString())) {
            Toast.makeText(this, "Has Ganado", Toast.LENGTH_SHORT).show();
            DIFICULT++;
            NIVEL++;
            nivel.setText("Nivel: "+NIVEL);
        } else{
            Toast.makeText(this, "Has Perdido", Toast.LENGTH_SHORT).show();
            DIFICULT=4;
            NIVEL=1;
            nivel.setText("Nivel: "+NIVEL);}
        findViewById(R.id.azul).setEnabled(false);
        findViewById(R.id.rojo).setEnabled(false);
        findViewById(R.id.amarillo).setEnabled(false);
        findViewById(R.id.verde).setEnabled(false);
    }

    void parpadear(final int posicionBotn){
        findViewById(botones[posicionBotn]).setBackgroundColor(colorClaro[posicionBotn]);
        final MediaPlayer audio = MediaPlayer.create(this, arrayAudio[posicionBotn]);
        audio.start();
        findViewById(botones[posicionBotn]).postDelayed(new Runnable() {
            public void run() {
                audio.reset();
                findViewById(botones[posicionBotn]).setBackgroundColor(Color.parseColor(colorBoton[posicionBotn]));
            }
        }, 600);

    }

    void restablecer(){
        Button start = (Button) findViewById(R.id.empezar);
        if (CONTADOR == DIFICULT) {
            comprobar();
            colores.clear();
            jugador.clear();
            CONTTIEMPO=0;
            start.setEnabled(true);
        }
    }


    void inicializarTimer (){
        tiempoTarea = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int numeroAlea=aleatorio();
                        colores.add(numeroAlea);
                        if(numeroAlea==0){
                            parpadear(0);}
                        if(numeroAlea==1){
                            parpadear(1);
                        }
                        if(numeroAlea==2){
                            parpadear(2);
                        }
                        if(numeroAlea==3){
                            parpadear(3);
                        }

                        CONTTIEMPO++;
                        if(CONTTIEMPO==DIFICULT){
                            pararTimer();
                        }

                    }
                });

            }
        };


    }
    public void empezarTimer(){
        tiempo = new Timer();
        inicializarTimer();
        tiempo.schedule(tiempoTarea, 200, 1000);
    }
    public void pararTimer(){
        if (tiempo !=null){
            tiempo.cancel();
            tiempo= null;
        }
    }

}