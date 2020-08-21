package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Tablica;
import model.Punkt;

/**
 * Klasa odpowiedzialna za stworzenie tablicy do gry.
 *
 */
public class PanelGry extends JPanel implements Observer {

    private static final long serialVersionUID = -8374538314410025360L;

    /**
     * Domyślne wymiary tablicy.
     */
    private static final Dimension WYMIARY_TABLICY = new Dimension(201, 401);

    /**
     * Domyślny rozmiar klocka.
     */
    private static final int ROZMIAR_KLOCKA = 20;

    /**
     * Zmienna przechowująca informację o tym, czy rysować siatkę.
     */
    private boolean czyRysowacSiatke;

    /**
     * Zbiór kolorów do rysowania klocków.
     */
    private final List<Color[]> listaKolorow;


    /**
     * Konstruktor
     */
    public PanelGry() {
        super();
        listaKolorow = new ArrayList<Color[]>();
        czyRysowacSiatke = false;
        final Color kolorTla = Color.DARK_GRAY;
        setBackground(kolorTla);
        setPreferredSize(WYMIARY_TABLICY);
    }

    /**
     * Rysowanie tablicy w panelu.
     *
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D graphics = (Graphics2D) theGraphics;
        Image obraz = null;
        for (int i = 0; i < listaKolorow.size(); i++) {
            final Color[] tymczasowaListaKolorow = listaKolorow.get(i);
            for (int j = 0; j < tymczasowaListaKolorow.length; j++) {
                final Punkt punkt = new Punkt(j * ROZMIAR_KLOCKA, i * ROZMIAR_KLOCKA);
                final Color color = tymczasowaListaKolorow[j];
                obraz = pobierzObrazBloku(color);
                if (tymczasowaListaKolorow[j] != null) {
                    graphics.drawImage(obraz, punkt.pobierzX(),getHeight() - punkt.pobierzY() - ROZMIAR_KLOCKA, this);
                }
            }
        }

        if (czyRysowacSiatke) {
            final int wysokosc = getHeight();
            final int szerokosc = getWidth();
            final Color kolorSiatki = Color.BLACK;
            final BasicStroke basicStroke = new BasicStroke(1);
            graphics.setStroke(basicStroke);
            graphics.setColor(kolorSiatki);
            for (int kolumna = 0; kolumna < szerokosc; kolumna += ROZMIAR_KLOCKA) {
                graphics.drawLine(kolumna, 0, kolumna, wysokosc);
            }
            for (int wiersz = 0; wiersz < wysokosc; wiersz += ROZMIAR_KLOCKA) {
                graphics.drawLine(0, wiersz, szerokosc, wiersz);
            }
            repaint();
        }
    }

    /**
     * Metoda odpowiada za wybranie obrazu bloczka dla konkretnego koloru klocka.
     *
     * @param kolorKlocka kolor klocka.
     * @return Zwracany jest obrazek dla konkretnego koloru.
     */
    private Image pobierzObrazBloku(final Color kolorKlocka) {
        Image obraz = null;
        if (kolorKlocka != null) {
            if (kolorKlocka == Color.GREEN) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/zielony.png");
            } else if (kolorKlocka == Color.ORANGE) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/pomaranczowy.png");
            } else if (kolorKlocka == Color.RED) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/czerwony.png");
            } else if (kolorKlocka == Color.MAGENTA) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/fioletowy.png");
            } else if (kolorKlocka == Color.BLUE) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/niebieski.png");
            } else if (kolorKlocka == Color.YELLOW) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/zolty.png");
            } else if (kolorKlocka == Color.PINK) {
                obraz = Toolkit.getDefaultToolkit().getImage("resources/obrazki/rozowy.png");
            }
        }
        return obraz;
    }

    /**
     * Metoda odpowiada za ustawienie pola 'czyRysowacSiatke'
     *
     * @param czyRysowac status pola.
     */
    public void rysujSiatke(final boolean czyRysowac) {
        czyRysowacSiatke = czyRysowac;
    }

    /**
     * Odświeżenie obrazków
     */
    @Override
    public void update(final Observable observable, final Object object) {
        if (observable instanceof Tablica && object instanceof List) {
            listaKolorow.clear();
            for (int i = 0; i < ((List) object).size(); i++) {
                final Color[] listaKolorow = (Color[]) ((List) object).get(i);
                this.listaKolorow.add(listaKolorow);
            }
        }
        repaint();
    }

}