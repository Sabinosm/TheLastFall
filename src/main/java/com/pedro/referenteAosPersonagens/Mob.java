package com.pedro.referenteAosPersonagens;

import com.pedro.UtilForMe;

import java.io.IOException;
import java.util.Random;

public abstract class Mob {
    public double life;
    public double actLife;
    public double damage;
    public double armor;
    public double magicArmor;
    public int level;
    public String nome;
    Random r = new Random();
    public double dificultModifyer =0d;
    int dmgRandomType = r.nextInt(1,101);
    public String dmt;
    public String passiva;

    public void selectDamageType(Mob i) {
        if(i instanceof Player){
            if(dmgRandomType <= 40)
            {
                i.dmt = "físico";
            }
            else if(dmgRandomType <= 75) {

                i.dmt = "mágico";
            }
            else{
                i.dmt = "true";
            }
        }
        else {
            if(dmgRandomType <= 55)
            {
                i.dmt = "físico";
            }
            else if(dmgRandomType <= 85) {

                i.dmt = "mágico";
            }
            else{
                i.dmt = "true";
            }
        }


    }

    public static boolean isDead(Mob alvo){
        return alvo.actLife <= 0;
    }

    public void atacar(double damage,Mob alvo,Mob atacante,int round) throws InterruptedException, IOException {

        double danoFinal;

        if (dmt.equals( "fisico") || dmt.equals( "físico"))
        {
            danoFinal = damage - (alvo.armor*1.15 + damage*0.04);
            if(danoFinal <= 0){
                danoFinal = 10;
                System.out.println("\nSeu dano é muito baixo perante a incrivel armadura do adversário.\n");
            }

        }
        else if(dmt.equals("magico") || dmt.equals("mágico"))
        {
            danoFinal = damage - (alvo.magicArmor*1.15 + damage*0.04);
            if(danoFinal <= 0){
                danoFinal = 10;
                System.out.println("\nSeu dano é muito baixo perante a incrivel armadura do adversário.\n");
            }
        }
        else{
            danoFinal = damage;
        }
        if (danoFinal < 0) danoFinal = 0;
        if(alvo instanceof Player && ((Player) alvo).espelhoArcano(((Player) alvo))){
                danoFinal *= 0.85;
        }
        if(atacante instanceof Player && ((Player)atacante).espelhoArcano(((Player) atacante))){
            danoFinal += alvo.damage * 0.08;
        }
        if(atacante instanceof Player && ((Player)atacante).umPoucoDeSorte(((Player) atacante))){
            if(atacante.dmt.equals("true")){
                danoFinal = atacante.damage * 2;
            }
            else{
                danoFinal = (atacante.damage - alvo.armor*1.15 + atacante.damage*0.04 ) * 2;
            }

        }
        alvo.actLife -= danoFinal;
        if(isDead(alvo) && alvo instanceof Enemy)
        {
            System.out.println("\nRound"+round+"=================================\n"+atacante.nome + " atacou "+alvo.nome+ " e causou " + danoFinal + " de dano!");
            System.out.println(alvo.nome + " died");


            if(atacante instanceof Player){
                ((Player)atacante).inimigosMortos++;

                if(alvo instanceof Carniceiro){
                    ((Player)atacante).carnMortos++;
                    ((Player)atacante).carnDeCadaLevelMorto.add(Integer.toString(alvo.level));
                }
                if(alvo instanceof Demon){
                    ((Player)atacante).demoniosMortos++;
                    ((Player)atacante).demonDeCadaLevelMorto.add(Integer.toString(alvo.level));
                }
                if(alvo instanceof Mage){
                    ((Player)atacante).magoMortos++;
                    ((Player)atacante).magoDeCadaLevelMorto.add(Integer.toString(alvo.level));
                }

                alvo.actLife = 0;
                System.out.println("\n"+atacante.nome +" ganhou "+xpDaMorte(alvo,alvo.dificultModifyer) +" de xp");
                ((Player)atacante).upLevel(xpDaMorte(alvo,alvo.dificultModifyer));

            }


            System.out.println("Round"+round+"=================================\n");
            UtilForMe.FakeClear(50,true); //verificado
        }
        else if(isDead(alvo) && alvo instanceof Player){

            System.out.println("\n===============================================\n"+atacante.nome + " atacou "+alvo.nome+ " e causou " + danoFinal + " de dano!");
            System.out.println(alvo.nome + " died");

            assert atacante instanceof Enemy;
            Player.MorteJogador((Player)alvo, (Enemy)atacante);

            System.out.println("\n===============================================\n");
        }
        else{
            System.out.println("\nRound"+round+"=================================\n"+atacante.nome + " atacou "+alvo.nome+ " e causou " + danoFinal + " de dano!\n"+
                    "A vida atual de "+ alvo.nome +" é "+ alvo.actLife+"\nRound"+round+"=================================\n");
        }

    }

    public static double xpDaMorte(Mob morto,double dificultModifyer){
        return (morto.level)*(morto.life + morto.damage + morto.armor + morto.magicArmor)*dificultModifyer+2;

    }


    public void setActLife(double actLife) {
        this.actLife = actLife;
    }
}

