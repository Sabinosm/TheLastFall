package com.pedro.historia;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.eventos.Checkpoint;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Random;


public abstract class EventosSecundarios {
    public static Random r = new Random();
    public static Terminal terminal;

    static {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EventosSecundarios(Player p) {

    }

    public static void primeiraQuedaEventos(Player p) throws InterruptedException, IOException, SQLException {
        UtilForMe.fakeClear(50,false); //verificado

        for (int evento = 1; evento < 5; evento++) {
            if (evento == 1 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(1,1);
                UtilForMe.fakeClear(50,true); //verificado
                break;
            } else if (evento == 2 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(2,1);
                UtilForMe.fakeClear(50,true); //verificado
                break;
            } else if (evento == 3 && !p.notasLidas.contains(Integer.toString(evento))) {
                p.notasLidas.add(Integer.toString(evento));
                Notas.notasEventos(3,1);
                UtilForMe.fakeClear(50,true); //verificado
                break;
            }
         else if (evento == 4 && !p.notasLidas.contains(Integer.toString(evento))) {
            p.notasLidas.add(Integer.toString(evento));
            Notas.notasEventos(5,1);
            UtilForMe.fakeClear(50, true); //verificado
            break;
            }
        }
        EventosSecundarios.Descanso(p, 1);

    }

    public static void Descanso(Player p, int queda) throws InterruptedException, IOException, SQLException {

        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        int escolha = -1;

        String txtDescanso = getTextoDescanso();

        //checkPoint
        if(queda == 1){
            p.setCheckPoint(Checkpoint.PRIMEIRA_QUEDA_DESCANSO);
        }

        while (escolha != 5) {
            UtilForMe.fakeClear(50,false); //verificado
            UtilForMe.tempoDeLeitura(txtDescanso);
            System.out.println("""
                    (1) Olhar pontos de habilidade – ver e distribuir pontos acumulados.
                    (2) Ver dados sobre monstros – lembranças e anotações dos inimigos já enfrentados.
                    (3) Ler notas encontradas – pedaços de história e segredos coletados durante a jornada.
                    (4) Salvar progresso – salvar seu jogo para continuar mais tarde.
                    
                    (5) Seguir adiante – deixar a o local e prosseguir pela fenda.
                    """);
            reader.getBuffer().clear();
            escolha = Integer.parseInt(reader.readLine());

            switch (escolha) {
                case 1:
                    Player.menuHabilidades(p);
                    break;
                case 2:
                    UtilForMe.fakeClear(50,false); //verificado
                    inimigosMortosTextos(queda, p);
                    reader.getBuffer().clear();
                     reader.readLine();
                    break;
                case 3:
                    StringBuilder notaTexto = new StringBuilder();
                    var notasL = new java.util.ArrayList<>(p.notasLidas);
                    Collections.sort(notasL);
                    int notaASerLida;
                    for (int i = 0; i < notasL.size(); i++) {

                        notaTexto.append("( ")
                                .append(i+1)
                                .append(" )")
                                .append(" Reler nota ")
                                .append(notasL.get(i))
                                .append("\n");

                        if (i + 1 == notasL.size()) {
                            notaTexto.append("\n\n\n( ")
                                    .append(notasL.size() + 1)
                                    .append(" ) Para sair");
                        }

                    }

                    while (true) {

                        System.out.println(notaTexto);
                        try {
                            reader.getBuffer().clear();
                            escolha = Integer.parseInt(reader.readLine());

                            if (escolha == notasL.size() + 1) {
                                break; // sair do while
                            }

                            Notas.notasEventos(escolha,queda); //fixme não é o escolha
                            UtilForMe.fakeClear(50,true); //verificado
                        } catch (NumberFormatException e) {
                            System.out.println("Digite um número válido!");
                        }
                    }

                    break;
                case 4:
                    UtilForMe.tempoDeLeitura("Salvando...................");
                    Thread.sleep(1000);
                    UtilForMe.tempoDeLeitura("JOGO SALVO COM SUCESSO\n\n");
                    Thread.sleep(500);
                    PlayerConfigurations.salvarPlayer(p);
                    break;
                case 5:

                    break;
            }

        }


    }

    private static String getTextoDescanso() {
        int x = r.nextInt(1, 4);
        String txt;
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
        return txt;
    }

    private static void inimigosMortosTextos(int queda, Player p) throws InterruptedException, IOException {

        String monstrosMortos = ("Monstros mortos===============\n" +
                "Total: " + p.inimigosMortos + "\n" +
                "Carniceiros mortos: " + p.carnMortos + "\n");
        String infoMonstros = (
                "Informações sobre os montros===============\n" +
                        "Carniceiros----\n" +
                        "Criaturas disformes que rastejam para fora da fenda, uma das poucas a faze-lo, guiadas apenas pelo instinto de caçar humanos.\n" +
                        "São considerados os mais fracos entre os horrores do abismo, mas também os mais numerosos — motivo pelo qual espalharam tanto terror.\n" +
                        "Seu corpo é um amontoado grotesco de carnes cruas, como se pedaços de animais diferentes tivessem sido costurados à força.\n" +
                        "Possuem sangue, mas não um sangue comum: em suas veias corre pura magia corrompida, responsável por manter suas formas instáveis e sua fome insaciável.\n");
        String sair = "( ENTER ) para sair";

        if (queda == 1) {

            UtilForMe.tempoDeLeitura(monstrosMortos);
            UtilForMe.tempoDeLeitura(infoMonstros);
            System.out.println(sair);


        } else if (queda == 2) {
            infoMonstros += ("Observadores----\n" +
                    "No coração da Primeira Queda ergue-se o Portão para a Segunda. Uma construção viva, formada por ossos, raízes e carne pulsante, que respira lentamente\n" +
                    "como se aguardasse. Ele não se abre para qualquer um. Para atravessá-lo, é necessário unir as essências dos Carniceiros. Essas criaturas grotescas,\n" +
                    "verdadeiros monstros do andar, guardam em si fragmentos únicos de poder. Somente reunindo todas as almas dos Carniceiros será possível fazer o portão\n" +
                    "reagir. Os Colhedores, demônios dedicados a recolher as almas mais fortes, vigiam o processo, levando as presas mais valiosas diretamente ao portão como oferendas.\n" +
                    "Arcanistas do vazio----\n" +
                    "Restos de uma civilização que já não existe, os Arcanistas do Vazio foram outrora sábios e estudiosos. Hoje, seus corpos chamuscados e ressecados caminham apenas\n" +
                    "pela magia que os consome. Usam capas negras e empunham cajados grotescos, conjurando feitiços sem beleza, feitos apenas de dor e poder. São lembranças distorcidas \n" +
                    "de um povo que caiu há milênios.\n");

            monstrosMortos += ("Magos mortos: " + p.magoMortos + "\n");

            UtilForMe.tempoDeLeitura(monstrosMortos);
            UtilForMe.tempoDeLeitura(infoMonstros);
            System.out.println(sair);


        }
    }

}
