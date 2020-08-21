

package model;

import java.awt.Color;
import java.util.Random;

/**
 * Enum typów klocka
 * Enumeration of the Klocek types.
 *
 * @author Alan Fowler
 * @version Spring 2015
 */
public enum Klocek {

    /**
     * Klocek 'I'.
     * */
    I(4, 1,
            Color.RED,
            new Punkt(0, 2), new Punkt(1, 2), new Punkt(2, 2), new Punkt(3, 2)),

    /**
     * Klocek 'J'.
     * */
    J(3, 2,
            Color.GREEN,
            new Punkt(0, 2), new Punkt(0, 1), new Punkt(1, 1), new Punkt(2, 1)),

    /**
     * Klocek 'L'.
     * */
    L(3, 2,
            Color.MAGENTA,
            new Punkt(2, 2), new Punkt(0, 1), new Punkt(1, 1), new Punkt(2, 1)),

    /**
     * Klocek 'O'.
     * */
    O(3, 2,
            Color.BLUE,
            new Punkt(1, 2), new Punkt(2, 2), new Punkt(1, 1), new Punkt(2, 1)),

    /**
     * Klocek 'S'.
     * */
    S(3, 2,
            Color.YELLOW,
            new Punkt(1, 2), new Punkt(2, 2), new Punkt(0, 1), new Punkt(1, 1)),

    /**
     * Klocek 'T'.
     * */
    T(3, 2,
            Color.PINK,
            new Punkt(1, 2), new Punkt(0, 1), new Punkt(1, 1), new Punkt(2, 1)),

    /**
     * Klocek 'Z'.
     * */
    Z(3, 2,
            Color.ORANGE,
            new Punkt(0, 2), new Punkt(1, 2), new Punkt(1, 1), new Punkt(2, 1));



    /**
     * Losowy obiekt
     */
    private static final Random RANDOM = new Random();


    /**
     * Szerokość klocka
     */
    private final int szerokoscKlocka;

    /**
     * Wysokość klocka.
     */
    private final int wysokoscKlocka;

    /**
     * Punkty klocka (4).
     */
    private final Punkt[] punkty;

    /**
     * Kolor klocka.
     */
    private final Color kolor;

    /**
     * Konstruktor.
     *
     * @param szerokosc szerokość klocka.
     * @param wysokosc wysokość klocka.
     * @param kolor kolor klocka.
     * @param punkty początkowe ułożenie punktów klocka.
     */
    Klocek(final int szerokosc, final int wysokosc, final Color kolor,
                   final Punkt... punkty) {
        this.szerokoscKlocka = szerokosc;
        this.wysokoscKlocka = wysokosc;
        this.kolor = kolor;
        this.punkty = punkty.clone();
    }

    /**
     * Pobierz szerokość klocka.
     *
     * @return zwraca szerokość klocka.
     */
    public int pobierzSzerokosc() {
        return szerokoscKlocka;
    }

    /**
     * Pobierz wysokość klocka.
     *
     * @return zwraca wysokość klocka.
     */
    public int pobierzWysokosc() {
        return wysokoscKlocka;
    }

    /**
     * Pobierz kolor klocka.
     *
     * @return zwraca kolor klocka.
     */
    public Color pobierzKolor() {
        return kolor;
    }

    /**
     * Pobierz punkty klocka.
     *
     * @return zwraca punkty klocka.
     */
    public Punkt[] pobierzPunkty() {
        return punkty.clone();
    }

    /**
     * Pobierz losowy klocek.
     *
     * @return zwraca losowy klocek.
     */
    public static Klocek pobierzLosowyKlocek() {
        return values()[RANDOM.nextInt(values().length)];
    }
}
