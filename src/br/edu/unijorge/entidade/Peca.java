package br.edu.unijorge.entidade;

import br.edu.unijorge.constante.Constante;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

/**
 * Abstrai a peça (peão) do jogo. Possui características como Exército, Título e Valor.
 * Obriga as sub-classes a implementar o método mouseReleased da Interface <code>MouseListener</code>.
 *
 * @author Glauber
 */

public abstract class Peca extends JToggleButton {
    public static final int
            LARGURA_PADRAO = 63, //Largura padrão do componente
            ALTURA_PADRAO = 63; //Altura padrão do componente
    private Exercito exercito;
    private int valor;
    private String id;
    private String titulo;

    //Construtor da classe
    public Peca(Exercito exercito, String titulo, int valor, String id) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.exercito = exercito;
        this.titulo = titulo;
        this.valor = valor;
        this.id = id;
        setBounds(0, 0, LARGURA_PADRAO, ALTURA_PADRAO);
        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pecaMouseReleased(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pecaMouseClicked(evt);
            }
        });
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
                setOpaque(false);
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
     * id (valor da peça concatenado com um id sequencial).
     * @param obj Object que será comparado com a instância dessa classe
     * @return <code>true</code> se as peças forem iguais, <code>false</code> caso contrário.
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Peca)) {
            return false;
        }

        return this.id.equals(((Peca) obj).getId());
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = this.valor + "-" + id;
    }
    
    @Override
    public String toString() {
        return "Peça: " + this.titulo + "(" + this.valor + ")." + " Exército: " + getExercito().toString();
    }

    public ImageIcon getInsignia(){
        return new ImageIcon(getClass().getResource(
                        Constante.PATH_IMAGENS
                        + "Insignia-"
                        + getTitulo() + Constante.EXTENSAO_IMAGEM));
    }

    public abstract void pecaMouseReleased(MouseEvent evt);
    
    public abstract void pecaMouseClicked(MouseEvent evt);
}
