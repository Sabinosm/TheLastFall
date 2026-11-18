package com.pedro.referenteAosPersonagens;

public class Enemy extends Mob {
    public void definirAtributos(double dificultModifyer, Player p){
        definirLevel(p);

        this.life = r.nextInt(100,130) + (level*dificultModifyer+dificultModifyer+1)*32;
        this.actLife = life;
        this.magicArmor = r.nextInt(8,10) + (level*dificultModifyer+dificultModifyer+3)*1.3;
        this.damage = r.nextInt(15,20) + (level*dificultModifyer+dificultModifyer+2)*18;
        this.armor = r.nextInt(10,15) +(level*dificultModifyer+dificultModifyer+2)*1.8;


    }

    public void definirLevel(Player p){
        int lvl = p.level;

        int sorteio = r.nextInt(1,101);
        if(lvl < 3){
            if( sorteio <= 40)
            {
                this.level = 1;
            }
            else if(sorteio <= 70)
            {
                this.level = 2;
            }
            else if(sorteio <= 85)
            {
                this.level = 3;
            }
            else if(sorteio <= 95)
            {
                this.level = 4;
            }
            else
            {
                this.level = 5;
            }
        }else if (lvl > 5 ){
            if( sorteio <= 15)
            {
                this.level = 1;
            }
            else if(sorteio <= 30)
            {
                this.level = 2;
            }
            else if(sorteio <= 50)
            {
                this.level = 3;
            }
            else if(sorteio <=75)
            {
                this.level = 4;
            }
            else
            {
                this.level = 5;
            }
        }
        else{
            if( sorteio <= 20)
            {
                this.level = 1;
            }
            else if(sorteio <= 40)
            {
                this.level = 2;
            }
            else if(sorteio <= 70)
            {
                this.level = 3;
            }
            else if(sorteio <= 85)
            {
                this.level = 4;
            }
            else
            {
                this.level = 5;
            }
        }


    }
}