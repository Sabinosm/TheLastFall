package com.pedro.eventos;

import com.pedro.referenteAosPersonagens.Passivas;
import com.pedro.referenteAosPersonagens.Player;
import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.Enemy;
import com.pedro.referenteAosPersonagens.Mob;


import java.io.IOException;
import java.util.Random;

public class Battle {

    public static String fugirContinuar = " ";
    public static boolean batalha = true;
    public static Random r = new Random();


    public Battle()  {
    }

    public static void batalha(Player p, Mob inimigo) throws InterruptedException, IOException {

        randomIntro();
        descricaoBatalhaDupla(p,inimigo);
        continuarFugir(inimigo);
        UtilForMe.FakeClear(3,false); //verificado

        double mediaPlayer = (p.life + p.damage + p.armor + p.magicArmor) / 4;
        double mediaEnemy = (inimigo.life + inimigo.damage + inimigo.armor + inimigo.magicArmor) / 4;

        Mob primeiro = (mediaPlayer >= mediaEnemy) ? p : inimigo;
        Mob segundo = (primeiro == p) ? inimigo : p;

        int contador = 0;


        while (segundo.actLife > 0 && primeiro.actLife > 0 && batalha) {
            ++contador;

            primeiro.atacar(primeiro.damage, segundo, primeiro, contador);

            if(segundo.actLife > 0){
                if(primeiro == p){
                    Passivas.atualizarAtributosBatalhaPlayerTexto(p, (Enemy)inimigo);
                }
                else{
                        Passivas.atualizarAtributos(inimigo,contador,p);
                }
            }
            if(segundo.actLife < 0 && !(segundo instanceof Player)){

                Passivas.atualizarAtributos(inimigo,contador,p);
            }

            // atualizarAtributos sempre chamado após o ataque do mob



            if (segundo.actLife > 0 ) {
                segundo.atacar(segundo.damage, primeiro, segundo, contador);

                if(primeiro.actLife > 0){
                    if(segundo == p){
                        Passivas.atualizarAtributosBatalhaPlayerTexto(p, (Enemy)inimigo);
                    }
                    else{
                        Passivas.atualizarAtributos(inimigo,contador,p);
                    }
                }
                if(primeiro.actLife < 0 && !(primeiro instanceof Player)){

                    Passivas.atualizarAtributos(inimigo,contador,p);
                }

                if (segundo.actLife > 0 && primeiro.actLife > 0){
                    continuarFugir(inimigo);

                }

            }

        }
        p.actLife = p.life;

    }

    public static void descricaoBatalhaDupla(Mob p, Mob i) throws InterruptedException {
        System.out.printf("\n%-30s%-30s\n", p.nome.toUpperCase(), i.nome.toUpperCase());
        System.out.printf("%-30s%-30s\n", "------------------------------", "------------------------------");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Level:", "[" + p.level + "]", "Level:", "[" + i.level + "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Tipo de dano:", "[" + p.dmt + "]", "Tipo de dano:", "[" + i.dmt + "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Vida total:", "[" +UtilForMe.Arr(p.life)  + "]", "Vida total:", "[" + UtilForMe.Arr(i.life) + "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Vida atual:", "[" + UtilForMe.Arr(p.actLife) + "]", "Vida atual:", "[" + UtilForMe.Arr(i.actLife) + "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Força:", "[" + UtilForMe.Arr(p.damage) + "]", "Força:", "[" + UtilForMe.Arr(i.damage )+ "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Armadura:", "[" +UtilForMe.Arr(p.armor ) + "]", "Armadura:", "[" + UtilForMe.Arr(i.armor ) + "]");
        System.out.printf("%-15s %-14s%-15s %-14s\n", "Arm Mágica:", "[" +UtilForMe.Arr(p.magicArmor )+ "]", "Arm Mágica:", "[" + UtilForMe.Arr(i.magicArmor ) + "]");
        System.out.printf("%-15s %-14.2f%-15s %-14.2f\n", "Média:", ((p.life + p.damage + p.armor + p.magicArmor) / 4), "Média:", ((i.life + i.damage + i.armor + i.magicArmor) / 4));

        Thread.sleep(7500);

        System.out.println("\nPassivas:");
        System.out.println(p.nome+"========\n"+p.passiva+"\n\n"+i.nome+"========\n"+i.passiva+"\n");
        System.out.println("==========================================================================");
    }

    public static void continuarFugir(Mob inimigo) throws IOException {



        System.out.println("Deseja continuar a batalha ou tentar fugir?\n(1) Para continuar \n(2) Para tentar fugir");
        
        fugirContinuar = String.valueOf(UtilForMe.ReadInt());

        if (fugirContinuar.equals("2")) {
            int df = r.nextInt(1, inimigo.level + 1);
            if (df == 1) {
                System.out.println("\nVocê conseguiu sair parabéns");
                UtilForMe.FakeClear(50,true); //verificado
                batalha = false;
            } else {

                System.out.println("\nVocê não conseguiu sair da batalha, ela irá continuar");
                UtilForMe.FakeClear(50,true); //verificado
                batalha = true;
            }

        }
        else if (fugirContinuar.equals("1")) {
            batalha = true;
            System.out.println("\nVocê escolheu continuar.");
            UtilForMe.FakeClear(50,true); //verificado
        }
        else{
            System.out.println("\nInsira um número válido da proxima vez. A batalha irá continuar");
            batalha = true;
        }

    }

    private static void randomIntro() throws IOException, InterruptedException {
        Random random = new Random();
        int x = random.nextInt(1,4);

        if(x == 1){
            UtilForMe.TempoDeLeitura("Do meio da névoa, surge uma forma distorcida.  \n" +
                    "Um Carniceiro se ergue, costurado de carne e ossos, olhos sem vida fixos em você.  \n" +
                    "A batalha começa.");
        } else if (x==2) {
            UtilForMe.TempoDeLeitura("""
                    Um estalo úmido corta a quietude, como se o próprio chão gemesse.
                    Uma figura grotesca se ergue da névoa, sua presença trazendo o frio do desespero.
                    O medo é imediato, e o combate se inicia, inevitável e brutal.
                    """);
        }
        else{
            UtilForMe.TempoDeLeitura("""
                    Um som úmido corta o ar — carne se retorce, grita sem voz.
                    Da escuridão, a Primeira Queda emerge, encarnando o medo que você sempre tentou ignorar.
                    Sua presença é um aviso: não há escapatória.
                    A batalha se anuncia, brutal e inevitável.
                    """);
        }

    }

}

