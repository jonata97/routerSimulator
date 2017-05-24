/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roteador;

/**
 *
 * @author jonata
 */
public class EntradaTabelaRoteamento {
    
    private String ipDestino;
    private int metrica;
    private String ipSaida;
    
    
    public EntradaTabelaRoteamento (String ipDestino, int metrica, String ipSaida) {
            this.ipDestino = ipDestino;
            this.ipSaida = ipSaida;
            this.metrica = metrica;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public int getMetrica() {
        return metrica;
    }
    
    public void addMetrica () {
        this.metrica =+ 1; 
    }

    public String getIpSaida() {
        return ipSaida;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public void setMetrica(int metrica) {
        this.metrica = metrica;
    }

    public void setIpSaida(String ipSaida) {
        this.ipSaida = ipSaida;
    }
    
    
}
