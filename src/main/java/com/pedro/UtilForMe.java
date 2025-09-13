package com.pedro;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.text.DecimalFormat;


public abstract class UtilForMe {
    static String velocidadeTexto = "";



    public static void setVelocidadeTexto(String velocidadeTexto) {
        UtilForMe.velocidadeTexto = velocidadeTexto;
    }

    public static String getVelocidadeTexto() {
        return velocidadeTexto;
    }

    public static void fakeClear(int clearNum,boolean enterParaSeguir ) throws IOException {
        if(enterParaSeguir){
            System.out.println("\n\n( ENTER ) Para seguir");
            Terminal terminal = TerminalBuilder.builder().system(true).build();
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            reader.getBuffer().clear();
            reader.readLine();
        }
        for (int x = 0; x < clearNum; x++)
        {
            System.out.println(" ");
        }
    }

    public static String arr(double d) {
        DecimalFormat dc = new DecimalFormat("#.##");
        return dc.format(d);
    }

    public static void tempoDeLeitura(String texto) throws InterruptedException, IOException {
        int vel ;

        if(UtilForMe.velocidadeTexto.equals("I"))
        {
            vel = 1;

        }
        else if(UtilForMe.velocidadeTexto.equals("R")){
            vel = 25;
        }
        else if(UtilForMe.velocidadeTexto.equals("L")){
            vel = 50;
        }
        else{
            vel = 40;
        }

        int quantidadeDeCaracteres = texto.length();
        int tempoLeitura = quantidadeDeCaracteres * vel;

        System.out.println(texto);
        Thread.sleep(tempoLeitura);

    }
}
