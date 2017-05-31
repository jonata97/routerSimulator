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

    TabelaRoteamento tabela; 
    Map<String,Long> ip_timeStamp = new HashMap<>();
    
    public void setIpAndTimeStamp(String ipSaida, long timeStamp) {
        if (ip_timeStamp.containsKey(ipSaida)) {
            ip_timeStamp.remove(ipSaida);
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
        } else {
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
        }
    }
    
    
    @Override
    public void run() {
        
        
        for (Map.Entry<String, Long> entry : ip_timeStamp.entrySet())
        {
            long now = System.nanoTime()/1000000000;
            if (now-entry.getValue() > 30) {
                tabela.remove_ip_tabela(entry.getKey());
            }
        }       
        
         
       
        
    }
    
}
