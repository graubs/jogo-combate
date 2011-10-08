/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import java.awt.Color;

/**
 *
 * @author Glauber
 */
public class Exercito {

    public static final String EXERCITO_VERMELHO = "Vermelho";
    public static final String EXERCITO_AZUL = "Azul";
    public static final int ID_EXERCITO_VERMELHO = 1;
    public static final int ID_EXERCITO_AZUL = 0;
    
    private long id;
    private String nome;

    public Exercito(String nome){
        this.nome = nome;
        this.id = (nome.equalsIgnoreCase(EXERCITO_AZUL) ? ID_EXERCITO_AZUL : ID_EXERCITO_VERMELHO);
    }
    
    public Color getColor(){
        return (this.nome.equals(EXERCITO_AZUL) ? Color.BLUE : Color.RED);
    }

    public String getCorExercito(){
        return (this.nome.equals(EXERCITO_AZUL) ? EXERCITO_AZUL : EXERCITO_VERMELHO);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Exercito)){
            return false;
        }
        
        return (((Exercito)obj).getNome().equals(this.nome) ? true : false);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        return hash;
    }
        
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
