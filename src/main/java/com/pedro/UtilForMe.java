package com.pedro;

import com.pedro.configuracoes.Checkpoint;
import com.pedro.eventos.EventosSecundarios;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


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

    public static void SetVelocidadeTexto(String velocidadeTexto) {
        UtilForMe.velocidadeTexto = velocidadeTexto;
    }

    public static String GetVelocidadeTexto() {
        return velocidadeTexto;
    }

    public static void FakeClear(int clearNum, boolean enterParaSeguir) throws IOException {
        if (enterParaSeguir) {

            System.out.println("\n\n( ENTER ) Para seguir");

            // 🔹 espera só o ENTER
            while (System.in.read() != '\n') {
            }
        }

        for (int x = 0; x < clearNum; x++) {
            System.out.println(" ");
        }
    }


    // Método para ler inteiro
    public static int ReadInt(Player player) throws IOException {
        int numero = -1;
        boolean valido = false;

        while (!valido) {
            try {
                // 🔹 limpa resíduos
                while (terminal.reader().ready()) {
                    terminal.reader().read();
                }

                String entrada = reader.readLine();
                if(entrada.startsWith("/")) {
                    Comando(entrada, player);
                    return 99;
                }
                    numero = Integer.parseInt(entrada.trim());
                valido = true;

            } catch (NumberFormatException e) {
                System.out.println("⚠ Digite apenas números.");

            } catch (IOException | SQLException | InterruptedException e) {
                System.out.println("Erro de leitura: " + e.getMessage());
            }
        }
        while (terminal.reader().ready()) {
            terminal.reader().read();
        }
        return numero;
    }


    public static int ReadInt() throws IOException {
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
        while (terminal.reader().ready()) {
            terminal.reader().read();
        }
        return numero;
    }


    //Método para ler string
    public static String ReadStr() throws IOException {
        String texto = "";
        boolean valido = false;

        while (!valido) {
            try {
                // 🔹 limpa resíduos
                while (terminal.reader().ready()) {
                    terminal.reader().read();
                }

                String entrada = reader.readLine();

                texto = entrada.trim();
                valido = true;

            } catch (NumberFormatException e) {
                System.out.println("Digite a palavra correta");

            } catch (IOException e) {
                System.out.println("Erro de leitura: " + e.getMessage());
            }
        }
        while (terminal.reader().ready()) {
            terminal.reader().read();
        }
        return texto;
    }

    private static void Comando(String cmd, Player player) throws SQLException, IOException, InterruptedException {

        List<String> cmdLines = Arrays.asList(cmd.split(" "));
        if(cmdLines.get(0).equals("/kill")){
            System.out.println("Lendo Comando.....");
            player.setActLife(0);
            Player.MorteJogador(player,null);
        }
        else if(cmdLines.get(0).equals("/upPlayerLevel")){
            System.out.println("Lendo Comando.....");
            try{
                for(int x = 1; x <= Integer.parseInt(cmdLines.get(1)); x++) {
                    double xpUpar = player.getXpParaProximoLevel() - player.getXpAtual();
                    player.upLevel(xpUpar);
                }
                System.out.println("Upou " + Integer.parseInt(cmdLines.get(1)) + " leveis");
                UtilForMe.FakeClear(2,true);
            } catch (NumberFormatException e) {
                System.out.println("Número de level inválido!");
                UtilForMe.FakeClear(2,true);
            }
        }
        else if(cmdLines.get(0).equals("/irParaBoss")){
            System.out.println("Lendo Comando.....");
            if(player.getCheckPoint().name().contains("PRIMEIRA_QUEDA")){
                player.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_BOSS);
            }
            else if(player.getCheckPoint().name().contains("SEGUNDA_QUEDA")){
                player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_BOSS);
            }
        }
        else if(cmdLines.get(0).equals("/irParaDescanso")){
            System.out.println("Lendo Comando.....");
            if(player.getCheckPoint().name().contains("PRIMEIRA_QUEDA")){
                EventosSecundarios.Descanso(player,1,null);
            }
            else if(player.getCheckPoint().name().contains("SEGUNDA_QUEDA")){
                System.out.println("AVISO! Isso mudara seu checkPoint para Segunda-Queda apenas!");
                EventosSecundarios.Descanso(player,2,0);
            }
        }
        else {
            System.out.println("ERR");
        }
    }


    public static String Arr(double d) {
        DecimalFormat dc = new DecimalFormat("#.##");
        return dc.format(d);
    }

    public static void TempoDeLeitura(String texto) throws InterruptedException, IOException {
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
