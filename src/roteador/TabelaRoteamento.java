package roteador;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;





public class TabelaRoteamento {
    
    private ArrayList<EntradaTabelaRoteamento> tabelaRoteamento;
    Semaphore sem;
    
    
    public TabelaRoteamento(Semaphore s){
        tabelaRoteamento = new ArrayList<>();
        sem = s;
    }
    
    
    // Itera sobre a tabela e remove as rotas que tem como saída o ip que desconectou da rede
    public void remove_ip_tabela(String ipToRemove) {
        for (Iterator<EntradaTabelaRoteamento> it = this.tabelaRoteamento.iterator(); it.hasNext();) {
            EntradaTabelaRoteamento entrada = it.next();
            if (entrada.getIpSaida().equals(ipToRemove)) {
                it.remove();
            }
        } 
    }
    
    
    public void update_tabela(String tabela_s,  InetAddress IPAddress){
    
        // Caso a tabela recebida contenha ! 
        if (tabela_s.contains("!")) {
            
            // Itera sobre as entradas e verfica se o Ip que enviou já esta cadastrado na tabela     
            Boolean alreadyExists = false; 
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                      if(entrada.getIpDestino().equals(IPAddress.getHostAddress())) {
                          alreadyExists = true;
                      }
              }

            // Caso o ip não exista, adiciona o Ip de quem enviou como Ip de saída como destino e como IP de destino
            if (!alreadyExists) {
                EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(IPAddress.getHostAddress(), 1, IPAddress.getHostAddress());
                this.tabelaRoteamento.add(novaEntrada);
                sem.release();
            }
       
        } 
        
        // Caso a tabela contenha * 
        if (tabela_s.contains("*")) { 
            
            // Divide as linhas usando o marcador *
            String[] lines = tabela_s.split("\\*");  

            
            // Itera sobre as linhas 
            for (int i = 0; i<lines.length; i++) {
                
                // O if ignora linhas vazias 
                if (!lines[i].equals("")) {
                    
                        // Separa a linha em colunas utilizando o marcador ; 
                        String[] columns = lines[i].split(";");

                        // Pegando a métrica da linha da tabela correspondente e transforma em inteiro
                        char aux = columns[1].charAt(0);
                        int met = Integer.parseInt(String.valueOf(aux));

                        // Caso a tabela do roteador esteja vazia adiciona o primeiro da tabela para continuar a iteração
                        if (this.tabelaRoteamento.isEmpty()) {
                            
                            EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(columns[0], met, IPAddress.getHostAddress());
                            this.tabelaRoteamento.add(novaEntrada);
                            sem.release();
                        
                        // Caso não esteja vazio performa o seguinte : 
                        } else {

                                // Cria booleano para verificar alterações e nova entrada para adicionar caso seja necessário
                                boolean deveAdicionar = false; 
                                EntradaTabelaRoteamento novaEntrada = new EntradaTabelaRoteamento(columns[0], met, IPAddress.getHostAddress());
                                
                                // Itera sobre todas as entradas da tabela de roteamento atual
                                // Altera o booleano caso deva adicionar na tabela
                                for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {
                                    
                                      // Verifica se o IP destino recebido não é igual ao que existe na tabela 
                                      if(!(entrada.getIpDestino().equals(columns[0]))) {
                                          
                                          // Caso não exista soma a metrica da nova entrada em mais 1 e marcar o booleano para adicionar como true
                                          novaEntrada.addMetrica();
                                          deveAdicionar = true;
                                          
                                          // Caso seja igual verifica se as metricas são diferentes
                                      } else if (entrada.getMetrica() > novaEntrada.getMetrica()) {
                                          
                                          // Caso forem iguais e a metrica atual é a menor atualiza ip saida e metrica da tabela
                                          entrada.setIpSaida(novaEntrada.getIpSaida());
                                          novaEntrada.addMetrica();
                                          entrada.setMetrica(novaEntrada.getMetrica());
                                          sem.release();   
                                          
                                      }
                                }
                                
                                // Caso deva adicionar o faz aqui
                                if (deveAdicionar) {
                                    this.tabelaRoteamento.add(novaEntrada);
                                    sem.release();
                                }

                            }

                }   
            }
            
            
        
        }
        
      // Printa a tabela de roteamento
      System.out.println( "Tabela: " + this.get_tabela_string() );
        
        
    }
    
    // 
    public String get_tabela_string(){
        /* Tabela de roteamento vazia conforme especificado no protocolo */
        String tabela_string = "";
        
        /* Converta a tabela de rotamento para string, conforme formato definido no protocolo . */
        if (this.tabelaRoteamento.isEmpty()) {
            
            // Caso tabela de roteamento esteja vazia. Tabela String = "!"
            tabela_string = "!";
            
        } else {
            
            for (EntradaTabelaRoteamento entrada : this.tabelaRoteamento) {                
                   tabela_string = tabela_string + "*"+entrada.getIpDestino()+";"+entrada.getMetrica();      
            }
           
        }
        
     return tabela_string;
    }
    
    


    
}
