/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import br.edu.unijorge.exception.PecaException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Glauber
 */
public class Posicao extends JPanel implements MouseListener {

    public static final int LARGURA_PADRAO = 65;
    public static final int ALTURA_PADRAO = 65;
    public static final int MAX_POSICOES = 100;
    public static final int MAX_POSICOES_INVALIDAS = 8;
    private boolean posicaoValida;

    public Posicao(int x, int y) {
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        setBounds(x, y, LARGURA_PADRAO, ALTURA_PADRAO);
        setBorder(LineBorder.createBlackLineBorder());
        setLayout(new GridLayout());
        setOpaque(false);
        addMouseListener(this);
    }

    @Override
    public Component add(Component comp) {
        Component result;
        result = super.add(comp);
        comp.setLocation(0, 0);
        repaint();
        return result;

    }

    public String getNome() {
        return String.valueOf(getX() / LARGURA_PADRAO) + String.valueOf(getY() / ALTURA_PADRAO);
    }

    public boolean isPosicaoValida() {
        return posicaoValida;
    }

    public void setPosicaoValida(boolean posicaoValida) {
        this.posicaoValida = posicaoValida;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Container parent = ((Component) this).getParent();
        if (null != parent) {
            try {
                PecaMovel peca = (PecaMovel) ((Tabuleiro) parent).getPosSelec().getComponent(0);
                ((Tabuleiro) parent).moverPeca(peca, this);
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(parent, "Não é possível mover essa peça.");
            } catch (NullPointerException ne) {
            } catch (ArrayIndexOutOfBoundsException aex) {
            } catch (PecaException pe) {
                JOptionPane.showMessageDialog(parent, pe.getMessage());
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
