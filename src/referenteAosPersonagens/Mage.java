package com.pedro.referenteAosPersonagens;

public class Mage extends Enemy {

    String[] nomeArray ={"Arcanista do Lamento","Arcanista do Vazio","Arcanista das Ruínas","Escriba Perdido","Arcanista Desfigurado","Arcanista do Lamento","Arcanista do Crepúsculo",
            "Arcanista das Cinzas","Arcanista Sem Rosto"};
    String[] possiblePassive = {"No resistence-\n" +
            "Esse Arcanista do Vazio possui a passiva no resistence, resistência mágica contra ele não funcionará \n" +
            "a resitência mágica do oponetne será reduzida à 0 durante o combate",
            "Magic upgrade-\n" +
                    "Esse arcanista possui a passiva Magic upgrade, ela faz com que a cada round \n" +
                    "o poder desse mago aumente em 4% a cada round",
            "Potion master-\n" +
                    "Esse arcanista é um mestre das poções, todo round ele bebe uma poção, \n" +
                    "Poção de força 5% a mais de dano \n" +
                    "Poção de vida cura 10% da vida\n" +
                    "Poção de resistência recebe 10% de resistência de acordo com a resistência do inimigo"};



    public Mage(Player player,int torre) {
        this.dificultModifyer =2.5d;
        definirAtributos(2.2,player,torre);
        this.dmt ="magico";
        this.nome = nomeArray[r.nextInt(nomeArray.length)];
        this.passiva = possiblePassive[r.nextInt(possiblePassive.length)];


    }

    public void definirAtributos(double dificultModifyer,Player player,int torre){
        this.definirLevel(torre);
        this.life = r.nextInt(100,130) + (level*dificultModifyer+dificultModifyer+2)*25;
        this.actLife = life;
        this.magicArmor = r.nextInt(5,10) + (level*dificultModifyer+dificultModifyer+1);
        this.damage = r.nextInt(15,20) + (level*dificultModifyer+dificultModifyer+1.5)*24;
        this.armor = r.nextInt(10,15) +(level*dificultModifyer+dificultModifyer+1)*1.3;
    };

    public void definirLevel(int torre){


        int sorteio = r.nextInt(1,101);

        if(torre == 1){
            if( sorteio <= 40) // 40%
            {
                this.level = 1;
            }
            else if(sorteio <= 70) //30%
            {
                this.level = 2;
            }
            else if(sorteio <= 95) //25%
            {
                this.level = 3;
            }
            else if(sorteio <= 99)  //4%
            {
                this.level = 4;
            }
            else //1%
            {
                this.level = 5;
            }
        }else if (torre == 2){
            if( sorteio <= 5) //5%
            {
                this.level = 1;
            }
            else if(sorteio <= 25) //20%
            {
                this.level = 2;
            }
            else if(sorteio <= 65) //40%
            {
                this.level = 3;
            }
            else if(sorteio <= 95)  //30%
            {
                this.level = 4;
            }
            else //5%
            {
                this.level = 5;
            }
        }
        else{
            if( sorteio <= 1) // 1%
            {
                this.level = 1;
            }
            else if(sorteio <= 4) //4%
            {
                this.level = 2;
            }
            else if(sorteio <= 30) //25%
            {
                this.level = 3;
            }
            else if(sorteio <= 70)  //40%
            {
                this.level = 4;
            }
            else //30%
            {
                this.level = 5;
            }
        }
    }

}

