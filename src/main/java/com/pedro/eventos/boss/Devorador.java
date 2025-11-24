package com.pedro.eventos.boss;

import com.pedro.UtilForMe;
import org.jline.reader.UserInterruptException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.pedro.UtilForMe.ReadStr;

public class Devorador {
    public int tentativas = 3;
    public String ActualcorrectAnswer;
    private long tempoInicio;
    private final int LIMITE = 30; // segundos
    private volatile boolean tempoEsgotado = false;


    public static String Intro() throws IOException, InterruptedException {
        return ("""
                Ao errar pela terceira vez, a torre não perdoa.
                Um estalo seco ecoa pelo chão arenoso, não um som, mas um veredito.
                A defesa final é acionada.
                
                A areia se agita.
                Racha.
                Cai para baixo como se o deserto respirasse pela primeira vez em séculos.
                
                E então ele se ergue. A apenas 30 segundos de distância.
                
                O Devorador de Mana.
                O Falso Deus.
                Um colosso anelar, espiralado como um verme antigo, cobrindo o sol, puxando a própria luz ao redor do corpo faminto.
                As runas vivas no interior de sua garganta brilham com uma fome que não negocia, apenas consome.
                
                Não há batalha.
                Não há fuga.
                Apenas uma verdade cruel:
                
                Diante dele, você fala a palavra certa… ou não fala mais nada.
                """);
    }

    private List<String> ChoosenWords(){
        Random r = new Random();

        String[] possibleWords = {
                "LAQUIRON","ALQURION","ALQUIORN","AALQUION","ALQUAIRON",
                "ALQUIROQ","QLAUIRON","ALQUIRNO","AIQUIRON","ALQVIRON",
                "ALGUIRON","ARQUIRON","ALQUNROI","ALQUIRAN","ELQUIRON",
                "ALUQIRON","ALQUISON","ALOUIRON","NLQUIROA", "ALQUYRON"
        };

        List<String> correctOne = new ArrayList<>(Arrays.asList("A", "L", "Q", "U", "I", "R", "O", "N"));

        Collections.shuffle(correctOne);

        String correctWord = String.join("", correctOne);

        String w1 = possibleWords[r.nextInt(possibleWords.length)];
        String w2 = possibleWords[r.nextInt(possibleWords.length)];

        List<String> group = Arrays.asList(w1, w2, correctWord);

        Collections.shuffle(group);
        return group;
    }

    public StringBuilder MontagemBatalha(){
      StringBuilder textoFinal = new StringBuilder(String.format("""
              ╔════════════════════════════╗
              ║          FALSE GOD         ║
              ║     O DEVORADOR DE MANA    ║
              ║       LV. ???   HP: ∞      ║
              ║   TEMPO RESTANTE: %s       ║
              ╚════════════════════════════╝
              
              
              𐌖  𐌈  𐌊   As areias se erguem.
              𐌊  𐌖  𐌈   O devorador observa.
              𐌈  𐌊  𐌖   Ele vem em sua direção.
              
              Escolha a palavra correta:
              
              """,TempoRestante()+""));
      List<String> group = ChoosenWords();
      UpdateCorrectAnswer(group);
      textoFinal.append(String.format("""
              [ 1 ] %s
              [ 2 ] %s
              [ 3 ] %s
              
              """, group.get(0), group.get(1), group.get(2)));
      textoFinal.append("Tentativas restantes: ");


      textoFinal.append("◆ ".repeat(Math.max(0, tentativas)));
      textoFinal.append("\n\n");

      return textoFinal;
    }

    private void UpdateCorrectAnswer(List<String> group){
        char[] original = "ALQUIRON".toCharArray();
        char[] opcao1 = group.get(0).toCharArray();
        char[] opcao2 = group.get(1).toCharArray();
        char[] opcao3 = group.get(2).toCharArray();

        Arrays.sort(original);
        Arrays.sort(opcao1);
        Arrays.sort(opcao2);
        Arrays.sort(opcao3);

        if(Arrays.equals(original, opcao1)) this.ActualcorrectAnswer= group.get(0);
        else if(Arrays.equals(original, opcao2)) this.ActualcorrectAnswer= group.get(1);
        else this.ActualcorrectAnswer= group.get(2);
    }

    private int TempoRestante() {
        long agora = System.currentTimeMillis();
        long passado = (agora - tempoInicio) / 1000;

        int restante = LIMITE - (int) passado;
        return Math.max(restante, 0);
    }

    public void iniciarTimer() {
        Thread t = new Thread(() -> {
            while (true) {
                if (TempoRestante() <= 0) {
                    tempoEsgotado = true;
                    break;
                }
                try {
                    Thread.sleep(200); // Atualiza a cada 0.2s
                } catch (InterruptedException ignored) {}
            }
        });

        t.setDaemon(true);
        t.start();
    }

    public boolean iniciarBatalha() throws IOException {
        tempoInicio = System.currentTimeMillis();
        tempoEsgotado = false;

        iniciarTimer(); // Liga o temporizador paralelo

        for (int i = 0; i < 3; i++) {

            if (tempoEsgotado) {
                return false;
            }

            System.out.println(MontagemBatalha());

            String resposta = ReadStr();

            if (tempoEsgotado) {
                return false;
            }

            // Verifica se acertou
            if (resposta.equalsIgnoreCase(ActualcorrectAnswer)) {
                System.out.println("\n" +
                        "✔ CERTO\n" +
                        "A palavra ilumina o deserto.\n" +
                        "A criatura perde forma e correntes aparecem do chão puxando o de volta.\n" +
                        "Você sobrevive.");
                return true;
            }

            tentativas--;
            System.out.println("\n✖ ERRADO\n" +
                    "A voz falha.  \n" +
                    "O devorador se aproxima.");

            if (tentativas == 0) {
                return false;
            }
        }

        return false;
    }

}
