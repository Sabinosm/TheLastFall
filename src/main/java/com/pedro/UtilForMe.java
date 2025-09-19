package com.pedro;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.text.DecimalFormat;



public abstract class UtilForMe {
    static String velocidadeTexto = "";
    // Criar o terminal e reader apenas uma vez (static)
    // assim você não precisa recriar toda vez.
    private static final Terminal terminal;
    private static final LineReader reader;

    static {
        try {
            terminal = TerminalBuilder.builder()
                    .jna(false)
                    .system(true)
                    .build();

            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar Terminal/Reader", e);
        }
    }

    public static void setVelocidadeTexto(String velocidadeTexto) {
        UtilForMe.velocidadeTexto = velocidadeTexto;
    }

    public static String getVelocidadeTexto() {
        return velocidadeTexto;
    }

    public static void fakeClear(int clearNum, boolean enterParaSeguir) throws IOException {
        if (enterParaSeguir) {
            System.out.println("\n\n( ENTER ) Para seguir");

            // 🔹 espera só o ENTER
            while (System.in.read() != '\n') {
                // ignora qualquer coisa até o Enter
            }
        }

        for (int x = 0; x < clearNum; x++) {
            System.out.println(" ");
        }
    }


    // Método para ler inteiro
    public static int readInt() throws IOException {
        int numero = -1;
        boolean valido = false;

        while (!valido) {
            try {
                // 🔹 limpa resíduos
                while (terminal.reader().ready()) {
                    terminal.reader().read();
                }

                String entrada = reader.readLine();
                numero = Integer.parseInt(entrada.trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("⚠ Digite apenas números.");

            } catch (IOException e) {
                System.out.println("Erro de leitura: " + e.getMessage());
            }
        }
        fakeClear(50,true);
        return numero;
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
