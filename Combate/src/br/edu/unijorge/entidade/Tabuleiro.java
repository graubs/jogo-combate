package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import br.edu.unijorge.constante.EntidadesImoveis;
import br.edu.unijorge.constante.EntidadesMoveis;
import br.edu.unijorge.constante.PosicoesInvalidas;
import br.edu.unijorge.exception.PecaException;
import br.edu.unijorge.form.MainForm;
import br.edu.unijorge.util.UtilX;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Container;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Glauber
 */
public class Tabuleiro extends JLayeredPane {

    public static final int LARGURA_PADRAO = 650,
            ALTURA_PADRAO = 650,
            LAYER_BACKGROUND = 0,
            LAYER_POSICAO = 400,
            LAYER_SLOT = 800;
    private Posicao posSelec;
    private ButtonGroup pecasTimeAzul;
    private ButtonGroup pecasTimeVerm;
    private Exercito exercitoAtual;
    private Exercito exercitoAnt;
    private boolean firstRun;

    public Tabuleiro() {
        this.firstRun = true;
        setBounds(0, 0, ALTURA_PADRAO, LARGURA_PADRAO);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img;
        if (firstRun) {
            img = new ImageIcon(getClass().getResource(Constante.IMAGEM_FUNDO_APRESENTACAO)).getImage();
        } else {
            img = new ImageIcon(getClass().getResource(Constante.IMAGEM_FUNDO_TABULEIRO)).getImage();
        }
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
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
                repaint();
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
        while (pecasAzul.hasMoreElements()) {
            ((Posicao) getComponentsInLayer(LAYER_POSICAO)[x]).add((Peca) pecasAzul.nextElement());
            x++;
        }

        x = 99;
        Enumeration<AbstractButton> pecasVerm = pecasTimeVerm.getElements();
        while (pecasVerm.hasMoreElements()) {
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

    public void setPecasTimeAzul(ButtonGroup pecas) {
        this.pecasTimeAzul = pecas;
    }

    public void setPecasTimeVerm(ButtonGroup pecas) {
        this.pecasTimeVerm = pecas;
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

    private void alternarExercito() {
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
        try {
            MainForm mf = (MainForm) getParent().getParent().getParent().getParent();
            mf.getJlJogadorInfo().setIcon(null);
            mf.getJlJogadorInfo().setText(null);
        } catch (ClassCastException ex) {
        } catch (NullPointerException ex2) {
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
            revelarJogo();
            if(JOptionPane.showConfirmDialog(this, "Parabéns! O exército " + peca.getExercito().getNome() + " venceu!", "Fim de Jogo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
              iniciarJogo();  
            }else{
                ((JFrame)this.getParent()).dispose();
            }
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Parabéns! O exército " + peca.getExercito().getNome() + " venceu!",
//                    "Fim de Jogo.",
//                    JOptionPane.INFORMATION_MESSAGE);

            return true;
        } else if (valorPecaAtacada == EntidadesImoveis.BOMBA.getValor()) {
            if (valorPeca == EntidadesMoveis.CABO_ARMEIRO.getValor()) {
                peca.tocarSom();
                revelarPeca(peca, pecaAtacada, 1);
                posDestino.removeAll();
                ((Posicao) peca.getParent()).removeAll();
                posDestino.add(peca);
            } else {
                pecaAtacada.tocarSom();
                revelarPeca(peca, pecaAtacada, 2);
                peca.getParent().removeAll();
            }
        } else if ((valorPeca == EntidadesMoveis.ESPIAO.getValor() && valorPecaAtacada == EntidadesMoveis.MARECHAL.getValor())) {
            peca.tocarSom();
            revelarPeca(peca, pecaAtacada, 1);
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca >= valorPecaAtacada) {
            peca.tocarSom();
            revelarPeca(peca, pecaAtacada, 1);
            posDestino.removeAll();
            ((Posicao) peca.getParent()).removeAll();
            posDestino.add(peca);
        } else if (valorPeca < valorPecaAtacada) {
            pecaAtacada.tocarSom();
            revelarPeca(peca, pecaAtacada, 2);
            peca.getParent().removeAll();
        }

        if (!temPecasMoveis(pecaAtacada.getExercito())) {
            revelarJogo();
            if(JOptionPane.showConfirmDialog(
                    this, "O Exército " + pecaAtacada.getExercito().getNome()
                    + " não possui movimentos disponíveis. O Exército "
                    + peca.getExercito().getNome() + " venceu.", 
                    "Fim de Jogo", 
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
              iniciarJogo();  
            }else{
                ((JFrame)this.getParent()).dispose();
            }
            
//            JOptionPane.showMessageDialog(
//                    this,
//                    "O Exército " + pecaAtacada.getExercito().getNome()
//                    + " não possui movimentos disponíveis. O Exército "
//                    + peca.getExercito().getNome() + " venceu.",
//                    "Fim de jogo",
//                    JOptionPane.INFORMATION_MESSAGE);
        }

        return false;
    }

    private void revelarPeca(Peca pecaOrigem, Peca pecaDestino, int vencedor){
        pecaDestino.setEnabled(true);
        pecaDestino.setOpaque(false);
        JOptionPane.showMessageDialog(
                this,
                (vencedor == 1 ? pecaOrigem.getTitulo() + " (" + pecaOrigem.getExercito().getNome() + ")" : pecaDestino.getTitulo() + " (" + pecaDestino.getExercito().getNome() + ")") + " venceu.",
                "",
                JOptionPane.WARNING_MESSAGE);
    }
    
    private void revelarJogo(){
        habilitarExercito(exercitoAnt);
        habilitarExercito(exercitoAtual);
        repaint();
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
                    peca.setOpaque(false);
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
        firstRun = false;
        pecasTimeAzul = new ButtonGroup();
        pecasTimeVerm = new ButtonGroup();
        exercitoAnt = new Exercito(Exercito.EXERCITO_VERMELHO);
        exercitoAtual = new Exercito(Exercito.EXERCITO_AZUL);
        construirPecas();
        construirPosicoes();
        autoPosicionarPecas();
        UtilX.tocarSom(Constante.AUDIO_INICIO_BATALHA);
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
        Component[] posicoes = getComponentsInLayer(LAYER_POSICAO);
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

    private void desordenarPecas() {
        //Exercito Azul
        List lPecasAzul = Collections.list(pecasTimeAzul.getElements());
        Collections.shuffle(lPecasAzul);

        pecasTimeAzul = new ButtonGroup();

        for (Object p : lPecasAzul) {
            pecasTimeAzul.add((AbstractButton) p);
        }

        //Exercito Vermelho
        List lPecasVerm = Collections.list(pecasTimeVerm.getElements());
        Collections.shuffle(lPecasVerm);

        pecasTimeVerm = new ButtonGroup();

        for (Object p : lPecasVerm) {
            pecasTimeVerm.add((AbstractButton) p);
        }
    }

    public Component addInLayer(Component c, int layer) {
        this.add(c);
        this.setLayer(c, layer);
        return c;
    }

    public void addPecasSlot(Container slot, ButtonGroup listPecas) {
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
        while (pecas.hasMoreElements()) {
            ((Posicao) slot.getComponent(x)).add((Peca) pecas.nextElement());
            x++;
        }
        slot.getParent().repaint();

    }

    private void aguardar(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tabuleiro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
