package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import model.odbicie.OdbicieOdSciany;

/**
 * Klasa przedstawia tablicę gry.
 */
public class Tablica extends Observable {

    /**
     * Domyślna szerokość tablicy.
     */
    private static final int DOMYSLNA_SZEROKOSC = 10;

    /**
     * Domyślna wysokość tablicy.
     */
    private static final int DOMYSLNA_WYSOKOSC = 20;

    /**
     * Szerokość tablicy.
     */
    private final int szerokosc;

    /**
     * Wysokość tablicy.
     */
    private final int wysokosc;

    /**
     * Zatrzymane klocki na tablicy.
     */
    private final List<Color[]> zatrzymaneKlocki;

    /**
     * Stan końca gry.
     */
    private boolean czyKoniecGry;

    /**
     * Zawiera zbiór klocków.
     */
    private List<Klocek> nieLosoweKlocki;

    /**
     * Indeks obecnej sekwencji nie losowego klocka.
     */
    private int indeksSekwencji;

    /**
     * Następny klocek.
     */
    private Klocek nastepnyKlocek;

    /**
     * Klocek, który aktualnie się porusza.
     */
    private AktywnyKlocek obecnieAktywnyKlocek;

    /**
     * Domyślny konstruktor.
     */
    public Tablica() {
        this(DOMYSLNA_SZEROKOSC, DOMYSLNA_WYSOKOSC);
    }

    /**
     * Konstruktor dla tablicy o specyficznych wymiarach.
     *
     * @param szerokosc Szerokość tablicy.
     * @param wysokosc Wysokość tablicy.
     */
    public Tablica(final int szerokosc, final int wysokosc) {
        super();
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        zatrzymaneKlocki = new LinkedList<Color[]>();
        for (int w = 0; w < this.wysokosc; w++) {
            zatrzymaneKlocki.add(new Color[this.szerokosc]);
        }
        czyKoniecGry = false;
        nieLosoweKlocki = new ArrayList<Klocek>();
        indeksSekwencji = 0;
    }

    /**
     * Pobierz szerokość tablicy.
     *
     * @return szerokość tablicy.
     */
    public int pobierzSzerokosc() {
        return szerokosc;
    }

    /**
     * Pobierz wysokość tablicy.
     *
     * @return wysokość tablicy.
     */
    public int pobierzWysokosc() {
        return wysokosc;
    }



    /**
     * Resetuje tablicę, aby uruchomić nową grę.
     * Metoda jest uruchamiana przed pierwszą grą.
     */
    public void nowaGra() {

        indeksSekwencji = 0;
        zatrzymaneKlocki.clear();
        for (int w = 0; w < wysokosc; w++) {
            zatrzymaneKlocki.add(new Color[szerokosc]);
        }

        czyKoniecGry = false;
        obecnieAktywnyKlocek = nastepnyAktywnyKlocek(true);

        setChanged();
        notifyObservers(new DaneTablicy().pobierzDaneTablicy());
    }

    /**
     * Krok, który odpowiada wykonaniu jednej z operacji:
     * - poruszenie się klocka
     * - zatrzymanie klocka jeśli nie ma miejsca
     * - usuwanie pełnej linii
     */
    public void krok() {
        wDol();
    }

    /**
     * Metoda odpowiada za przesunięcie klocka na dół i jeśli nie jest to możliwe, ma za zadanie obsłużyć blokowanie klocka.
     * W momencie, gdy jest pełna linia, jest cała usuwana.
     */
    public void wDol() {
        if (!czyMoznaPrzesunac(obecnieAktywnyKlocek.przesunNaDol())) {
            dodajKlocekDoDanychTablicy(zatrzymaneKlocki, obecnieAktywnyKlocek);
            sprawdzWiersze();
            if (!czyKoniecGry) {
                obecnieAktywnyKlocek = nastepnyAktywnyKlocek(false);
            }
            setChanged();
            notifyObservers(new DaneTablicy().pobierzDaneTablicy());
        }
    }

    /**
     * Spróbuj przesunąć klocek na lewo.
     */
    public void przesunNaLewo() {
        if (obecnieAktywnyKlocek != null) {
            czyMoznaPrzesunac(obecnieAktywnyKlocek.przesunNaLewo());
        }
    }

    /**
     * Spróbuj przesunąć klocek na prawo.
     */
    public void przesunNaPrawo() {
        if (obecnieAktywnyKlocek != null) {
            czyMoznaPrzesunac(obecnieAktywnyKlocek.przesunNaPrawo());
        }
    }

    /**
     * Spróbuj obrócić klocek zgodnie z ruchem wskazówek zegara.
     */
    public void obrotZgodnieZRuchemWskazowekZegara() {
        if (obecnieAktywnyKlocek != null) {
            if (obecnieAktywnyKlocek.pobierzKlocek() == Klocek.O) {
                czyMoznaPrzesunac(obecnieAktywnyKlocek.obrocZgodnieZRuchemZegara());
            } else {
                final AktywnyKlocek obroconyKlocek = obecnieAktywnyKlocek.obrocZgodnieZRuchemZegara();
                final Punkt[] przesunietePunkty = OdbicieOdSciany.pobierzPrzesuniecieKlocka(obroconyKlocek.pobierzKlocek(),
                        obecnieAktywnyKlocek.pobierzRotacje(),
                        obroconyKlocek.pobierzRotacje());
                for (final Punkt punkt : przesunietePunkty) {
                    final Punkt przesunietyPunkt = obroconyKlocek.pobierzPozycje().przesun(punkt);
                    final AktywnyKlocek tymczasowyKlocek = obroconyKlocek.ustawPozycje(przesunietyPunkt);
                    if (czyMoznaPrzesunac(tymczasowyKlocek)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Spróbuj obrócić klocek przeciwnie do ruchu wskazówek zegara.
     */
    public void obrotPrzeciwnieDoRuchuWskazowekZegara() {
        if (obecnieAktywnyKlocek != null) {
            if (obecnieAktywnyKlocek.pobierzKlocek() == Klocek.O) {
                czyMoznaPrzesunac(obecnieAktywnyKlocek.obrocPrzeciwnieDoRuchuZegara());
            } else {
                final AktywnyKlocek obroconyKlocek = obecnieAktywnyKlocek.obrocPrzeciwnieDoRuchuZegara();
                final Punkt[] przesunietePunkty = OdbicieOdSciany.pobierzPrzesuniecieKlocka(obroconyKlocek.pobierzKlocek(),
                        obecnieAktywnyKlocek.pobierzRotacje(),
                        obroconyKlocek.pobierzRotacje());
                for (final Punkt punkt : przesunietePunkty) {
                    final Punkt przesunietyPunkt = obroconyKlocek.pobierzPozycje().przesun(punkt);
                    final AktywnyKlocek tymczasowyKlocek = obroconyKlocek.ustawPozycje(przesunietyPunkt);
                    if (czyMoznaPrzesunac(tymczasowyKlocek)) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Przesun klocek na sam dół.
     */
    public void zrzucKlocekNaDol() {
        if (!czyKoniecGry) {
            while (czyKlocekMozeIstniec(obecnieAktywnyKlocek.przesunNaDol())) {
                wDol();
            }
            wDol();
        }
    }



    @Override
    public String toString() {
        final List<Color[]> tablica = pobierzTablice();
        tablica.add(new Color[szerokosc]);
        tablica.add(new Color[szerokosc]);
        tablica.add(new Color[szerokosc]);
        tablica.add(new Color[szerokosc]);
        if (obecnieAktywnyKlocek != null) {
            dodajKlocekDoDanychTablicy(tablica, obecnieAktywnyKlocek);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = tablica.size() - 1; i >= 0; i--) {
            final Color[] wiersz = tablica.get(i);
            stringBuilder.append('|');
            for (final Color kolor : wiersz) {
                if (kolor == null) {
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append('*');
                }
            }
            stringBuilder.append("|\n");
            if (i == this.wysokosc) {
                stringBuilder.append(' ');
                for (int j = 0; j < this.szerokosc; j++) {
                    stringBuilder.append('-');
                }
                stringBuilder.append('\n');
            }
        }
        stringBuilder.append('|');
        for (int s = 0; s < szerokosc; s++) {
            stringBuilder.append('-');
        }
        stringBuilder.append('|');
        return stringBuilder.toString();
    }

    /**
     * Metoda odpowiadająca za sprawdzenie, czy dany klocek może zostać przesunięty.
     *
     * @param aktywnyKlocek pozycja do przesunięcia aktywnego klocka.
     * @return jeśli można przesunąć zwracana jest wartość 'true'.
     */
    private boolean czyMoznaPrzesunac(final AktywnyKlocek aktywnyKlocek) {
        boolean czyMoznaPrzesunac = false;
        if (czyKlocekMozeIstniec(aktywnyKlocek)) {
            obecnieAktywnyKlocek = aktywnyKlocek;
            czyMoznaPrzesunac = true;
            setChanged();
            notifyObservers(new DaneTablicy().pobierzDaneTablicy());
        }
        return czyMoznaPrzesunac;
    }

    /**
     * Funkcja sprawdza, czy klocek może znajdować się na tablicy.
     * Jeśli klocek przekracza wymiary tablicy lub nachodzi na inny, to nie może istnieć.
     *
     * @param aktywnyKlocek aktywny klocek do sprawdzenia.
     * @return zwraca 'true', jeśli klocek może istnieć.
     */
    private boolean czyKlocekMozeIstniec(final AktywnyKlocek aktywnyKlocek) {
        boolean czyKlocekMozeIstniec = true;
        for (final Punkt punkt : aktywnyKlocek.pobierzPunktyKlockaNaTablicy()) {
            if (punkt.pobierzX() < 0 || punkt.pobierzX() >= szerokosc) {
                czyKlocekMozeIstniec = false;
            }
            if (punkt.pobierzY() < 0) {
                czyKlocekMozeIstniec = false;
            }
        }
        return czyKlocekMozeIstniec && !czyJestKolizja(aktywnyKlocek);
    }

    /**
     * Dodaje ruchomy klocek do listy.
     *
     * @param zatrzymaneKlocki Lista zatrzymanych klocków.
     * @param aktywnyKlocek aktywny klocek do ustawienia.
     */
    private void dodajKlocekDoDanychTablicy(final List<Color[]> zatrzymaneKlocki,
                                            final AktywnyKlocek aktywnyKlocek) {
        for (final Punkt punkt : aktywnyKlocek.pobierzPunktyKlockaNaTablicy()) {
            ustawPunkt(zatrzymaneKlocki, punkt, aktywnyKlocek.pobierzKlocek().pobierzKolor());
        }
    }

    /**
     * Metoda sprawdza, czy na tablicy są całe wypełnione wiersze.
     */
    private void sprawdzWiersze() {
        final List<Integer> wypelnioneWiersze = new ArrayList<>();
        for (final Color[] wiersz : zatrzymaneKlocki) {
            boolean czyJestWypelniony = true;
            for (final Color kolor : wiersz) {
                if (kolor == null) {
                    czyJestWypelniony = false;
                    break;
                }
            }
            if (czyJestWypelniony) {
                wypelnioneWiersze.add(zatrzymaneKlocki.indexOf(wiersz));
                setChanged();
            }
        }
        if (!wypelnioneWiersze.isEmpty()) {
            for (int i = wypelnioneWiersze.size() - 1; i >= 0; i--) {
                final Color[] wiersz = zatrzymaneKlocki.get(wypelnioneWiersze.get(i));
                zatrzymaneKlocki.remove(wiersz);
                zatrzymaneKlocki.add(new Color[szerokosc]);
            }
        }
        notifyObservers(wypelnioneWiersze.toArray(new Integer[wypelnioneWiersze.size()]));
    }

    /**
     * Funkcja kopiująca tablicę.
     *
     * @return Zwraca kopię tablicy.
     */
    private List<Color[]> pobierzTablice() {
        final List<Color[]> tablica = new ArrayList<Color[]>();
        for (final Color[] wiersz : zatrzymaneKlocki) {
            tablica.add(wiersz.clone());
        }
        return tablica;
    }

    /**
     * Metoda sprawdza, czy punkt jest na tablicy.
     *
     * @param tablicaKlockow tymczasowa tablica.
     * @param punkt Punkt do sprawdzenia.
     * @return Zwraca 'true', gdy punkt znajduje się na tablicy.
     */
    private boolean czyPunktJestNaTablicy(final List<Color[]> tablicaKlockow, final Punkt punkt) {
        return punkt.pobierzX() >= 0 && punkt.pobierzX() < szerokosc && punkt.pobierzY() >= 0
                && punkt.pobierzY() < tablicaKlockow.size();
    }

    /**
     * Ustawia bloczek jako kolor na tablicy punktowej (pojedyncza kratka).
     *
     * @param tablica Tablica do ustawiania punktów.
     * @param punkt punkt na tablicy.
     * @param kolor kolor punktu.
     */
    private void ustawPunkt(final List<Color[]> tablica,
                            final Punkt punkt,
                            final Color kolor) {

        if (czyPunktJestNaTablicy(tablica, punkt)) {
            final Color[] wiersz = tablica.get(punkt.pobierzY());
            wiersz[punkt.pobierzX()] = kolor;
        } else if (!czyKoniecGry) {
            czyKoniecGry = true;
            setChanged();
            notifyObservers(czyKoniecGry);
        }
    }

    /**
     * Zwraca kolor bloku jako specyficzny punkt.
     *
     * @param punkt konkretny punkt.
     * @return Kolor punktu albo null, jeśli punktu nie ma.
     */
    private Color pobierzPunkt(final Punkt punkt) {
        Color kolor = null;
        if (czyPunktJestNaTablicy(zatrzymaneKlocki, punkt)) {
            kolor = zatrzymaneKlocki.get(punkt.pobierzY())[punkt.pobierzX()];
        }
        return kolor;
    }

    /**
     * Funkcja sprawdza, czy następuje czyJestKolizja z innymi blokami.
     *
     * @param aktywnyKlocek aktualnie spadający klocek.
     * @return Zwraca 'true', jeśli klocek koliduje z innym.
     */
    private boolean czyJestKolizja(final AktywnyKlocek aktywnyKlocek) {
        boolean czyJestKolizja = false;
        for (final Punkt punkt : aktywnyKlocek.pobierzPunktyKlockaNaTablicy()) {
            if (pobierzPunkt(punkt) != null) {
                czyJestKolizja = true;
            }
        }
        return czyJestKolizja;
    }

    /**
     * Metoda odpowiada za zwrócenie następnego aktywnego klocka.
     *
     * @param czyZresetowac zmienna odpowiadająca za resetowanie.
     * @return zwracany jest nowy aktywny klocek.
     */
    private AktywnyKlocek nastepnyAktywnyKlocek(final boolean czyZresetowac) {

        if (nastepnyKlocek == null || czyZresetowac) {
            przygotujNastepnyAktywnyKlocek();
        }
        final Klocek nastepnyKlocek = this.nastepnyKlocek;
        int poczatkowaWspolrzednaY = wysokosc - 1;
        if (this.nastepnyKlocek == Klocek.I) {
            poczatkowaWspolrzednaY--;
        }
        przygotujNastepnyAktywnyKlocek();
        return new AktywnyKlocek(nastepnyKlocek, new Punkt((szerokosc - this.nastepnyKlocek.pobierzSzerokosc()) / 2, poczatkowaWspolrzednaY));
    }

    /**
     * Przygotuj podgląd nastepnego klocka.
     */
    private void przygotujNastepnyAktywnyKlocek() {
        final boolean czyBedzieNastepnyKlocek = nastepnyKlocek != null;
        if (nieLosoweKlocki == null || nieLosoweKlocki.isEmpty()) {
            nastepnyKlocek = Klocek.pobierzLosowyKlocek();
        } else {
            indeksSekwencji %= nieLosoweKlocki.size();
            nastepnyKlocek = nieLosoweKlocki.get(indeksSekwencji++);
        }
        if (czyBedzieNastepnyKlocek && !czyKoniecGry) {
            setChanged();
            notifyObservers(nastepnyKlocek);
        }
    }

    /**
     * Klasa reprezentująca tablicę widoczna dla gracza.
     * Klasa obsługuje obecny klocek oraz wszystkie zablokowane elementy.
     */
    protected final class DaneTablicy {

        private final List<Color[]> zbiorElementowNaTablicy;

        protected DaneTablicy() {
            zbiorElementowNaTablicy = pobierzTablice();
            zbiorElementowNaTablicy.add(new Color[szerokosc]);
            zbiorElementowNaTablicy.add(new Color[szerokosc]);
            zbiorElementowNaTablicy.add(new Color[szerokosc]);
            zbiorElementowNaTablicy.add(new Color[szerokosc]);
            if (obecnieAktywnyKlocek != null) {
                dodajKlocekDoDanychTablicy(zbiorElementowNaTablicy, obecnieAktywnyKlocek);
            }
        }

        /**
         * Metoda odpowiada za zwrócenie tablicy z obecnymi elementami.
         *
         * @return zwracany jest zbior elementów.
         */
        protected List<Color[]> pobierzDaneTablicy() {
            final List<Color[]> tablicaElementow = new ArrayList<Color[]>();
            for (final Color[] wiersz : zbiorElementowNaTablicy) {
                tablicaElementow.add(wiersz.clone());
            }
            return tablicaElementow;
        }

    }
}
