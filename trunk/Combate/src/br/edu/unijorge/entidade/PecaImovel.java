/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import java.awt.event.MouseEvent;

/**
 *
 * @author Glauber
 */
public class PecaImovel extends Peca {

    public PecaImovel(Exercito exercito, String titulo, int valor) {
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

    @Override
    public void mouseReleased(MouseEvent e) {
        return;
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
