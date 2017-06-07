/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonata
 */
public class GerenciadorTabelaRoteamento implements Runnable {
    
    // Define Propriedades
    TabelaRoteamento tabela; 
    private static Map<String, Long> ip_timeStamp = new HashMap<String, Long>();
    
    
    // Recebe tabela para gerenciar 
    public GerenciadorTabelaRoteamento (TabelaRoteamento tabela) {
        this.tabela = tabela;
    }
    
    // Metodo para adicionar um TimeStamp quando receber informação. 
    public static void setIpAndTimeStamp(String ipSaida, long timeStamp) {
      
        // Caso contenha o IP de saída
        if (ip_timeStamp.containsKey(ipSaida)) {
            
            // Remove o antigo
            ip_timeStamp.remove(ipSaida);
            // Adiciona o novo
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
            
        } else {
            // Caso não tenha simplesmente adiciona o novo 
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
        }
    }
    
    
    @Override
    public void run() {
    while (true) {
        
        // Itera sobre as entradas do gerenciador 
        for (Map.Entry<String, Long> entry : ip_timeStamp.entrySet())
        {
            // Pega o tempo em segundos agora
            long now = System.nanoTime()/1000000000;
            
            // Verifica se diferença do tempo atual e do timestamp é > 30
            if (now-entry.getValue() > 30) {
                
                //Remove da tabela 
                tabela.remove_ip_tabela(entry.getKey());
                
            }
        }     
        
        try {
    
            // Aguarda 10 segundos
            Thread.sleep(10000);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(GerenciadorTabelaRoteamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
        
    }
    
}
