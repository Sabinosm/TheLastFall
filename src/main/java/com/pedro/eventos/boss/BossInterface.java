package com.pedro.eventos.boss;

import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.List;

public interface BossInterface {
    String Introducao(Player player) throws IOException, InterruptedException;

    void AtacarBoss(Boss bossAtacante, Player playerAlvo, List<String> frasesAtaque, int round) throws IOException, InterruptedException;

    void DefinirAtributosBoss(Player p);

    void MorteBoss() throws IOException, InterruptedException;

    void AtacarPlayer(Player player, Boss boss, int round) throws IOException;


}
