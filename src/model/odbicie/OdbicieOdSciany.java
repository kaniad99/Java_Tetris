package model.odbicie;

import model.Klocek;
import model.Punkt;
import model.Rotacja;

/**
 * Klasa odpowiada za zachowanie się klocka w momencie, gdy nie ma miejsca na obrócenie go, ponieważ klocek zablokowany jest przez ściany
 *
 */
public final class OdbicieOdSciany {

    /**
     * Sposoby przesunięcia klocka w zależności od jego kształtu
     */
    private static final Punkt[][] WARIANTY_PRZESUNIECIA = {
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(-1, +1),
                    new Punkt(0, -2), new Punkt(-1, -2)},  // BRAK to CWIERC
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(+1, -1),
                    new Punkt(0, +2), new Punkt(+1, +2) }, // CWIERC to BRAK
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(+1, -1),
                    new Punkt(0, +2), new Punkt(+1, +2) }, // CWIERC to POL
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(-1, +1),
                    new Punkt(0, -2), new Punkt(-1, -2) }, // POL to CWIERC
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(+1, +1),
                    new Punkt(0, -2), new Punkt(+1, -2) }, // POL to TRZYCZWARTE
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(-1, -1),
                    new Punkt(0, +2), new Punkt(-1, +2) }, // TRZYCZWARTE to POL
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(-1, -1),
                    new Punkt(0, +2), new Punkt(-1, +2) }, // TRZYCZWARTE to BRAK
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(+1, +1),
                    new Punkt(0, -2), new Punkt(+1, -2) }  // BRAK to TRZYCZWARTE
    };

    /**
     * Przesunięcie długiego klocka (I)
     */
    private static final Punkt[][] I_PRZESUNIECIE = {
            {new Punkt(0, 0), new Punkt(-2, 0), new Punkt(+1, 0),
                    new Punkt(-2, -1), new Punkt(+1, +2)},  // BRAK to CWIERC
            {new Punkt(0, 0), new Punkt(+2, 0), new Punkt(-1, 0),
                    new Punkt(+2, +1), new Punkt(-1, -2) }, // CWIERC to BRAK
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(+2, 0),
                    new Punkt(-1, +2), new Punkt(+2, -1) }, // CWIERC to POL
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(-2, 0),
                    new Punkt(+1, -2) , new Punkt(-2, +1) }, // POL to CWIERC
            {new Punkt(0, 0), new Punkt(+2, 0), new Punkt(-1, 0),
                    new Punkt(+2, +1), new Punkt(-1, -2) }, // POL to TRZYCZWARTE
            {new Punkt(0, 0), new Punkt(-2, 0), new Punkt(+1, 0),
                    new Punkt(-2, -1), new Punkt(+1, +2) }, // TRZYCZWARTE to POL
            {new Punkt(0, 0), new Punkt(+1, 0), new Punkt(-2, 0),
                    new Punkt(+1, -2), new Punkt(-2, +1) }, // TRZYCZWARTE to BRAK
            {new Punkt(0, 0), new Punkt(-1, 0), new Punkt(+2, 0),
                    new Punkt(-1, +2), new Punkt(+2, -1) }  // BRAK to TRZYCZWARTE
    };

    /**
     * Konstruktor
     */
    private OdbicieOdSciany() {
    }

    /**
     * Metoda zwraca zbiór punktów reprezentujących odbicie od ścian według zdefiniowanych parametrów
     *
     * @param klocek klocek który ma zostać obrócony
     * @param pierwotnaRotacja stan rotacji przed obrotem
     * @param docelowaRotacja docelowe ułożenie klocka
     * @return zwraca przesunięcie klocka
     */
    public static Punkt[] pobierzPrzesuniecieKlocka(final Klocek klocek,
                                                    final Rotacja pierwotnaRotacja,
                                                    final Rotacja docelowaRotacja) {

        Punkt[] punkty = new Punkt[0];

        if (klocek == Klocek.I) {
            punkty = pobierzPrzesuniecieKlockaI(pierwotnaRotacja, docelowaRotacja);
        } else if (klocek != Klocek.O) {
            punkty = pobierzWariantyPrzesuniec(pierwotnaRotacja, docelowaRotacja);
        }

        return punkty;
    }

    /**
     * Zwraca zbiór punktów reprezentujących odbicie od ściany wszystkich klocków (poza I)
     *
     * @param pierwotnaRotacja stan rotacji przed obrotem
     * @param docelowaRotacja docelowy stan klocka po obróceniu
     * @return zwraca przesunięcie dla podanych warunków
     */
    private static Punkt[] pobierzWariantyPrzesuniec(final Rotacja pierwotnaRotacja,
                                                     final Rotacja docelowaRotacja) {
        Punkt[] punkty = new Punkt[0];
        switch (pierwotnaRotacja) {
            case BRAK:
                if (docelowaRotacja == Rotacja.CWIERC) {
                    punkty = WARIANTY_PRZESUNIECIA[0];
                } else if (docelowaRotacja == Rotacja.TRZYCZWARTE) {
                    punkty = WARIANTY_PRZESUNIECIA[7];
                }
                break;
            case CWIERC:
                if (docelowaRotacja == Rotacja.POL) {
                    punkty = WARIANTY_PRZESUNIECIA[2];
                } else if (docelowaRotacja == Rotacja.BRAK) {
                    punkty = WARIANTY_PRZESUNIECIA[1];
                }
                break;
            case POL:
                if (docelowaRotacja == Rotacja.TRZYCZWARTE) {
                    punkty = WARIANTY_PRZESUNIECIA[4];
                } else if (docelowaRotacja == Rotacja.CWIERC) {
                    punkty = WARIANTY_PRZESUNIECIA[3];
                }
                break;
            case TRZYCZWARTE:
                if (docelowaRotacja == Rotacja.BRAK) {
                    punkty = WARIANTY_PRZESUNIECIA[6];
                } else if (docelowaRotacja == Rotacja.POL) {
                    punkty = WARIANTY_PRZESUNIECIA[5];
                }
                break;
            default:
                // should never happen
                break;
        }
        return punkty;
    }

    /**
     * Returns an array of Points representing the wall kick offsets
     * for IPieces for the situation defined by the parameters.
     *
     * @param pierwotnaRotacja the rotational state before the rotation
     * @param docelowaRotacja the desired rotational state
     * @return The wall kick offsets for these conditions
     */
    private static Punkt[] pobierzPrzesuniecieKlockaI(final Rotacja pierwotnaRotacja,
                                                      final Rotacja docelowaRotacja) {
        Punkt[] zwracanyPunkt = new Punkt[0];
        switch (pierwotnaRotacja) {
            case BRAK:
                if (docelowaRotacja == Rotacja.CWIERC) {
                    zwracanyPunkt = I_PRZESUNIECIE[0];
                } else if (docelowaRotacja == Rotacja.TRZYCZWARTE) {
                    zwracanyPunkt = I_PRZESUNIECIE[7];
                }
                break;
            case CWIERC:
                if (docelowaRotacja == Rotacja.POL) {
                    zwracanyPunkt = I_PRZESUNIECIE[2];
                } else if (docelowaRotacja == Rotacja.BRAK) {
                    zwracanyPunkt = I_PRZESUNIECIE[1];
                }
                break;
            case POL:
                if (docelowaRotacja == Rotacja.TRZYCZWARTE) {
                    zwracanyPunkt = I_PRZESUNIECIE[4];
                } else if (docelowaRotacja == Rotacja.CWIERC) {
                    zwracanyPunkt = I_PRZESUNIECIE[3];
                }
                break;
            case TRZYCZWARTE:
                if (docelowaRotacja == Rotacja.BRAK) {
                    zwracanyPunkt = I_PRZESUNIECIE[6];
                } else if (docelowaRotacja == Rotacja.POL) {
                    zwracanyPunkt = I_PRZESUNIECIE[5];
                }
                break;
            default:
                // nigdy się nie wydarzy
                break;
        }
        return zwracanyPunkt;
    }

}
