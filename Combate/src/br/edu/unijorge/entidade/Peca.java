package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

/**
 * Abstrai a peça (peão) do jogo. Possui características como Exército, Título e Valor.
 *
 * @author Glauber
 */
public abstract class Peca extends JToggleButton implements MouseListener {

    public static final int LARGURA = 63, //Largura padrão do componente
            ALTURA = 63; //Altura padrão do componente
    private Exercito exercito;
    private int valor;
    private String titulo;
    //private boolean enabled;

    //Construtor da classe
    public Peca(Exercito exercito, String titulo, int valor) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.exercito = exercito;
        this.titulo = titulo;
        this.valor = valor;
        //this.enabled = true;
        //setText(getDescricao());
        //setFont(new Font(Constante.FONTE_TIPO_PADRAO, Constante.FONTE_ESTILO_PADRAO, Constante.FONTE_TAMANHO_PADRAO));
        //setBackground(exercito.getColor());
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBorder(new BevelBorder(BevelBorder.RAISED));
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isEnabled()) {
            super.paintComponent(g);

            try {
                Image i = (new ImageIcon(getClass().getResource(
                        Constante.PATH_IMAGENS
                        + getExercito().getCorExercito() + "-"
                        + getTitulo() + Constante.EXTENSAO_IMAGEM))).getImage();

                g.drawImage(i, 0, 0, this.getWidth(), this.getHeight(), this);
            }catch(NullPointerException ex){
                System.out.println(Constante.PATH_IMAGENS
                        + getExercito().getCorExercito() + "-"
                        + getTitulo() + Constante.EXTENSAO_IMAGEM);
                ex.printStackTrace();
            }

        } else {
            super.paintComponent(g);
            setOpaque(true);
        }
    }

    /**
     * Compara uma peca a outra (recebida no argumento). Para fazer isso, compara se o
     * exército, título e valor são iguais.
     * @param obj Object que será comparado com a instância dessa classe
     * @return <code>true</code> se as peças forem iguais, <code>false</code> caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Peca)) {
            return false;
        }

        Peca peca = (Peca) obj;

        if (this.exercito.equals(peca.getExercito())
                && this.titulo.equals(peca.getTitulo())
                && this.valor == peca.getValor()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retorna uma coleção de <code>Peca</code> que fazem parte do mesmo exército
     * dessa peça.
     * @return Uma <code>List<Peca></code>
     */
    private List<Peca> getTime() {
        Component[] todasAsPecas = ((JLayeredPane) ((Component) this.getParent()).getParent()).getComponentsInLayer(Tabuleiro.LAYER_POSICAO);
        List<Peca> pecasTime = new ArrayList<Peca>();

        for (int i = 0; i < todasAsPecas.length; i++) {
            Posicao pos = (Posicao) todasAsPecas[i];
            if (pos.getComponentCount() > 0) {
                Peca pca = (Peca) pos.getComponent(0);
                if (pca.getExercito().equals(exercito)) {
                    pecasTime.add(pca);
                }
            }
        }

        return pecasTime;
    }

    /**
     * Método acionado no evento mouseReleased da Subclasse <code>MouseListenerImpl</code>.
     * Garante que somente uma peça (Classe que extende JToggleButton) será selecionada.
     * @see MouseListenerImpl
     */
    protected void garantirUnicidadeSelecao() {
        List<Peca> pecas = getTime();

        for (Peca p : pecas) {
            if (p.equals(this)) {
                ((JToggleButton) p).setSelected(true);
            } else {
                ((JToggleButton) p).setSelected(false);
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.exercito != null ? this.exercito.hashCode() : 0);
        hash = 83 * hash + this.valor;
        hash = 83 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        return hash;
    }

    public Exercito getExercito() {
        return exercito;
    }

    public void setExercito(Exercito exercito) {
        this.exercito = exercito;
    }

    public int getValor() {
        return valor;
    }

    public String getTitulo() {
        return titulo;
    }

    protected void setTitulo(String titulo) {
        this.titulo = titulo;
    }

//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
    /**
     * Retorna uma descrição da peça. Essa descrição é composta pelo título
     * da peça (Sargento, General, Capitão, etc) concatenado com o seu valor
     * (2, 9 ,6, etc). Essa descrição é utilizada para preencher o rótulo do
     * componente
     * @return Uma <code>String</code> com a descrição da peça
     */
    public String getDescricao() {
        return this.titulo + "\r\n" + "(" + this.valor + ")";
    }
    /**
     * Classe interna que implementa a Interface <code>MouseListener</code>.
     * Captura os eventos de mouse e executa as implementações necessárias.
     */
//    class MouseListenerImpl implements MouseListener {
//
//
//    }
}
