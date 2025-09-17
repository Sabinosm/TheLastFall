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



    public Mage(Player player) {
        this.dificultModifyer =2.5d;
        definirAtributos(2.2,player);
        this.dmt ="magico";
        this.nome = nomeArray[r.nextInt(nomeArray.length)];
        this.passiva = possiblePassive[r.nextInt(possiblePassive.length)];


    }

    public void definirAtributos(double dificultModifyer,Player player){
        definirLevel(player);
        this.life = r.nextInt(100,130) + (level*dificultModifyer+dificultModifyer+2)*25;
        this.actLife = life;
        this.magicArmor = r.nextInt(5,10) + (level*dificultModifyer+dificultModifyer+1);
        this.damage = r.nextInt(15,20) + (level*dificultModifyer+dificultModifyer+1.5)*24;
        this.armor = r.nextInt(10,15) +(level*dificultModifyer+dificultModifyer+1)*1.3;
    };

}

