//package com.pedro.eventos;
//
//import com.pedro.referenteAosPersonagens.Mage;
//import com.pedro.referenteAosPersonagens.Player;
//import com.pedro.UtilForMe;
//import com.pedro.referenteAosPersonagens.Demon;
//import com.pedro.referenteAosPersonagens.Enemy;
//import com.pedro.referenteAosPersonagens.Mob;
//import com.pedro.referenteAosPersonagens.Carniceiro;
//import org.jline.reader.LineReader;
//import org.jline.reader.LineReaderBuilder;
//import org.jline.terminal.Terminal;
//import org.jline.terminal.TerminalBuilder;
//
//import java.io.IOException;
//import java.util.*;
//
//public class Dungeon extends Battle {
//
//    static {
//        try {
//            terminal = TerminalBuilder.builder().system(true).build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    static Terminal terminal;
//    Set<Integer> carnDeCadaLevelMorto = new HashSet<>();
//    Set<Integer> magoDeCadaLevelMorto = new HashSet<>();
//    Set<Integer> demonioDeCadaLevelMorto = new HashSet<>();
//    List<Integer> verificacao = Arrays.asList(1,2,3,4,5);
//    public static int escolha = -1;
//    public static int escolha2 = -1;
//
//    public Dungeon(Enemy type, Player p) throws InterruptedException, IOException {
//        if (type instanceof Carniceiro) {
//            System.out.println("Você esta entrando em uma doungeon de zumbis, a dungeon se inicia\nO portal que estava aberto se fecha");
//            do{
//
//                Mob carn = new Carniceiro(p);
//                batalha(p, carn);
//                //Todo algum tipo de intervalo
//                //TODO restruturar
//
//                Mob outroCarn = new Carniceiro(p);
//                batalha(p, outroCarn);
//                descanso(p, 2);
//                for (Mob i : p.inimigosMortos) {
//                    if (i instanceof Carniceiro) {
//                      carnDeCadaLevelMorto.add(i.level);
//                    }
//                }
//                if(p.carnMortos > 10 && !carnDeCadaLevelMorto.containsAll(verificacao)){
//                    System.out.println("\nVocê matou todos os monstros desta dungeon, encontrando a chave para uma sala diferente, o que será que lhe espera?");
//                    break;
//                }
//            } while (!carnDeCadaLevelMorto.containsAll(verificacao));
//
//        }
//        else if (type instanceof Mage){
//            System.out.println("Você esta entrando em uma doungeon de magos, a dungeon se inicia\nO portal que estava aberto se fecha");
//            do{
//
//                Mob mage = new Mage(p);
//                batalha(p, mage);
//                //Todo algum tipo de intervalo
//
//                Mob outroMage = new Mage(p);
//                batalha(p, outroMage);
//                descanso(p, 2);
//                for (Mob i : p.inimigosMortos) {
//                    if (i instanceof Mage) {
//                        magoDeCadaLevelMorto.add(i.level);
//
//                    }
//                }
//                if(magoDeCadaLevelMorto.size() > 10 && !magoDeCadaLevelMorto.containsAll(verificacao)){
//                    System.out.println("\nVocê matou todos os monstros desta dungeon, encontrando a chave para uma sala diferente, o que será que lhe espera?");
//                    break;
//                }
//            } while (!magoDeCadaLevelMorto.containsAll(verificacao));
//
//        }
//        else if (type instanceof Demon){
//            System.out.println("Você esta entrando em uma doungeon de demônios, a dungeon se inicia\nO portal que estava aberto se fecha");
//            do{
//
//                Mob demon = new Demon(p);
//                batalha(p, demon);
//                //Todo algum tipo de intervalo
//
//                Mob outroDemon = new Demon(p);
//                batalha(p, outroDemon);
//                descanso(p, 2);
//                for (Mob i : p.inimigosMortos) {
//                    if (i instanceof Demon) {
//                        demonioDeCadaLevelMorto.add(i.level);
//
//                    }
//                }
//                if(demonioDeCadaLevelMorto.size() > 10 && !demonioDeCadaLevelMorto.containsAll(verificacao)){
//                    System.out.println("\nVocê matou todos os monstros desta dungeon, encontrando a chave para uma sala diferente, o que será que lhe espera?");
//                    break;
//                }
//            } while (!demonioDeCadaLevelMorto.containsAll(verificacao));
//
//        }
//
//    }
//
//
//    public static void descanso(Player p, int vez) throws InterruptedException, IOException {
//        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
//
//        UtilForMe.fakeClear(50);
//        if (vez == 1) {
//
//            System.out.print("================Um bar na entrada de algo==============\n" +
//                    "\nOlha só o que temos aqui, um visitante, faz tempo que eu não vejo um, vamos tome,\n" +
//                    ".....essa é por conta da casa ( poção estranha )\n" +
//                    "-Você bebe uma poção dada pelo Bartender do bar, ela restaurará sua vida perdida após as batalhas\n" +
//                    p.nome + " vida atual: " + (p.actLife = p.life)+
//                    "\nVolte sempre!.\n"+
//                    "\n================Saida do Bar==============");
//
//            Thread.sleep(7000);
//            UtilForMe.fakeClear(20);
//        }
//        else {
//            System.out.print("================Um bar estranho==============\n" +
//                    "Oi, você de novo? " + p.nome + " certo?\n");
//            escolha = 0;
//
//
//
//            while (escolha != 4) {
//
//                System.out.println(
//                        """
//
//                                O que procura?
//                                (1) Olhar os status e pontos de habilidades
//                                (2) Esperar mais um pouco e conversar com o bartender
//                                (3) Monstros mortos e detalhes
//                                (4) Sair do bar
//                                """
//
//                );
//                reader.getBuffer().clear();
//                escolha = Integer.parseInt(reader.readLine());
//                switch (escolha) {
//                    case 1:
//                        Player.menuHabilidades(p);
//                        break;
//                    case 2:
//                        System.out.println("""
//                                Anh? Ainda por aqui? O que deseja?
//                                (1) Como eu saio daqui?
//                                (2) Que lugar é esse?
//                                (3) Quem é você?
//                                """);
//                        reader.getBuffer().clear();
//                        escolha2 = Integer.parseInt(reader.readLine());
//                        conversaComBartender();
//                        break;
//                    case 3:
//                        System.out.println("Monstros mortos===============\n" +
//                                "Total: "+ p.inimigosMortos.size()+"\n" +
//                                "Carniceiros mortos: "+ p.carnMortos+"\n" +
//                                "Demônis mortos: "+p.demoniosMortos+"\n" +
//                                "Magos mortos: "+p.magoMortos);
//
//                        break;
//                    case 4:
//                        System.out.println("Entendi, então até mais\n\nVocê saiu do bar\n================Saida do Bar==============\n\n");
//                        break;
//                }
//            }
//        }
//
//    }
//
//    public static void conversaComBartender() throws InterruptedException {
//        switch (escolha2) {
//            case 1:
//                System.out.println("""
//                        Sair? ha, boa piada, não tem como sair, isso é um loop, depois que se entra, não há saída
//                        Mas...há rumores.....contados por aqueles que observaram....heróis
//                        assim como você. Você não é o primeiro, nem o escolhido, já passaram alguns como você
//                        talvez até com o mesmo nome. MAS enfim, aparentemente tem uma condição para você sair, ou ir para outro lugar
//                        cada monstro tem um certo nivel, eles melhoram, se evoluem, talvez se matar todos de cada evolução
//                        talvez ache alguma pista.
//                        """);
//                Thread.sleep(3500);
//                break;
//            case 2:
//                System.out.println("Isso é um bar, não é obvio?");
//                break;
//            case 3:
//                System.out.println("""
//                        Sou mais um que ficou preso, esse lugar é como um purgatorio, dizem que os herois veem para nós salvar,
//                        nós, eu digo, aqueles que também foram jogados aqui. Mas nenhum herói conseguiu ou foi forte o bastante para
//                        nós livrar desse pesadelo.
//                        """);
//                break;
//            default:
//                System.out.println("Anh?Não entendi o que disse, poderia repetir?");
//                break;
//
//        }
//
//    }
//
//}
//
