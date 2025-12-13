package com.pedro.eventos;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public abstract class EventosSecundarios {
    public static Random r = new Random();
    static List<Integer> notas = Arrays.asList(6,7,8,9,10);
    static boolean emb = false;
    public EventosSecundarios() {

    }

    public static void PrimeiraQuedaEventos(Player p) throws InterruptedException, IOException, SQLException {
        UtilForMe.FakeClear(50,false); //verificado

        for (int evento = 1; evento < 5; evento++) {
            if (evento == 1 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(1,1);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            } else if (evento == 2 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(2,1);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            } else if (evento == 3 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(3,1);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            }
         else if (evento == 4 && !p.notasLidas.contains(Integer.toString(evento))) {
            p.notasLidas.add(Integer.toString(evento));
            Notas.notasEventos(5,1);
            UtilForMe.FakeClear(50, true); //verificado
            break;
            }
        }
        EventosSecundarios.Descanso(p, 1,null);

    }

    public static void Descanso(Player p, int queda, Integer torre) throws InterruptedException, IOException, SQLException {

        int escolha = -1;

        String txtDescanso = GetTextoDescanso(queda);

        //checkPoint
        if(queda == 1){
            p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_DESCANSO);
        }
        else if(queda == 2){
            switch (torre){
                case 1 -> p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE1_D);
                case 2 -> p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE2_D);
                case 3 -> p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE3_D);
                case 4 -> p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA_TORRE4_D);
                default -> p.setCheckPoint(Checkpoint.SEGUNDA_QUEDA);
            }
        }

        while (escolha != 5) {
            UtilForMe.FakeClear(50,false); //verificado
            UtilForMe.TempoDeLeitura(txtDescanso);
            System.out.println("""
                    (1) Olhar pontos de habilidade – ver e distribuir pontos acumulados.
                    (2) Ver dados sobre monstros – lembranças e anotações dos inimigos já enfrentados.
                    (3) Ler notas encontradas – pedaços de história e segredos coletados durante a jornada.
                    (4) Salvar progresso – salvar seu jogo para continuar mais tarde.
                    
                    (5) Seguir adiante – deixar a o local e prosseguir pela fenda.
                    """);
            
            escolha = UtilForMe.ReadInt(p);

            switch (escolha) {
                case 1:
                    Player.menuHabilidades(p);
                    break;
                case 2:
                    UtilForMe.FakeClear(50,false); //verificado
                    InimigosMortosTextos(queda, p);
                    
                     UtilForMe.FakeClear(1,true); //verificado
                    break;
                case 3:
                    StringBuilder notaTexto = new StringBuilder();
                    var notasL = new java.util.ArrayList<>(p.notasLidas);
                    Collections.sort(notasL);
                    for (int i = 0; i < notasL.size(); i++) {

                        notaTexto.append("( ")
                                .append(i+1)
                                .append(" )")
                                .append(" Reler nota ")
                                .append(notasL.get(i))
                                .append("\n");

                        if (i + 1 == notasL.size()) {
                            if(i+1 !=5 ){
                                notaTexto.append("\n\n\n( ")
                                        .append(notasL.size() + 1)
                                        .append(" ) Para sair");
                            }
                            else{
                                notaTexto.append("\n\n\n( ")
                                        .append(notasL.size() + 2)
                                        .append(" ) Para sair");
                            }

                        }

                    }

                    while (true) {

                        System.out.println(notaTexto);
                        try {
                            
                            escolha = UtilForMe.ReadInt(p);

                            if (escolha == notasL.size() + 1) {
                                break; // sair do while
                            }
                            //Para pegar a nota certa, já que foi organizado em ordem decrescente.
                            Notas.notasEventos(Integer.parseInt(notasL.get(escolha-1)),queda);
                            UtilForMe.FakeClear(50,true); //verificado
                        } catch (NumberFormatException e) {
                            System.out.println("Digite um número válido!");
                        }
                    }

                    break;
                case 4:
                    UtilForMe.TempoDeLeitura("Salvando...................");
                    Thread.sleep(1000);
                    UtilForMe.TempoDeLeitura("JOGO SALVO COM SUCESSO\n\n");
                    Thread.sleep(500);
                    PlayerConfigurations.SalvarPlayer(p);
                    break;
                case 5:

                    break;
            }

        }


    }

    private static String GetTextoDescanso(int queda) {
        int x = r.nextInt(1, 4);
        String txt;
        if(queda == 1){
            if (x == 1) {
                txt = ("""
                    Depois de vagar pela fenda, os gritos e o cheiro de sangue parecem se dissipar por alguns instantes.
                    Entre ruínas quebradas, você encontra uma fogueira ainda acesa — brasas tímidas resistindo à névoa.
                    O calor frágil contrasta com o frio sufocante do abismo.
                    Aqui, por um momento, você pode descansar… refletir… ou se preparar para o que virá.
                    
                    O que deseja fazer?
                    """);
            } else if (x == 2) {
                txt = ("""
                    Você encontra abrigo entre as paredes quebradas de uma antiga construção.
                    As pedras frias e gastas guardam histórias de quem viveu aqui antes da queda.
                    O silêncio é tão profundo que até a própria fenda parece respeitar o lugar.
                    
                    O que deseja fazer?
                    """);
            } else {
                txt = ("""
                    Em meio à névoa vermelha, ergue-se um altar antigo, tomado por musgo e sangue seco.
                    Não se sabe a quem foi dedicado, mas há uma aura de proteção sutil emanando dele.
                    Talvez seja apenas uma ilusão… ou talvez, por um instante, os deuses ainda olhem para cá.
                    
                    O que deseja fazer?
                    """);
            }
        }
        else if(queda == 2){
            if (x == 1) {
                txt = ("""
                    Depois de vagar pela torre, já não se ouve barulho algum.
                    Em meio as paredes pequenas e sufocantes, há um tempo de descanso.
                    Uma porta a frente leva para a saída deste lugar.
                    Aqui, por um momento, antes de sair você pode descansar… refletir… ou se preparar para o que virá.
                    
                    O que deseja fazer?
                    """);
            } else if (x == 2) {
                txt = ("""
                    As portas do templo se abrem com um lamento surdo.
                    Um véu de névoa cobre o chão, e dela ecoam murmúrios — preces, arrependimentos e gritos fundidos em um só coro.
                    As almas aqui não descansam… apenas esperam.
                    No centro, uma fogueira etérea queima sem consumir nada, emanando um calor que parece mais espiritual do que real.
                    
                    O que deseja fazer?
                    """);
            } else {
                txt = ("""
                    O chão gélido brilha sob a luz azulada que emana do nada.
                    As paredes rachadas parecem pulsar — como se respirassem junto ao Vazio.
                    Entre as sombras, uma fogueira luta contra o frio, suas chamas moldando formas que lembram rostos.
                    Por um momento, você sente paz... ou seria apenas a calmaria antes de algo maior?
                    
                    O que deseja fazer?
                    """);
            }
        }
        else{
            if (x == 1) {
                txt = ("""
                    Depois de vagar pela fenda, os gritos e o cheiro de sangue parecem se dissipar por alguns instantes.
                    Entre ruínas quebradas, você encontra uma fogueira ainda acesa — brasas tímidas resistindo à névoa.
                    O calor frágil contrasta com o frio sufocante do abismo.
                    Aqui, por um momento, você pode descansar… refletir… ou se preparar para o que virá.
                    
                    O que deseja fazer?
                    """);
            } else if (x == 2) {
                txt = ("""
                    Você encontra abrigo entre as paredes quebradas de uma antiga construção.
                    As pedras frias e gastas guardam histórias de quem viveu aqui antes da queda.
                    O silêncio é tão profundo que até a própria fenda parece respeitar o lugar.
                    
                    O que deseja fazer?
                    """);
            } else {
                txt = ("""
                    Em meio à névoa vermelha, ergue-se um altar antigo, tomado por musgo e sangue seco.
                    Não se sabe a quem foi dedicado, mas há uma aura de proteção sutil emanando dele.
                    Talvez seja apenas uma ilusão… ou talvez, por um instante, os deuses ainda olhem para cá.
                    
                    O que deseja fazer?
                    """);
            }
        }

        return txt;
    }

    private static void InimigosMortosTextos(int queda, Player p) throws InterruptedException, IOException {

        String monstrosMortos = ("Monstros mortos===============\n" +
                "Total: " + p.inimigosMortos + "\n" +
                "Carniceiros mortos: " + p.carnMortos + "\n");

        String infoMonstros = ("""
                        Informações sobre os montros===============
                        
                        ----Carniceiros----
                        Criaturas disformes que rastejam para fora da fenda, uma das poucas a faze-lo, guiadas apenas pelo instinto de caçar humanos.
                        São considerados os mais fracos entre os horrores do abismo, mas também os mais numerosos — motivo pelo qual espalharam tanto terror.
                        Seu corpo é um amontoado grotesco de carnes cruas, como se pedaços de animais diferentes tivessem sido costurados à força.
                        Possuem sangue, mas não um sangue comum: em suas veias corre pura magia corrompida, responsável por manter suas formas instáveis e sua fome insaciável.
                        
                        """);


        if (queda == 1) {

            UtilForMe.TempoDeLeitura(monstrosMortos);
            UtilForMe.TempoDeLeitura(infoMonstros);


        } else if (queda == 2) {
            infoMonstros += ("""
                    ----Observadores----
                    No coração da Primeira Queda ergue-se o Portão para a Segunda. Uma construção viva, formada por ossos, raízes e carne pulsante, que respira lentamente
                    como se aguardasse. Ele não se abre para qualquer um. Para atravessá-lo, é necessário unir as essências dos Carniceiros. Essas criaturas grotescas,
                    verdadeiros monstros do andar, guardam em si fragmentos únicos de poder. Somente reunindo todas as almas dos Carniceiros será possível fazer o portão
                    reagir. Os Colhedores, demônios dedicados a recolher as almas mais fortes, vigiam o processo, levando as presas mais valiosas diretamente ao portão como oferendas.
                    
                    ----Arcanistas do vazio----
                    Restos de uma civilização que já não existe, os Arcanistas do Vazio foram outrora sábios e estudiosos. Hoje, seus corpos chamuscados e ressecados caminham apenas
                    pela magia que os consome. Usam capas negras e empunham cajados grotescos, conjurando feitiços sem beleza, feitos apenas de dor e poder. São lembranças distorcidas\s
                    de um povo que caiu há milênios.
                    
                    """);

            monstrosMortos += ("Magos mortos: " + p.magoMortos + "\n");

            UtilForMe.TempoDeLeitura(monstrosMortos);
            UtilForMe.TempoDeLeitura(infoMonstros);


        }
    }

    public static void SegundaQuedaEventos(Player p) throws IOException, InterruptedException, SQLException {
        UtilForMe.FakeClear(50,false); //verificado
        if(!emb){
            Collections.shuffle(notas);
            emb=true;
        }

        for (int evento = 6; evento < 11; evento++) {
            if (evento == 6 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(notas.get(0),2);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            } else if (evento == 7 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(notas.get(1),2);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            } else if (evento == 8 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(notas.get(2),2);
                UtilForMe.FakeClear(50,true); //verificado
                break;
            }
            else if (evento == 9 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(notas.get(3),2);
                UtilForMe.FakeClear(50, true); //verificado
                break;
            }

            else if (evento == 10 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(notas.get(4),2);
                UtilForMe.FakeClear(50, true); //verificado
                break;
            }
        }

    }

    public static void ObtencaoChaves(Player player, int chave) throws IOException, InterruptedException {

        if(chave==1){

            UtilForMe.TempoDeLeitura("""
                    A torre se silência, parece que todos os inimigos já se foram, porém ainda é possível sentir uma fagulha de mana
                    vinda do interior da torre. Ao caminhar em direção a ela, é possível ver diversas escrituras e algumas pinturas,
                    na parede....não pareciam runas, ou algum encantamento...os textos já consumidos pelo tempo, não podiam ser lidos
                    por completos.
                    
                    """);
            UtilForMe.TempoDeLeitura("""
                    Mas ao que parece eles eram a repetição de várias frases fragmentadas. Deseja ler?
                    
                    """);
            System.out.println("""
                     [ 1 ] Ler
                     [ 2 ] Seguir em frente
                     
                     """);
            int escolha = UtilForMe.ReadInt();
            while(escolha != 1 && escolha != 2){
                System.out.println("Digite um número válido");
                escolha = UtilForMe.ReadInt();
            }
            UtilForMe.FakeClear(50,false);
            if(escolha == 1){
                player.leituraParede +=1;
                UtilForMe.TempoDeLeitura("""
                        Existem 3 frases legíveis:
                        "O mundo está desabando, as torres ainda são seguras, eu acho, talvez por que foram protegidas pela magia de Arquilon...
                        por favor grande mestre, nós salve deste inferno..."
                        
                        """);
                UtilForMe.TempoDeLeitura("""
                        "...estou com medo, não quero morrer, eu juro que tentei....mãe, pai.....mas meu dom de nada serviu perante aqueles monstros,
                        não fui forte o suficiente....ó céus, tenha piedade, ó criador tenha misericórdia de sua criação...."
                        
                        """);
                UtilForMe.TempoDeLeitura("""
                        "falso deus, falso mundo, falsa esperança, falsa luta, que todos vão pro inferno.......bom, logo logo se concretizará"
                        
                        """);
                UtilForMe.FakeClear(50,true);
                UtilForMe.TempoDeLeitura("""
                    Saindo do corredor é possível ver, a pequena fagulha de mana, vinha de uma cápsula que guardava uma chave. Uma pequena proteção, que pode ser
                    fácilmente quebrada. Uma criptografia rúnica simples....você realiza o encantamento e descobre a palavra: "Socorro". [ Você obteve a 1° chave ]
                    """);
                UtilForMe.FakeClear(50,true);
                return;
            }

            UtilForMe.TempoDeLeitura("""
                    Saindo do corredor é possível ver, a pequena fagulha de mana, vinha de uma cápsula que guardava uma chave. Uma pequena proteção, que pode ser
                    fácilmente quebrada. Uma criptografia rúnica simples....você realiza o encantamento e descobre a palavra: "Socorro". [ Você obteve a 1° chave ]
                    """);
            UtilForMe.FakeClear(50,true);
            if(player.leituraParede == 3) Notas.notasEventos(11,2);

        }
        else if(chave==2){

            UtilForMe.TempoDeLeitura("""
                    O interior da torre Leste está escuro e silencioso.
                    O ar é frio.
                    No chão, há marcas de arrasto, móveis destruídos e mesas viradas.
                    Este lugar já foi usado para experimentos.
                    
                    """);

            UtilForMe.TempoDeLeitura("""
                    Em uma das paredes, há uma pintura grande, quase intacta.
                    Retrata uma planície viva, com árvores, rios e um céu claro.
                    A tinta está velha, mas o traço é firme.
                    Não parece arte comum. Parece lembrança.
                    
                    Deseja observar mais de perto?
                    
                    """);

            System.out.println("""
                     [ 1 ] Observar
                     [ 2 ] Seguir em frente
                     
                     """);

            int escolha2 = UtilForMe.ReadInt();
            while(escolha2 != 1 && escolha2 != 2){
                System.out.println("Digite um número válido");
                escolha2 = UtilForMe.ReadInt();
            }
            UtilForMe.FakeClear(50,false);

            if(escolha2 == 1){
                player.leituraParede +=1;
                UtilForMe.TempoDeLeitura("""
                        Ao tocar a parede, a pintura reage.
                        Pequenos brotos surgem no chão de pedra.
                        Rápido demais.
                        Em segundos, secam e viram pó.
    
                        """);

                UtilForMe.TempoDeLeitura("""
                        Em uma mesa próxima, um caderno aberto:
                        "Tentativa 37. A mana ainda não obedece ao ciclo natural."
                        """);

                UtilForMe.TempoDeLeitura("""
                        "A floresta surge… mas morre no mesmo instante.
                        O tempo aqui não colabora."
                        """);

                UtilForMe.TempoDeLeitura("""
                        "Se isso funcionasse, talvez houvesse saída."
                        """);

                UtilForMe.FakeClear(50,true);

                UtilForMe.TempoDeLeitura("""
                    No fundo da sala, uma cápsula metálica presa ao chão.
                    Dentro dela, uma chave envolta por mana instável.
                    A trava cede fácil.
                    
                    Uma palavra surge nas runas: "Ele mente".
                    
                    [ Você obteve a 3° chave ]
                    """);
                UtilForMe.FakeClear(50,true);
                return;
            }

            UtilForMe.TempoDeLeitura("""
                    No fundo da sala, uma cápsula metálica presa ao chão.
                    Dentro dela, uma chave envolta por mana instável.
                    A trava cede fácil.
                    
                    Uma palavra surge nas runas: "Ele mente".
                    
                    [ Você obteve a 3° chave ]
                    """);

            UtilForMe.FakeClear(50,true);

        }
        else{
            UtilForMe.TempoDeLeitura("""
                    A torre está intacta demais para um lugar como este.
                    O chão não afundou, as paredes ainda estão de pé.
                    Há mana espalhada em pequenos traços pelo corredor, levando até um salão fechado.
                    
                    """);

            UtilForMe.TempoDeLeitura("""
                    No salão, uma parede inteira foi usada como registro.
                    Não são runas, nem círculos mágicos.
                    São frases. Muitas frases.
                    Algumas riscadas com força, outras quase apagadas.
                    
                    Entre elas, um único poema permanece inteiro.
                    
                    Deseja ler?
                    
                    """);

            System.out.println("""
                     [ 1 ] Ler
                     [ 2 ] Seguir em frente
                     
                     """);

            int escolha = UtilForMe.ReadInt();
            while(escolha != 1 && escolha != 2){
                System.out.println("Digite um número válido");
                escolha = UtilForMe.ReadInt();
            }
            UtilForMe.FakeClear(50,false);

            if(escolha == 1){
                player.leituraParede +=1;
                UtilForMe.TempoDeLeitura("""
                        Eles chamavam de soberba.
                        Eu chamava de clareza.
                        
                        Nem todos nasceram para seguir.
                        Alguns enxergam antes.
                        Outros só chamam isso de arrogância
                        quando não conseguem alcançar.
                        
                        Eu não subi sobre os ombros de ninguém.
                        Eu apenas caminhei enquanto outros hesitavam.
                        
                        Diziam: “Tema.”
                        Eu respondia: “Compreenda.”
                        
                        """);

                UtilForMe.TempoDeLeitura("""
                        Diziam: “Espere.”
                        Eu dizia: “Agora.”
                        
                        Não é orgulho quando o mundo confirma.
                        Não é vaidade quando os resultados se repetem.
                        
                        Se a verdade machuca,
                        a culpa não é da lâmina.
                        
                        """);

                UtilForMe.TempoDeLeitura("""
                        Eu moldava a mana porque ela pedia forma.
                        Eu rompia limites porque eles eram frágeis.
                        
                        “Não é arrogância”, eu dizia em silêncio,
                        enquanto corrigia os céus com as próprias mãos,
                        “eu apenas sou o próximo passo.”
                        
                        E por muito tempo,
                        ninguém conseguiu provar o contrário.
                        
                        """);


                UtilForMe.FakeClear(50,true);

                UtilForMe.TempoDeLeitura("""
                    Ao sair do salão, a mana leva até um pedestal baixo.
                    Uma cápsula de vidro antigo protege uma chave.
                    A proteção é simples.
                    
                    Ao quebrar o selo rúnico, uma palavra se revela: "Salve-se".
                    
                    [ Você obteve a 2° chave ]
                    """);
                UtilForMe.FakeClear(50,true);
                return;
            }
            UtilForMe.TempoDeLeitura("""
                    Ao sair do salão, a mana leva até um pedestal baixo.
                    Uma cápsula de vidro antigo protege uma chave.
                    A proteção é simples.
                    
                    Ao quebrar o selo rúnico, uma palavra se revela: "Salve-se".
                    
                    [ Você obteve a 2° chave ]
                    """);
            UtilForMe.FakeClear(50,true);


        }
    }

}
