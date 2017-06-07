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
    private static Map<String, Long> ip_timeStamp = new HashMap<String, Long>();
    
    
    public GerenciadorTabelaRoteamento (TabelaRoteamento tabela) {
        this.tabela = tabela;
    }
    
    public static void setIpAndTimeStamp(String ipSaida, long timeStamp) {
        if (ip_timeStamp.containsKey(ipSaida)) {
            ip_timeStamp.remove(ipSaida);
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
            System.out.println("setting ip and time stamp");
        } else {
            ip_timeStamp.put(ipSaida, timeStamp/1000000000);
        }
    }
    
    
    @Override
    public void run() {
    while (true) {
        
      //  System.out.println("Size" +  ip_timeStamp.size());
        for (Map.Entry<String, Long> entry : ip_timeStamp.entrySet())
        {
            System.out.println("testando se entra no for");
            long now = System.nanoTime()/1000000000;
            if (now-entry.getValue() > 30) {
                tabela.remove_ip_tabela(entry.getKey());
                System.out.println("removing ip from table");
            }
        }     
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GerenciadorTabelaRoteamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
        
    }
    
}
