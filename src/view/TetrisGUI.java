package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Tablica;

/**
 * Klasa odpowiedzialna za stworzenie GUI
 */
public class TetrisGUI implements Observer {

    /**
     * Toolkit.
     */
    private static final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

    /**
     * Wymiary ekranu.
     */
    private static final Dimension ROZMIAR_OKNA = TOOLKIT.getScreenSize();

    /**
     * Szerokość ekranu.
     */
    private static final int SZEROKOSC_OKNA = ROZMIAR_OKNA.width;

    /**
     * Wysokość ekranu.
     */
    private static final int WYSOKOSC_OKNA = ROZMIAR_OKNA.height;


    /**
     * Domyślne opóźnienie timera.
     */
    private static final int DOMYSLNE_OPOZNIENIE_TIMERA = 500;

    /**
     * Czcionka końca gry.
     */
    private static final Font CZCIONKA_KONCA_GRY = new Font("Arial", 3, 30);

    /**
     * JFrame gry.
     */
    private final JFrame jFrame;

    /**
     * Timer gry.
     */
    private Timer timer;

    /**
     * Tablica gry.
     */
    private Tablica tablica;

    /**
     * Stan gry.
     */
    private Boolean stanGry;

    /**
     * Przycisk kończący grę.
     */
    private JMenuItem przyciskKoncaGry;

    /**
     * Przycisk ppauzujący grę.
     */
    private JMenuItem przyciskPauzy;

    /**
     * Przycisk tworzący nową grę.
     */
    private JMenuItem przyciskNowejGry;

    /**
     * Przycisk zamykający grę.
     */
    private JMenuItem przyciskWyjscia;

    /**
     * Przełącznik odpowiadający za rysowanie siatki.
     */
    private JCheckBoxMenuItem przelacznikRysowaniaSiatki;

    /**
     * Sterowanie klockiem.
     */
    private Sterowanie sterowanie;

    /**
     * Panel tabeli wyników.
     */
    private PanelWynikow panelWynikow;

    /**
     * Panel gry przedstawiający grę.
     */
    private PanelGry panelGry;

    /**
     * Etykieta zakończenia gry.
     */
    private JLabel napisKoncowy;

    /**
     * Konstruktor.
     */
    public TetrisGUI() {
        jFrame = new JFrame("Tetris");
        stanGry = false;
        ustawienieGUI();
    }

    /**
     * Ustawienie GUI.
     */
    private void ustawienieGUI() {
        timer = new Timer(DOMYSLNE_OPOZNIENIE_TIMERA, new TimerListener());
        final PanelTla panelTla = new PanelTla();
        panelGry = new PanelGry();
        panelGry.setLayout(new GridBagLayout());
        panelTla.add(panelGry);
        final JMenuBar pasekMenu = zwrocPasekMenu();
        jFrame.setJMenuBar(pasekMenu);
        tablica = new Tablica();
        sterowanie = new Sterowanie(tablica);
        sterowanie.czyKlocekSiePorusza(false);
        final JPanel stronaPanelu = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(stronaPanelu, BoxLayout.PAGE_AXIS);
        stronaPanelu.setLayout(boxLayout);
        final PanelNastepnegoKlocka panelNastepnegoKlocka = new PanelNastepnegoKlocka();
        final PanelSterowania panelSterowania = new PanelSterowania();
        panelWynikow = new PanelWynikow(tablica, timer);
        final Box sideBox = new Box(BoxLayout.PAGE_AXIS);
        sideBox.add(panelNastepnegoKlocka);
        sideBox.add(panelWynikow);
        sideBox.add(panelSterowania);
        napisKoncowy = new JLabel("Game Over");
        napisKoncowy.setFont(CZCIONKA_KONCA_GRY);
        napisKoncowy.setForeground(Color.RED);
        stronaPanelu.add(sideBox);
        jFrame.add(stronaPanelu, BorderLayout.EAST);
        jFrame.addKeyListener(sterowanie);
        jFrame.add(panelTla);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(200, 200);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.pack();
        tablica.addObserver(panelNastepnegoKlocka);
        tablica.addObserver(panelGry);
        tablica.addObserver(this);
    }

    /**
     * Sprawdź czy gra się skończyła.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable observable, final Object o) {
        if (observable instanceof Tablica && o instanceof Boolean) {
            timer.stop();
            przyciskPauzy.setEnabled(false);
            przyciskKoncaGry.setEnabled(false);
            przyciskNowejGry.setEnabled(true);
            panelGry.add(napisKoncowy);
            jFrame.validate();
        }
    }

    /**
     * Metoda zwraca pasek menu.
     *
     * @return zwraca pasek menu.
     */
    private JMenuBar zwrocPasekMenu() {
        final JMenuBar pasekMenu = new JMenuBar();
        final JMenu menuGry = ustawMenuGry();
        final JMenu menuUstawien = ustawMenuUstawien();
        final JMenu menuPomocy = ustawMenuPomocy();
        pasekMenu.add(menuGry);
        pasekMenu.add(menuUstawien);
        pasekMenu.add(menuPomocy);
        return pasekMenu;
    }

    /**
     * Metoda zwraca pasek "GRA".
     *
     * @return zwraca menu gry.
     */
    private JMenu ustawMenuGry() {
        final MenuActionListener menuActionListener = new MenuActionListener();
        final JMenu menuGry = new JMenu("Gra");
        menuGry.setMnemonic(KeyEvent.VK_F);
        przyciskNowejGry = new JMenuItem("Nowa gra", KeyEvent.VK_N);
        przyciskNowejGry.addActionListener(menuActionListener);
        przyciskKoncaGry = new JMenuItem("Koniec gry", KeyEvent.VK_E);
        przyciskKoncaGry.setEnabled(false);
        przyciskKoncaGry.addActionListener(menuActionListener);
        przyciskWyjscia = new JMenuItem("Wyjście", KeyEvent.VK_Q);
        przyciskWyjscia.addActionListener(menuActionListener);
        przyciskPauzy = new JMenuItem("Pauza", KeyEvent.VK_P);
        przyciskPauzy.setEnabled(false);
        przyciskPauzy.setAccelerator(KeyStroke.getKeyStroke('p'));
        przyciskPauzy.addActionListener(menuActionListener);
        menuGry.add(przyciskNowejGry);
        menuGry.add(przyciskKoncaGry);
        menuGry.addSeparator();
        menuGry.add(przyciskPauzy);
        menuGry.addSeparator();
        menuGry.add(przyciskWyjscia);
        return menuGry;
    }

    /**
     * Metoda zwraca menu opcji.
     *
     * @return zwraca menu opcji.
     */
    private JMenu ustawMenuUstawien() {

        final JMenu menuUstawien = new JMenu("Ustawienia");
        menuUstawien.setMnemonic(KeyEvent.VK_O);
        przelacznikRysowaniaSiatki = new JCheckBoxMenuItem("Siatka");
        przelacznikRysowaniaSiatki.setMnemonic(KeyEvent.VK_G);
        przelacznikRysowaniaSiatki.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (przelacznikRysowaniaSiatki.isSelected()) {
                    panelGry.rysujSiatke(true);
                    panelGry.repaint();
                } else {
                    panelGry.rysujSiatke(false);
                }
            }
        });
        menuUstawien.add(przelacznikRysowaniaSiatki);
        return menuUstawien;
    }

    /**
     * Metoda zwraca menu pomocy.
     *
     * @return zwraca menu pomocy.
     */
    private JMenu ustawMenuPomocy() {
        final JMenu menuPomocy = new JMenu("Pomoc");
        menuPomocy.setMnemonic(KeyEvent.VK_H);
        final JMenuItem informacjeOPunktacji = new JMenuItem("Punktacja", KeyEvent.VK_S);
        informacjeOPunktacji.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(jFrame,
                        "Gra rozpoczyna się od poziomu 1. \n"
                                + "Aby przejść na kolejny poziom, należy ułożyć 5 linii. \n"
                                + "Za każdą ułożoną linię dostajesz punkty. \n"
                                + "Ilość przyznanych punktów zależy od tego, ile linii na raz ułożysz. \n"
                                + "Za ułożenie pojedynczej linii otrzymasz 100 punktów. \n"
                                + "Za ułożenie podwójnej linii otrzymasz 300 punktów. \n"
                                + "Za ułożenie potrójnej linii otrzymasz 500 punktów. \n"
                                + "Za ułożenie poczwórnej linii otrzymasz 800 punktów. \n",
                        "Punkty", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuPomocy.add(informacjeOPunktacji);
        return menuPomocy;
    }

    /**
     * Klasa wykorzystująca timer do wykonania pojedynczego kroku.
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            tablica.krok();
        }
    }

    /**
     * Klasa tworząca obsługę menu.
     */
    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            if (actionEvent.getSource() == przyciskNowejGry) {
                timer.start();
                tablica.nowaGra();
                panelWynikow.usunWyniki();
                stanGry = true;
                sterowanie.czyKlocekSiePorusza(true);
                przyciskNowejGry.setEnabled(false);
                przyciskKoncaGry.setEnabled(true);
                przyciskPauzy.setEnabled(true);
                panelGry.remove(napisKoncowy);
            }
            if ((actionEvent.getSource() == przyciskKoncaGry) && stanGry) {
                timer.stop();
                sterowanie.czyKlocekSiePorusza(false);
                przyciskKoncaGry.setEnabled(false);
                przyciskNowejGry.setEnabled(true);
                przyciskPauzy.setEnabled(false);
                panelGry.add(napisKoncowy);
                panelGry.validate();
                panelGry.repaint();
            }
            if (actionEvent.getSource() == przyciskPauzy) {
                if (timer.isRunning()) {
                    timer.stop();
                    sterowanie.czyKlocekSiePorusza(false);
                } else {
                    timer.start();
                    sterowanie.czyKlocekSiePorusza(true);
                }
            }
            if (actionEvent.getSource() == przyciskWyjscia) {
                jFrame.dispatchEvent(new WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING));
            }

        }

    }


    /**
     * Klasa tworząca tło gry.
     */
    private final class PanelTla extends JPanel {
        private static final long serialVersionUID = 1073937863574422710L;

        /**
         * Obrazek tła.
         */
        private final Image obrazekTla;

        /**
         * Konstruktor.
         */
        public PanelTla() {
            super();
            setLayout(new GridBagLayout());
            final ImageIcon image = new ImageIcon("resources/obrazki/tlo.png");
            obrazekTla = image.getImage();
            setPreferredSize(new Dimension(obrazekTla.getWidth(null), obrazekTla.getHeight(null)));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(final Graphics graphics) {
            super.paintComponent(graphics);
            setOpaque(false);
            graphics.drawImage(obrazekTla, 0, 0, null);
        }
    }

}