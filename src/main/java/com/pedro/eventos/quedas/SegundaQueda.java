package com.pedro.eventos.quedas;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SegundaQueda {

    public SegundaQueda() {
    }
    static List<String> chavesTotais = Arrays.asList("1","2","3");
    public static int correctAnswer;


    public static void Start(Player p) throws IOException, InterruptedException {
        if(p.getCheckPoint() != Checkpoint.SEGUNDA_QUEDA_BOSS && !p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE")){
            p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA);
        }
        //Base segunda queda 3 torres 3 inimigos em cada, 3 desafios em cada, Chance de aparecer um boss secundário. Para sair, tem que entrar nas torres e pegar 3 chaves.
        //Ativação mini boss -> fugiu mais de 2 vezes na mesma torre, errou um desafio ( pode aparecer um mago de level 5 ou o mini boss),
        //talvez leveis diferentes para cada torre
        //sendo a 3° torre muito dificil ou sei la
        //Diferentemente da primeira queda, que é um loop, essa é linear

        //TODO -> Notas
        //TODO -> Desafios
        //Todo ->



        if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA || p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE1")){

            if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE1 || p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA )
            {
                UtilForMe.TempoDeLeitura(GetIntroTorre(1));
                //desafio
                //Morte do player
                //Como acabar a dungeon -> sistema de navegação
                if(Desafio(1)){
                    UtilForMe.TempoDeLeitura(GetRespostaDesafio(10 + correctAnswer));
                    UtilForMe.FakeClear(50,true);
                    //analise interior da torre
                    //começo das batalhas
                    //descanso
                }

            }
            else if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE1_D){

            }

        }
        if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA || p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE2")){

            if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE2 || p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA )
            {
                UtilForMe.TempoDeLeitura(GetIntroTorre(2));
                //desafio
                if(Desafio(2)){
                    UtilForMe.TempoDeLeitura(GetRespostaDesafio(20 + correctAnswer));
                    UtilForMe.FakeClear(50,true);
                    //analise interior da torre
                    //começo das batalhas
                    //descanso
                }
                else{

                }
            }
            else if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE2_D){

            }

        }
        if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA || p.getCheckPoint().name().contains("SEGUNDA_QUEDA_TORRE3")){

            if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE3 || p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA )
            {
                UtilForMe.TempoDeLeitura(GetIntroTorre(3));
                //desafio
                if(Desafio(3)){
                    UtilForMe.TempoDeLeitura(GetRespostaDesafio(30 + correctAnswer));
                    UtilForMe.FakeClear(50,true);
                    //analise interior da torre
                    //começo das batalhas
                    //descanso
                }
            }
            else if(p.getCheckPoint() == Checkpoint.SEGUNDA_QUEDA_TORRE3_D){

            }

        }

    }
    private static boolean Desafio(int torre) throws IOException, InterruptedException {

           UtilForMe.FakeClear(50,true);
           UtilForMe.TempoDeLeitura(GetDesafio(torre));
           int answer = UtilForMe.ReadInt();
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

}
