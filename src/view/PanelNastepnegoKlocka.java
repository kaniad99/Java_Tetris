package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Klocek;
import model.Tablica;
import model.Punkt;

/**
 * Klasa odpowiedzialna za utworzenie panelu następnego klocka.
 */
public class PanelNastepnegoKlocka extends JPanel implements Observer {
    private static final long serialVersionUID = 4342848274444040935L;

    /**
     * Domyślny wymiar panelu.
     */
    private static final Dimension WYMIARY_PANELU = new Dimension(165, 45);

    /**
     * Domyślna czcionka.
     */
    private static final Font CZCIONKA = new Font("Arial", Font.BOLD, 12);

    /**
     * Domyślny rozmiar bloczka.
     */
    private static final int ROZMIAR_BLOKU = 20;

    /**
     * Domyślny środek na osi X.
     */
    private static final int WSP_X = 40;

    /**
     * Domyślny środek na osi Y.
     */
    private static final int WSP_Y = 40;

    /**
     * Następny klocek.
     */
    private Klocek nastepnyKlocek;

    /**
     * Konstruktor
     */
    public PanelNastepnegoKlocka() {
        super();
        final Color kolorTla = Color.DARK_GRAY;
        setBackground(kolorTla);
        setPreferredSize(WYMIARY_PANELU);
        ustawRamke();
    }

    /**
     * Ta metoda tworzy ramkę wokół panelu.
     */
    private void ustawRamke() {
        final TitledBorder tytulRamki = BorderFactory.createTitledBorder(null, "Następny", TitledBorder.CENTER, TitledBorder.TOP, CZCIONKA, Color.YELLOW);
        setBorder(tytulRamki);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g2d = (Graphics2D) graphics;
        Image obrazek = null;
        if (nastepnyKlocek != null) {
            obrazek = getBlockImage(nastepnyKlocek.pobierzKolor());
            for (int i = 0; i < nastepnyKlocek.pobierzPunkty().length; i++) {
                final Punkt punkt = nastepnyKlocek.pobierzPunkty()[i];
                g2d.drawImage(obrazek, (punkt.pobierzX() * ROZMIAR_BLOKU) + WSP_X, getHeight() - (punkt.pobierzY() * ROZMIAR_BLOKU) - WSP_Y, this);
            }
        }
        final int wysokosc = 101;
        final int szerokosc = 160;
        final int rozmiarX = 140;
        final int rozmiarY = 100;
        final Color kolorSiatki = Color.BLACK;
        final BasicStroke basicStroke = new BasicStroke(1);
        g2d.setStroke(basicStroke);
        g2d.setColor(kolorSiatki);
        for (int kolumna = ROZMIAR_BLOKU; kolumna < szerokosc; kolumna += ROZMIAR_BLOKU) {
            g2d.drawLine(kolumna, ROZMIAR_BLOKU, kolumna, rozmiarY);
        }
        for (int wiersz = ROZMIAR_BLOKU; wiersz < wysokosc; wiersz += ROZMIAR_BLOKU) {
            g2d.drawLine(ROZMIAR_BLOKU, wiersz, rozmiarX, wiersz);
        }
    }

    /**
     * Pobierz następny klocek z tablicy.
     *
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable observable, final Object o) {
        if (observable instanceof Tablica && o instanceof Klocek) {
            nastepnyKlocek = (Klocek) o;
        }
        repaint();
    }

    /**
     * Metoda odpowiada za wybranie obrazka dla klocka na podstawie jego koloru.
     *
     * @param kolorKlocka kolor klocka.
     * @return Zwracany jest obrazek odpowiadający kolorowi klocka.
     */
    private Image getBlockImage(final Color kolorKlocka) {
        Image obrazek = null;
        if (kolorKlocka != null) {
            if (kolorKlocka == Color.GREEN) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/zielony.png");
            } else if (kolorKlocka == Color.ORANGE) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/pomaranczowy.png");
            } else if (kolorKlocka == Color.RED) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/czerwony.png");
            } else if (kolorKlocka == Color.MAGENTA) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/fioletowy.png");
            } else if (kolorKlocka == Color.BLUE) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/niebieski.png");
            } else if (kolorKlocka == Color.YELLOW) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/zolty.png");
            } else if (kolorKlocka == Color.PINK) {
                obrazek = Toolkit.getDefaultToolkit().getImage("resources/obrazki/rozowy.png");
            }
        }
        return obrazek;
    }
}
