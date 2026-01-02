package com.pedro.eventos.quedas;

import com.pedro.RetornoMultiplo;
import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.eventos.Battle;
import com.pedro.eventos.EventosSecundarios;
import com.pedro.eventos.boss.BossBattle;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Mage;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.pedro.UtilForMe.ReadInt;
import static com.pedro.eventos.Battle.Batalha;
import static com.pedro.historia.Notas.*;

public class SegundaQueda {

    public SegundaQueda() {
    }
    static List<Integer> chavesTotais = Arrays.asList(1,2,3);
    public static int correctAnswer;
    static int wrongAnswers;
    static boolean magoMorto = false;


    public static void Start(Player p) throws IOException, InterruptedException, SQLException {
        UtilForMe.FakeClear(50,false);
        while (p.getCheckPoint() != Checkpoint.SEGUNDA_QUEDA_BOSS || p.getCheckPoint() != Checkpoint.SEGUNDA_QUEDA_TORRE4_D){

            if(!p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE")){
                p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA);
            }
            if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA){
                Notas.notasIntroSegundaTorre();
                Viagem(p,0);
            }
            if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE1")){
                DentroTorre(p,1);
            }
            else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE2")){
                DentroTorre(p,2);
            }
            else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE3")){
                DentroTorre(p,3);
            }
            else if(p.getCheckPoint().name().equals("SEGUNDA_QUEDA_TORRE4")){
                if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE4)
                {
                    EntradaTorre(4,p);
                }
            }
            else{
                break;
            }
        }


    }

    private static void DentroTorre(Player p, int torre) throws SQLException, IOException, InterruptedException {
        int mageLeft = 3;
        if(p.chavesAdquiridas.contains(chavesTotais.indexOf(torre))){
            mageLeft = 0;
        }
        if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE"+torre))
        {
            if(EntradaTorre(torre,p)){
                for(int x = 0; x != mageLeft; x++){
                    Battle.Batalha(p,new Mage(p,torre),false,2);
                }
                System.out.println("\n\nNão há mais inimigos restantes!\n\n");
                EventosSecundarios.ObtencaoChaves(p,torre);
                SaidaTorre(torre,p);
            }
        }
        else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE"+torre+"_D")){
                SaidaTorre(torre,p);
        }
    }

    private static boolean Desafio(int torre) throws IOException, InterruptedException {
           RetornoMultiplo desafioResposta = GetDesafio(torre);
           correctAnswer = desafioResposta.inteiro;
           UtilForMe.FakeClear(50,magoMorto);
           UtilForMe.TempoDeLeitura(desafioResposta.string);
           magoMorto = false;
           int answer = ReadInt(null);
           UtilForMe.FakeClear(50,false);

           return answer == correctAnswer;

    }

    public static void MiniBoss(Player p,int torre) throws IOException, InterruptedException {
        if(wrongAnswers >= 3){
            DevoradorSpawn(p);
        }
        else{
            Mage m = new Mage(p,torre);
            UtilForMe.TempoDeLeitura("""
                    Ao errar o desafio, você sente uma presença diferente, um arcanista detecta um invasor...
                    """);
            UtilForMe.FakeClear(40,true);
            Batalha(p,m,false,2);
        }
    }

    public static boolean EntradaTorre(int torre,Player p) throws IOException, InterruptedException, SQLException {
        if(!p.chavesAdquiridas.contains(chavesTotais.indexOf(torre))) UtilForMe.TempoDeLeitura(Notas.GetIntroTorre(torre));
        int escolha = 0;
        System.out.println("\nDESEJA continuar? Ou ir para outra torre?\n" +
                "[ 1 ] Continuar\n" +
                "[ 2 ] Ir para outra torre");

        escolha = ReadInt(null);

        if(torre == 4 && !p.chavesAdquiridas.contains(Arrays.asList(1,2,3))){
            System.out.println("\nA porta está fechada mas é possível forçar a passagem, é um perigo, " +
                    "mas não impossível.....provavelmente\n" +
                    "[ 1 ] Continuar\n" +
                    "[ 2 ] Ir para outra torre");


            escolha = ReadInt(null);

            while (escolha != 2 && escolha != 1){
                System.out.println("Digite um número válido");
                escolha = ReadInt(null);
            }

            if(escolha == 1){
                DevoradorSpawn(p);
                return false;
            }
        }else if(torre == 4 && p.chavesAdquiridas.contains(Arrays.asList(1,2,3))){
            //Indo para o boss
        }
        while (escolha != 2 && escolha != 1){
            System.out.println("Digite um número válido");
            escolha = ReadInt(null);
        }

        if(escolha == 1 && !p.chavesAdquiridas.contains(chavesTotais.indexOf(torre))){
            while (!Desafio(torre)){
                wrongAnswers++;
                MiniBoss(p,torre);
                if(wrongAnswers < 3 && !p.PlayerMorto()){
                    magoMorto = true;
                    System.out.println("O arcanista morre, mas a torre continua selada," +
                            "\ntente novamente o desafio, agora já não há outra opção");
                }

            }
            if(wrongAnswers > 0) wrongAnswers--;
            UtilForMe.TempoDeLeitura(GetRespostaDesafio(30 + correctAnswer));
            UtilForMe.FakeClear(50,true);
            UtilForMe.TempoDeLeitura(GetIntroTorreInside(torre));
            UtilForMe.FakeClear(50,true );
            return true;
        }else if(!p.chavesAdquiridas.contains(chavesTotais.indexOf(torre))){
            System.out.println("\n\nNão há mais nada a se fazer aqui, melhor ir para outro lugar.\n\n");
            Viagem(p,torre);
            return false;
        }
        else{
            Viagem(p,torre);
            return false;
        }


    }

    public static void Viagem(Player player, int torre) throws IOException, InterruptedException, SQLException {
        int quantTorre = !(torre == 0) ? 3 : 4;
        String onde;
        UtilForMe.FakeClear(50,false);
        UtilForMe.TempoDeLeitura( String.format("""
                Por alguns instantes, tudo é silêncio, um silêncio pesado, quase vivo.
                Então você nota: trilhas etéreas serpenteiam pelo chão, como fios luminosos que se afastam em direções distintas.
                
                Cada rastro vibra num tom diferente, pulsando com ecos da magia que sustenta este mundo partido.
                É impossível ignorá-los; São %s caminhos, levando a diferentes torres ancestrais. Dentro de cada uma é revelado uma parte da história,
                tanto do presente como do passado e pistas para que possa haver um futuro.
                
                """, quantTorre));

        if(torre == 0){
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                [ 4 ] Torre do Leste
                """);

            do{
                onde = switch (ReadInt(null)){
                    case 1 -> "Norte";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    case 4 -> "Leste";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));

        } else if (torre == 1) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Leste
                [ 3 ] Torre do Sul
                """);

            do{
                onde = switch (ReadInt(null)){
                    case 1 -> "Norte";
                    case 2 -> "Leste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));

        } else if (torre == 2) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                """);
            do{
                onde = switch (ReadInt(null)){
                    case 1 -> "Norte";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));
        } else if (torre == 3) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Leste
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                """);

            do{
                onde = switch (ReadInt(null)){
                    case 1 -> "Leste";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));
        }else {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Leste
                """);

           do{
               onde = switch (ReadInt(null)){
                   case 1 -> "Norte";
                   case 2 -> "Oeste";
                   case 3 -> "Leste";
                   default -> "none";
               };
               if(onde.equals("none")){
                   System.out.println("Digite uma opção válida!");
               }
           }while(onde.equals("none"));

        }

        switch (onde) {
            case "Oeste" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE1);
            case "Leste" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE2);
            case "Norte" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE3);
            case "Sul"   -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE4);
        }
        UtilForMe.FakeClear(50,false);
        UtilForMe.TempoDeLeitura("VIAJANDO..............");
        Thread.sleep(2000);

        UtilForMe.FakeClear(50,false);
        EventosSecundarios.SegundaQuedaEventos(player);

        UtilForMe.TempoDeLeitura("VIAJANDO..............");
        Thread.sleep(2000);
        UtilForMe.FakeClear(50,false);

    }

    public static void SaidaTorre(int torre,Player player) throws SQLException, IOException, InterruptedException {

        if(torre!=4)EventosSecundarios.Descanso(player, 2,torre);
        else EventosSecundarios.Descanso(player, 2,1);
        Viagem(player,torre);
    }

    private static void DevoradorSpawn(Player p)throws IOException, InterruptedException {
        if(!BossBattle.SecundaryBossBattleSQ(p)){
            UtilForMe.FakeClear(50,false);
            UtilForMe.TempoDeLeitura("""
                        A palavra sai da sua boca… e o deserto não responde.
                        Silêncio.
                        Depois, um rugido que rasga o mundo.
                        O Falso Deus inclina a cabeça, como se compreendesse a sua falha antes mesmo de você.
                        A areia abre caminho. A escuridão engole tudo.
                        O grito que segue não é seu —
                        é o do próprio deserto, devorando mais uma alma para alimentar o vazio.
                        Você não acorda.
                        Ninguém acorda.
                        O Falso Deus se alimenta.
                        """);
            p.setActLife(0);
            UtilForMe.FakeClear(50,true);
            Player.MorteJogador(p,null);
        }
        else{
            UtilForMe.FakeClear(50,true);
            UtilForMe.TempoDeLeitura("""
                        O colosso de carne e mana curva o corpo, fazendo a própria ordem do mundo
                        lembrar-lhe de quem o criou, e de quem ele mesmo matou.
                        Gritando sem voz, ele se retorce, afundando na areia que se abre como um túmulo antigo.
                        
                        O vento corta, os gritos do devorador fazem a mana ao redor se tornar instável.
                        A luz se torce e de pouco em pouco o monstro afunda na areia junto de suas correntes.
                        A fenda se fecha sobre ele com violência divina.
                        
                        Por um instante, tudo silencia… exceto o som da besta batendo contra as paredes do seu novo cárcere, 
                        cada impacto mais distante que o anterior.
                        
                        E então, apenas o vazio.
                        
                        Você não o derrotou.
                        Ninguém jamais o fará.
                        
                        Mas hoje — ele voltou a dormir.
                        """);
        }
    }


}
