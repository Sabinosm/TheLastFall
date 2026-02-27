package com.pedro;

public class RetornoMultiplo {
    public String string;
    public int inteiro;
    public boolean booleano;
    public char[][] matrizChar;

    public void StringInteiro(String string, int inteiro){
        this.string = string;
        this.inteiro = inteiro;
    }

    public void String2dArrayInteiro(char[][] matriz, int posicao){
        this.matrizChar = matriz;
        this.inteiro = posicao;
    }
}
