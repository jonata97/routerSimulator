package roteador;

import java.net.InetAddress;
import java.util.List;




public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */
    
    
    private List<EntradaTabelaRoteamento> tabelaRoteamento;
    private GerenciadorTabelaRoteamento gerenciadorTabelaRoteamento;
    
    public TabelaRoteamento(){
        
    }
    
    
    public void remove_ip_tabela(String ipToRemove) {
        for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
            if (entrada.getIpSaida().equals(ipToRemove)) {
                tabelaRoteamento.remove(entrada);
            }
        }
    }
    
    
    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
       
        
        String[] lines = tabela_s.split("\\*");
        
        for (int i = 0; i<lines.length; i++) {
            
            
            String[] columns = lines[i].split(";");
         
            
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(columns[0], Integer.parseInt(columns[1]), IPAddress.getHostAddress());
                
                gerenciadorTabelaRoteamento.setIpAndTimeStamp(entrada.getIpSaida(),System.nanoTime());  
                
                if(entrada.getIpDestino().equals(columns[0])) {
                   novaEntrada.addMetrica();
                   this.tabelaRoteamento.add(novaEntrada);
                }
                
                if (entrada.getIpDestino().equals(columns[0]) && entrada.getMetrica() < novaEntrada.getMetrica()) {
                    entrada.setIpSaida(novaEntrada.getIpSaida());
                    novaEntrada.addMetrica();
                    entrada.setMetrica(novaEntrada.getMetrica());
                }
                
                
                
            }
            
            
            //(String ipDestino, int metrica, String ipSaida)
    
        }
        
        //quebrar a string tabela_s em ip destino e metrica.
        //o saida vai ser o ipAdddress do parametro 
        //salvar isso no entradaTabelaRoteamento
        
        
        System.out.println( IPAddress.getHostAddress() + ": " + tabela_s);
        
    }
    
    public String get_tabela_string(){
        String tabela_string = "!"; /* Tabela de roteamento vazia conforme especificado no protocolo */
        
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        
        if (!this.tabelaRoteamento.isEmpty()) {
            tabela_string = "";
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
        //        *192.168.1.2;1*192.168.1.3;1
                   tabela_string = tabela_string + "*"+entrada.getIpDestino()+";"+entrada.getMetrica();
           }
        }
        
        return tabela_string;
    }
    
    


    
}
