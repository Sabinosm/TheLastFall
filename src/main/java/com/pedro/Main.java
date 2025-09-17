package com.pedro;


import com.pedro.configuracoes.PlayerConfigurations;
import com.pedro.eventos.PrimeiraQueda;
import com.pedro.configuracoes.TelaInicial;

import com.pedro.historia.EventosSecundarios;
import com.pedro.historia.Notas;
import com.pedro.referenteAosPersonagens.Player;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main{
 public static void main(String[] args) throws InterruptedException, IOException, SQLException {
  Logger.getLogger("org.jline").setLevel(Level.OFF);
  PlayerConfigurations.carregarConfiguracoes();

  //TODO: Boss figth
  //TODO: order - history - boss figth - banco de dados
  //Todo: locais
  //FIXME texto passivas
  //todo: intro opcional
  

  Terminal terminal = TerminalBuilder.builder().system(true).build();
  LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
//  Notas.notasIntro(1);

  Player player = TelaInicial.escolhasTelaInicial();

  if(player.getCheckPoint().name().contains("NO_CHECK")){
    UtilForMe.tempoDeLeitura(Notas.notasPersonagens(player));
    UtilForMe.fakeClear(50,true); //verificado
  }


  if(player.getCheckPoint().name().contains("PRIMEIRA_QUEDA") || player.getCheckPoint().name().contains("NO_CHECK")){
   PrimeiraQueda.Start(player);
  }







 }


}
