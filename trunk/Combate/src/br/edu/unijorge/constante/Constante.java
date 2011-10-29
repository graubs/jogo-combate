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
    public static final String PATH_IMAGEM = "/br/edu/unijorge/imagens/";
    public static final String EXTENSAO_IMAGEM = ".png";
    public static final String IMAGEM_FUNDO_TABULEIRO = PATH_IMAGEM + "Terreno-Combate" + EXTENSAO_IMAGEM;
    public static final String IMAGEM_FUNDO_APRESENTACAO = PATH_IMAGEM + "Imagem-Main"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_SANGUE = PATH_IMAGEM + "Sangue"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_FUNDO_QUARTEL = PATH_IMAGEM + "Quartel-Combate"  + EXTENSAO_IMAGEM;
    public static final String IMAGEM_ICONE = PATH_IMAGEM + "combateIcon"  + EXTENSAO_IMAGEM;
    
    //Audios utilizados pela aplicação
    public static final String PATH_AUDIO = "/br/edu/unijorge/audio/";
    public static final String EXTENSAO_AUDIO = ".mp3";
    public static final String AUDIO_INICIO_BATALHA = PATH_AUDIO + "Som-InicioBatalha" + EXTENSAO_AUDIO;

}
