package model;

import java.util.Objects;

/**
 * Klasa przedstawia punkt o współrzędnych pobierzX i pobierzY.
 * Punkt jest niezmienny.
 */
public final class Punkt {

    /**
     *  Współrzędna pobierzX.
     */
    private final int x;

    /**
     *  Współrzędna pobierzY.
     */
    private final int y;

    /**
     * Konstruktor wykorzystujący podane współrzędne punktu.
     *
     * @param wspX współrzędna pobierzX.
     * @param wspY współrzędna pobierzY.
     */
    public Punkt(final int wspX, final int wspY) {
        x = wspX;
        y = wspY;
    }

    /**
     * Metoda zwracająca współrzędną pobierzX.
     *
     * @return zwraca współrzędną pobierzX danego punktu.
     */
    public int pobierzX() {
        return x;
    }

    /**
     * Metoda zwracająca współrzędną pobierzY.
     *
     * @return zwraca współrzędną pobierzY danego punktu.
     */
    public int pobierzY() {
        return y;
    }

    /**
     * Tworzenie nowego punktu przesuniętego wzdłuż osi X lub Y.
     *
     * @param przesuniecieNaOsiX zmienna informująca, ile pól zmieniła się współrzędna X.
     * @param przesuniecieNaOsiY zmienna informująca, ile pól zmieniła się współrzędna Y.
     * @return zwracany jest zmieniony punkt.
     */
    public Punkt przesun(final int przesuniecieNaOsiX, final int przesuniecieNaOsiY) {
        return new Punkt(x + przesuniecieNaOsiX, y + przesuniecieNaOsiY);
    }

    /**
     * Tworzenie nowego punktu zmienionego przez inny punkt.
     *
     * @param punkt punkt do przesunięcia.
     * @return nowy przekształcony punkt.
     */
    public Punkt przesun(final Punkt punkt) {
        return przesun(punkt.pobierzX(), punkt.pobierzY());
    }

    @Override
    public boolean equals(final Object innyObiekt) {
        boolean result = false;
        if (innyObiekt == this) {
            result = true;
        } else if (innyObiekt != null && innyObiekt.getClass() == getClass()) {
            final Punkt punkt = (Punkt) innyObiekt;
            result = x == punkt.x && y == punkt.y;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
