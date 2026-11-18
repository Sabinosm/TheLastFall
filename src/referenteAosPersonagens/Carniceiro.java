package com.pedro.referenteAosPersonagens;

public class Carniceiro extends Enemy {
    public boolean frenezzi;
    public boolean ultimoSus = true;
    String[] possiblePassive = {"Último suspiro-\n" +
            "Esse carniceiro possui uma vontade de viver incomparável, tanta que ele pode ao chegar perto de morrer, perder 70% de seu dano\n" +
            " em troca de retornar a sua vida total \n",
            "Frenezi-\n" +
                    "Um carniceiro frenético, com um buff de frenezzi , quando ele estiver com\n" +
                    "menos de 20% da vida seu próximo ataque dará 25% a mais de dano",
            "Caça Implacável-\n" +
                    "Insaciável, é o que descreve esse monstro, com um buff de caça implacável, faz com que ele\n" +
                    "dê +5% de dano e a cada round esse valor aumenta"};


    public Carniceiro(Player p) {
        this.dificultModifyer = 1.5;
        definirAtributos(1.4,p);
        selectDamageType(this);
        // Sorteia nome
        this.nome = "Carniceiro";
        this.frenezzi = false;
        this.passiva = possiblePassive[r.nextInt(possiblePassive.length)];

    }



}
