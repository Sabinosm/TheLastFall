package com.pedro.referenteAosPersonagens;

public class Demon extends Enemy {
    public Demon(Player p) {
        definirAtributos(3d, p);
        this.dificultModifyer = 3d;
        selectDamageType(this);
        this.nome = nomeArray[r.nextInt(nomeArray.length)];
        this.passiva = possiblePassive[r.nextInt(possiblePassive.length)];

    }

    String[] nomeArray = {"A Voz do Abismo", "O Devorador de Almas", "O Flagelo do Vazio", "O Arauto do Fim", "O Senhor das Correntes","A Lâmina Quebrada",
            "O Juiz Sem Olhos", "A Voz do Abismo", "A Coroa de Sangue"};
    String[] possiblePassive = {"Life steal-\n" +
            "Esse demônio possui a passiva life steal, que faz com que 10% do dano bruto do demônio\n" +
            "volte como cura",
            "Hellfire-\n" +
                    "Um fogo infernal que queima o inimigo até que quem lançou o feitiço morra \n" +
                    "O fogo do inferno causa 5% da vida atual do inimigo como dano verdadeiro adicional",
            "A bet with the devil-\n" +
                    "Esse demônio é um apostador nato girando um dado de 4 lados podendo receber buffs e debuffs por um round" +
                    "Ganhar 15% a mais de um atributo aleátorio \n" +
                    "Ganhar 20% da vida atual como cura\n" +
                    "Perder 40% do dano\n" +
                    "Inimigo recuperar 15% da vida"};

}
