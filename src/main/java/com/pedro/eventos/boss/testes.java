package com.pedro.eventos.boss;

import com.pedro.RetornoMultiplo;
import com.pedro.UtilForMe;

import java.io.IOException;
import java.util.Random;

public class testes {
    public static void main(String[] args) throws IOException, InterruptedException{
                RetornoMultiplo retCacaP = CriarCacaPalavras("bobo");
        char[][] cacaP = retCacaP.matrizChar;
        testes x = new testes();
        if(x.RespostaCorretaCacaPalavras(cacaP, UtilForMe.ReadStr(),"bobo",retCacaP.inteiro)) System.out.println("YAY");
        else System.out.println("NO");

    }

    public static RetornoMultiplo CriarCacaPalavras(String palavra){
        // Converte a palavra em um array de caracteres
        palavra = palavra.toLowerCase();
        char[] palavraCerta = palavra.toCharArray();
        Random r = new Random();

        // Letras possíveis
        char[] letras = {
                'a','b','c','d','e','f','g','h','i','j','k',
                'l','m','n','o','p','q','r','s','t','u',
                'v','w','x','y','z'
        };

        // Cria o quadro
        char[][] quadro = new char[palavraCerta.length + 3][palavraCerta.length + 3];


        // Preenche o quadro com letras aleatórias
        for (int i = 0; i < quadro.length; i++) {
            for (int j = 0; j < quadro[i].length; j++) {
                quadro[i][j] = letras[r.nextInt(letras.length)];
            }
        }

        int posicao = AleatorizadorCacaPalavras(quadro,palavraCerta);

        char[][] quadroFinal = new char[quadro.length + 1][quadro[0].length + 2];


        for (int i = 0; i < quadroFinal.length; i++) {
            for (int j = 0; j < quadroFinal[i].length; j++) {

                // Canto superior esquerdo
                if (i == 0 && j == 0) {
                    quadroFinal[i][j] = ' ';
                }
                // Índices das linhas
                else if (j == 0 && i > 0) {
                    quadroFinal[i][j] = Integer.toString(i - 1).charAt(0);
                }
                // Espaços de alinhamento
                else if (j == 1 && i != 0) {
                    quadroFinal[i][j] = ' ';
                }
                // Índices das colunas
                else if (i == 0 && j > 1) {
                    quadroFinal[i][j] = Integer.toString(j - 2).charAt(0);
                }
                // Copia o quadro original
                else if (i > 0 && j > 1) {
                    quadroFinal[i][j] = quadro[i - 1][j - 2];
                }
                else {
                    quadroFinal[i][j] = ' ';
                }
                System.out.print(quadroFinal[i][j] + " ");
            }
            System.out.println();
        }

        RetornoMultiplo quadroFinalEPosicao = new RetornoMultiplo();
        quadroFinalEPosicao.String2dArrayInteiro(quadro,posicao);
        return quadroFinalEPosicao;

    }
    public boolean RespostaCorretaCacaPalavras(char[][] matriz, String input, String resposta, int posicao) throws IOException, InterruptedException {
        int porOndePercorrer;
        int intervalo1;
        int intervalo2;
        String palavra;
        String invertida;
        char[] respostaC = input.toCharArray();

        if(posicao == 2 || posicao == 4){
            char[][] matrizTransposta = new char[matriz[0].length][matriz.length];

            for(int i=0;i<matriz.length;i++){
                for(int j=0;j<matriz[0].length;j++){
                    matrizTransposta[j][i] = (matriz[i][j]);
                }
            }
            matriz = matrizTransposta;
        }

        try{

            if(posicao == 1 || posicao == 3){
                porOndePercorrer = Integer.parseInt(String.valueOf(respostaC[0]));
                intervalo1 = Integer.parseInt(String.valueOf(respostaC[2]));
                intervalo2 = Integer.parseInt(String.valueOf(respostaC[4]));
            }
            else{
                intervalo1 = Integer.parseInt(String.valueOf(respostaC[0]));
                intervalo2 = Integer.parseInt(String.valueOf(respostaC[2]));
                porOndePercorrer = Integer.parseInt(String.valueOf(respostaC[4]));

            }

            int inicio = Math.min(intervalo1, intervalo2);
            int fim = Math.max(intervalo1, intervalo2) + 1;

            palavra = new String(
                    java.util.Arrays.copyOfRange(
                            matriz[porOndePercorrer],
                            inicio,
                            fim
                    )
            );

            invertida = new StringBuilder(palavra)
                    .reverse()
                    .toString();


            System.out.println(palavra);

            return resposta.equalsIgnoreCase(palavra)
                    || resposta.equalsIgnoreCase(invertida);


        }catch (Exception e){
            UtilForMe.TempoDeLeitura("RESPOSTA EM FORMATO INCORRETO");
            UtilForMe.TempoDeLeitura(e.getMessage());
            UtilForMe.FakeClear(50,true);
            return false;
        }



    }
    private static int AleatorizadorCacaPalavras(char[][]matriz, char[] palavraCerta){
        // 1 -> horizontal
        // 2 -> vertical
        // 3 -> invertida horizontal
        // 4 -> invertida vertical

        Random r = new Random();

        int posicao = r.nextInt(4) + 1;

        if (posicao == 1) {
            int linha = r.nextInt(matriz.length);
            int coluna = r.nextInt(4);

            for (int index = 0; index < palavraCerta.length; index++) {
                matriz[linha][coluna + index] = palavraCerta[index];
            }
        }

        else if (posicao == 2) {
            int linha = r.nextInt(4);
            int coluna = r.nextInt(matriz[0].length);

            for (int index = 0; index < palavraCerta.length; index++) {
                matriz[linha + index][coluna] = palavraCerta[index];
            }
        }

        else if (posicao == 3) {
            int linha = r.nextInt(matriz.length);
            int coluna = r.nextInt(4);

            for (int index = 0; index < palavraCerta.length; index++) {
                matriz[linha][coluna + index] =
                        palavraCerta[palavraCerta.length - 1 - index];
            }
        }

        else {
            int linha = r.nextInt(4);
            int coluna = r.nextInt(matriz[0].length);

            for (int index = 0; index < palavraCerta.length; index++) {
                matriz[linha + index][coluna] =
                        palavraCerta[palavraCerta.length - 1 - index];
            }
        }

        return posicao;
    }

}
