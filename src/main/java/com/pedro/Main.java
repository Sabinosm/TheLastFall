package com.pedro;


import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.eventos.quedas.PrimeiraQueda;
import com.pedro.configuracoes.TelaInicial;

import com.pedro.eventos.quedas.SegundaQueda;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main{
 public static void main(String[] args) throws InterruptedException, IOException, SQLException {
  Logger.getLogger("org.jline").setLevel(Level.OFF);
  PlayerConfigurations.CarregarConfiguracoes();

  //TODO: order - history - boss figth - banco de dados
  //Todo: locais
  

  Notas.notasIntroPrimeiraTorre(1);

  Player player = TelaInicial.EscolhasTelaInicial();

  if(player.getCheckPoint().name().contains("NO_CHECK")){
    UtilForMe.TempoDeLeitura(Notas.notasPersonagens(player));
    UtilForMe.FakeClear(50,true); //verificado
  }


  if(player.getCheckPoint().name().contains("PRIMEIRA_QUEDA") || player.getCheckPoint().name().contains("NO_CHECK")){
   PrimeiraQueda.Start(player);
  }

  if(player.getCheckPoint().name().contains("SEGUNDA_QUEDA")){
   SegundaQueda.Start(player);
  }








 }


}
