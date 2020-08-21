package model;

/**
 * Klasa przedstawiająca klocek z jego przekręceniem.
 * Aktywny klocek jest niezmienny.
 */
public final class AktywnyKlocek {

    /**
     * Liczba punktów klocka.
     */
    private static final int ILOSCBLOKOWKLOCKA = 4;

    /**
     * Klocek.
     */
    private final Klocek klocek;

    /**
     * Położenie klocka na tablicy.
     */
    private final Punkt polozenie;

    /**
     * Wartość obrotu klocka.
     */
    private final Rotacja rotacja;

    /**
     * Konstruktor używa konkretnego typu i pozycji klocka.
     * Początkowy obrót obiektu ustawiony jest na 'BRAK'
     *
     * @param klocek  typ klocka.
     * @param pozycja pozycja klocka na tablicy.
     */
    public AktywnyKlocek(final Klocek klocek,
                         final Punkt pozycja) {

        this(klocek, pozycja, Rotacja.BRAK);
    }

    /**
     * Konstruktor używa konkretnej typu, pozycji i rotacji klocka.
     * Początkowy obrót obiektu jest z góry założony.
     *
     * @param klocek typ klocka.
     * @param pozycja pozycja klocka na tablicy.
     * @param rotacja początkowy obrót klocka.
     */
    public AktywnyKlocek(final Klocek klocek,
                         final Punkt pozycja,
                         final Rotacja rotacja) {

        this.klocek = klocek;
        polozenie = pozycja;
        this.rotacja = rotacja;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder(polozenie.toString());
        stringBuilder.append('\n');
        final String[][] bloki = new String[ILOSCBLOKOWKLOCKA][ILOSCBLOKOWKLOCKA];
        for (int wysokosc = 0; wysokosc < ILOSCBLOKOWKLOCKA; wysokosc++) {
            for (int szerokosc = 0; szerokosc < ILOSCBLOKOWKLOCKA; szerokosc++) {
                bloki[szerokosc][wysokosc] = "   ";
            }
        }
        for (final Punkt punkt : pobierzLokalnePunkty()) {
            bloki[punkt.pobierzY()][punkt.pobierzX()] = "[ ]";
        }

        for (int wysokosc = ILOSCBLOKOWKLOCKA - 1; wysokosc >= 0; wysokosc--) {
            for (int szerokosc = 0; szerokosc < ILOSCBLOKOWKLOCKA; szerokosc++) {
                if (bloki[wysokosc][szerokosc] != null) {
                    stringBuilder.append(bloki[wysokosc][szerokosc]);
                }
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * Pobierz typ poruszającego się klocka
     *
     * @return Obiekt klocka opisujący klocek.
     */
    protected Klocek pobierzKlocek() {
        return klocek;
    }

    /**
     * Pobierz aktualną pozycję klocka.
     *
     * @return zwraca pozycję klocka.
     */
    protected Punkt pobierzPozycje() {
        return polozenie;
    }

    /**
     * Pobierz aktualny obrót klocka.
     *
     * @return zwraca wartość rotacji klocka.
     */
    protected Rotacja pobierzRotacje() {
        return rotacja;
    }

    /**
     * Pobierz punkty obróconego klocka przekonwertowane na punkty na tablicy.
     *
     * @return punkty klocka na tablicy.
     */
    protected Punkt[] pobierzPunktyKlockaNaTablicy() {
        return pobierzPunkty(polozenie);
    }

    /**
     * Obraca klocek zgodnie z ruchem wskazówek zegara.
     *
     * @return nowy, obrócony poruszający się klocek.
     */
    protected AktywnyKlocek obrocZgodnieZRuchemZegara() {
        return new AktywnyKlocek(klocek,
                polozenie,
                rotacja.zgodnieZRuchemZegara());
    }

    /**
     * Obraca klocek przeciwnie do ruchu wskazówek zegara.
     *
     * @return nowy, obrócony poruszający się klocek.
     */
    protected AktywnyKlocek obrocPrzeciwnieDoRuchuZegara() {
        return new AktywnyKlocek(klocek,
                polozenie,
                rotacja.przeciwnieDoRuchuZegara());
    }

    /**
     * Metoda przesuwa klocek w przesunNaLewo na tablicy.
     *
     * @return zwracany jest przesunięty klocek.
     */
    protected AktywnyKlocek przesunNaLewo() {
        return new AktywnyKlocek(klocek,
                polozenie.przesun(-1, 0),
                rotacja);
    }

    /**
     * Metoda przesuwa klocek w przesunNaPrawo na tablicy.
     *
     * @return zwracany jest przesunięty klocek.
     */
    protected AktywnyKlocek przesunNaPrawo() {
        return new AktywnyKlocek(klocek,
                polozenie.przesun(1, 0),
                rotacja);
    }

    /**
     * Metoda przesuwa klocek w dół na tablicy.
     *
     * @return zwracany jest przesunięty klocek.
     */
    protected AktywnyKlocek przesunNaDol() {
        return new AktywnyKlocek(klocek,
                polozenie.przesun(0, -1),
                rotacja);
    }

    /**
     * Zwracany jest klocek obecnego typu i o tej samej rotacji w konkretnym miejscu.
     *
     * @param pozycja pozycja nowego klocka.
     * @return nowy klocek po zmianie pozycji.
     */
    protected AktywnyKlocek ustawPozycje(final Punkt pozycja) {
        return new AktywnyKlocek(klocek, pozycja, rotacja);
    }

    /**
     * Pobierz punkty klocka po zmianach względem osi pobierzX i pobierzY.
     *
     * @param punkt punkt do przesunięcia.
     * @return zbiór punktów klocka.
     */
    private Punkt[] pobierzPunkty(final Punkt punkt) {

        final Punkt[] punktyKlocka = klocek.pobierzPunkty();

        for (int i = 0; i < punktyKlocka.length; i++) {
            final Punkt pojedynczyPunktKlocka = punktyKlocka[i];
            if (klocek != Klocek.O) {
                switch (rotacja) {
                    case CWIERC:
                        punktyKlocka[i] = new Punkt(pojedynczyPunktKlocka.pobierzY(),
                                klocek.pobierzSzerokosc() - pojedynczyPunktKlocka.pobierzX() - 1);
                        break;
                    case POL:
                        punktyKlocka[i] = new Punkt(klocek.pobierzSzerokosc() - pojedynczyPunktKlocka.pobierzX() - 1,
                                klocek.pobierzSzerokosc() - pojedynczyPunktKlocka.pobierzY() - 1);
                        break;
                    case TRZYCZWARTE:
                        punktyKlocka[i] = new Punkt(klocek.pobierzSzerokosc() - pojedynczyPunktKlocka.pobierzY() - 1,
                                pojedynczyPunktKlocka.pobierzX());
                        break;
                    default:
                }
            }
            if (punkt != null) {
                punktyKlocka[i] = punktyKlocka[i].przesun(punkt);
            }
        }
        return punktyKlocka;
    }

    /**
     * Pobierz punkty obecnego klocka po rotacji.
     *
     * @return zbiór punktów obróconego klocka.
     */
    private Punkt[] pobierzLokalnePunkty() {
        return pobierzPunkty(null);
    }


}
