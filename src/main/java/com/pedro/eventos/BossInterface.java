package com.pedro.eventos;

import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public interface BossInterface {
    public String introducao() throws IOException, InterruptedException;


    public void atacarBoss(Boss bossAtacante, Player playerAlvo, List<String> frasesAtaque, int round) throws IOException, InterruptedException;

    public void definirAtributosBoss(Player p);

    public void morteBoss();

    public void atacarPlayer(Player player, Boss boss,int round);


}
