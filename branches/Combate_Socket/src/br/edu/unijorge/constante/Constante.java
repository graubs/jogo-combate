/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.constante;

import java.awt.Font;

/**
 *
 * @author Glauber
 */
public abstract class Constante {
    
    public static final String FONTE_TIPO_PADRAO = "Arial";
    public static final int FONTE_TAMANHO_PADRAO = 10;
    public static final int FONTE_ESTILO_PADRAO = Font.BOLD;
    public static final int MAX_PECAS_EXERCITO = 40;
    public static final int LARGURA_DIALOG = 500;
    public static final int ALTURA_DIALOG = 400;

    //Imagens utilizadas pela aplicação
    public static final String PATH_IMAGENS = "/br/edu/unijorge/imagens/";
    public static final String EXTENSAO_IMAGEM = ".png";
    public static final String IMAGEM_FUNDO_TABULEIRO = PATH_IMAGENS + "Terreno-Combate" + EXTENSAO_IMAGEM;
    public static final String IMAGEM_FUNDO_APRESENTACAO = PATH_IMAGENS + "Imagem-Main"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_SANGUE = PATH_IMAGENS + "Sangue"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_FUNDO_QUARTEL = PATH_IMAGENS + "Quartel-Combate"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_ICONE = PATH_IMAGENS + "combateIcon"  + EXTENSAO_IMAGEM;

}