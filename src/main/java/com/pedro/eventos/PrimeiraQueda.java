package com.pedro.eventos;

import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.historia.EventosSecundarios;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Mage;
import com.pedro.referenteAosPersonagens.Player;
import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Demon;
import com.pedro.referenteAosPersonagens.Enemy;
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

        Terminal terminal = TerminalBuilder.builder().system(true).build();
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
                    reader.getBuffer().clear();
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
            System.out.println("Cheguei aqui");
            BossBattle.bossBattlePrimeiraQueda(p);
        }


    }
    public static void quebraPortao(Player player){
        System.out.println("Quebrou portão");
    }


}
