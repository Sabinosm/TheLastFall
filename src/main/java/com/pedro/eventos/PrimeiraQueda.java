package com.pedro.eventos;

import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.historia.EventosSecundarios;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Player;
import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Mob;
import com.pedro.referenteAosPersonagens.Carniceiro;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

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

          Terminal terminal = TerminalBuilder.builder()
                .jna(false) // evita bug de raw mode em alguns terminais
                .system(true)
                .build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        if (p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA){
            Notas.notasIntro(2);
        }

        while(p.carnDeCadaLevelMorto.containsAll(verificacao) || p.carnMortos <= 10  ){

            if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_DESCANSO){
                EventosSecundarios.Descanso(p,1); //descanso
                p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA);
            }
            if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA) {


                UtilForMe.fakeClear(50, false); //verificado
                Random r = new Random();
                int rd = r.nextInt(1, 4);
                for (int i = 1; i <= rd; i++) {
                    Mob carn = new Carniceiro(p);
                    batalha(p, carn);
                    if (p.carnMortos == 1 && !p.notasLidas.contains("4")) {
                        Notas.notaSegredo(p);
                        UtilForMe.fakeClear(50, true); //verificado
                    }
                    if (p.carnDeCadaLevelMorto.containsAll(verificacao) || (p.carnMortos > 10 && !p.carnDeCadaLevelMorto.containsAll(verificacao))) {
                        quebraPortao(p);
                        p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_BOSS);
                        PlayerConfigurations.salvarPlayer(p);
                        break;
                    }

                }
            }
                if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA) {
                    System.out.println("\n\n" + "Parece que não existem mais inimigos nesse local, o banho de sangue finalmente acaba,\n" +
                            "e você caminha em meio a névoa. Que Deus lhe acompanhe\n");

                    System.out.println("( ENTER para continuar )\n\n");
                    
                    reader.readLine();
                    EventosSecundarios.primeiraQuedaEventos(p);
                    p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA);
                }


        }

        if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_DESCANSO || p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_BOSS){
            EventosSecundarios.Descanso(p,1); //descanso
            p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_BOSS);
        }

        if(p.getCheckPoint() == Checkpoint.PRIMEIRA_QUEDA_BOSS){

            BossBattle.bossBattlePrimeiraQueda(p);
        }

    }
    public static void quebraPortao(Player player) throws IOException, InterruptedException {
        UtilForMe.tempoDeLeitura("""
                O ar da Primeira Queda muda de repente. \s
                Um som grave — não de metal, mas de algo vivo — vibra através do chão. \s
                Das sombras entre as rochas, surgem os Observadores: formas altas e magras, sem rosto, mas com olhos que queimam como brasas brancas. \s
                Eles não falam. Mesmo assim, uma voz sem palavras penetra sua mente, ecoando como um pensamento que não lhe pertence.
                
                “Você caminhou onde muitos caíram… e permaneceu.”
               
                """);
        UtilForMe.tempoDeLeitura("""
                As criaturas silenciosas o cercam, mas não há ameaça em seus movimentos. \s
                Em uníssono, eles erguem as mãos — longas, etéreas — e um portão surge do chão e começa a pulsar. \s
                Runas esquecidas brilham em tons de azul e cinza, cada símbolo latejando no ritmo do seu próprio coração. \s
                O ar vibra, primeiro suave, depois ensurdecedor, até que a pedra se racha como vidro sob pressão. \s
                Uma fenda de luz fria corta a escuridão. O portão se abre, e as criaturas dizem em uníssono.
                 
                """);
        UtilForMe.tempoDeLeitura("""
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
        UtilForMe.tempoDeLeitura(condicao);
        System.out.println("\n\nPor fim, você entra no portão.....o desfecho da sua história fica cada vez mais próximo.");
        UtilForMe.fakeClear(50,true);

    }


}
