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

    public PecaImovel(Exercito exercito, String titulo, int valor, String id) {
        super(exercito, titulo, valor, id);
    }

    /**
     * Método acionado quando o usuário clica na peça. Se esta peça estiver
     * desabilitada (isEnable == false), a peça chama o método mouseReleased
     * do seu pai, que é uma <code>Posicao</code>.
     * @param e Evento do mouse
     * @see Posicao
     */
    @Override
    public void pecaMouseClicked(MouseEvent e) {
        if (!isEnabled()) {
            ((Posicao) getParent()).mouseReleased(e);
        }
    }

    @Override
    public void pecaMouseReleased(MouseEvent evt) {
        return;
    }

    
}
