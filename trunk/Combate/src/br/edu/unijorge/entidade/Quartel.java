/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Glauber
 */
public class Quartel extends JPanel {

    public static final int
            LARGURA_PADRAO = 260,
            ALTURA_PADRAO = 650;
    
    ButtonGroup pecas;
    
    public Quartel(){
        this.pecas = new ButtonGroup();
        setBounds(0, 0, LARGURA_PADRAO, ALTURA_PADRAO);
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon(getClass().getResource(Constante.IMAGEM_FUNDO_QUARTEL)).getImage();
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    
    public void popularQuartel(ButtonGroup exercito) {
        this.pecas = exercito;
        Posicao pos;
        //Preenche o quartel com as posicoes (casas). Por definicao, e uma matriz de 10x4.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);
                pos.setPosicaoValida(true);
                pos.setOpaque(false);
                add(pos);
            }
        }

        int x = 0;
        Enumeration<AbstractButton> pcs = this.pecas.getElements();

        while (pcs.hasMoreElements()) {
            ((Posicao) getComponent(x)).add((Peca) pcs.nextElement());
            x++;
        }
    }
}
