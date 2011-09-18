/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.constante;

/**
 *
 * @author Glauber
 */
public enum EntidadesImoveis {

    //Nome          Título          Nome abreviado    Valor   Qtd. máx. perm. por exército
    BANDEIRA        ("Bandeira",    "BDRA",          -1,      1),
    BOMBA           ("Bomba",       "BMBA",           0,      6);
    
    private EntidadesImoveis(String nome, String nomeAbreviado, int valor, int maxPorExercito){
        this.nome = nome;
        this.nomeAbreviado = nomeAbreviado;
        this.valor = valor;
        this.maxPorExercito = maxPorExercito;
    }
    
    private String nome;
    private String nomeAbreviado;
    private int valor;
    private int maxPorExercito;

    public String getNome() {
        return nome;
    }
    
    public String getNomeAbreviado(){
        return nomeAbreviado;
    }

    public int getValor() {
        return valor;
    }

    public int getMaxPorExercito() {
        return maxPorExercito;
    }
        
}
