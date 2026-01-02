package com.pedro.eventos.boss;
import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.UserInterruptException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BossAmalgama extends Boss{
    String[] frasesAtaque = {"A massa disforme investe, esmagando tudo!",
            "Fragmentos de ossos voam como lâminas etéreas!",
            "Garras improvisadas rasgam o ar!",
            "O Colosso ruge, o som é tão agudo que a sala toda se racha",
            "Tentáculos de ossos estalam no ar, chicoteando com força brutal",
            "O monstro joga diversos corpos em sua direção"};

    public BossAmalgama(Player p) {

            DefinirAtributosBoss(p);
    }

    @Override
    public String Introducao(Player player){
        String intro;
        if(!player.notasLidas.contains("5")){
            intro = """
            Ao atravessar o último arco da ruína você observa a sala em que está.
            A câmara é vasta, iluminada apenas por fendas de luz cinzenta que filtram do teto rachado.
            No chão, vários corpos jogados, aventureiros, carniceiros, ou apenas pessoas sem magia.
            Um som profundo — metade respiração, metade lamento — vibra pelas paredes.
        
            Das sombras, surgem os Observadores.
            Eles não falam com a boca, mas uma voz sem eco invade sua mente, fria como o Vazio.
        
            "Você ousa enfrentar o Guardião da Primeira Queda..."
        
            Um segundo pensamento, mais agudo, corta sua consciência:
            "A carne que guarda este portão não teme lâminas nem feitiços.
            Somente as palavras que ele mesmo revela podem feri-lo.
            Quando os símbolos surgirem no ar, repita-os com exatidão, ou sua força será em vão."
        
            A voz silencia, mas o aviso permanece gravado em seus ossos.
            À sua frente, a massa colossal de carne pulsa lentamente, respirando como um coração gigante.
            O Amálgama desperta.
            """;
        }
        else{
            intro = "A carne pulsa e a sala treme. Símbolos de luz aparecem no ar, " +
                    "um monstro enorme feito totalmente de corpos decompostos...alguns deles continuam na sala.\n" +
                    "Existe um portão atrás da criatura, para onde será que leva?";
        }
        return intro;
    }

    @Override
    public void DefinirAtributosBoss(Player player) {

        //calculo da vida -> vida base mais ou menos como funciona um carniceiro, más com o level do player, ou seja ,constante + variável;
        Random random = new Random();
        double vidaBase = random.nextInt(180,250) + (player.level * 1.4 + 1.4 + 1) * 30;
        double mediaPlayer = player.life + player.damage + player.armor + player.magicArmor;
        double aumentoEscalar = mediaPlayer * 1.15;
        this.life = vidaBase + aumentoEscalar;
        this.actLife = this.life;

        //calculo dano -> supondo que a vida do player deve ser perto la de 500
        double danoBase = random.nextInt(50,100);
        double danoEscalar = (mediaPlayer - 150)  * 0.05;
        this.damage = danoEscalar + danoBase;

        //O calculo das armaduras vai ser mais simples pra não impactar tanto na dificuldade. Qualquer coisa so tirar se tiver muito broken.
        this.magicArmor = player.magicArmor * 0.15;
        this.armor = player.armor * 0.25;

        //nome
        this.name = "O Amálgama";

        //passiva
        this.nomePassiva = "Recomposição Profana";
        this.passiva ="Recomposição Profana – os corpos que estão ao lado são alimentos para a criatura\na cada 3 turnos recupera 5% da vida perdida por consumir um corpo.";

    }

    @Override
    public void MorteBoss() throws IOException, InterruptedException {
        UtilForMe.TempoDeLeitura("""
                O último rugido do Amálgama ecoa como um trovão sufocado. \s
                A massa de carne treme, racha e desaba em um mar de sangue e vapor quente. \s
                Enquanto o silêncio se impõe, você percebe um brilho atrás dos restos: \s
                um portão colossal, feito de pedra negra e runas ardentes, pulsa com luz própria. \s
                
                Não há caminho de volta. \s
                As ruínas atrás de você já se fecharam, como se o mundo negasse a fuga. \s
                Os Observadores, silenciosos, apenas observam. \s
                
                Com um último olhar para a sala profanada, você caminha em direção ao portão. \s
                A luz das runas se intensifica e a Segunda Queda o chama.""");
    }

    @Override
    public void AtacarPlayer(Player player, Boss boss, int round) throws IOException {
        String palavraEscolhida = GetPalavraEscolhida();
        double danoFinal = GetDanoFinal(player,boss);


        Thread mainThread = Thread.currentThread(); // thread do readLine
        AtomicLong tempoGasto = new AtomicLong();
        AtomicLong tempoInicio = new AtomicLong();


        Thread timer = new Thread(() -> {

            try {
                tempoInicio.set(System.currentTimeMillis()); //calcular o bonus de ataque
                Thread.sleep(5000); // 5 segundos
                System.out.println("\nAs palavras rúnicas desaparecem, a brecha se fecha");
                long tempoFinal = System.currentTimeMillis(); //calcular o bonus de ataque

                tempoGasto.set(tempoFinal - tempoInicio.get());

                mainThread.interrupt(); // interrompe o readLine
            } catch (InterruptedException e) {
                long tempoFinal = System.currentTimeMillis();
                tempoGasto.set(tempoFinal - tempoInicio.get());

                // timer foi interrompido porque usuário digitou
            }

        });

        timer.start();
        try {
            System.out.println("⚔ A barreira do Amálgama pulsa, uma brecha aparece!\n" +
                    "Uma palavra aparece em meio à barreira - " + palavraEscolhida + " -\n");

            System.out.println("Digite a palavra corretamente ( 3 segundos ) para abrir a brecha: ");
            String text = UtilForMe.ReadStr();  // bloqueia
            timer.interrupt(); // usuário digitou antes do tempo acabar
            if(text.equalsIgnoreCase(palavraEscolhida)){
                System.out.println("✔ Brecha aberta! Você acerta um golpe\n");
                if(tempoGasto.get() <= 1500){
                    System.out.println("✔ Um golpe rápido e poderoso! Você acerta um golpe crítico\n");
                    boss.actLife -= danoFinal*1.50;
                    boss.ExibirRound(round,boss,player,"",danoFinal*1.50,false);

                }
                else{
                    boss.actLife -=danoFinal;
                    boss.ExibirRound(round,boss,player,"",danoFinal,false);
                }



            }



        } catch (UserInterruptException e) {
            System.out.println("Você não conseguiu digitar a tempo, a brecha se fecha");
        }
        try {
            if(IsDead(boss))
            {
                UtilForMe.FakeClear(50,false); //verificado
                //morteBoss
                boss.MorteBoss();

                boss.actLife = 0;
                System.out.println("\n"+player.nome +" ganhou "+ Boss.XpDaMorteBoss(boss) +" de xp");
                player.upLevel(Boss.XpDaMorteBoss(boss));
                System.out.println("-------------------------------------------------\n");
                 //verificado
            }
            else{
                System.out.println("A batalha continua...");
                UtilForMe.FakeClear(50,true); //verificado
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }

    private static String GetPalavraEscolhida() {
        Random random = new Random();
        String[] listaPalavras = {
                "Xalther", "Morveth", "Irrakai", "Thyss", "Vorun",
                "Kreth", "Naaloth", "Syrkai", "Druveth", "Orris",
                "Ajuda", "Socorro", "Mae", "Acabe", "Perdao",
                "Libertem", "Piedade", "Nao", "Dor", "Chega",
                "v0id", "bl00d", "s@nct", "cr1es", "d3ep",
                "r!ft", "s0rr0w", "pa!n", "h@te", "f34r",
        };
        return listaPalavras[random.nextInt(0,listaPalavras.length)];
    }

    public static double GetDanoFinal(Player player, Boss boss){

        String dmt = player.dmt;
        double danoFinal;

        if (dmt.equals( "fisico") || dmt.equals( "físico"))
        {
            danoFinal = player.damage - (boss.armor*1.15 + player.damage*0.04);
            if(danoFinal <= 0){
                danoFinal = 10;
                System.out.println("\nSeu dano é muito baixo perante a incrivel armadura do adversário.\n");
            }

        }
        else if(dmt.equals("magico") || dmt.equals("mágico"))
        {
            danoFinal = player.damage - (boss.magicArmor*1.15 + player.damage*0.04);
            if(danoFinal <= 0){
                danoFinal = 10;
                System.out.println("\nSeu dano é muito baixo perante a incrivel armadura do adversário.\n");
            }
        }
        else{
            danoFinal = player.damage;
        }
        if (danoFinal < 0) danoFinal = 10;

        if(player.espelhoArcano(player)){
            danoFinal += boss.damage * 0.08;
        }
        if(player.umPoucoDeSorte(player)){
            if(dmt.equals("true")){
                danoFinal = player.damage * 2;
            }
            else if (dmt.equalsIgnoreCase("físico")){
                danoFinal = (player.damage - boss.armor*1.15 + player.damage*0.04 ) * 2;
            }else{
                danoFinal = (player.damage - boss.magicArmor*1.15 + player.damage*0.04 ) * 2;
            }

        }
        return danoFinal;
    }
}














