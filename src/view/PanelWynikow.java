package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import model.Tablica;

/**
 * Klasa odpowiada za panel wyników
 *
 */
public class PanelWynikow extends JPanel implements Observer {

    private static final long serialVersionUID = -1045650997186417248L;

    /**
     * Domyślny rozmiar panelu.
     */
    private static final Dimension ROZMIAR_PANELU = new Dimension(150, 120);

    /**
     * Domyślna czcionka.
     */
    private static final Font CZCIONKA = new Font("Arial", Font.BOLD, 12);

    /**
     * Czyszczenie pojedynczej linii.
     */
    private static final int POJEDYNCZA_LINIA = 1;
    /**
     * Czyszczenie podwójnej linii.
     */
    private static final int PODWOJNA_LINIA = 2;

    /**
     * Czyszczenie potrójnej linii.
     */
    private static final int POTROJNA_LINIA = 3;

    /**
     * Czyszczenie poczwórnej linii.
     */
    private static final int POCZWORNA_LINIA = 4;

    /**
     * Ilość punktów za usunięcie pojedynczej linii.
     */
    private static final int PUNKTY_ZA_POJEDYNCZA_LINIE = 100;

    /**
     * Ilość punktów za usunięcie podwójnej linii.
     */
    private static final int PUNKTY_ZA_PODWOJNA_LINIE = 300;

    /**
     * Ilość punktów za usunięcie potrójnej linii.
     */
    private static final int PUNKTY_ZA_POTROJNA_LINIE = 500;

    /**
     * Ilość punktów za usunięcie poczwórnej linii.
     */
    private static final int PUNKTY_ZA_POCZWORNA_LINIE = 800;

    /**
     * Poziom 2.
     */
    private static final int POZIOM_2 = 2;

    /**
     * Poziom 3.
     */
    private static final int POZIOM_3 = 3;

    /**
     * Poziom 4.
     */
    private static final int POZIOM_4 = 4;

    /**
     * Poziom 5.
     */
    private static final int POZIOM_5 = 5;

    /**
     * Ilość wyczyszczonych linii potrzebnych do wejścia na poziom 2.
     */
    private static final int LINIE_NA_POZIOM_2 = 5;

    /**
     * Ilość wyczyszczonych linii potrzebnych do wejścia na poziom 3.
     */
    private static final int LINIE_NA_POZIOM_3 = 10;

    /**
     * Ilość wyczyszczonych linii potrzebnych do wejścia na poziom 4.
     */
    private static final int LINIE_NA_POZIOM_4 = 15;

    /**
     * Ilość wyczyszczonych linii potrzebnych do wejścia na poziom 5.
     */
    private static final int LINIE_NA_POZIOM_5 = 25;

    /**
     * Przerwa w spadaniu klocka na poziomie 2.
     */
    private static final int OPOZNIENIE_POZIOM_2 = 400;

    /**
     * Przerwa w spadaniu klocka na poziomie 3.
     */
    private static final int OPOZNIENIE_POZIOM_3 = 350;

    /**
     * Przerwa w spadaniu klocka na poziomie 4.
     */
    private static final int OPOZNIENIE_POZIOM_4 = 325;

    /**
     * Przerwa w spadaniu klocka na poziomie 5.
     */
    private static final int OPOZNIENIE_POZIOM_5 = 300;


    /**
     * Ilość usuniętych linii.
     */
    private static final String USUNIETE_LINIE_TEKST = "Lącznie linii:   ";

    /**
     * Ilość punktów.
     */
    private static final String PUNKTY_TEKST = "Punkty:  ";

    /**
     * Informacja o poziomie.
     */
    private static final String POZIOM_TEKST = "Poziom:   ";

    /**
     * Ilość linii do następnego poziomu.
     */
    private static final String KIEDY_NASTEPNY_POZIOM_TEKST = "Następny poziom za: ";

    /**
     * Tekst informujący o łacznej liczbie usunietych pojedynczych linii.
     */
    private static final String ILOSC_POJEDYNCZYCH_LINII_TEKST = "Pojedyncze linie:   ";

    /**
     * Tekst informujący o ilości usuniętych podwójnych linii.
     */
    private static final String ILOSC_PODWOJNYCH_LINII_TEKST = "Podwójne Linie:  ";

    /**
     * Tekst informujący o ilości usuniętych potrójnych linii.
     */
    private static final String ILOSC_POTROJNYCH_LINII_TEKST = "Potrójne linie:    ";

    /**
     * Tekst informujący o ilości usuniętych poczwórnych linii.
     */
    private static final String ILOSC_POCZWORNYCH_LINII_TEKST = "Poczwórne linie:    ";

    /**
     * Etykieta z ilością usuniętych linii.
     */
    private JLabel etykietaUsunietychLinii;

    /**
     * Etykieta z ilością punktów.
     */
    private JLabel etykietaPunktow;

    /**
     * Etykieta z numerem poziomu.
     */
    private JLabel etykietaPoziomu;

    /**
     * Etykieta z informacją o następnym poziomie.
     */
    private JLabel etykietaNastepnegoPoziomu;

    /**
     * Etykieta z informacją o usuniętych pojedynczych liniach.
     */
    private JLabel usunietePojedynczeLinieEtykieta;

    /**
     * Etykieta z informacją o usuniętych podwójnych liniach.
     */
    private JLabel usunietePodwojneLinieEtykieta;

    /**
     * Etykieta z informacją o usuniętych potrójnych liniach.
     */
    private JLabel usunietePotrojneLinieEtykieta;

    /**
     * Etykieta z informacją o usuniętych poczwórnych liniach.
     */
    private JLabel usunietePoczwornelinieEtykieta;

    /**
     * Licznik.
     */
    private final Timer licznik;

    /**
     * Ilość usuniętych linii.
     */
    private int usunieteLinie;

    /**
     * Ilość uzyskanych punktów.
     */
    private int uzyskanePunkty;

    /**
     * Numer poziomu.
     */
    private int numerPoziomu;

    /**
     * Ilość pojedynczych linii.
     */
    private int pojedynczeLinie;

    /**
     * Ilość podwójnych linii.
     */
    private int podwojneLinie;

    /**
     * Ilość potrójnych linii.
     */
    private int potrojneLinie;

    /**
     * Ilość poczwórnych linii.
     */
    private int poczworneLinie;

    /**
     * Konstruktor
     *
     * @param tablica Tablica przechouje tablicę gry.
     * @param licznik Licznik którego będzie używał ten panel.
     */
    public PanelWynikow(final Tablica tablica, final Timer licznik) {
        super();
        setPreferredSize(ROZMIAR_PANELU);
        setBackground(Color.WHITE);
        this.licznik = licznik;
        final Tablica mojaTablica = tablica;
        mojaTablica.addObserver(this);
        init();
        stworzRamke();
        rysujZdobytePunkty();
    }

    /**
     * Ta metoda inicjije zdobyte punkty.
     */
    private void init() {
        final Color kolorTla = Color.DARK_GRAY;
        setBackground(kolorTla);
        numerPoziomu = 1;
        uzyskanePunkty = 0;
        usunieteLinie = 0;
        pojedynczeLinie = 0;
        podwojneLinie = 0;
        potrojneLinie = 0;
        poczworneLinie = 0;
    }

    /**
     * Ta metoda tworzy linie panelu.
     */
    private void stworzRamke() {
        final TitledBorder tytulRamki = BorderFactory.createTitledBorder(null,"Punktacja", TitledBorder.CENTER, TitledBorder.TOP, CZCIONKA, Color.YELLOW);
        setBorder(tytulRamki);
    }

    /**
     * Ta metoda wyświetla zdobyte punkty.
     */
    private void rysujZdobytePunkty() {
        final Box box = Box.createVerticalBox();
        List<JLabel> napisy = new ArrayList<>();
        etykietaUsunietychLinii = new JLabel(USUNIETE_LINIE_TEKST + usunieteLinie);
        etykietaPunktow = new JLabel(PUNKTY_TEKST + uzyskanePunkty);
        etykietaPoziomu = new JLabel(POZIOM_TEKST + numerPoziomu);
        etykietaNastepnegoPoziomu = new JLabel(KIEDY_NASTEPNY_POZIOM_TEKST + LINIE_NA_POZIOM_2 + " linii");
        usunietePojedynczeLinieEtykieta = new JLabel(ILOSC_POJEDYNCZYCH_LINII_TEKST + pojedynczeLinie);
        usunietePodwojneLinieEtykieta = new JLabel(ILOSC_PODWOJNYCH_LINII_TEKST + podwojneLinie);
        usunietePotrojneLinieEtykieta = new JLabel(ILOSC_POTROJNYCH_LINII_TEKST + potrojneLinie);
        usunietePoczwornelinieEtykieta = new JLabel(ILOSC_POCZWORNYCH_LINII_TEKST + poczworneLinie);
        napisy.add(etykietaUsunietychLinii);
        napisy.add(etykietaPunktow);
        napisy.add(etykietaPoziomu);
        napisy.add(etykietaNastepnegoPoziomu);
        napisy.add(usunietePojedynczeLinieEtykieta);
        napisy.add(usunietePodwojneLinieEtykieta);
        napisy.add(usunietePotrojneLinieEtykieta);
        napisy.add(usunietePoczwornelinieEtykieta);
        for (JLabel label : napisy){
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setForeground(Color.WHITE);
            box.add(label);
        }
        add(box);
    }

    /**
     * Pobierz ilość usuniętych linii z tablicy.
     *
     * {@inheritDoc}
     */
    @Override
    public void update(final Observable observable, final Object o) {
        if (observable instanceof Tablica && o instanceof Integer[]) {
            final int usunieteLinie = ((Integer[]) o).length;
            przeliczPunkty(usunieteLinie);
            przeliczPoziom();
            repaint();
        }
    }

    /**
     * Metoda wylicza ilość usuniętych linii.
     *
     * @param iloscLinii Ilość wyczyszczonych linii.
     */
    private void przeliczPunkty(final int iloscLinii) {
        final int usunieteLinie = iloscLinii;
        this.usunieteLinie += usunieteLinie;
        etykietaUsunietychLinii.setText(USUNIETE_LINIE_TEKST + this.usunieteLinie);
        if (usunieteLinie == POJEDYNCZA_LINIA) {
            uzyskanePunkty += numerPoziomu * PUNKTY_ZA_POJEDYNCZA_LINIE;
            pojedynczeLinie += usunieteLinie;
        } else if (usunieteLinie == PODWOJNA_LINIA) {
            uzyskanePunkty += numerPoziomu * PUNKTY_ZA_PODWOJNA_LINIE;
            podwojneLinie += usunieteLinie / usunieteLinie;
        } else if (usunieteLinie == POTROJNA_LINIA) {
            uzyskanePunkty += numerPoziomu * PUNKTY_ZA_POTROJNA_LINIE;
            potrojneLinie += usunieteLinie / usunieteLinie;
        } else if (usunieteLinie == POCZWORNA_LINIA) {
            uzyskanePunkty += numerPoziomu * PUNKTY_ZA_POCZWORNA_LINIE;
            poczworneLinie += usunieteLinie / usunieteLinie;
        }
        etykietaPunktow.setText(PUNKTY_TEKST + uzyskanePunkty);
        usunietePojedynczeLinieEtykieta.setText(ILOSC_POJEDYNCZYCH_LINII_TEKST + pojedynczeLinie);
        usunietePodwojneLinieEtykieta.setText(ILOSC_PODWOJNYCH_LINII_TEKST + podwojneLinie);
        usunietePotrojneLinieEtykieta.setText(ILOSC_POTROJNYCH_LINII_TEKST + potrojneLinie);
        usunietePoczwornelinieEtykieta.setText(ILOSC_POCZWORNYCH_LINII_TEKST + poczworneLinie);

    }

    /**
     * Ta metoda oblicza obecny poziom gry.
     */
    private void przeliczPoziom() {
        obliczNastepnyPoziom(LINIE_NA_POZIOM_2);
        if (usunieteLinie >= LINIE_NA_POZIOM_2 && usunieteLinie < LINIE_NA_POZIOM_3) {
            obliczNastepnyPoziom(LINIE_NA_POZIOM_3);
            numerPoziomu = POZIOM_2;
            etykietaPoziomu.setText(POZIOM_TEKST + numerPoziomu);
            licznik.setDelay(OPOZNIENIE_POZIOM_2);
        } else if (usunieteLinie >= LINIE_NA_POZIOM_3 && usunieteLinie < LINIE_NA_POZIOM_4) {
            obliczNastepnyPoziom(LINIE_NA_POZIOM_4);
            numerPoziomu = POZIOM_3;
            etykietaPoziomu.setText(POZIOM_TEKST + numerPoziomu);
            licznik.setDelay(OPOZNIENIE_POZIOM_3);
        } else if (usunieteLinie >= LINIE_NA_POZIOM_4 && usunieteLinie < LINIE_NA_POZIOM_5) {
            obliczNastepnyPoziom(LINIE_NA_POZIOM_5);
            numerPoziomu = POZIOM_4;
            etykietaPoziomu.setText(POZIOM_TEKST + numerPoziomu);
            licznik.setDelay(OPOZNIENIE_POZIOM_4);
        } else if (usunieteLinie >= LINIE_NA_POZIOM_5) {
            etykietaNastepnegoPoziomu.setText("Ostatni poziom.");
            numerPoziomu = POZIOM_5;
            etykietaPoziomu.setText(POZIOM_TEKST + numerPoziomu);
            licznik.setDelay(OPOZNIENIE_POZIOM_5);
        }
    }

    /**
     * Metoda ta oblicza kiedy będzie następny poziom.
     *
     * @param iloscLiniiNaObecnyPoziom Ilość linii potrzebnych do wejścia na obecny poziom.
     */
    private void obliczNastepnyPoziom(final int iloscLiniiNaObecnyPoziom) {
        final int result = iloscLiniiNaObecnyPoziom - usunieteLinie;
        etykietaNastepnegoPoziomu.setText(KIEDY_NASTEPNY_POZIOM_TEKST + result + " linii");
    }

    /**
     * Metoda ta usuwa punktację.
     */
    public void usunWyniki() {
        this.usunieteLinie = 0;
        this.uzyskanePunkty = 0;
        this.numerPoziomu = 1;
        this.etykietaUsunietychLinii.setText(USUNIETE_LINIE_TEKST + usunieteLinie);
        this.etykietaPunktow.setText(PUNKTY_TEKST + uzyskanePunkty);
        this.etykietaPoziomu.setText(POZIOM_TEKST + numerPoziomu);
        this.usunietePojedynczeLinieEtykieta.setText(ILOSC_POJEDYNCZYCH_LINII_TEKST + pojedynczeLinie);
        this.usunietePodwojneLinieEtykieta.setText(ILOSC_PODWOJNYCH_LINII_TEKST + podwojneLinie);
        this.usunietePotrojneLinieEtykieta.setText(ILOSC_POTROJNYCH_LINII_TEKST + potrojneLinie);
        this.usunietePoczwornelinieEtykieta.setText(ILOSC_POCZWORNYCH_LINII_TEKST + poczworneLinie);
    }
}
