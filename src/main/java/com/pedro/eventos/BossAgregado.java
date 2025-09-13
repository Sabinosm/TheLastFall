package com.pedro.eventos;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;

import java.util.Random;

public class BossAgregado extends Boss{

    public BossAgregado(Player p) {

        definirAtributosBoss(p);

            LineReader reader = LineReaderBuilder.builder().build();

            Thread mainThread = Thread.currentThread(); // thread do readLine

            Thread timer = new Thread(() -> {
                try {
                    Thread.sleep(5000); // 5 segundos
                    System.out.println("\nTempo esgotado! O herói falhou!");
                    mainThread.interrupt(); // interrompe o readLine
                } catch (InterruptedException e) {
                    // timer foi interrompido porque usuário digitou
                }
            });

            timer.start();

            try {
                String text = reader.readLine("Digite algo em 5s: "); // bloqueia
                timer.interrupt(); // usuário digitou antes do tempo acabar
                System.out.println("Você digitou: " + text);
            } catch (UserInterruptException e) {
                System.out.println("Entrada cancelada pelo tempo!");
            }

            System.out.println("Programa continua...");
        }

            //TODO: História, Obviamente, um jeito de mencionar as palavras que podem ser ditas dentro da própria história, terminar de ajustar os diálogos, danos
            //TODO:


    @Override
    public String introducao() {
        return "";
    }

    @Override
    public void batalha() {

    }

    @Override
    public void atacarBoss() {

    }

    @Override
    protected void definirAtributosBoss(Player player) {

        //calculo da vida -> vida base mais ou menos como funciona um carniceiro, más com o level do player, ou seja ,constante + variável;
        Random random = new Random();
        double vidaBase = random.nextInt(100,130) + (player.level * 1.4 + 1.4 + 1) * 25;
        double mediaPlayer = player.life + player.damage + player.armor + player.magicArmor;
        double aumentoEscalar = mediaPlayer * 3;
        this.life = vidaBase + aumentoEscalar;
        this.actLife = this.life;

        //calculo dano -> supondo que a vida do player deve ser perto la de 500
        double danoBase = random.nextInt(60,110);
        double danoEscalar = mediaPlayer * 0.35;
        this.damage = danoEscalar + danoBase;

        //O calculo das armaduras vai ser mais simples pra não impactar tanto na dificuldade. Qualquer coisa so tirar se tiver muito broken.
        this.magicArmor = player.magicArmor * 0.35;
        this.armor = player.armor * 0.45;

        //nome
        this.name = "O Agregado";

        //passiva
        



    }
}













