package com.pedro.eventos.boss;

import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;

public class BossAlquiron extends Boss{
    @Override
    public String Introducao(Player player) throws IOException, InterruptedException {
        return "";
    }

    @Override
    public void DefinirAtributosBoss(Player p) {

    }

    @Override
    public void MorteBoss() throws IOException, InterruptedException {

    }

    @Override
    public void AtacarPlayer(Player player, Boss boss, int round) throws IOException {

    }

    //TODO: tudo isso acima, a batalha será através de um caça palavras eu já até tenho o codigo salvo só preciso colocar aqui,
    // o resto eu tenho que pensar: Falas, Morte, Introdução, batalha em si e como o caça palavras se integrará com o dano do player.
}
