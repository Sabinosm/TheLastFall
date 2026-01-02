package com.pedro.referenteAosPersonagens;

import com.pedro.UtilForMe;
import com.pedro.configuracoes.Checkpoint;
import com.pedro.configuracoes.PlayerConfigurations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.pedro.UtilForMe.ReadInt;

public class Player extends Mob {
    public double pLife = 50;
    public int pontosHabilidade = 50;
    public double pDamage;
    public double pArmor;
    public double pMagicArmor;
    public int inimigosMortos;
    public int carnMortos = 0;
    public int demoniosMortos = 0;
    public int magoMortos = 0;
    double xpParaProximoLevel = 0;
    public Set<String> carnDeCadaLevelMorto = new HashSet<>();
    public Set<String> magoDeCadaLevelMorto = new HashSet<>();
    public Set<String> demonDeCadaLevelMorto = new HashSet<>();
    public Set<String> notasLidas = new LinkedHashSet<>();
    public Set<String> chavesAdquiridas = new LinkedHashSet<>();
    public int leituraParede = 0;
    public Integer PlayerId;
    private Checkpoint checkpoint = Checkpoint.NO_CHECK;
    String lore;
    public boolean umPoucoDeSorteAconteceu = false;
    double xpAtual =0;

    String[] possiblePassive ={
                """
                Corpo divino-
                Abençoado pelo criador, herança do mártir , este que o possui, ganha 30% a mais de vida, e seus ataques tiram mais
                10% a mais de dano, porem têm 12% a menos
                de armadura e armadura mágica""",
                            """
                Guardião da ruína-
                Um sobrevivente que transformou sua carne em couraça.
                Este possui um aumento extremo de armadura,
                ganhando 30% a mais de armadura
                e 25% de armadura mágica, porém -8% de dano""",
                            """
                Berserker-
                Você se alimenta daquilo que todos temem. O sangue dos Carniceiros é veneno para uns, mas para você… é sustento.
                Com um estilo de luta extremamente poderoso e violento, à custo de sua vida
                ele ganha 40% de dano, -8% da vida e -10% de armadura mágica""",
                            """
                Espelho arcano-
                Seu poder é puro, limpo, sua mana é invejável, a pureza dela é tanta que assim como frequências, apenas ataques na sintonia correta poderão te acertar
                Bloqueia 15% do dano e reflete 8% do dano ao adversário -10% de armadura e -10% de armadura mágica e - 5% do dano.""",
                            """
                Um pouco de sorte-
                Um cara atrevido, mas sortudo, seu poder é inconsistente, poderia ser muito destrutivo, mas so em momentos aleatórios, a sorte é sua maior amiga
                E o azar seu maior inimigo. Perde 10% do dano em troca de 40% de chance de causar um ataque crítico, que dara 200% do dano atual."""

    };

    public Checkpoint getCheckPoint() {
        return checkpoint;
    }

    public void setCheckPoint(Checkpoint checkPoint) throws SQLException {
        this.checkpoint = checkPoint;
        PlayerConfigurations.SalvarPlayer(this);
    }

    public boolean PlayerMorto(){
        return this.actLife <= 0;
    }

    public boolean umPoucoDeSorte(Player x){
        Random r = new Random();
        int sorte = r.nextInt(1,11);
        if(x.passiva.contains("Um pouco de sorte") && sorte <= 3){
            resultadoSorte(1,x);
            return true;
        }
        else{
            resultadoSorte(0,x);
            return false;
        }
    }

    public boolean resultadoSorte(int aconteceu,Player p){
        if (aconteceu == 1){
            p.umPoucoDeSorteAconteceu = true;
            return true;
        }
        else if (aconteceu == 0){
            p.umPoucoDeSorteAconteceu = false;
            return false;
        }
        else{
            return p.umPoucoDeSorteAconteceu;
        }
    }

    public boolean espelhoArcano(Player x){
        return x.passiva.equals("Espelho arcano");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Player(int passiva, String tipoDano,int level) {

        this.level = level;

        if(tipoDano.equals("r")) {
            selectDamageType(this);
        }
        else{
            this.dmt = tipoDano;
        }


        if(passiva == -1)
        {
            this.passiva = possiblePassive[r.nextInt(possiblePassive.length)];
        }
        else{
            this.passiva = possiblePassive[passiva];
        }

        Passivas.atualizarAtributosSemBatalha(this);
        xpParaProximoLevel=(500*(this.level));
    }

    public void atualizarVidaAtual(Player p){
        p.actLife = p.life;
    }

    public void upLevel(double dropXp) throws InterruptedException {
        this.xpAtual += dropXp;
        if(this.xpAtual >= this.xpParaProximoLevel)
        {
            while(this.xpAtual >= this.xpParaProximoLevel) {

                if(level <= 10){
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    this.level+=1;
                    this.xpAtual -= this.xpParaProximoLevel;
                    this.xpParaProximoLevel = (700 * (this.level));
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    System.out.println("Parabéns, você upou de nível você ganhou 6 pontos de habilidade");

                    this.pontosHabilidade += 7;

                }
                else if(level <=20){
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    this.xpAtual -= this.xpParaProximoLevel;
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    System.out.println("Parabéns, você upou de nível você ganhou 8 pontos de habilidade");
                    this.level+=1;
                    this.pontosHabilidade += 8;
                    this.xpParaProximoLevel = (1200 * (this.level));
                }
                else{
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    this.xpAtual -= this.xpParaProximoLevel;
                    System.out.println(this.xpAtual + " / " + this.xpParaProximoLevel);
                    System.out.println("Parabéns, você upou de nível você ganhou 9 pontos de habilidade");
                    this.level+=1;
                    this.pontosHabilidade += 10;
                    this.xpParaProximoLevel = (2500 * (this.level));
                }



            }
            System.out.println("Novo nivel será disponível em: "+this.xpParaProximoLevel+"de xp");
            Thread.sleep(3000);
        }
        else{
            System.out.println(this.xpAtual+" / "+this.xpParaProximoLevel);
            System.out.println("Faltam: "+(this.xpParaProximoLevel-this.xpAtual)+" de xp para o próximo nível");
        }

    }

    public static void menuHabilidades(Player p) throws InterruptedException, IOException, SQLException {



        String choose = "-1";
        int pontosQ;
        while(!choose.trim().equals("6") ) {

            Passivas.atualizarAtributosSemBatalha(p);

            System.out.println("\n=========== PONTOS DE HABILIDADE ===========\n" +
                    "Pontos restantes: " + p.pontosHabilidade + "\n"+
                    "Level: " + p.level +"\n" +
                    "--------------------------------------------\n" +
                    "(1) Força        [+7 Dano]          ➤ Atual: " + p.damage + "\n" +
                    "(2) Vida         [+12 Vida]         ➤ Atual: " + p.life + "\n" +
                    "(3) Armadura     [+2 Armadura]      ➤ Atual: " + p.armor + "\n" +
                    "(4) Arm. Mágica  [+1 Arm. Mágica]   ➤ Atual: " + p.magicArmor + "\n" +
                    "(5) Resetar pontos                  ➤ Total Gasto: " + p.calcularPontosGastos() + "\n" +
                    "(6) Sair\n" +
                    "--------------------------------------------\n" +
                    "Passiva atual: " + p.passiva + "\n" +
                    "--------------------------------------------\n" +
                    "Tipo de dano: " + p.dmt + "\n" +
                    "============================================\n");

            System.out.print("Escolha a opção: ");
            
            choose = String.valueOf(ReadInt(null)).trim();

            if(choose.equals("5")){
                System.out.println("Seus pontos foram resetados com sucesso\n");
                p.pontosHabilidade += p.calcularPontosGastos();
                p.resetarStatus();
                UtilForMe.FakeClear(50,true);
            }

            if (p.pontosHabilidade > 0 && !choose.equals("6") &&  !choose.equals("5") )
            {
                pontosQ = 0;
                System.out.print("Escolha a quantidade de pontos: ");
                try{
                    
                    pontosQ = ReadInt(null);
                } catch (NumberFormatException e) {
                    choose = "7";
                }

                UtilForMe.FakeClear(50,false); //verificado
                if(pontosQ > p.pontosHabilidade){
                    System.out.println("Você não possui essa quantidade de pontos, nova quantidade selecionada é: "+ 0);
                    UtilForMe.FakeClear(50,true); //verificado
                    pontosQ = 0;
                }

                switch (choose) {

                    case "1":
                        System.out.println("Seus pontos foram adicionados com sucesso\n");
                        p.pDamage += (pontosQ * 7); p.pontosHabilidade -= pontosQ; break;
                    case "2":
                        System.out.println("Seus pontos foram adicionados com sucesso\n");
                        p.pLife += (pontosQ * 12); p.pontosHabilidade -= pontosQ; break;
                    case "3":
                        System.out.println("Seus pontos foram adicionados com sucesso\n");
                        p.pArmor += (pontosQ * 2); p.pontosHabilidade -= pontosQ; break;
                    case "4":
                        System.out.println("Seus pontos foram adicionados com sucesso\n");
                        p.pMagicArmor += (pontosQ); p.pontosHabilidade -= pontosQ; break;
                    default:
                        System.out.println("Seus pontos não foram adicionados. Insira um número válido\n");
                        UtilForMe.FakeClear(50,true); //verificado
                        break;

                }
            }
            else if(p.pontosHabilidade == 0 && !choose.equals("5"))
            {
                if (choose.equals("6")) {
                    Passivas.atualizarAtributosSemBatalha(p);
                    if(p.getPlayerId() != null) PlayerConfigurations.SalvarPlayer(p);

                }
                else {
                    System.out.println("Insira um número válido, você não possui mais pontos, evolua de nivel para conseguir mais\n");
                    UtilForMe.FakeClear(50, true); //verificado
                }
            }

        }
        UtilForMe.TempoDeLeitura("\n\n------Você escolheu sair-----");

    }

    public static void MorteJogador(Player p, Enemy mortoPor) throws IOException, InterruptedException {
        String maiorStr;
        if(p.actLife <= 0){
            UtilForMe.FakeClear(50,true); //verificado
            UtilForMe.TempoDeLeitura("""
                    Seu corpo cai.
                    O som da carne se desfazendo ecoa pelo Vazio.
                    Mas a morte não é o fim.
                    Aqui, ela é apenas mais uma corrente.
                    
                    Você sente sua alma se desprender, tentando fugir, tentando alcançar algo além…
                    Mas o Vazio não permite.
                    Ele a arrasta de volta, com garras invisíveis, jogando-a novamente contra o chão da Primeira Queda.
          
                    """);
            UtilForMe.TempoDeLeitura("""
                    No processo, parte do que você conquistou se desfaz.
                    As almas que havia consumido se dispersam, duas fogem pelas fendas,
                    e a mais forte delas — aquela que o definia — se parte em fragmentos, deixando-o vazio.
                    
                    Você se ergue novamente.
                    Mais fraco.
                    Mais vazio.
                    Mas condenado a continuar.
                    
                    XP atual -> 0
                    Alma mais forte se foi
                    -2 almas na contagem total de mortos e nas dos monstros específicos
                    
                    """);


            p.setXpAtual(0);
            if(mortoPor != null){
                switch (mortoPor) {
                    case Carniceiro carniceiro ->{

                        if(p.carnMortos >= p.carnMortos - 2) p.setCarnMortos(p.carnMortos - 2);
                        if(p.inimigosMortos >= p.inimigosMortos - 2) p.setInimigosMortos(p.inimigosMortos - 2);

                        boolean existeNulo = p.carnDeCadaLevelMorto.contains(null);

                        try{
                            if(!existeNulo){
                                maiorStr= Collections.max(p.carnDeCadaLevelMorto, Comparator.comparingInt(Integer::parseInt));
                            }else{
                                maiorStr = "999";
                            }
                        } catch (NumberFormatException e) {
                            maiorStr = "999";
                        }



                        if (p.carnDeCadaLevelMorto.contains(maiorStr)) {
                            p.carnDeCadaLevelMorto.remove(maiorStr);
                            System.out.println("Uma alma de nível " + maiorStr + " fugiu...\n");
                        } else {
                            System.out.println("Não havia nenhuma alma.\n");
                        }

                    }
                    case Mage mage -> {

                        if (p.magoMortos >= p.magoMortos - 2) p.setMagoMortos(p.magoMortos - 2);
                        if (p.inimigosMortos >= p.inimigosMortos - 2) p.setInimigosMortos(p.inimigosMortos - 2);

                        boolean existeNulo = p.magoDeCadaLevelMorto.contains(null);

                        try{
                           if(!existeNulo){
                               maiorStr= Collections.max(p.magoDeCadaLevelMorto, Comparator.comparingInt(Integer::parseInt));
                           }else{
                               maiorStr = "999";
                           }
                        } catch (NumberFormatException e) {
                            maiorStr = "999";
                        }

                        if (p.magoDeCadaLevelMorto.contains(maiorStr)) {
                            p.magoDeCadaLevelMorto.remove(maiorStr);
                            System.out.println("Uma alma de nível " + maiorStr + " fugiu...\n");
                        } else {
                            System.out.println("Não havia nenhuma alma correspondente.\n");
                        }

                    }
                    case Demon demon -> {

                        if (p.demoniosMortos >= p.demoniosMortos - 2) p.setCarnMortos(p.demoniosMortos - 2);
                        if (p.inimigosMortos >= p.inimigosMortos - 2) p.setInimigosMortos(p.inimigosMortos - 2);

                        boolean existeNulo = p.demonDeCadaLevelMorto.contains(null);

                        try{
                            if(!existeNulo){
                                maiorStr= Collections.max(p.demonDeCadaLevelMorto, Comparator.comparingInt(Integer::parseInt));
                            }else{
                                maiorStr = "999";
                            }
                        } catch (NumberFormatException e) {
                            maiorStr = "999";
                        }

                        if (p.demonDeCadaLevelMorto.contains(maiorStr)) {
                            p.demonDeCadaLevelMorto.remove(maiorStr);
                            System.out.println("Uma alma de nível " + maiorStr + " fugiu...\n");
                        } else {
                            System.out.println("Não havia nenhuma alma.\n");
                        }
                    }
                    default -> {
                    }
                }
            }


            System.out.println("Assim, tendo pagado o preço, você retorna ao campo de batalha. Não existe descanso no vazio.");


        }
        else{
            System.out.println("Erro");
        }
    }

    public void setCarnMortos(int carnMortos) {
        this.carnMortos = carnMortos;
    }

    public void setInimigosMortos(int inimigosMortos) {
        this.inimigosMortos = inimigosMortos;
    }

    public void setDemoniosMortos(int demoniosMortos) {
        this.demoniosMortos = demoniosMortos;
    }

    public void setMagoMortos(int magoMortos) {
        this.magoMortos = magoMortos;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public double getXpParaProximoLevel() {
        return xpParaProximoLevel;
    }

    public void setXpParaProximoLevel(double xpParaProximoLevel) {
        this.xpParaProximoLevel = xpParaProximoLevel;
    }

    public double getXpAtual() {
        return xpAtual;
    }

    public void setXpAtual(double xpAtual) {
        this.xpAtual = xpAtual;
    }

    public void setPontosHabilidade(int pontosHabilidade) {
        this.pontosHabilidade = pontosHabilidade;
    }

    public void setCarnDeCadaLevelMorto(Set<String> carnDeCadaLevelMorto) {
        this.carnDeCadaLevelMorto = carnDeCadaLevelMorto;
    }

    public void setMagoDeCadaLevelMorto(Set<String> magoDeCadaLevelMorto) {
        this.magoDeCadaLevelMorto = magoDeCadaLevelMorto;
    }

    public void setDemonDeCadaLevelMorto(Set<String> demonDeCadaLevelMorto) {
        this.demonDeCadaLevelMorto = demonDeCadaLevelMorto;
    }

    public void setNotasLidas(Set<String> notasLidas) {
        this.notasLidas = notasLidas;
    }

    public String getLore() {
        return lore;
    }

    public Integer getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(Integer playerId) {
        PlayerId = playerId;
    }

    private int calcularPontosGastos(){
        int level = this.level;
        int totalPontos = 50;

        if(level <= 10){
            if(level == 1){
                totalPontos -= 7;
            }
            totalPontos += level*7 - this.pontosHabilidade;
        }
        else if(level <=20){
            totalPontos += (10*7) + level * 8 - this.pontosHabilidade;
        }
        else{
            totalPontos += (10*7) + (10*8) + level*10 - this.pontosHabilidade;
        }

        return totalPontos;
    }

    private void resetarStatus(){
        this.pLife = 50;
        this.pMagicArmor = 0;
        this.pDamage = 0;
        this.pArmor = 0;
        Passivas.atualizarAtributosSemBatalha(this);

    }

    public void setChavesAdquiridas(Set<String> chavesAdquiridas) {
        this.chavesAdquiridas = chavesAdquiridas;
    }

    public void setLeituraParede(int leituraParede) {
        this.leituraParede = leituraParede;
    }

}

