package com.pedro.eventos.quedas;

import com.pedro.configuracoes.Checkpoint;
import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.eventos.Battle;
import com.pedro.eventos.boss.BossBattle;
import com.pedro.eventos.EventosSecundarios;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Player;
import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Mob;
import com.pedro.referenteAosPersonagens.Carniceiro;


import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class PrimeiraQueda extends Battle {

    static List<String> verificacao = Arrays.asList("1","2","3","4","5");


    public PrimeiraQueda(){

    }

    public static void Start(Player p) throws InterruptedException, IOException, SQLException {
        if(p.getCheckPoint() != Checkpoint.PRIMEIRA_QUEDA_BOSS && p.getCheckPoint() != Checkpoint.PRIMEIRA_QUEDA_DESCANSO ){
            p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA);
        }


        if (p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA){
            Notas.notasIntroPrimeiraTorre(2);
        }

        while(p.carnDeCadaLevelMorto.containsAll(verificacao) || p.carnMortos <= 10  ){

            if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_DESCANSO){
                EventosSecundarios.Descanso(p,1,null); //descanso
                p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA);
            }
            if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA) {


                UtilForMe.FakeClear(50, false); //verificado
                Random r = new Random();
                int rd = r.nextInt(1, 4);
                for (int i = 1; i <= rd; i++) {
                    Mob carn = new Carniceiro(p);
                    Batalha(p, carn,true,1);
                    if (p.carnMortos == 1 && !p.notasLidas.contains("4")) {
                        Notas.notaSegredo(p);
                        UtilForMe.FakeClear(50, true); //verificado
                    }
                    if (p.carnDeCadaLevelMorto.containsAll(verificacao) || (p.carnMortos > 10 && !p.carnDeCadaLevelMorto.containsAll(verificacao))) {
                        QuebraPortao(p);
                        p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_BOSS);
                        PlayerConfigurations.SalvarPlayer(p);
                        break;
                    }

                }
            }
                if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA) {
                    System.out.println("\n\n" + "Parece que não existem mais inimigos nesse local, o banho de sangue finalmente acaba,\n" +
                            "e você caminha em meio a névoa. Que Deus lhe acompanhe\n");

                    UtilForMe.FakeClear(1,true);

                    EventosSecundarios.PrimeiraQuedaEventos(p);
                    p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA);
                }


        }

        if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_DESCANSO || p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_BOSS){
            EventosSecundarios.Descanso(p,1,null); //descanso
            p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_BOSS);
        }

        if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_BOSS){
            BossBattle.bossBattlePrimeiraQueda(p);
        }

    }

    public static void QuebraPortao(Player player) throws IOException, InterruptedException {
        UtilForMe.TempoDeLeitura("""
                O ar da Primeira Queda muda de repente. \s
                Um som grave — não de metal, mas de algo vivo — vibra através do chão. \s
                Das sombras entre as rochas, surgem os Observadores: formas altas e magras, sem rosto, mas com olhos que queimam como brasas brancas. \s
                Eles não falam. Mesmo assim, uma voz sem palavras penetra sua mente, ecoando como um pensamento que não lhe pertence.
                
                “Você caminhou onde muitos caíram… e permaneceu.”
               
                """);
        UtilForMe.TempoDeLeitura("""
                As criaturas silenciosas o cercam, mas não há ameaça em seus movimentos. \s
                Em uníssono, eles erguem as mãos — longas, etéreas — e um portão surge do chão e começa a pulsar. \s
                Runas esquecidas brilham em tons de azul e cinza, cada símbolo latejando no ritmo do seu próprio coração. \s
                O ar vibra, primeiro suave, depois ensurdecedor, até que a pedra se racha como vidro sob pressão. \s
                Uma fenda de luz fria corta a escuridão. O portão se abre, e as criaturas dizem em uníssono.
                 
                """);
        UtilForMe.TempoDeLeitura("""
                "Vá e siga em frente. Não há mais lugar para você aqui, avance, para que o vazio veja sua história, e a consuma.
                Como assim fez com todos os que o desafiaram."
                
                O vento que sopra do portão não é comum: carrega cheiros de terra úmida, mas também um leve aroma de ozônio e algo… antigo. \s
                Por um instante, você sente que é observado não apenas pelos Observadores, mas por algo além, algo que aguarda na próxima descida.
                
                """);

        String condicao;

        if (player.carnMortos > 10) {
            condicao =
                    "Atrás de você, o silêncio é absoluto.\n" +
                            "Não restam gritos, nem rastros de vida — apenas a poeira dos que ousaram resistir.\n" +
                            "Os Observadores deslizam o olhar vazio sobre o campo limpo, e um brilho mais forte percorre suas pupilas pálidas.\n" +
                            "Eles reconhecem: a caça terminou, nada mais ameaça este abismo.\n";

        } else if (player.carnDeCadaLevelMorto.containsAll(verificacao)) {
            condicao =
                    "Os poucos monstros que ainda habitam a fenda se encolhem nas sombras.\n" +
                            "Você sente o ar vibrar com um medo que não lhe pertence; a própria Queda parece se afastar.\n" +
                            "Os Observadores inclinam levemente as cabeças, como se reverenciassem a supremacia que você impôs.\n" +
                            "Nenhuma criatura ousará cruzar seu caminho novamente.\n";
        } else {
            condicao = "";
        }
        UtilForMe.TempoDeLeitura(condicao);
        System.out.println("\n\nPor fim, você entra no portão.....o desfecho da sua história fica cada vez mais próximo.");
        UtilForMe.FakeClear(50,true);

    }


}
