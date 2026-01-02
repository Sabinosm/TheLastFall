package com.pedro.configuracoes;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Player;
import java.sql.*;
import java.io.IOException;
import static com.pedro.UtilForMe.ReadInt;
import static com.pedro.configuracoes.PlayerConfigurations.MostrarPlayers;

public final class TelaInicial {


    public TelaInicial() {

    }




    public static Player EscolhasTelaInicial() throws SQLException, IOException, InterruptedException {

        PlayerConfigurations.CarregarConfiguracoes();


        Player x = null;

        String escolha;
        String url = "jdbc:sqlite:dataSave.db";
        Connection conexao = DriverManager.getConnection(url);

        while (x == null) {
            UtilForMe.FakeClear(50,false); //verificado




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


            
            escolha = String.valueOf(ReadInt(null));

            switch (escolha) {
                case "1":
                    PlayerConfigurations.CriarTabela();
                    x = PlayerConfigurations.CriarNovoPlayer();

                break;
                case "2":
                   x =  PlayerConfigurations.CarregarPlayer(MostrarPlayers());
                    break;
                case "3":
                         System.out.println("""
                                 
                               
                                 Importante --------
                                 ===Não digite entre os textos, letras, números, enter, espaço etc. Pode acabar causando erro na leitura de dados
                                 e acabando por estragar o seu salvamento.
                                 
                                
                                 
                                 """);

                         UtilForMe.FakeClear(0,true);


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

                        UtilForMe.FakeClear(50,false); //verificado
                        System.out.println(VelText());

                        
                        escolha = String.valueOf(ReadInt(null)).trim();

                        switch (escolha){
                            case "1":
                                UtilForMe.SetVelocidadeTexto("I");
                                AtualizarVelocidade(conexao,"I");
                                break;
                            case "2":
                                UtilForMe.SetVelocidadeTexto("R");
                                AtualizarVelocidade(conexao,"R");
                                break;
                            case "3":
                                UtilForMe.SetVelocidadeTexto("L");
                                AtualizarVelocidade(conexao,"L");
                                break;
                            case "4":
                                UtilForMe.SetVelocidadeTexto("D");
                                AtualizarVelocidade(conexao,"D");

                                break;
                            case "5": UtilForMe.FakeClear(50,false); //verificado
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

    private static String VelText(){
        String velText = UtilForMe.GetVelocidadeTexto();
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

    private static void AtualizarVelocidade(Connection conexao, String valor) throws SQLException {
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
