/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.constante;

/**
 *
 * @author Glauber
 */
public enum EntidadesMoveis {

    //Nome          Título              Nome abreviado  Valor   Qtd. casas por rodada   Qtd. máx. perm. por exército
    ESPIAO         ("Espiao",           "ESPI",          1,      1,                      1),
    SOLDADO        ("Soldado",          "SLDO",          2,      10,                     8),
    CABO_ARMEIRO   ("Cabo_Armeiro",     "CARM",          3,      1,                      5),
    SARGENTO       ("Sargento",         "SGTO",          4,      1,                      4),
    TENENTE        ("Tenente",          "TNTE",          5,      1,                      4),
    CAPITAO        ("Capitao",          "CAPT",          6,      1,                      4),
    MAJOR          ("Major",            "MAJR",          7,      1,                      3),
    CORONEL        ("Coronel",          "CONL",          8,      1,                      2),
    GENERAL        ("General",          "GENL",          9,      1,                      1),
    MARECHAL       ("Marechal",         "MACH",          10,     1,                      1);
    
    private String nome;
    private int valor;
    private String nomeAbreviado;
    private int maxPorExercito;
    private int maxCasasPorRodada;
    private String localImagem;
    
    private EntidadesMoveis(
            String nome,
            String nomeAbreviado,
            int valor,
            int maxCasasPorRodada,
            int maxPorExercito){
        this.nome = nome;
        this.nomeAbreviado = nomeAbreviado;
        this.valor = valor;
        this.maxCasasPorRodada = maxCasasPorRodada;
        this.maxPorExercito = maxPorExercito;
    }

    public String getNome() {
        return nome;
    }
    
    public String getNomeAbreviado(){
        return nomeAbreviado;
    }

    public int getValor() {
        return valor;
    }
    
    public int getMaxCasasPorRodada(){
        return maxCasasPorRodada;
    }

    public int getMaxPorExercito() {
        return maxPorExercito;
    }
}
