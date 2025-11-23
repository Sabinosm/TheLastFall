package com.pedro.eventos.boss;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public abstract class BossBattle extends Boss{
    public static void bossBattlePrimeiraQueda(Player player) throws IOException, InterruptedException {

        UtilForMe.FakeClear(50,true); // verificado
        BossAmalgama boss = new BossAmalgama(player);
        int round = 0;


        UtilForMe.TempoDeLeitura(boss.Introducao(player));

        UtilForMe.FakeClear(50, true);

        System.out.println(Boss.ExibirStatusBatalha(boss, player));

        UtilForMe.FakeClear(50, true);

        UtilForMe.TempoDeLeitura("""
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
    }   
}
