package com.pedro.referenteAosPersonagens;

import java.util.Random;

public class Passivas {
    public Passivas() {

    }

    static int last = 0;

    public static String qualPassiva(Mob m) {
        String[] passString = m.passiva.split("-");
        return switch (passString[0]) {
            case "Corpo divino" -> "Corpo divino";
            case "Guardião da ruína" -> "Guardião da ruína";
            case "Berserker" -> "Berserker";
            case "Espelho arcano"->"Espelho arcano";
            case "Um pouco de sorte"->"Um pouco de sorte";
            case "Último suspiro" -> "Último suspiro";
            case "Caça Implacável" -> "Caça Implacável";
            case "Frenezi" -> "Frenezi";
            case "No resistence" -> "No resistence";
            case "Potion master" -> "Potion master";
            case "Magic upgrade" -> "Magic upgrade";
            case "Hellfire" -> "Hellfire";
            case "A bet with the devil" -> "A bet with the devil";
            case "Life steal" -> "Life steal";
            default -> "none";
        };
    }

    public static void atualizarAtributos(Mob x, int round, Mob atacante) {
        Random r = new Random();

        switch (qualPassiva(x)) {
            case "Último suspiro":
                if (x.actLife < x.life * .25 && ((Carniceiro) x).ultimoSus) {
                    ((Carniceiro) x).ultimoSus = false;
                    ((Carniceiro) x).actLife = ((Carniceiro) x).life;
                    ((Carniceiro) x).damage *= 0.30;
                    System.out.println("\n" + x.nome + " Usou o bonus da passiva Último suspiro, ele recuperou toda sua vida, mas perdeu 70% do dano\n");

                }
                break;

            case "Caça Implacável":
                System.out.println("\n" + x.nome + " Usou o bonus da passiva Caça Implacável +5% de dano\n");
                x.damage *= 1.05;
                break;

            case "Frenezi":
                if (x.actLife <= x.life * 0.1 && !((Carniceiro) x).frenezzi) {
                    ((Carniceiro) x).frenezzi = true;
                    x.damage *= 1.25;

                    System.out.println("\n" + x.nome + " Usou o bonus da passiva Frenezi +25% de dano\n");
                }
                break;

            case "No resistence":

                System.out.println("\n" + x.nome + " Usou o bonus da No resistense a resistência de " + atacante.nome + "\n" +
                        "foi negada." + x.nome + " terá dano verdadeiro.\n");
                x.dmt = "true";
                break;

            case "Potion master":

                int potion = r.nextInt(1, 10);
                switch (potion) {
                    case 0:
                        x.damage *= 1.05;
                        System.out.println("\n" + x.nome + " Usou o bonus de Potion master e bebeu a poção de força \n" +
                                "recebendo 5% de dano\n");
                        break;
                    case 1:
                        x.actLife = x.life * 0.1;
                        System.out.println("\n" + x.nome + " Usou o bonus de Potion master e bebeu a poção de vida \n" +
                                "recebendo 10% de vida\n");// cura até o máximo
                        break;
                    case 2:
                        x.armor += atacante.armor * 0.1;
                        x.magicArmor += atacante.magicArmor * 0.1;
                        System.out.println("\n" + x.nome + " Usou o bonus de Potion master e bebeu a poção de armadura \n" +
                                "recebendo " + atacante.magicArmor * 0.1 + " de armadura mágica e\n" + atacante.armor * 0.1 + " de armadura\n");
                        break;
                }
                break;
            case "Magic upgrade":
                x.damage *= 1.04;
                System.out.println("\n" + x.nome + " Usou o bonus da passiva magic upgrade e ganhou +4% de dano\n");
                break;
            case "A bet with the devil":
                int escolha = r.nextInt(1, 5);
                int parImpar = round % 2 == 0 ? 0 : 1;
                if (last == 11 && parImpar == 0) {
                    x.damage *= 0.84;
                } else if (last == 12 && parImpar == 0) {
                    x.actLife *= 0.876;
                } else if (last == 13 && parImpar == 0) {
                    x.armor *= 0.876;
                } else if (last == 14 && parImpar == 0) {
                    x.magicArmor *= 0.876;
                } else if (last == 3 && parImpar == 0) {
                    x.damage *= 1.6667;
                }

                if (escolha == 1) {
                    escolha = r.nextInt(1, 5);
                    if (escolha == 1) {
                        x.damage *= 1.15;
                        System.out.println("\n" + x.nome + " Usou o bonus da passiva bet with devil e ganhou +15% de dano\n");
                        last = 11;
                    } else if (escolha == 2) {
                        x.actLife *= 1.15;
                        System.out.println("\n" + x.nome + " Usou o bonus da passiva bet with devil e curou +15% de vida\n");
                        last = 12;
                    } else if (escolha == 3) {
                        x.armor *= 1.15;
                        System.out.println("\n" + x.nome + " Usou o bonus da passiva bet with devil e ganhou +15% de armadura\n");
                        last = 13;
                    } else {
                        x.magicArmor *= 1.15;
                        System.out.println("\n" + x.nome + " Usou o bonus da passiva bet with devil e ganhou +15% de armadura mágica\n");
                        last = 14;
                    }

                } else if (escolha == 2) {
                    x.actLife += x.actLife * 1.20;
                    System.out.println("\n" + x.nome + " Recebeu o bonus da passiva bet with devil e curou +20% de vida\n");
                } else if (escolha == 3) {
                    x.damage *= 0.6;
                    System.out.println("\n" + x.nome + " Recebeu o debuff da passiva bet with devil e perdeu -40% de de dano\n");
                    last = 3;
                } else {
                    System.out.println("\n" + atacante.nome + " Recebeu o bonus da passiva bet with devil e curou +15% de vida\n");
                    atacante.actLife += atacante.life * 1.15;

                }

                break;
            case "Hellfire":
                x.actLife -= (x.actLife * 0.05);
                System.out.println("\n" + atacante.nome + " recebeu: " +
                        (x.actLife * 0.05) +
                        " de dano por causa da passiva hellfire\n");
                break;
            case "Life steal":
                x.actLife += x.damage * 0.10;
                System.out.println("\n" + atacante.nome + " Recebeu o bonus da passiva life steal e curou " + x.damage * 0.10 + " de vida\n");
                break;


            default:
                x.life = ((Player) x).pLife;
                x.armor = ((Player) x).pArmor;
                x.magicArmor = ((Player) x).pMagicArmor;
                x.damage = ((Player) x).pDamage;
        }




        // Reset da vida atual ao máximo
    }

    public static void atualizarAtributosSemBatalha(Player player) {
        switch (Passivas.qualPassiva(player)) {
            case "Corpo divino":
                player.life = player.pLife * 1.30; // 30% de bônus de vida
                player.damage = player.pDamage * 1.1;  // 10% a mais de dano
                player.armor = player.pArmor * 0.85;    // 15% a menos de armadura
                player.magicArmor = player.pMagicArmor * 0.88;// 12% a menos de armadura mágica
                break;

            case "Guardião da ruína":
                player.life = player.pLife;
                player.armor =player.pArmor * 1.3;           // 30% a mais de armadura
                player.magicArmor = player.pMagicArmor * 1.25; // 25% a mais de armadura mágica
                player.damage = player.pDamage * 0.92;          // 8% a menos de dano
                break;

            case "Berserker":
                player.armor = player.pArmor;
                player.life = player.pLife * 0.92;       // -8% de vida
                player.magicArmor = player.pMagicArmor * 0.90; // -10% de armadura mágica
                player.damage = player.pDamage * 1.4;          // +40% de dano
                break;

            case "Espelho arcano":
                player.life = player.pLife;
                player.magicArmor = player.pMagicArmor * 0.90; // -10% de armadura Mágica
                player.damage = player.pDamage * 0.95; // -5% do dano
                player.armor = player.pArmor * 0.90; // -10% de armadura
                break;

            case "Um pouco de sorte":
                    player.life = player.pLife;
                    player.magicArmor = player.pMagicArmor;
                    player.armor = player.pArmor;
                    player.damage = player.pDamage * 0.90; //-10% do dano
                break;

            default:
                player.life =player.pLife;
                player.armor = player.pArmor;
                player.magicArmor = player.pMagicArmor;
                player.damage = player.pDamage;

        }

        player.actLife = player.life;
    }

    public static void atualizarAtributosBatalhaPlayerTexto(Player x,Enemy adversario) {
        switch (Passivas.qualPassiva(x)) {
            case "Espelho arcano":
                System.out.println("\n" + "Por causa da passiva" + x.nome + " causou dano verdadeiro adicional: " + adversario.damage * 0.8);
                System.out.println("Por causa da passiva" + x.nome + "teve dano bloqueado: " + adversario.damage * 0.15 + "\n");
                break;

            case "Um pouco de sorte":

                if (x.resultadoSorte(-1, x )) {
                    System.out.println("\n" + x.nome + " Parece que foi seu dia de sorte, seu ataque recebeu um bônus de 200% de dano\n");
                    x.resultadoSorte(0, x);
                }
                break;
        }

    }


}

