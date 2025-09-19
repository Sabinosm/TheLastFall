package com.pedro.configuracoes;
import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.sql.*;


import java.io.IOException;

import static com.pedro.UtilForMe.readInt;
import static com.pedro.configuracoes.PlayerConfigurations.mostrarPlayers;

public final class TelaInicial {


    public TelaInicial() {

    }




    public static Player escolhasTelaInicial() throws SQLException, IOException, InterruptedException {

        PlayerConfigurations.carregarConfiguracoes();

        Terminal terminal;
        Player x = null;
        try {
              terminal = TerminalBuilder.builder()
                .jna(false) // evita bug de raw mode em alguns terminais
                .system(true)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
        String escolha;
        String url = "jdbc:sqlite:dataSave.db";
        Connection conexao = DriverManager.getConnection(url);

        while (x == null) {
            UtilForMe.fakeClear(50,false); //verificado




            System.out.println("""
                     ▄▄▄▄▄▄▄▄  ▄▄    ▄▄  ▄▄▄▄▄▄▄▄                      ▄▄           ▄▄       ▄▄▄▄    ▄▄▄▄▄▄▄▄                      ▄▄▄▄▄▄▄▄     ▄▄     ▄▄        ▄▄      \s
                     ▀▀▀██▀▀▀  ██    ██  ██▀▀▀▀▀▀                      ██          ████    ▄█▀▀▀▀█   ▀▀▀██▀▀▀                      ██▀▀▀▀▀▀    ████    ██        ██      \s
                        ██     ██    ██  ██                            ██          ████    ██▄          ██                         ██          ████    ██        ██      \s
                        ██     ████████  ███████                       ██         ██  ██    ▀████▄      ██                         ███████    ██  ██   ██        ██      \s
                        ██     ██    ██  ██                            ██         ██████        ▀██     ██                         ██         ██████   ██        ██      \s
                        ██     ██    ██  ██▄▄▄▄▄▄                      ██▄▄▄▄▄▄  ▄██  ██▄  █▄▄▄▄▄█▀     ██                         ██        ▄██  ██▄  ██▄▄▄▄▄▄  ██▄▄▄▄▄▄\s
                        ▀▀     ▀▀    ▀▀  ▀▀▀▀▀▀▀▀                      ▀▀▀▀▀▀▀▀  ▀▀    ▀▀   ▀▀▀▀▀       ▀▀                         ▀▀        ▀▀    ▀▀  ▀▀▀▀▀▀▀▀  ▀▀▀▀▀▀▀▀\s
                    """);

            System.out.println("""
                    
                    
                    
                    Bem-vindo, aventureiro.
                    Você não está aqui por acaso.
                    Se caiu neste lugar, é porque o vazio o escolheu… ou o condenou.
                    Aqui, cada passo será pesado, cada respiração custará caro.
                    As sombras o observarão, e a dor será seu guia.
                    Ainda assim, há uma chance, pequena, quase esquecida: sobreviver… e descobrir a verdade da Queda......
                    
                    (1) Novo jogo
                    (2) Carregar
                    (3) Importante
                    (4) Configurações
                    """);


            
            escolha = String.valueOf(readInt());

            switch (escolha) {
                case "1":
                    PlayerConfigurations.criarTabela();
                    x = PlayerConfigurations.criarNovoPlayer();

                break;
                case "2":
                   x =  PlayerConfigurations.carregarPlayer(mostrarPlayers());
                    break;
                case "3":
                         System.out.println("""
                                 
                               
                                 Importante --------
                                 ===Não digite entre os textos, letras, números, enter, espaço etc. Pode acabar causando erro na leitura de dados
                                 e acabando por estragar o seu salvamento.
                                 
                                 
                                 ( ENTER ) para sair
                                 
                                 """);

                         
                         reader.readLine();

                         break;

                case "4":


                    var sql3 = """
                            INSERT OR IGNORE INTO Configuracoes(id,velocidadeTexto)
                            VALUES(?,?)
                            """;


                    var pstmt = conexao.prepareStatement(sql3);
                    pstmt.setInt(1,1);
                    pstmt.setString(2,"D");

                    pstmt.executeUpdate();

                    while (!escolha.equals("5")){

                        UtilForMe.fakeClear(50,false); //verificado
                        System.out.println(velText());

                        
                        escolha = String.valueOf(readInt()).trim();

                        switch (escolha){
                            case "1":
                                UtilForMe.setVelocidadeTexto("I");
                                atualizarVelocidade(conexao,"I");
                                break;
                            case "2":
                                UtilForMe.setVelocidadeTexto("R");
                                atualizarVelocidade(conexao,"R");
                                break;
                            case "3":
                                UtilForMe.setVelocidadeTexto("L");
                                atualizarVelocidade(conexao,"L");
                                break;
                            case "4":
                                UtilForMe.setVelocidadeTexto("D");
                                atualizarVelocidade(conexao,"D");

                                break;
                            case "5": UtilForMe.fakeClear(50,false); //verificado
                                break;
                            default:
                                System.out.println("Digite um número válido.");
                                break;

                        }
                    }
                    break;



            }

        }

        return x;
    }

    private static String velText(){
        String velText = UtilForMe.getVelocidadeTexto();
        String texto = switch (velText) {
            case "I" -> ("""
                    Configurações - Velocidade do texto
                    
                    ( 1 ) Instantâneo -> Ativo
                    ( 2 ) Rápido
                    ( 3 ) Lento
                    ( 4 ) Normal
                    
                    
                    ( 5 ) Voltar
                    """);
            case "R" -> ("""
                    Configurações - Velocidade do texto
                    
                    ( 1 ) Instantâneo
                    ( 2 ) Rápido -> Ativo
                    ( 3 ) Lento
                    ( 4 ) Normal
                    
                    
                    ( 5 ) Voltar
                    """);
            case "L" -> ("""
                    Configurações - Velocidade do texto
                    
                    ( 1 ) Instantâneo
                    ( 2 ) Rápido
                    ( 3 ) Lento -> Ativo
                    ( 4 ) Normal
                    
                    
                    ( 5 ) Voltar
                    """);
            default -> ("""
                    Configurações - Velocidade do texto
                    
                    ( 1 ) Instantâneo
                    ( 2 ) Rápido
                    ( 3 ) Lento
                    ( 4 ) Normal -> Ativo
                    
                    
                    ( 5 ) Voltar
                    """);
        };

        return texto +"\n";
    }

    private static void atualizarVelocidade(Connection conexao, String valor) throws SQLException {
        String sql = """
        UPDATE Configuracoes
        SET velocidadeTexto = ?
        WHERE id = 1
        """;
        try (var pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, valor);
            pstmt.executeUpdate();
        }
    }

}
