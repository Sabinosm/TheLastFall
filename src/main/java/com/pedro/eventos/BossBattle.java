package com.pedro.eventos;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public abstract class BossBattle extends Boss{
    public static void bossBattlePrimeiraQueda(Player player) throws IOException, InterruptedException {

        UtilForMe.fakeClear(50,true); // verificado
        BossAmalgama boss = new BossAmalgama(player);
        int round = 0;


        UtilForMe.tempoDeLeitura(boss.introducao(player));

        UtilForMe.fakeClear(50, true);

        System.out.println(Boss.exibirStatusBatalha(boss, player));

        UtilForMe.fakeClear(50, true);

        UtilForMe.tempoDeLeitura("""
        O chão estremece.
        A massa de carne se ergue, exalando um fedor quente e metálico.
        Olhos improvisados se abrem na superfície pulsante — dezenas, encarando você.
        Um rugido úmido ecoa, e o Amálgama avança.
        """);
        //Loop da batalha
        while (player.actLife > 0 && boss.actLife > 0) {
            round++;
            boss.atacarBoss(boss,player, List.of(boss.frasesAtaque),round);
            boss.atacarPlayer(player,boss,round);
        }
    }   
}
