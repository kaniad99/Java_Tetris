package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Tablica;

/**
 * Klasa ta odpowiada za sterowanie w grze.
 *
 */
public class Sterowanie extends KeyAdapter {

    /**
     * Klawisz odpowiedzialny za przesunięcie klocka w lewo.
     */
    private final String przesuniecieNaLewoKlawisz;

    /**
     * Klawisz odpowiedzialny za przesunięcie klocka w prawo.
     */
    private final String przesuniecieNaPrawoKlawisz;

    /**
     * Klawisz odpowiedzialny za obrócenie klocka w lewo.
     */
    private final String obrotNaLewoKlawisz;

    /**
     * Klawisz odpowiedzialny za obrócenie klocka w prawo.
     */
    private final String obrotNaPrawoKlawisz;

    /**
     * Klawisz nr 2 odpowiedzialny za obrócenie klocka w prawo.
     */
    private final String obrotNaPrawoKlawisz2;

    /**
     * Klawisz odpowiedzialny za przesunięcie klocka na dół.
     */
    private final String przesunNaDolKlawisz;

    /**
     * Klawisz odpowiedzialny za zrzucenie klocka na sam dół.
     */
    private final String zrzucKlawisz;

    /**
     * Tablica gry.
     */
    private final Tablica tablica;

    /**
     * Stan klocka.
     */
    private Boolean czyKlocekSiePorusza;


    /**
     * Tworzenie sterowania w grze.
     *
     * @param tablica Tablica gry.
     */
    public Sterowanie(final Tablica tablica) {
        super();
        this.przesuniecieNaLewoKlawisz = "Left";
        this.przesuniecieNaPrawoKlawisz = "Right";
        this.obrotNaPrawoKlawisz2 = "Up";
        this.obrotNaLewoKlawisz = "Z";
        this.obrotNaPrawoKlawisz = "X";
        this.przesunNaDolKlawisz = "Down";
        this.zrzucKlawisz = "Space";
        this.tablica = tablica;
        this.czyKlocekSiePorusza = true;
    }


    /**
     * Metoda odpowiedzialna za ustawienie poruszania się klocka.
     *
     * @param czySiePorusza Stan klocka.
     */
    public void czyKlocekSiePorusza(final Boolean czySiePorusza) {
        czyKlocekSiePorusza = czySiePorusza;
    }

    /**
     * Metoda tworzy sterowanie klockami.
     *
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        final String nazwaKlawisza = KeyEvent.getKeyText(keyEvent.getKeyCode());
        if (czyKlocekSiePorusza) {
            if (nazwaKlawisza.equals(przesuniecieNaLewoKlawisz)) {
                tablica.przesunNaLewo();
            } else if (nazwaKlawisza.equals(przesuniecieNaPrawoKlawisz)) {
                tablica.przesunNaPrawo();
            } else if (nazwaKlawisza.equals(obrotNaLewoKlawisz)) {
                tablica.obrotPrzeciwnieDoRuchuWskazowekZegara();
            } else if (nazwaKlawisza.equals(obrotNaPrawoKlawisz) || nazwaKlawisza.equals(obrotNaPrawoKlawisz2)) {
                tablica.obrotZgodnieZRuchemWskazowekZegara();
            } else if (nazwaKlawisza.equals(przesunNaDolKlawisz)) {
                tablica.wDol();
            } else if (nazwaKlawisza.equals(zrzucKlawisz)) {
                tablica.zrzucKlocekNaDol();
            }
        }
    }
}
