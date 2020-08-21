package view;

import java.awt.EventQueue;

/**
 * Klasa uruchomieniowa.
 */
public final class TetrisMain {

    /**
     * Konstruktor.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * Metoda uruchomieniowa.
     *
     * @param args
     */
    public static void main(final String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI();
            }
        });
    }

}
