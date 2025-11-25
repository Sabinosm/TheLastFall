package com.pedro.eventos.quedas;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.eventos.boss.BossBattle;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Mage;
import com.pedro.referenteAosPersonagens.Player;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.pedro.UtilForMe.ReadInt;
import static com.pedro.eventos.Battle.Batalha;

public class SegundaQueda {

    public SegundaQueda() {
    }
    static List<String> chavesTotais = Arrays.asList("1","2","3");
    public static int correctAnswer;
    static int wrongAnswers;

    //Base segunda queda 3 torres 3 inimigos em cada, 3 desafios em cada, Chance de aparecer um boss secundário. Para sair, tem que entrar nas torres e pegar 3 chaves.
    //Ativação mini boss -> fugiu mais de 2 vezes na mesma torre, errou um desafio ( pode aparecer um mago de level 5 ou o mini boss),
    //talvez leveis diferentes para cada torre
    //sendo a 3° torre muito dificil ou sei la
    //Diferentemente da primeira queda, que é um loop, essa é linear

    //TODO -> Notas
    //Todo -> Batalhas
    //Todo -> diálogo de obtensão das chaves e atualizar database
    //TODO -> Torre 4 intro, spawn do boss se a entrada for forçada, pensar no 2° boss
    //TODO -> Não deixar o jogador entrar em um lugar no qual ele ja tenha a chave, aviso " nao há mais inimigos nem chaves "
    //TODO -> descanso + navegação


    public static void Start(Player p) throws IOException, InterruptedException, SQLException {
        while (p.getCheckPoint() != Checkpoint.SEGUNDA_QUEDA_BOSS || p.getCheckPoint() != Checkpoint.SEGUNDA_QUEDA_TORRE4_D){

            if(!p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE")){
                p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA);
            }
            if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA){
                Notas.notasIntroSegundaTorre();
                Viagem(p,0);
            }
            if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE1")){
                DentroTorre(p,1);
            }
            else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE2")){
                DentroTorre(p,2);
            }
            else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE3")){
                DentroTorre(p,3);
            }
            else if(p.getCheckPoint().name().equals("SEGUNDA_QUEDA_TORRE4")){
                if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE4)
                {
                    EntradaTorre(4,p);
                }
            }
            else{
                break;
            }
        }


    }

    private static void DentroTorre(Player p, int torre) throws SQLException, IOException, InterruptedException {
        if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE"+torre))
        {
            if(EntradaTorre(torre,p)){

            }else{

            }
        }
        else if(p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE"+torre+"_D")){

        }
    }

    private static boolean Desafio(int torre) throws IOException, InterruptedException {

           UtilForMe.FakeClear(50,true);
           UtilForMe.TempoDeLeitura(GetDesafio(torre));
           int answer = ReadInt();
           UtilForMe.FakeClear(50,false);

           return answer == correctAnswer;

    }

    private static String GetDesafio(int torre){
        String desafio;
        Random r = new Random();
        int qualDesafio = r.nextInt(1,4);
        if(torre == 1){

            if(qualDesafio == 1){
                desafio = """
                        Quando a terra ser partiu , muitos se agarraram à própria culpa
                        e afundaram com ela.
                        Dize-me: o que pesa mais?
                        A chama que mata,
                        ou a culpa que nos faz afundar antes mesmo do toque das chamas?
                        
                        [ 1 ] A culpa
                        [ 2 ] A chama
                        
                        """;

                correctAnswer = 1;
            }
            else if(qualDesafio ==2){
                desafio = """
                        Uma árvore que nunca descansa dá fruto sem cessar.
                        E o fruto que nasce sem pausas… vive?
                        Ou apodrece antes mesmo de cair no solo?
                        
                        [ 1 ] O fruto vive
                        [ 2 ] O fruto apodrece
                        
                        """;
                correctAnswer = 2;
            }
            else{
                desafio = """
                        Ele prometeu redenção.            -1-
                        A fenda trouxe fogo.              -2-
                        A promessa nunca foi para nós.    -3-
                        
                        Ordena a verdade na sequência correta:
                        do que veio primeiro ao que restou no fim.
                        
                        [ 1 ] 1 -> 2 -> 3
                        [ 2 ] 1 -> 3 -> 2
                        [ 3 ] 3 -> 1 -> 2
                        [ 4 ] 2 -> 1 -> 3
                        
                        """;
                correctAnswer = 3;
            }
        }
        else if(torre == 2){
            if(qualDesafio == 1){
                desafio = """
                        Três recipientes aguardam essência.
                        Um texto aparece, lentamente:
                        
                        “A vida cresce quando damos.
                        Mas quando damos tudo… o que permanece?
                        
                        Três cálices vazios,
                        três destinos possíveis.
                        
                        Dize-me:
                        o mundo se sustenta pelo acúmulo…
                        ou pela falta?”
                        
                        [ 1 ] Deixar vazio - pela falta
                        [ 2 ] Colocar essência - pelo acúmulo
                        
                        """;

                correctAnswer = 1;
            }
            else if(qualDesafio ==2){
                desafio = """
                        Quando o colapso começou, pedimos aos céus por salvação.
                        O silêncio foi a resposta.
                        Então, pela primeira vez, ajoelhamos… não para servir, mas para sobreviver.
                        Contra o que estávamos lutando?
                        
                        [ 1 ] O vazio
                        [ 2 ] O cri*&¨#@ador
                        
                        """;
                correctAnswer = 2;
            }
            else{
                desafio = """
                        Fomos chamados de guias.
                        Luz para um povo perdido.
                        Mas quando o abismo abriu-se… quem iluminou nosso caminho?
                        
                        [ 1 ] O criador
                        [ 2 ] O abismo
                        [ 3 ] O devorador
                        
                        """;
                correctAnswer = 3;
            }
        }
        else{
            if(qualDesafio == 1){
                desafio = """
                        “Chamamos de legado tudo aquilo que não conseguimos abandonar.”
                        “Mas, no fim, nossos legados foram correntes.”
                        “Guardamos nossas torres como reis guardam túmulos: com orgulho inútil.”
                        “Nada restou para nós, senão um falso deus, que nós consome de pouco em pouco.”
                        Qual é o nosso legado?
                        
                        [ 1 ] A crença em demônio que se passa por deus
                        [ 2 ] Um mundo destruído
                        
                        """;

                correctAnswer = 1;
            }
            else if(qualDesafio ==2){
                desafio = """
                        Não foi o Vazio que me tomou primeiro.
                        Foi a magia que eu mesmo cultivei.
                        Ela cresceu como uma semente orgulhosa, regada por nossa arrogância.
                        Quando imploramos por ajuda, ela já havia aprendido a sobreviver sem nós.
                        E assim nos devorou, silenciosa, obediente apenas à fome do próprio poder.
                        A magia se voltou contra nós porque…?
                        
                        [ 1 ] Ela apenas refletiu os nossos desejos mais profundos.
                        [ 2 ] Ela sempre pertenceu ao Vazio, e nós éramos só hospedeiros temporários.
                        
                        """;
                correctAnswer = 2;
            }
            else{
                desafio = """
                        O fim não nos uniu.
                        Transformou-nos em sombras desconfiadas, cada uma guardando sua própria torre.
                        Mas ainda assim os portões só abrem por consenso. Chaves separadas, por que ainda existe uma saída,
                        se não podemos fugir?
                        
                        [ 1 ] Destino.
                        [ 2 ] Punição disfarçada de misericórdia.
                        [ 3 ] Existe sim uma forma de fugir.
                        
                        """;
                correctAnswer = 3;

            }
        }

        return desafio;
    }

    private static String GetRespostaDesafio(int torreQualDesafio){
        String respostaDesafio;
        if(torreQualDesafio == 11){
            respostaDesafio = """
                    A culpa sentida pelos arcanistas, magos antigos, por não conseguirem fazerem nada
                    perante a imensa vastidão do vazio, é mais dolorosa, do que a dor de sucumbir e descer ao inferno.
                    
                    """;
        }
        else if(torreQualDesafio == 12){
            respostaDesafio = """
                    O que um escravo pode trazer de bom fruto? Que um com liberdade não faria muito melhor?
                    As correntes da culpa, seguram aqueles que são fracos demais para se perdoar e seguir em frente.
                    É impossível nascer de novo, se não há crescimento, um fruto novo não será melhor se a árvore não for antes
                    purificada.
                    
                    """;
        }
        else if(torreQualDesafio == 13){
            respostaDesafio = """
                    A promessa nunca foi para nós, a fenda trouxe o fogo, a dor, e dizimou a todos nos. Ele prometeu redenção
                    mas não para a humanidade, pelo menos, não para a nossa...
                    
                    """;
        }
        else if(torreQualDesafio == 21){
            respostaDesafio = """
                    A vida ao ser entregue, é dada para quem não pode prover salvação, ninguém o faz.
                    Acumular vida no mundo não trouxe salvação, pois um mundo cheio de prisioneiros e pecadores, não salvará ninguém.
                    Na falta, na ausência é que se percebe o verdadeiro valor, o pouco sustenta e dá ao ego o necessário para ser feliz
                    e o máximo para que não se desvie do caminho correto.
                    
                    """;
        }
        else if(torreQualDesafio == 22){
            respostaDesafio = """
                    Não somos os primeiros, mas seremos os últimos, não seremos salvos mas não dessitiremos, o dever
                    de um mago é purificar, a magia é benção e não caos...mas como apagar com uma chama o incêndio?
                    
                    """;
        }
        else if(torreQualDesafio == 23){
            respostaDesafio = """
                    Que povo patético, após a derrota, o que restou se não depositarmos nossas esperanças em falsos deuses?
                    Um que nós mesmos criamos, uma criatura falha, assim como nosso povo, mas também devastadora e cruel, assim como fomos.
                    Mas a quem posso culpar? Nunca existiu um deus, não um verdadeiro deus, não o que sonhamos, existiam somente demônios.
                    
                    """;


        }else if(torreQualDesafio == 31){
            respostaDesafio = """
                    Com que propriedade afirmamos isto? O último herói não passou de uma farsa, não existe predestinado.
                    NÃO EXISTE SAÍDA!
                    
                    """;

       }
        else if(torreQualDesafio == 32){
            respostaDesafio = """
                    Nós fomos um teste, fomos mais um que deu errado, a benção nunca foi nossa.
                    E para nos, o que era benção tornou-se maldição.
                    
                    """;
        }
        else {
            respostaDesafio = """
                   O devorador de mana, o que era nossa esperança, tornou se o motivo pelo qual ainda estamos aqui,
                   sofrendo pela eternidade, seria isso tudo premeditado?
                   
                   """;
        }
        return respostaDesafio;
    }

    private static String GetIntroTorre(int torre){
        String t;
        if(torre == 1){
            t = """
                    - Torre do Oeste -
                    Ergue-se como um monumento cansado, seus blocos gastos pelo vento seco e pela poeira azulada do deserto de mana.
                    A luz tênue que brota das rachaduras nas paredes pulsa de forma irregular, fraca, mas viva.
                    Há marcas antigas de reconstruções improvisadas, vigas reforçadas, passagens estreitas, símbolos riscados às pressas.
                    Tudo nela indica um poder instável, menor, porém ainda perigoso.
                    Mesmo assim, a porta permanece ereta… e vigilante.
                    Ela sente quem se aproxima. E pesa suas intenções.
                    
                    """;
        } else if (torre == 2) {
            t= """
                    - Torre do Leste -
                    A construção se ergue firme contra os ventos do vazio, mas ainda corroída pelo tempo.
                    Diferente das ruínas do Oeste, aqui cada bloco está alinhado com precisão, geometria severa, uma ordem que 
                    força o visitante a endireitar a postura.
                    Faixas de mana correm pelas paredes como rios luminosos, formando circuitos complexos que se ajustam conforme alguém se aproxima.
                    É uma torre que não apenas vigia…
                    Ela calcula. Mede. Julga.
                    O ar ao seu redor vibra com um poder controlado, disciplinado, perigoso.
                    
                    """;
        }
        else {
            t = """
                    - Torre do Norte -
                    Aqui o deserto de mana parece se curvar.
                    Os ventos param na base; a luz muda de cor conforme toca as paredes; a temperatura flutua, obedecendo regras que não pertencem ao mundo comum.
                    A torre é imensa, composta de um material negro e polido que reflete não o viajante, mas silhuetas que não deveriam estar ali.
                    As runas gravadas nela não brilham, ardem, como brasas presas sob camadas de vidro.
                    Uma presença silenciosa pesa sobre o espírito de qualquer um que se aproxima, exigindo respeito, talvez submissão.
                    Não é uma construção.
                    É um aviso.
                    
                    """;
        }

        return t;
    }

    private static String GetIntroTorreInside(int torre){
        String t;
        if(torre == 1){
            t = """
                    - Torre do Oeste -
                    O interior é estreito e irregular, com paredes rachadas que ainda sussurram resquícios de mana perdida.
                    Runas apagadas oscilam fracamente, lutando para não desaparecer.
                    O ar cheira a pó antigo e magia mal preservada, frágil como o Arcanista que a guarda.
                    
                    """;
        } else if (torre == 2) {
            t= """
                    - Torre do Leste -
                    Corredores simétricos e frios se estendem, repletos de runas vivas que vigiam cada passo.
                    A arquitetura é precisa, rígida, com a torre exigindo disciplina.
                    O silêncio é tão absoluto que mais parece uma ordem do próprio Arcanista.
                    
                    """;
        }
        else {
            t = """
                    - Torre do Norte -
                    O interior pulsa com mana densa, formando padrões que se movem nas paredes como seres vivos.
                    A luz não tem cor definida, muda conforme você respira, julgando sua presença.
                    Cada passo ecoa pesado, e a própria torre parece consciente, orgulhosa do Arcanista que protege.
                    
                    """;
        }

        return t;
    }

    private static void MiniBoss(Player p,int torre) throws IOException, InterruptedException {
        if(wrongAnswers >= 3){
            if(!BossBattle.SecundaryBossBattleSQ(p)){
                UtilForMe.FakeClear(50,false);
                UtilForMe.TempoDeLeitura("""
                        A palavra sai da sua boca… e o deserto não responde.
                        Silêncio.
                        Depois, um rugido que rasga o mundo.
                        
                        O Falso Deus inclina a cabeça, como se compreendesse a sua falha antes mesmo de você.
                        A areia abre caminho. A escuridão engole tudo.
                        
                        O grito que segue não é seu —
                        é o do próprio deserto, devorando mais uma alma para alimentar o vazio.
                        
                        Você não acorda.
                        Ninguém acorda.
                        
                        O Falso Deus se alimenta.
                        """);
                p.setActLife(0);
                UtilForMe.FakeClear(50,true);
                Player.MorteJogador(p,null);
            }
            else{

                UtilForMe.FakeClear(50,true);
                UtilForMe.TempoDeLeitura("""
                        O colosso de carne e mana curva o corpo, fazendo a própria ordem do mundo
                        lembrar-lhe de quem o criou, e de quem ele mesmo matou.
                        Gritando sem voz, ele se retorce, afundando na areia que se abre como um túmulo antigo.
                        
                        O vento corta, os gritos do devorador fazem a mana ao redor se tornar instável.
                        A luz se torce e de pouco em pouco o monstro afunda na areia junto de suas correntes.
                        A fenda se fecha sobre ele com violência divina.
                        
                        Por um instante, tudo silencia… exceto o som da besta batendo contra as paredes do seu novo cárcere, 
                        cada impacto mais distante que o anterior.
                        
                        E então, apenas o vazio.
                        
                        Você não o derrotou.
                        Ninguém jamais o fará.
                        
                        Mas hoje — ele voltou a dormir.
                        """);
            }
        }
        else{
            Mage m = new Mage(p,torre);
            UtilForMe.TempoDeLeitura("""
                    Ao errar o desafio, você sente uma presença diferente, um arcanista detecta um invasor...
                    """);
            UtilForMe.FakeClear(40,true);
            Batalha(p,m,false,2);
        }
    }

    private static boolean EntradaTorre(int torre,Player p) throws IOException, InterruptedException, SQLException {
        UtilForMe.TempoDeLeitura(GetIntroTorre(torre));
        int escolha = 0;
        System.out.println("\nDESEJA continuar? Ou ir para outra torre?\n" +
                "[ 1 ] Continuar\n" +
                "[ 2 ] Ir para outra torre");

        escolha = ReadInt();

        while (escolha != 2 || escolha != 1){
            System.out.println("Digite um número válido");
            escolha = ReadInt();
        }

        if(escolha == 1){
            while (!Desafio(torre)){
                wrongAnswers++;
                MiniBoss(p,torre);

            }
            if(wrongAnswers > 0) wrongAnswers--;
            UtilForMe.TempoDeLeitura(GetRespostaDesafio(30 + correctAnswer));
            UtilForMe.FakeClear(50,true);
            UtilForMe.TempoDeLeitura(GetIntroTorreInside(torre));
            UtilForMe.FakeClear(50,true );
            return true;
        }
        else{
            Viagem(p,torre);
            return false;
        }


    }

    public static void Viagem(Player player, int torre) throws IOException, InterruptedException, SQLException {
        int quantTorre = !(torre == 0) ? 3 : 4;
        String onde;
        UtilForMe.FakeClear(50,false);
        UtilForMe.TempoDeLeitura( String.format("""
                Por alguns instantes, tudo é silêncio, um silêncio pesado, quase vivo.
                Então você nota: trilhas etéreas serpenteiam pelo chão, como fios luminosos que se afastam em direções distintas.
                
                Cada rastro vibra num tom diferente, pulsando com ecos da magia que sustenta este mundo partido.
                É impossível ignorá-los; São %s caminhos, levando a diferentes torres ancestrais. Dentro de cada uma é revelado uma parte da história,
                tanto do presente como do passado e pistas para que possa haver um futuro.
                
                """, quantTorre));

        if(torre == 0){
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                [ 4 ] Torre do Leste
                
                """);

            do{
                onde = switch (ReadInt()){
                    case 1 -> "Norte";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    case 4 -> "Leste";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));

        } else if (torre == 1) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Leste
                [ 3 ] Torre do Sul
                
                """);

            do{
                onde = switch (ReadInt()){
                    case 1 -> "Norte";
                    case 2 -> "Leste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));

        } else if (torre == 2) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                
                """);
            do{
                onde = switch (ReadInt()){
                    case 1 -> "Norte";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));
        } else if (torre == 3) {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Leste
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Sul
                
                """);

            do{
                onde = switch (ReadInt()){
                    case 1 -> "Leste";
                    case 2 -> "Oeste";
                    case 3 -> "Sul";
                    default -> "none";
                };
                if(onde.equals("none")){
                    System.out.println("Digite uma opção válida!");
                }
            }while(onde.equals("none"));
        }else {
            UtilForMe.TempoDeLeitura("""
                Escolha em qual direção irá (o ponto central do andar é usado como referencia)
                [ 1 ] Torre do Norte
                [ 2 ] Torre do Oeste
                [ 3 ] Torre do Leste
                
                """);

           do{
               onde = switch (ReadInt()){
                   case 1 -> "Norte";
                   case 2 -> "Oeste";
                   case 3 -> "Leste";
                   default -> "none";
               };
               if(onde.equals("none")){
                   System.out.println("Digite uma opção válida!");
               }
           }while(onde.equals("none"));

        }

        switch (onde) {
            case "Oeste" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE1);
            case "Leste" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE2);
            case "Norte" -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE3);
            case "Sul"   -> player.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE4);
        }
        UtilForMe.FakeClear(50,false);
        UtilForMe.TempoDeLeitura("VIAJANDO..............");
        Thread.sleep(2000);
        UtilForMe.FakeClear(50,false);
    }
}
