package com.pedro.eventos;

import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public interface BossInterface {
    String introducao(Player player) throws IOException, InterruptedException;


    void atacarBoss(Boss bossAtacante, Player playerAlvo, List<String> frasesAtaque, int round) throws IOException, InterruptedException;

    void definirAtributosBoss(Player p);

    void morteBoss() throws IOException, InterruptedException;

    void atacarPlayer(Player player, Boss boss,int round) throws IOException;


}
