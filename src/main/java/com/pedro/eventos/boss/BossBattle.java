package com.pedro.eventos.boss;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.eventos.EventosSecundarios;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.UserInterruptException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.pedro.UtilForMe.FakeClear;
import static com.pedro.UtilForMe.TempoDeLeitura;

public abstract class BossBattle extends Boss{
    public static void bossBattlePrimeiraQueda(Player player) throws IOException, InterruptedException, SQLException {

        FakeClear(50,true); // verificado
        BossAmalgama boss = new BossAmalgama(player);
        int round = 0;


        TempoDeLeitura(boss.Introducao(player));

        FakeClear(50, true);

        System.out.println(Boss.ExibirStatusBatalha(boss, player));

        FakeClear(50, true);

        TempoDeLeitura("""
        O chão estremece.
        A massa de carne se ergue, exalando um fedor quente e metálico.
        Olhos improvisados se abrem na superfície pulsante — dezenas, encarando você.
        Um rugido úmido ecoa, e o Amálgama avança.
        """);

        //Loop da batalha
        while (player.actLife > 0 && boss.actLife > 0) {
            round++;
            boss.AtacarBoss(boss,player, List.of(boss.frasesAtaque),round);
            boss.AtacarPlayer(player,boss,round);
        }
        player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA);
        FakeClear(50,false);
        System.out.println("JOGO SALVO AUTOMÁTICAMENTE!");
        PlayerConfigurations.SalvarPlayer(player);
        FakeClear(50,true);


    }

    public static boolean  SecundaryBossBattleSQ(Player player) throws IOException, InterruptedException {

        Devorador d = new Devorador();
        TempoDeLeitura(Devorador.Intro());
        FakeClear(50,true);
        return d.iniciarBatalha();

    }
}
