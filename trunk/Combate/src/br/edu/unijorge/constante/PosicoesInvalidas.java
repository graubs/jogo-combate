/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.constante;

/**
 *
 * @author Glauber
 */
public enum PosicoesInvalidas {

    POSICAO_01(42),
    POSICAO_02(43),
    POSICAO_03(46),
    POSICAO_04(47),
    POSICAO_05(52),
    POSICAO_06(53),
    POSICAO_07(56),
    POSICAO_08(57);
    
    private int valor;
    
    private PosicoesInvalidas(int valor){
        this.valor = valor;
    }
    
    public int getValor(){
        return this.valor;
    }
}
