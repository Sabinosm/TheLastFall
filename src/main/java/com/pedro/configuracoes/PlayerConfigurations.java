package com.pedro.configuracoes;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Passivas;
import com.pedro.referenteAosPersonagens.Player;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.pedro.UtilForMe.ReadInt;
import static com.pedro.UtilForMe.ReadStr;

public class PlayerConfigurations {

    static String url = "jdbc:sqlite:dataSave.db";
    static Connection conexao;

    static {
        try {
            conexao = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Player CriarNovoPlayer() throws IOException, InterruptedException, SQLException {

        String escolha = "-1";
        List<String> st = new ArrayList<>();
        String nome;
        Player z = null;

        UtilForMe.FakeClear(50,false); //verificado
        while (!escolha.equals("5")) {
            System.out.println(CriarNovoPlayerTextos(1));

            escolha = String.valueOf(ReadInt(null)).trim();

            switch (escolha) {
                case "1" -> st.add("Destino");
                case "2" -> st.add("Aventureiro");
                case "3" -> st.add("Sacrifício");
                case "4" -> st.add("Protetor");
                case "5" -> { /* Voltar */ }
                default -> {
                    System.out.println("Insira um número válido");
                    UtilForMe.FakeClear(50,true); //verificado

                }
            }

            while (!escolha.equals("5")) {
                UtilForMe.FakeClear(50,false); //verificado
                System.out.println(CriarNovoPlayerTextos(2));

                escolha = String.valueOf(ReadInt(null)).trim();

                switch (escolha) {
                    case "1" -> z = NovoPlayer(2, "físico", st.getFirst());
                    case "2" -> z = NovoPlayer(3, "mágico", st.getFirst());
                    case "3" -> z = NovoPlayer(-1, "r", st.getFirst());
                    case "5" -> st.removeFirst();
                    default -> {
                        System.out.println("Insira um número válido");
                        UtilForMe.FakeClear(50,true); //verificado
                    }


                }
                if(!escolha.equals("5") && z != null){

                    UtilForMe.FakeClear(50,false); //verificado
                    System.out.println(CriarNovoPlayerTextos(3));

                    nome = ReadStr();
                    z.setNome(nome);
                    UtilForMe.FakeClear(50,false); //verificado
                    UtilForMe.TempoDeLeitura("""
                    O Criador deixou uma centelha em cada ser humano: rara, instável, mas capaz de moldar destinos.
                    Essa chama não é infinita. Você deve decidir onde acendê-la,
                    pois o Vazio cobra caro de quem desperdiça poder.
                
                    Agora, diante do abismo, você deve distribuir seus dons. Cada ponto reflete não apenas força,
                    mas também o preço que está disposto a pagar.\n\n
                    """);
                    Player.menuHabilidades(z);
                    CriarNoBancoPlayer(z, st.getFirst());
                    escolha = ("5");
                    break;
                }

            }
        }
        return z;
    }

    private static String CriarNovoPlayerTextos(int qualText) {
        return switch (qualText) {
            case 1 -> """
                    ---Escolha do Destino---
                    A escuridão respira nos cantos do mundo sobrevivente. Entre ruínas e fortalezas, a chama da magia ainda palpita nos corações daqueles que
                    ousam enfrentar o Vazio. A cada ano, os ventos trazem o cheiro de carcaças e feitiçaria, lembrando que o terror nunca dorme.
                    Agora, é sua vez. Entre guerreiros, magos e exilados, o destino e espera por você. A fenda chama, e apenas os audaciosos ousam
                    atravessar seus portais.
                    
                    Me diga quem é você?:
                    
                    ( 1 ) Destino — Herdeiro do Herói
                          "Carrego o peso de uma lenda que nunca foi minha."
                         
                    ( 2 ) Aventureiro Perdido
                          "Busquei glória e encontrei apenas ruínas."
                    
                    ( 3 ) Sacrifício
                          "Jogaram-me ao abismo como moeda barata."
                    
                    ( 4 ) Protetor Caído
                          "Jurei guardar a fenda… e caí com ela."
                    
                    ( 5 ) Voltar
                    """;
            case 2 -> """
                    O sangue em suas veias carrega a última dádiva do Criador. Cada escolha, cada passo, moldará não apenas quem você é,
                    mas o impacto que terá sobre o Vazio.Antes de adentrar o abismo, você deve definir seu caminho.
                    A classe não é apenas poder — é identidade, estratégia, sobrevivência.
                    
                    Escolha agora sua classe
                    
                    ( 1 ) Guerreiro
                          > Tipo de dano : físico
                          > Passiva berserker: + dano - vida
                    
                    ( 2 ) Mago
                          > Tipo de dano : mágico
                          > Passiva Espelho arcano: Reflexão de dano, - Dano recebido, - armadura física e mágica
                    
                    ( 3 ) Aleátorio - mais passivas, danos diferentes, mesma história
                    
                    ( 5 ) Voltar
                    """;
            case 3 -> """
                    Sobrevivente, a fenda conhece todos os rostos, mas não sabe o seu. Cada passo seu deixa marcas de sangue e magia,
                    cada golpe pode virar lenda ou desaparecer no esquecimento. Antes de seguir adiante, você deve assumir 
                    sua própria identidade, pois sem nome, será apenas mais um entre os Filhos do Vazio.
                    
                    Me fale guerreiro qual é o seu nome:
                    
                    
                    """;
            default -> "";
        };
    }

    public static Player NovoPlayer(int passiva, String tipoDano, String lore) {
        Player p = new Player(passiva, tipoDano,1);
        p.setLore(lore);
        return p;
    }

    public static void CriarNoBancoPlayer(Player p, String lore) throws SQLException {
        String sqlPlayer = """
        INSERT INTO Player
            (nome, level, nomePassiva, pontosHabilidade, tipoDano, lore, vida, dano, armaduraMagica, armaduraFisica)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
    """;

        String sqlStats = """
        INSERT INTO PlayerDados
            (player_id, carnMortos, magoMortos, demonMortos,
             xpAtual, xpParaProximoLevel,
             notasLidas, carnDeCadaLevelMorto, magoDeCadaLevelMorto, demonDeCadaLevelMorto,
             checkpoint, chavesAdquiridas, leituraParede)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
    """;

        conexao.setAutoCommit(false); // inicia a transação
        try (
                PreparedStatement psPlayer = conexao.prepareStatement(sqlPlayer, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psStats  = conexao.prepareStatement(sqlStats)
        ) {
            // -------- Inserir Player --------
            String[] passivaNome = p.passiva.split("-");
            psPlayer.setString(1, p.nome);
            psPlayer.setInt(2, p.level);
            psPlayer.setString(3, passivaNome[0].trim());
            psPlayer.setInt(4, p.pontosHabilidade);
            psPlayer.setString(5, p.dmt); // tipoDano
            psPlayer.setString(6, lore);
            psPlayer.setDouble(7, p.life);
            psPlayer.setDouble(8, p.damage);
            psPlayer.setDouble(9, p.armor);
            psPlayer.setDouble(10, p.magicArmor);
            psPlayer.executeUpdate();

            // Pegar o id gerado
            try (ResultSet rs = psPlayer.getGeneratedKeys()) {
                if (!rs.next()) throw new SQLException("Falha ao obter ID do Player");
                int playerId = rs.getInt(1);
                p.setPlayerId(playerId);


                // -------- Inserir PlayerDados --------
                String notasL = String.join(",", p.notasLidas);
                String carnLevelMorto  = String.join(",", p.carnDeCadaLevelMorto);
                String magoLevelMorto  = String.join(",", p.magoDeCadaLevelMorto);
                String demonLevelMorto = String.join(",", p.demonDeCadaLevelMorto);

                psStats.setInt(1, playerId);
                psStats.setInt(2, p.carnMortos);
                psStats.setInt(3, p.magoMortos);
                psStats.setInt(4, p.demoniosMortos);
                psStats.setDouble(5, p.getXpAtual());
                psStats.setDouble(6, p.getXpParaProximoLevel());
                psStats.setString(7, notasL);
                psStats.setString(8, carnLevelMorto);
                psStats.setString(9, magoLevelMorto);
                psStats.setString(10, demonLevelMorto);
                psStats.setString(11, p.getCheckPoint().name());

                psStats.executeUpdate();
            }

            conexao.commit(); // confirma a transação
        } catch (SQLException e) {
            conexao.rollback(); // reverte se algo der errado
            throw e;
        } finally {
            conexao.setAutoCommit(true);
        }
    }

    public static void SalvarPlayer(Player p) throws SQLException {

        String sqlUpdatePlayer = """
        UPDATE Player
        SET nome = ?,
            level = ?,
            nomePassiva = ?,
            pontosHabilidade = ?,
            tipoDano = ?,
            lore = ?,
            vida = ?,
            dano = ?,
            armaduraMagica = ?,
            armaduraFisica = ?
        WHERE id = ?;
    """;

        String sqlUpdateStats = """
        UPDATE PlayerDados
        SET carnMortos = ?,
            magoMortos = ?,
            demonMortos = ?,
            xpAtual = ?,
            xpParaProximoLevel = ?,
            notasLidas = ?,
            carnDeCadaLevelMorto = ?,
            magoDeCadaLevelMorto = ?,
            demonDeCadaLevelMorto = ?,
            checkpoint = ?,
            chavesAdquiridas = ?,
            leituraParede = ?
        WHERE player_id = ?;
    """;

        conexao.setAutoCommit(false); // inicia a transação
        try (
                PreparedStatement psPlayer = conexao.prepareStatement(sqlUpdatePlayer);
                PreparedStatement psStats  = conexao.prepareStatement(sqlUpdateStats)
        ) {
            // -------- Atualiza Player --------
            String[] passivaNome = p.passiva.split("-");
            psPlayer.setString(1, p.nome);
            psPlayer.setInt(2, p.level);
            psPlayer.setString(3, passivaNome[0].trim());
            psPlayer.setInt(4, p.pontosHabilidade);
            psPlayer.setString(5, p.dmt);      // tipoDano
            psPlayer.setString(6, p.getLore());
            psPlayer.setDouble(7, p.pLife);
            psPlayer.setDouble(8, p.pDamage);
            psPlayer.setDouble(9, p.pMagicArmor);
            psPlayer.setDouble(10, p.pArmor);
            psPlayer.setInt(11, p.getPlayerId());
            psPlayer.executeUpdate();

            // -------- Atualiza PlayerDados --------
            String notasL         = String.join(",", p.notasLidas);
            String carnLevelMorto = String.join(",", p.carnDeCadaLevelMorto);
            String magoLevelMorto = String.join(",", p.magoDeCadaLevelMorto);
            String demonLevelMorto= String.join(",", p.demonDeCadaLevelMorto);
            String chavesPegas = String.join(",", p.chavesAdquiridas);

            psStats.setInt(1,  p.carnMortos);
            psStats.setInt(2,  p.magoMortos);
            psStats.setInt(3,  p.demoniosMortos);
            psStats.setDouble(4, p.getXpAtual());
            psStats.setDouble(5, p.getXpParaProximoLevel());
            psStats.setString(6,  notasL);
            psStats.setString(7,  carnLevelMorto);
            psStats.setString(8,  magoLevelMorto);
            psStats.setString(9,  demonLevelMorto);
            psStats.setString(10,  p.getCheckPoint().name());
            psStats.setInt(11,  p.getPlayerId());
            psStats.setString(12,chavesPegas);
            psStats.setInt(13, p.leituraParede);
            psStats.executeUpdate();

            conexao.commit();
        } catch (SQLException e) {
            conexao.rollback();  // garante atomicidade
            throw e;
        } finally {
            conexao.setAutoCommit(true);
        }
    }

    public static int MostrarPlayers() throws SQLException, IOException {
        CriarTabela();

        String escolha = "";
        String sql3 = """
                SELECT p.*, s.*
                FROM Player p
                INNER JOIN PlayerDados s
                    ON p.id = s.player_id;
                """;
        List<String> idsPossivel = new ArrayList<>();
        int x = 0;
        int idEscolhido;

        try (PreparedStatement pstmt = conexao.prepareStatement(sql3)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int level = rs.getInt("level");
                double xpAtual = rs.getDouble("xpAtual");
                double xpParaProximoLevel = rs.getDouble("xpParaProximoLevel");
                String checkPoint = rs.getString("checkpoint");

                System.out.println(
                        "ID: " + id +
                                "\n| Nome: " + nome +
                                "\n| Level: " + level +
                                "\n| XP: " + xpAtual + "/" + xpParaProximoLevel +
                                "\n| Checkpoint: " + checkPoint +"\n\n"
                );
                idsPossivel.add(Integer.toString(id));
            }

            while (!escolha.equalsIgnoreCase("0")) {
                System.out.println("----------------------------");
                System.out.println("\nDigite o ID do save que deseja carregar (ou 0 para sair):");


                escolha = String.valueOf(ReadInt(null)).trim();

                if (escolha.equalsIgnoreCase("0")) break;

                try {
                    idEscolhido = Integer.parseInt(escolha.trim());
                    if (idsPossivel.contains(Integer.toString(idEscolhido))) {
                        x = idEscolhido;
                        break;
                    } else {
                        System.out.println("ID não encontrado, tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    UtilForMe.FakeClear(50,false); //verificado
                    System.out.println("Digite um número válido");
                }
            }
        }
        return x;
    }

    public static Player CarregarPlayer(int id) {
        if(id==0){
            return null;
        }
        String sql = """
                SELECT p.*, s.*
                FROM Player p
                JOIN PlayerDados s
                     ON p.id = s.player_id
                WHERE p.id = ?;
                """;
        Player y = null;

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Set<String> chavesAdquiridas;
                Set<String> notasL = new LinkedHashSet<>(Arrays.asList(rs.getString("notasLidas").split(",")));
                Set<String> carnLevelMorto = new LinkedHashSet<>(Arrays.asList(rs.getString("carnDeCadaLevelMorto").split(",")));
                Set<String> magoLevelMorto = new LinkedHashSet<>(Arrays.asList(rs.getString("magoDeCadaLevelMorto").split(",")));
                Set<String> demonLevelMorto = new LinkedHashSet<>(Arrays.asList(rs.getString("demonDeCadaLevelMorto").split(",")));
                try{
                    chavesAdquiridas = new LinkedHashSet<>(Arrays.asList(rs.getString("chavesAdquiridas").split(",")));
                }catch (NullPointerException e){
                    chavesAdquiridas = new LinkedHashSet<>(Collections.singleton(""));
                }
                int inimigosMortos = rs.getInt("carnMortos") + rs.getInt("magoMortos") + rs.getInt("demonMortos");

                int numPassiva = switch (rs.getString("nomePassiva")) {
                    case "Corpo divino" -> 0;
                    case "Guardião da ruína" -> 1;
                    case "Berserker" -> 2;
                    case "Espelho arcano" -> 3;
                    case "Um pouco de sorte" -> 4;
                    default -> -1;
                };

                y = new Player(numPassiva, rs.getString("tipoDano"),rs.getInt("level"));
                y.setPlayerId(id);
                y.setLore(rs.getString("lore"));
                y.setCarnMortos(rs.getInt("carnMortos"));
                y.setCarnDeCadaLevelMorto(carnLevelMorto);
                y.setDemonDeCadaLevelMorto(demonLevelMorto);
                y.setNotasLidas(notasL);
                y.setMagoDeCadaLevelMorto(magoLevelMorto);
                y.setMagoMortos(rs.getInt("magoMortos"));
                y.setDemoniosMortos(rs.getInt("demonMortos"));
                y.setPontosHabilidade(rs.getInt("pontosHabilidade"));
                y.setNome(rs.getString("nome"));
                y.setXpAtual(rs.getDouble("xpAtual"));
                y.setXpParaProximoLevel(rs.getDouble("xpParaProximoLevel"));
                y.setInimigosMortos(inimigosMortos);
                y.pLife = rs.getDouble("vida");
                y.pDamage = rs.getDouble("dano");
                y.pArmor = rs.getDouble("armaduraFisica");
                y.pMagicArmor = rs.getDouble("armaduraMagica");
                y.setCheckPoint(Checkpoint.valueOf(rs.getString("checkpoint")));
                y.atualizarVidaAtual(y);
                y.setChavesAdquiridas(chavesAdquiridas);
                y.setLeituraParede(rs.getInt("leituraParede"));
                Passivas.atualizarAtributosSemBatalha(y);

            }
        } catch (SQLException e) {
            System.out.println("Erro no SELECT: " + e.getMessage());
        }

        return y;
    }

    public static void CarregarConfiguracoes() throws SQLException {
        String url = "jdbc:sqlite:dataSave.db";
        Connection conexao = DriverManager.getConnection(url);

        var sql2 = """
                            CREATE TABLE IF NOT EXISTS Configuracoes(
                            id INTEGER PRIMARY KEY,
                            velocidadeTexto VARCHAR(1)
                            )
                            """;

        try {
            Statement statement = conexao.createStatement();
            statement.execute(sql2);


        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        String sql = """
                SELECT * FROM Configuracoes;
                """;

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                UtilForMe.SetVelocidadeTexto(rs.getString("velocidadeTexto"));
            }

        }
        catch (SQLException e) {
            System.out.println("Erro no SELECT: " + e.getMessage());
        }
    }

    public static void CriarTabela(){
        var sql = """
                -- Tabela principal do jogador
                  CREATE TABLE IF NOT EXISTS Player (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      nome  VARCHAR(100),
                      level INTEGER,
                      nomePassiva VARCHAR(100),
                      pontosHabilidade INTEGER,
                      tipoDano  VARCHAR(25),
                      lore VARCHAR(25),
                      vida  DECIMAL,
                      dano  DECIMAL,
                      armaduraMagica DECIMAL,
                      armaduraFisica DECIMAL
                  );
                """;

        var sql2 ="""
                  -- Tabela com estatísticas e progresso
                  CREATE TABLE IF NOT EXISTS PlayerDados (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      player_id   INTEGER NOT NULL,
                      carnMortos  INTEGER,
                      magoMortos  INTEGER,
                      demonMortos INTEGER,
                      xpAtual     DECIMAL,
                      xpParaProximoLevel DECIMAL,
                      notasLidas  VARCHAR(30),
                      carnDeCadaLevelMorto  VARCHAR(30),
                      magoDeCadaLevelMorto  VARCHAR(30),
                      demonDeCadaLevelMorto VARCHAR(30),
                      checkpoint VARCHAR(100),
                      leituraParede INTEGER,
                      chavesAdquiridas VARCHAR(30),
                      FOREIGN KEY (player_id) REFERENCES Player(id)
                
                  );
                """;

        try {
            Statement statement = conexao.createStatement();
            statement.execute(sql);
            statement.execute(sql2);


        } catch (SQLException e) {
            System.out.println("Erro na criação da tabela: " + e.getMessage());
        }
    }

    //TODO deletar player e tabela
}