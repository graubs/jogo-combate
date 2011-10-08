/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import br.edu.unijorge.constante.EntidadesImoveis;
import br.edu.unijorge.constante.EntidadesMoveis;
import br.edu.unijorge.constante.PosicoesInvalidas;
import br.edu.unijorge.exception.PecaException;
import br.edu.unijorge.util.UtilX;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Container;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Glauber
 */
public class Tabuleiro extends JLayeredPane {

    public static final int 
            LARGURA = 650,
            ALTURA = 650,
            LAYER_BACKGROUND = 0,
            LAYER_POSICAO = 400,
            LAYER_SLOT = 800,
            LARGURA_SLOT = 260,
            ALTURA_SLOT = 650;
    
    private static Tabuleiro tabuleiro;
    private Posicao posSelec;
    private ButtonGroup pecasTimeAzul;
    private ButtonGroup pecasTimeVerm;
    private Exercito exercitoAtual;
    private Exercito exercitoAnt;
    //private JLabel bg;
    private JPanel slotAzul;
    private JPanel slotVerm;

    private Tabuleiro() {
        setBounds(0, 0, ALTURA, LARGURA);
        //inicializarSlots();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = new ImageIcon(getClass().getResource(Constante.IMAGEM_FUNDO_TABULEIRO)).getImage();
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
    
    

    public static Tabuleiro getInstance() {
        if (null == tabuleiro) {
            tabuleiro = new Tabuleiro();
        }
        return tabuleiro;
    }

    private void construirPecas() {
        PecaMovel pecaMovel;
        //Constroi as pecas moveis
        for (EntidadesMoveis c : EntidadesMoveis.values()) {
            for (int i = 0; i < c.getMaxPorExercito(); i++) {
                //Exercito azul
                pecaMovel = new PecaMovel(
                        new Exercito(Exercito.EXERCITO_AZUL),
                        c.getNome(),
                        c.getValor(),
                        String.valueOf(i));
                pecasTimeAzul.add(pecaMovel);
                //Exercito vermelho
                pecaMovel = new PecaMovel(
                        new Exercito(Exercito.EXERCITO_VERMELHO),
                        c.getNome(),
                        c.getValor(),
                        String.valueOf(i));
                pecasTimeVerm.add(pecaMovel);
            }
        }

        PecaImovel pecaImovel;
        //Constroi as pecas imoveis
        for (EntidadesImoveis c : EntidadesImoveis.values()) {
            for (int i = 0; i < c.getMaxPorExercito(); i++) {
                //Exercito azul
                pecaImovel = new PecaImovel(
                        new Exercito(Exercito.EXERCITO_AZUL),
                        c.getNome(),
                        c.getValor(),
                        String.valueOf(i));
                pecasTimeAzul.add(pecaImovel);
                //Exercito vermelho
                pecaImovel = new PecaImovel(
                        new Exercito(Exercito.EXERCITO_VERMELHO),
                        c.getNome(),
                        c.getValor(),
                        String.valueOf(i));
                pecasTimeVerm.add(pecaImovel);
            }
        }
    }

    private void construirPosicoes() {
        //Limpa o tabuleiro
        removeFromLayer(LAYER_POSICAO);

        Posicao pos;
        int idx = 0;
        //Preenche o tabuleiro com as posicoes (casas). Por definicao, e uma matriz de 10x10.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);

                pos.setPosicaoValida(isPosicaoInvalida(idx++) ? false : true);
                pos.setOpaque(false);
                addInLayer(pos, LAYER_POSICAO);
            }
        }
    }

    public Posicao getPosicao(String nome) {
        Component[] posicoesTab = getComponentsInLayer(LAYER_POSICAO);

        for (int i = 0; i < posicoesTab.length; i++) {
            if (posicoesTab[i] instanceof Posicao) {
                if (((Posicao) posicoesTab[i]).getNome().equals(nome)) {
                    return (Posicao) posicoesTab[i];
                }
            }
        }
        return null;
    }

    public void moverPeca(PecaMovel peca, Posicao posDestino) throws PecaException {
        boolean isConfronto = false;
        boolean isFimJogo = false;

        if (validarPosicoesDeslocadas(peca, posDestino)) {
            if (isPosicaoInvalida(Integer.parseInt(posDestino.getNome())) || !isFaixaValida(peca, posDestino)) {
                throw new PecaException("A peça não pode se mover para a posição selecionada.");
            }

            if (posDestino.getComponents().length > 0) {
                if (((Peca) posDestino.getComponent(0)).getExercito().equals(peca.getExercito())) {
                    throw new PecaException("Duas peças do mesmo Exército não podem ocupar a mesma posição.");
                } else if (peca.getTitulo().equals(EntidadesMoveis.SOLDADO.getNome())
                        && getQtdCasasDeslocadas(peca, posDestino) > 1) {
                    throw new PecaException("Um soldado não pode se mover e atacar na mesma jogada.");
                } else {
                    isFimJogo = confrontar(peca, posDestino);
                    isConfronto = true;
                }
            }

            if (!isFimJogo) {
                if (!isConfronto) {
                    if (null != getPosicaoPeca(peca)) {
                        getPosicaoPeca(peca).removeAll();
                    }
                    posDestino.removeAll();
                    posDestino.add(peca);
                }
                alternarExercito();
                tabuleiro.repaint();
            } else {
                finalizarJogo();
            }
        } else {
            throw new PecaException("Quantidade de casas deslocadas excede a máxima permitida.");
        }
        pecasTimeAzul.clearSelection();
        pecasTimeVerm.clearSelection();
    }

    public void autoPosicionarPecas() {

        desordenarPecas();
        
        int x = 0;
        Enumeration<AbstractButton> pecasAzul = pecasTimeAzul.getElements();
        while(pecasAzul.hasMoreElements()){
            ((Posicao)getComponentsInLayer(LAYER_POSICAO)[x]).add((Peca)pecasAzul.nextElement());
            x++;
        }
        
        x = 99;
        Enumeration<AbstractButton> pecasVerm = pecasTimeVerm.getElements();
        while(pecasVerm.hasMoreElements()) {
            ((Posicao) getComponentsInLayer(LAYER_POSICAO)[x]).add(pecasVerm.nextElement());
            x--;
        }
        repaint();
        alternarExercito(true);
    }

    public ButtonGroup getPecasTimeAzul() {
        return pecasTimeAzul;
    }

    public ButtonGroup getPecasTimeVerm() {
        return pecasTimeVerm;
    }

    public Posicao getPosSelec() {
        return posSelec;
    }

    public void setPosSelec(Posicao posSelec) {
        this.posSelec = posSelec;
    }

    public Exercito getExercitoAnt() {
        return exercitoAnt;
    }

    public void setExercitoAnt(Exercito exercitoAnt) {
        this.exercitoAnt = exercitoAnt;
    }

    public Exercito getExercitoAtual() {
        return exercitoAtual;
    }

    public void setExercitoAtual(Exercito exercitoAtual) {
        this.exercitoAtual = exercitoAtual;
    }
    
    private void alternarExercito(){
        alternarExercito(false);
    }

    private void alternarExercito(boolean firstRun) {
        Exercito atual = this.exercitoAnt;
        Exercito anterior = this.exercitoAtual;
        this.exercitoAtual = atual;
        this.exercitoAnt = anterior;
        desabilitarExercito(this.exercitoAnt);
        if (!firstRun) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sua jogada terminou. Agora é a vez do seu adversário.",
                    "Jogada encerrada",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        habilitarExercito(this.exercitoAtual);
        repaint();
    }

    private boolean isPosicaoInvalida(int i) {
        for (PosicoesInvalidas p : PosicoesInvalidas.values()) {
            if (i == p.getValor()) {
                return true;
            }
        }
        return false;
    }

    private boolean validarPosicoesDeslocadas(PecaMovel peca, Posicao posDestino) {
        int qtdCasas = getQtdCasasDeslocadas(peca, posDestino);
        return (qtdCasas <= EntidadesMoveis.valueOf(UtilX.getTextoSemAcento(peca.getTitulo()).toUpperCase()).getMaxCasasPorRodada());
    }

    private Posicao getPosicaoPeca(Peca peca) {
        return (Posicao) ((Component) peca).getParent();
    }

    private boolean confrontar(PecaMovel peca, Posicao posDestino) {
        Peca pecaAtacada = (Peca) posDestino.getComponent(0);
        int valorPeca = peca.getValor();
        int valorPecaAtacada = pecaAtacada.getValor();

        if (valorPecaAtacada == EntidadesImoveis.BANDEIRA.getValor()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Parabéns! O exército " + peca.getExercito().getNome() + " venceu!",
                    "Fim de Jogo.",
                    JOptionPane.INFORMATION_MESSAGE);

            return true;
        } else if (valorPecaAtacada == EntidadesImoveis.BOMBA.getValor()) {
            if (valorPeca == EntidadesMoveis.CABO_ARMEIRO.getValor()) {
                posDestino.removeAll();
                ((Posicao) peca.getParent()).removeAll();
                posDestino.add(peca);
            } else {
                peca.getParent().removeAll();
            }
        } else if ((valorPecaAtacada == EntidadesMoveis.ESPIAO.getValor() && valorPeca == EntidadesMoveis.MARECHAL.getValor())
                || (valorPeca == EntidadesMoveis.ESPIAO.getValor() && valorPecaAtacada == EntidadesMoveis.MARECHAL.getValor())) {
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca == valorPecaAtacada || valorPeca > valorPecaAtacada) {
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca < valorPecaAtacada) {
            peca.getParent().removeAll();
        }

        if (!temPecasMoveis(pecaAtacada.getExercito())) {
            JOptionPane.showMessageDialog(
                    this,
                    "O Exército " + pecaAtacada.getExercito().getNome()
                    + " não possui movimentos disponíveis. O Exército "
                    + peca.getExercito().getNome() + " venceu.",
                    "Fim de jogo",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        return false;
    }

    private int getQtdCasasDeslocadas(PecaMovel peca, Posicao posDestino) {
        Posicao posOrigem = (Posicao) peca.getParent();
        String posInicial = posOrigem.getNome();
        String posFinal = posDestino.getNome();

        if (posInicial.substring(1, 2).equals(posFinal.substring(1, 2))) {
            return Math.abs(Integer.valueOf(posInicial.substring(0, 1)) - Integer.valueOf(posFinal.substring(0, 1)));
        } else {
            return Math.abs(Integer.valueOf(posInicial.substring(1, 2)) - Integer.valueOf(posFinal.substring(1, 2)));
        }
    }

    private void desabilitarExercito(Exercito exercito) {
        for (Object o : this.getComponentsInLayer(LAYER_POSICAO)) {
            Posicao posicao = (Posicao) o;
            if (posicao.getComponentCount() > 0) {
                Peca peca = (Peca) posicao.getComponent(0);
                if (peca.getExercito().equals(exercito)) {
                    peca.setEnabled(false);
                    peca.setFocusable(false);
                    peca.setText("");
                }
            }
        }
        repaint();
    }

    private void habilitarExercito(Exercito exercito) {
        for (Object o : this.getComponentsInLayer(LAYER_POSICAO)) {
            Posicao posicao = (Posicao) o;
            if (posicao.getComponentCount() > 0) {
                Peca peca = (Peca) posicao.getComponent(0);
                if (peca.getExercito().equals(exercito)) {
                    peca.setEnabled(true);
                    peca.setText(peca.getDescricao());
                }
            }
        }
        repaint();
    }

    private void finalizarJogo() {
        desabilitarExercito(this.exercitoAnt);
        desabilitarExercito(this.exercitoAtual);
        this.exercitoAnt = null;
        this.exercitoAtual = null;
    }

    public void iniciarJogo() {
        pecasTimeAzul = new ButtonGroup();
        pecasTimeVerm = new ButtonGroup();
        exercitoAnt = new Exercito(Exercito.EXERCITO_VERMELHO);
        exercitoAtual = new Exercito(Exercito.EXERCITO_AZUL);
        construirPosicoes();
        construirPecas();
        inicializarSlots();
    }

    private boolean isFaixaValida(PecaMovel peca, Posicao posDestino) {
        String posA = ((Posicao) peca.getParent()).getNome();
        String posB = posDestino.getNome();

        if (posA.substring(0, 1).equals(posB.substring(0, 1))) { //Deslocamento vertical
            return true;
        } else if (posA.substring(1, 2).equals(posB.substring(1, 2))) {//Deslocamento horizontal
            return true;
        } else if (Math.abs(Integer.valueOf(posA) - Integer.valueOf(posB)) % 11 == 0) { //Deslocamento diagonal (da esquerda pra direita)
            return true;
        } else if (Math.abs(Integer.valueOf(posA) - Integer.valueOf(posB)) % 9 == 0) { //Deslocamento diagonal (da direita pra esquerda)
            return true;
        } else {
            return false;
        }
    }

    public void removeFromLayer(int layer) {
        for (Component c : getComponentsInLayer(layer)) {
            remove(c);
        }
    }

    public boolean temPecasMoveis(Exercito exercito) {
        Component[] posicoes = tabuleiro.getComponentsInLayer(LAYER_POSICAO);
        for (Component c : posicoes) {
            Posicao pos = (Posicao) c;

            if (pos.getComponentCount() > 0) {
                Peca peca = (Peca) pos.getComponent(0);
                if (null != peca) {
                    if (peca.getExercito().equals(exercito) && (peca instanceof PecaMovel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void desordenarPecas(){
        //Exercito Azul
        List lPecasAzul = Collections.list(pecasTimeAzul.getElements());
        Collections.shuffle(lPecasAzul);
        
        pecasTimeAzul = new ButtonGroup();
        
        for(Object p : lPecasAzul){
            pecasTimeAzul.add((AbstractButton)p);
        }
        
        //Exercito Vermelho
        List lPecasVerm = Collections.list(pecasTimeVerm.getElements());
        Collections.shuffle(lPecasVerm);
        
        pecasTimeVerm = new ButtonGroup();
        
        for(Object p : lPecasVerm){
            pecasTimeVerm.add((AbstractButton)p);
        }
    }

    public JPanel getSlotAzul() {
        return slotAzul;
    }

    public void setSlotAzul(JPanel slotAzul) {
        this.slotAzul = slotAzul;
    }

    public JPanel getSlotVerm() {
        return slotVerm;
    }

    public void setSlotVerm(JPanel slotVerm) {
        this.slotVerm = slotVerm;
    }

    private void inicializarSlots(){
        slotAzul = new JPanel();
        slotAzul.setBounds(0, 0, LARGURA_SLOT, ALTURA_SLOT);
        slotAzul.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        slotVerm = new JPanel();
        slotVerm.setBounds(0, 0, LARGURA_SLOT, ALTURA_SLOT);
        slotVerm.setBorder(BorderFactory.createLineBorder(Color.RED));

        Posicao pos;
        //Preenche o slot com as posicoes (casas). Por definicao, e uma matriz de 10x4.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);
                pos.setPosicaoValida(true);
                pos.setOpaque(false);
                slotAzul.add(pos);
                
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);
                pos.setPosicaoValida(true);
                pos.setOpaque(false);
                slotVerm.add(pos);
            }
        }
        
        int x = 0;
        Enumeration<AbstractButton> pecasAzul = pecasTimeAzul.getElements();
        Enumeration<AbstractButton> pecasVerm = pecasTimeVerm.getElements();
        
        while(pecasAzul.hasMoreElements()){
            ((Posicao)slotAzul.getComponent(x)).add((Peca)pecasAzul.nextElement());
            ((Posicao)slotVerm.getComponent(x)).add((Peca)pecasVerm.nextElement());
            x++;
        }
    }

    public Component addInLayer(Component c, int layer) {
        this.add(c);
        this.setLayer(c, layer);
        return c;
    }
    
    public void addPecasSlot(Container slot, ButtonGroup listPecas){
        //Limpa o slot
        slot.removeAll();

        Posicao pos;
        //Preenche o slot com as posicoes (casas). Por definicao, e uma matriz de 10x4.
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                pos = new Posicao(i * Posicao.LARGURA_PADRAO, j * Posicao.ALTURA_PADRAO);
                pos.setOpaque(false);
                slot.add(pos);
            }
        }
        
        int x = 0;
        Enumeration<AbstractButton> pecas = listPecas.getElements();
        while(pecas.hasMoreElements()){
            ((Posicao)slot.getComponent(x)).add((Peca)pecas.nextElement());
            x++;
        }
        slot.getParent().repaint();
        
    }
}
