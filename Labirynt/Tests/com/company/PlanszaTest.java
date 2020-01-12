package com.company;

import org.junit.Test;

import java.awt.*;

import static java.awt.Color.magenta;
import static java.awt.Color.white;
import static org.junit.Assert.*;

public class PlanszaTest {

    //dwa testy dla kolorów po przygotowaniu pól, po jednym dla funkcji koloryzujących, dwa dla komunikatu
    @Test
    public void prepareFields_white() throws Exception {
        Main main = new Main();
        main.side = 7;
        Plansza board = new Plansza();
        board.prepareFields();
        assertEquals(board.tab[2][5].getBackground(), white);
    }

    @Test
    public void prepareFields_magenta() throws Exception {
        Main main = new Main();
        main.side = 9;
        Plansza board = new Plansza();
        board.prepareFields();
        assertEquals(board.tab[3][3].getBackground(), magenta);
    }

    @Test
    public void magenten() throws Exception {
        Main main = new Main();
        main.side = 6;
        Plansza board = new Plansza();
        board.prepareFields();
        board.magenten(4, 1);
        assertEquals(board.tab[4][1].getBackground(), magenta);
    }

    @Test
    public void whiten() throws Exception {
        Main main = new Main();
        main.side = 12;
        Plansza board = new Plansza();
        board.prepareFields();
        board.whiten(8, 10);
        assertEquals(board.tab[8][10].getBackground(), white);
    }

    @Test
    public void greenen() throws Exception {
        Main main = new Main();
        main.side = 8;
        Plansza board = new Plansza();
        board.prepareFields();
        board.greenen(0, 6);
        assertEquals(board.tab[0][6].getBackground(), Color.green);
    }

    @Test
    public void orangen() throws Exception {
        Main main = new Main();
        main.side = 14;
        Plansza board = new Plansza();
        board.prepareFields();
        board.orangen(2, 1);
        assertEquals(board.tab[2][1].getBackground(), Color.orange);
    }

    @Test
    public void recolour() throws Exception {
        Main main = new Main();
        main.side = 10;
        Plansza board = new Plansza();
        board.prepareFields();
        board.orangen(5, 5);
        board.recolour(5, 5);
        assertEquals(board.tab[5][5].getBackground(), magenta);
    }

    @Test
    public void komunikat1() throws Exception {
        Main main = new Main();
        main.side = 17;
        Plansza board = new Plansza();
        board.komunikat("Testowanie ma sens.");
        assertEquals(board.t.getText(), "Testowanie ma sens.");
    }

    @Test
    public void komunikat2() throws Exception {
        Main main = new Main();
        main.side = 17;
        Plansza board = new Plansza();
        board.komunikat("Testowanie nie ma sensu.");
        assertEquals(board.t.getText(), "Testowanie nie ma sensu.");
    }
}