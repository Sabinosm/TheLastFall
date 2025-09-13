package com.pedro.referenteAosPersonagens;

public class Enemy extends Mob {
    public void definirAtributos(double dificultModifyer, Player p){
        definirLevel(p);

        this.life = r.nextInt(100,130) + (level*dificultModifyer+dificultModifyer+1)*25;
        this.actLife = life;
        this.magicArmor = r.nextInt(8,10) + (level*dificultModifyer+dificultModifyer+3)*1.5;
        this.damage = r.nextInt(15,20) + (level*dificultModifyer+dificultModifyer+2)*20.5;
        this.armor = r.nextInt(10,15) +(level*dificultModifyer+dificultModifyer+2)*3;
    }

    public void definirLevel(Player p){
        int lvl = p.level;

        int sorteio = r.nextInt(1,101);
        if(lvl < (3*this.dificultModifyer)){
            if( sorteio <= 40)
            {
                this.level = 1;
            }
            else if(sorteio <= 65)
            {
                this.level = 2;
            }
            else if(sorteio <= 80)
            {
                this.level = 3;
            }
            else if(sorteio <= 90)
            {
                this.level = 4;
            }
            else
            {
                this.level = 5;
            }
        }
        else{
            if( sorteio <= 25)
            {
                this.level = 1;
            }
            else if(sorteio <= 35)
            {
                this.level = 2;
            }
            else if(sorteio <= 65)
            {
                this.level = 3;
            }
            else if(sorteio <= 80)
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