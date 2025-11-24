package com.pedro.eventos.boss;

import com.pedro.UtilForMe;
import com.pedro.referenteAosPersonagens.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public abstract class Boss implements BossInterface {
    public double life;
    public double actLife;
    public double damage;
    public double armor;
    public double magicArmor;
    public String name;
    public String nomePassiva;
    String passiva;


    public void PassivaAtributos(Boss boss,int roundBatalha,Player player){
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
    public static String ExibirStatusBatalha(Boss boss, Player player) throws IOException {
        StringBuilder sb = new StringBuilder();

        UtilForMe.FakeClear(50,false); //verificado
        // Cabeçalho
        sb.append(String.format("\n%-30s%-30s\n",
                boss.name.toUpperCase(), "  "+player.nome.toUpperCase()));
        sb.append(String.format("%-30s%-30s\n",
                "-----------------------------/", "/-----------------------------"));

        // Vida (atual / total)
        sb.append(String.format("%-15s %-14s%-15s %-14s\n",
                "Vida:", UtilForMe.Arr(boss.life),
                "  "+"Vida:", UtilForMe.Arr(player.life)));

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
    private static String BuildHealthBar(double porcentual) {
        int filled = (int) Math.round(porcentual * 10);
        if(filled < 0) filled = 0;
        String filledBlocks = "█".repeat(filled);
        String emptyBlocks  = "-".repeat(10 - filled);
        return "|" + filledBlocks + emptyBlocks + "|";
    }

    public boolean IsDead(Boss boss){
        return boss.actLife <= 0;
    }

    public void AtacarBoss(Boss bossAtacante, Player playerAlvo, List<String> frasesAtaque, int round) throws IOException, InterruptedException {

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

            if(Mob.isDead(playerAlvo)){

                System.out.println("\n------------------------------------------------\n"+bossAtacante.name + " atacou "+playerAlvo.nome+ " e causou " + danoFinal + " de dano!");
                System.out.println(playerAlvo.nome + " died");


                Player.MorteJogador(playerAlvo,null);

                System.out.println("\n-------------------------------------------------\n");
            }
            else{
                    Random r = new Random();
                    String[] ataqueFrases = frasesAtaque.toArray(new String[0]);
                    ExibirRound(round,bossAtacante,playerAlvo,ataqueFrases[r.nextInt(0,ataqueFrases.length)],danoFinal,true);
                    PassivaAtributos(bossAtacante,round,playerAlvo);
                    UtilForMe.FakeClear(50,true); //verificado

            }


    }

    static double XpDaMorteBoss(Boss boss){
        return (boss.life + boss.damage + boss.armor + boss.magicArmor + 100) * 3.5;

    }

    public void ExibirRound(int round, Boss boss, Player player, String acaoBoss, double dano, boolean eBoss) {

        // ----- Boss -----
        double porcentagemBoss = boss.actLife / boss.life;
        double porcentagemB = Math.round((boss.actLife*100)/boss.life);
        String barraBoss = BuildHealthBar(porcentagemBoss);

        // ----- Player -----
        double porcentagemJogador = player.actLife / player.life;
        double porcentagemP = Math.round((player.actLife*100)/player.life);
        String barraJogador = BuildHealthBar(porcentagemJogador);

if(eBoss){
    System.out.printf("""
    ╔═════════════ Round %d ═════════════╗
    
        Boss: %s
        Vida Boss: %s
        
    ──────────────────────────────────────
     %s
     
        Dano recebido: %f
        Vida Player: %s
    ╚════════════════════════════════════╝
    """,
            round,
            boss.name,
            barraBoss+"---"+porcentagemB+"%",
            acaoBoss,
            dano,
            barraJogador+"---"+porcentagemP+"%");
}
else{
    System.out.printf("""
    ╔═════════════ Round %d  ═════════════╗
    
        Player: %s
        Vida Player: %s
        
    ──────────────────────────────────────
    
        Dano causado: %f
        Vida Boss: %s
   ╚══════════════════════════════════════╝
    """,
            round,
            player.nome,
            barraJogador+"---"+porcentagemP+"%",
            dano,
            barraBoss+"---"+porcentagemB+"%");
}

    }
}

