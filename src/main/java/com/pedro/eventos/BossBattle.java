package com.pedro.eventos;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public abstract class BossBattle extends Boss{
    public static void bossBattlePrimeiraQueda(Player player) throws IOException, InterruptedException {

        BossAmalgama boss = new BossAmalgama(player);
        int round = 0;

        System.out.println("O monstro olha para você e logo percebe que o banquete está prestes a começar....a sua carne e sua mana\n" +
                "o deixam salivando, é possível ver o quanto ele te quer morto, um belo corpo para gigantesca sua coleção...assim ele parte para cima\n\n");

        System.out.println(Boss.exibirStatusBatalha(boss, player));

        UtilForMe.fakeClear(50, true);

        //Loop da batalha
        while (player.actLife > 0 && boss.actLife > 0) {
            round++;
            boss.atacarBoss(boss,player, List.of(boss.frasesAtaque),round);
            boss.atacarPlayer(player,boss,round);
        }
    }   
}
