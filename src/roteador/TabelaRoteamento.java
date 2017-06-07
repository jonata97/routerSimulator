package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;





public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */
    
    
    private ArrayList<EntradaTabelaRoteamento> tabelaRoteamento;
    Semaphore sem;
    
    
    public TabelaRoteamento(Semaphore s){
        tabelaRoteamento = new ArrayList<>();
        sem = s;
    }
    
    
//    for (Iterator<DrugStrength> it = aDrugStrengthList.iterator(); it.hasNext(); ) {
//    DrugStrength aDrugStrength = it.next();
//    if (!aDrugStrength.isValidDrugDescription()) {
//        it.remove();
//    }
//}
    
    
//            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
//            if (entrada.getIpSaida().equals(ipToRemove)) {
//                tabelaRoteamento.remove(entrada);
//            }
//        }
//    
    public void remove_ip_tabela(String ipToRemove) {

        for (Iterator<EntradaTabelaRoteamento> it = this.tabelaRoteamento.iterator(); it.hasNext();) {
            EntradaTabelaRoteamento entrada = it.next();
            if (entrada.getIpSaida().equals(ipToRemove)) {
                it.remove();
            }
        }
        
    }
    
    
    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
        Boolean alreadyExists = false; 
        
        if (tabela_s.contains("!")) {
        for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                  if(entrada.getIpDestino() == IPAddress.getHostAddress()) {
                      alreadyExists = true;
                  }
          }
        
          if (!alreadyExists) {
            EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(IPAddress.getHostAddress(), 1, IPAddress.getHostAddress());
            this.tabelaRoteamento.add(novaEntrada);
            sem.release();
          }
          
        
        } 
        
        if (tabela_s.contains("*")) { 
//        System.out.println("Tabela recebida contém *");
        String[] lines = tabela_s.split("\\*");  
        
             
//            System.out.println("Count de linhas: " + lines.length);
//            System.out.println("linha 0: " + lines[0]);
//            System.out.println("linha 1: " + lines[1]);
        
        
        for (int i = 0; i<lines.length; i++) {
            
            if (!lines[i].equals("")) {
            String[] columns = lines[i].split(";");
            
//            System.out.println("Count de colunas: " + columns.length);
//            System.out.println("Coluna 0: " + columns[0]);
//            System.out.println("Coluna 1: " + columns[1]);
            
        
            
            if (this.tabelaRoteamento.isEmpty()) {
            EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(IPAddress.getHostAddress(), 1, IPAddress.getHostAddress());
            this.tabelaRoteamento.add(novaEntrada);
            sem.release();
          //  System.out.println("Entrando 1");
            }
            
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                
                char aux = columns[1].charAt(0);
                int met = Integer.parseInt(String.valueOf(aux));
      
                
                        
                EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(columns[0], met, IPAddress.getHostAddress());
             //   System.out.println("Ip destino nova entrada: " + novaEntrada.getIpDestino());
              
                
                if(!(entrada.getIpDestino().equals(columns[0]))) {
                   novaEntrada.addMetrica();
                   this.tabelaRoteamento.add(novaEntrada);
                   sem.release();
              //     System.out.println("Entrando 2");
                   
                } else if (entrada.getMetrica() < novaEntrada.getMetrica()) {
                    entrada.setIpSaida(novaEntrada.getIpSaida());
                    novaEntrada.addMetrica();
                    entrada.setMetrica(novaEntrada.getMetrica());
                  //  System.out.println("Entrando 3");
                }
                
                
                
            }
            
    
            }
        }
        
        }
        

        
        
      System.out.println( "Tabela: " + this.get_tabela_string() );
        
        
    }
    
    public String get_tabela_string(){
        String tabela_string = "!"; /* Tabela de roteamento vazia conforme especificado no protocolo */
        
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        
        if (this.tabelaRoteamento.isEmpty()) {
           // System.out.println("Get: Retornando tabela vazia");
            return "!";
            
        } else {
             //System.out.println("Get: Retornando tabela com algo");
            tabela_string = "";
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
        //        *192.168.1.2;1*192.168.1.3;1
                   tabela_string = tabela_string + "*"+entrada.getIpDestino()+";"+entrada.getMetrica();
                  
           }
            
           // System.out.println(tabela_string);
            return tabela_string;
        }
        
     
    }
    
    


    
}
