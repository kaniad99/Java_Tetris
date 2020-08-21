package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * Klasa odpowiada za stworzenie panelu informującego o tym, jak sterować grą.
 *
 */
public class PanelSterowania extends JPanel {
    private static final long serialVersionUID = -6899658540250320208L;

    /**
     * Domyślny rozmiar panelu.
     */
    private static final Dimension ROZMIAR_PANELU = new Dimension(150, 110);

    /**
     * Domyślna czcionka
     */
    private static final Font CZCIONKA = new Font("Arial", Font.BOLD, 12);

    /**
     * Konstruktor.
     */
    public PanelSterowania() {
        super();
        final Color kolorTla = Color.DARK_GRAY;
        setBackground(kolorTla);
        setPreferredSize(ROZMIAR_PANELU);
        ustawRamke();
        ustawTekstPanelu();
    }

    /**
     * Metoda tworzy ramkę panelu.
     */
    private void ustawRamke() {
        final TitledBorder tytulRamki = BorderFactory.createTitledBorder(null,"Sterowanie", TitledBorder.CENTER, TitledBorder.TOP, CZCIONKA, Color.YELLOW);
        setBorder(tytulRamki);
    }

    /**
     * Metoda odpowiada za utworzenie tekstu wyświetlanego w panelu.
     */
    private void ustawTekstPanelu() {
        final Box box = Box.createVerticalBox();
        List<JLabel> napisy = new ArrayList<>();
        napisy.add(new JLabel("'←'   Przesuń w lewo"));
        napisy.add(new JLabel("'→'   Przesuń w prawo"));
        napisy.add(new JLabel("'↓'   Opuszczenie"));
        napisy.add(new JLabel("'↑' lub 'X'   Obróć w prawo"));
        napisy.add(new JLabel("'Z'   Obróć w lewo"));
        napisy.add(new JLabel("'Spacja'   Zrzucenie klocka"));
        napisy.add(new JLabel("'P'   Pauza"));
        for (JLabel napis : napisy){
            napis.setAlignmentX(Component.LEFT_ALIGNMENT);
            napis.setForeground(Color.WHITE);
            box.add(napis);
        }
        add(box);
    }
}
