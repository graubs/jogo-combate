/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;

/**
 *
 * @author Glauber
 */
public class PecaMovel extends Peca {

    public PecaMovel(Exercito exercito, String titulo, int valor) {
        super(exercito, titulo, valor);
    }

    /**
     * Método acionado quando o usuário clica na peça. Se esta peça estiver
     * desabilitada (isEnable == false), a peça chama o método mouseReleased
     * do seu pai, que é uma <code>Posicao</code>.
     * @param e Evento do mouse
     * @see Posicao
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
            ((Posicao) getParent()).mouseReleased(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }

    /**
     * Método acionado quando o usuário libera o mouse da peça. Se esta peça estiver
     * habilitada (isEnable == true), a peça define a posição(<code>Posicao</code>) dela como
     * posição selecionada do <code>Tabuleiro</code>.
     * @param e Evento do mouse
     * @see Posicao
     * @see Tabuleiro
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        if (isEnabled()) {
            Container tabuleiro;
            Component posicao;

            garantirUnicidadeSelecao();

            posicao = (Component) getParent();

            if (null != posicao) {
                tabuleiro = (Container) posicao.getParent();
                if (null != tabuleiro) {
                    try {
                        ((Tabuleiro) tabuleiro).setPosSelec((Posicao) posicao);
                    } catch (ClassCastException ex) {
                        return;
                    }
                }
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }
}
