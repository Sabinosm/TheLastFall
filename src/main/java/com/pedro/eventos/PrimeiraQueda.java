package com.pedro.eventos;

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
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        Notas.notasIntro(2);
        do{
            UtilForMe.fakeClear(50,false); //verificado
            Random r = new Random();
            int rd = r.nextInt(1,4);
            for(int i = 1; i <= rd; i++) {
                Mob carn = new Carniceiro(p);
                batalha(p, carn);
                if(p.carnMortos == 1 && !p.notasLidas.contains("4")) {
                    Notas.notaSegredo(p);
                    UtilForMe.fakeClear(50, true); //verificado
                }
                if(p.carnMortos > 10 && !p.carnDeCadaLevelMorto.containsAll(verificacao)) break;
                if(p.carnDeCadaLevelMorto.containsAll(verificacao)) break;
            }
            System.out.println("\n\n" + "Parece que não existem mais inimigos nesse local, o banho de sangue finalmente acaba,\n" +
                    "e você caminha em meio a névoa. Que Deus lhe acompanhe\n");

            System.out.println("( ENTER para continuar )\n\n");
            reader.getBuffer().clear();
            reader.readLine();
            EventosSecundarios.primeiraQuedaEventos(p);

            if(p.carnMortos > 10 && !p.carnDeCadaLevelMorto.containsAll(verificacao)){
                System.out.println("\nVocê matou todos os monstros desta dungeon, encontrando a chave para uma sala diferente, o que será que lhe espera?"); //TODO mudar
                break;
            }

        } while (!p.carnDeCadaLevelMorto.containsAll(verificacao));
    }


}
