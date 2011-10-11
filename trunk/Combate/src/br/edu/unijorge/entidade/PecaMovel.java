/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import br.edu.unijorge.form.MainForm;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;

/**
 *
 * @author Glauber
 */
public class PecaMovel extends Peca {

    public PecaMovel(Exercito exercito, String titulo, int valor, String id) {
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

    /**
     * Método acionado quando o usuário libera o mouse da peça. Se esta peça estiver
     * habilitada (isEnable == true), a peça define a posição(<code>Posicao</code>) dela como
     * posição selecionada do <code>Tabuleiro</code>.
     * @param e Evento do mouse
     * @see Posicao
     * @see Tabuleiro
     */
    @Override
    public void pecaMouseReleased(MouseEvent e) {

        if (isEnabled()) {
            Container parent;
            Component posicao;
            MainForm mf;

            posicao = (Component) getParent();

            if (null != posicao) {
                parent = (Container) posicao.getParent();
                if (null != parent) {
                    try {
                        ((Tabuleiro) parent).setPosSelec((Posicao) posicao);
                        mf = (MainForm)parent.getParent().getParent().getParent().getParent();
                        mf.getJlJogadorInfo().setText(this.toString());
                    } catch (ClassCastException ex) {
                        return;
                    }
                }
            }
        }
    }
}
