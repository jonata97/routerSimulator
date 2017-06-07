package roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Roteador {

    public static void main(String[] args) throws IOException {
        /* Lista de endereço IPs dos vizinhos */
        ArrayList<String> ip_list = new ArrayList<>();

        /* Le arquivo de entrada com lista de IPs dos roteadores vizinhos. */
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("IPVizinhos.txt"))) {
            String ip;
            
            while( (ip = inputFile.readLine()) != null){
                ip_list.add(ip);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Roteador.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        /* Cria instâncias da tabela de roteamento e das threads de envio e recebimento de mensagens. */ 
        /* Cria também semaphore para controle de envio de tabela */
        Semaphore sem = new Semaphore(1);
        TabelaRoteamento tabela = new TabelaRoteamento(sem);
        Thread sender = new Thread(new MessageReceiver(tabela));
        Thread receiver = new Thread(new MessageSender(tabela, ip_list, sem));
        Thread gerenciadorTabela = new Thread(new GerenciadorTabelaRoteamento(tabela));
       
        /* Começa as threads */ 
        gerenciadorTabela.start(); 
        sender.start();
        receiver.start();
        
    }
    
}
