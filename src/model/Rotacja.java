
package model;

import java.util.Random;

/**
 * Enum typów obrotu
 */
public enum Rotacja {

    /**
     * Brak obrotu.
     */
    BRAK,

    /**
     * Obrót klocka o 90 stopni.
     */
    CWIERC,

    /**
     * Obrót klocka o 180 stopni.
     */
    POL,

    /**
     * Obrót klocka o 270 stopni.
     */
    TRZYCZWARTE;

    /**
     * Obiekt wykorzystywany do wybrania losowego obrotu
     */
    private static final Random LOSOWO = new Random();

    /**
     * Metoda obraza klocek o 90 stopni zgodnie z ruchem wskazówek zegara.
     *
     * @return zwraca obiekt obrócony o 90 stopni zgodnie z ruchem wskazówek zegara.
     */
    public Rotacja zgodnieZRuchemZegara() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    /**
     * Metoda obraza klocek o 90 stopni przeciwnie do ruchu wskazówek zegara.
     *
     * @return zwraca obiekt obrócony o 90 stopni przeciwnie do ruchu wskazówek zegara.
     */
    public Rotacja przeciwnieDoRuchuZegara() {
        return values()[(this.ordinal() - 1 + values().length) % values().length];
    }

    /**
     * Tworzenie rotacji o losowy kąt obrotu.
     *
     * @return nowa losowa rotacja.
     */
    public static Rotacja random() {
        return values()[LOSOWO.nextInt(values().length)];
    }

}
