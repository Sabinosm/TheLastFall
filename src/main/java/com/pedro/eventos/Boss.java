package com.pedro.eventos;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class Boss  implements BossInterface {
    public double life;
    public double actLife;
    public double damage;
    public double armor;
    public double magicArmor;
    public String name;
    public String nomePassiva;
    String passiva;


    public void passivaAtributos(Boss boss,int roundBatalha,Player player){
        String passiva = switch (boss.nomePassiva){
            case "Recomposição Profana" -> "Recomposição Profana";
            default -> "none";
        };
        //1° boss
        if(passiva.equals("Recomposição Profana")){
            if(roundBatalha % 2 == 0){
                System.out.println("O amontoado de corpos se alimenta dos corpos aos arredores ganhando +5% da vida perdida");
                double vidaPerdida = boss.life - boss.actLife;
                boss.actLife += vidaPerdida*0.05;
            }
        }
    }

    // Gera a barra de vida e status completo.
    public static String exibirStatusBatalha(Boss boss, Player player) throws IOException {
        StringBuilder sb = new StringBuilder();

        UtilForMe.fakeClear(50,false); //verificado
        // Cabeçalho
        sb.append(String.format("\n%-30s%-30s\n",
                boss.name.toUpperCase(), "  "+player.nome.toUpperCase()));
        sb.append(String.format("%-30s%-30s\n",
                "-----------------------------/", "/-----------------------------"));

        // Vida (atual / total)
        sb.append(String.format("%-15s %-14s%-15s %-14s\n",
                "Vida:", UtilForMe.arr(boss.life),
                "  "+"Vida:", UtilForMe.arr(player.life)));

        // Dano base
        sb.append(String.format("%-15s %-14.1f%-15s %-14.1f\n",
                "Dano base:", boss.damage,
                "  "+"Dano base:", player.damage));

        // Armadura física
        sb.append(String.format("%-15s %-14.1f%-15s %-14.1f\n",
                "Armadura:", boss.armor,
                "  "+"Armadura:", player.armor));

        // Armadura mágica
        sb.append(String.format("%-15s %-14.1f%-15s %-14.1f\n",
                "Arm Mágica:", boss.magicArmor,
                "  "+"Arm Mágica:", player.magicArmor));

        // Passivas
        sb.append("\nPassivas:\n");
        sb.append(boss.name).append(" ========\n").append(boss.passiva).append("\n\n");
        sb.append(player.nome).append(" ========\n").append(player.passiva).append("\n");
        sb.append("==========================================================================\n");

        return sb.toString();
    }

    // Barra visual |██████--|
    private static String buildHealthBar(double porcentual) {
        int filled = (int) Math.round(porcentual * 10);
        String filledBlocks = "█".repeat(filled);
        String emptyBlocks  = "-".repeat(10 - filled);
        return "|" + filledBlocks + emptyBlocks + "|";
    }

    public boolean isDead(Boss boss){
        return boss.actLife <= 0;
    }

    public void atacarBoss(Boss bossAtacante, Player playerAlvo, List<String> frasesAtaque, int round) throws IOException, InterruptedException {

            double danoFinal;

            danoFinal = bossAtacante.damage - (playerAlvo.armor*1.15 + bossAtacante.damage*0.04);

            if(danoFinal <= 0){
                danoFinal = 10;
                System.out.println("\nO dano causado pelo boss é muito pequeno, devido a alta armadura do adversário.\n");
            }
            if(playerAlvo.espelhoArcano(playerAlvo)){
                danoFinal *= 0.85;
            }
            playerAlvo.actLife -= danoFinal;

            if(isDead(bossAtacante))
            {
                //morteBoss
                bossAtacante.morteBoss();

                bossAtacante.actLife = 0;
                System.out.println("\n"+playerAlvo.nome +" ganhou "+ Boss.xpDaMorteBoss(bossAtacante) +" de xp");
                playerAlvo.upLevel(Boss.xpDaMorteBoss(bossAtacante));

                System.out.println("-------------------------------------------------\n");
                UtilForMe.fakeClear(50,true); //verificado
            }
            else if(Mob.isDead(playerAlvo)){

                System.out.println("\n------------------------------------------------\n"+bossAtacante.name + " atacou "+playerAlvo.nome+ " e causou " + danoFinal + " de dano!");
                System.out.println(playerAlvo.nome + " died");


                Player.morteJogador(playerAlvo,null);

                System.out.println("\n-------------------------------------------------\n");
            }
            else{
                    Random r = new Random();
                    String[] ataqueFrases = frasesAtaque.toArray(new String[0]);
                    exibirRound(round,bossAtacante,playerAlvo,ataqueFrases[r.nextInt(0,ataqueFrases.length+1)],danoFinal,true);
                    passivaAtributos(bossAtacante,round,playerAlvo);
                    UtilForMe.fakeClear(50,true); //verificado

            }


    }

    private static double xpDaMorteBoss(Boss boss){
        return (boss.life + boss.damage + boss.armor + boss.magicArmor + 100) * 4.5;

    }

    public void exibirRound(int round, Boss boss, Player player, String acaoBoss, double dano, boolean eBoss) {

        // ----- Boss -----
        double porcentagemBoss = boss.actLife / boss.life;
        String barraBoss = buildHealthBar(porcentagemBoss);

        // ----- Player -----
        double porcentagemJogador = player.actLife / player.life;
        String barraJogador = buildHealthBar(porcentagemJogador);

if(eBoss){
    System.out.printf("""
    ╔══════════ Round %d ══════════╗
        Boss: %s
        Vida Boss: %s
    ───────────────────────────────
     %s
     
        Dano recebido: %f
        Vida Player: %s
    ╚══════════════════════════════╝
    """,
            round,
            boss.name,
            barraBoss,
            acaoBoss,
            dano,
            barraJogador);
}
else{
    System.out.printf("""
    ╔══════════ Round %d ══════════╗
        Player: %s
        Vida Player: %s
    ───────────────────────────────
        Dano causado: %f
        Vida Boss: %s
    ╚══════════════════════════════╝
    """,
            round,
            player.nome,
            barraJogador,
            dano,
            barraBoss);
}

    }
}

