package com.pedro.eventos;

import com.pedro.referenteAosPersonagens.Player;

public abstract class Boss  implements BossInterface {
    public double life;
    public double actLife;
    public double damage;
    public double armor;
    public double magicArmor;
    public String name;
    public String passiva;

    protected abstract void definirAtributosBoss(Player p);

    private String qualPassiva(Boss boss){
        return switch (boss.passiva){
            case("Self Destruction") -> "Self Destruction";
            default -> "x";
        };
    }
    //TODO melhorar
}
