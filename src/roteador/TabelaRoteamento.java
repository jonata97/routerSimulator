package roteador;

import java.net.InetAddress;
import java.util.List;




public class TabelaRoteamento {
    /*Implemente uma estrutura de dados para manter a tabela de roteamento. 
     * A tabela deve possuir: IP Destino, Métrica e IP de Saída.
    */
    
    
    private List<EntradaTabelaRoteamento> tabelaRoteamento;
    
    
    public TabelaRoteamento(){
        
    }
    
    
    public void update_tabela(String tabela_s,  InetAddress IPAddress){
        /* Atualize a tabela de rotamento a partir da string recebida. */
       
        
        String[] lines = tabela_s.split("\\*");
        System.out.println(lines[0]);
     
        for (int i = 0; i<lines.length; i++) {
            
            String[] columns = lines[i].split(";");
         
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(columns[0], Integer.parseInt(columns[1]), IPAddress.getHostAddress());
                if(entrada.getIpDestino().equals(columns[0])) {
                   this.tabelaRoteamento.add(novaEntrada);
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
        
        
        return tabela_string;
    }
    
    
   // *192.168.1.2;1*192.168.1.3;1

    
}
