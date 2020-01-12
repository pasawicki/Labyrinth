package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    //cztery testy badające ruchy strzałkami oraz przy okazji IsItLegal
    //a Legal to był znany francuski szachista
    @Test
    public void ruchwLewodoOporu() throws Exception {
        Main main = new Main();
        main.side = 7;
        main.wherex = 3;
        main.wherey = 3;
        Plansza.prepareFields();
        for (int i=0; i<100; i++) main.ruch(3);
        assertEquals(0, main.wherey);
    }

    @Test
    public void ruchwPrawodoOporu() throws Exception {
        Main main = new Main();
        main.side = 7;
        main.wherex = 3;
        main.wherey = 3;
        Plansza.prepareFields();
        for (int i=0; i<100; i++) main.ruch(1);
        assertEquals(6, main.wherey);
    }

    @Test
    public void ruchwGoredoOporu() throws Exception {
        Main main = new Main();
        main.side = 7;
        main.wherex = 3;
        main.wherey = 3;
        Plansza.prepareFields();
        for (int i=0; i<100; i++) main.ruch(0);
        assertEquals(0, main.wherex);
    }

    @Test
    public void ruchwDoldoOporu() throws Exception {
        Main main = new Main();
        main.side = 7;
        main.wherex = 3;
        main.wherey = 3;
        Plansza.prepareFields();
        for (int i=0; i<100; i++) main.ruch(2);
        assertEquals(6, main.wherex);
    }

    //pojedyncze testy getterów, dla porządku
    @Test
    public void getWartosc() throws Exception {
        Main main = new Main();
        main.wartosc[3][5] = 73;
        assertEquals("73", main.getWartosc(3, 5));
    }

    @Test
    public void getSide() throws Exception {
        Main main = new Main();
        main.side = 37;
        assertEquals(37, main.getSide());
    }

    @Test
    public void getStart() throws Exception {
        Main main = new Main();
        main.start = 0;
        assertEquals(0, main.getStart());
    }

    //a teraz kilka testów sprawdzających poprawną ocenę zwycięstwa
    @Test
    public void winConditionwithoutLicznik() throws Exception {
        Main main = new Main();
        main.side = 7;
        main.wherex = 6; main.wherey = 6; main.wartosc[6][6] = 14; main.whereh = 13;
        assertEquals(false, main.winCondition());
    }

    @Test
    public void winConditionwithoutPolozenie() throws Exception {
        Main main = new Main();
        main.side = 10;
        main.wartosc[9][9] = 23; main.whereh = 22; for (int i=1; i<24; i++) main.licznik[i] = 1;
        assertEquals(false, main.winCondition());
    }

    @Test
    public void winConditionwithoutPlaceinHistory() throws Exception {
        Main main = new Main();
        main.side = 4;
        main.wherex = 3; main.wherey = 3; main.wartosc[3][3] = 4; for (int i=1; i<5; i++) main.licznik[i] = 1;
        assertEquals(false, main.winCondition());
    }

    @Test
    public void winConditionwithEverything() throws Exception {
        Main main = new Main();
        main.side = 16;
        main.wherex = 15; main.wherey = 15; main.wartosc[15][15] = 26; main.whereh = 25; for (int i=1; i<27; i++) main.licznik[i] = 1;
        assertEquals(true, main.winCondition());
    }

    //i test setera dla porządku
    @Test
    public void setKontrolka() throws Exception {
        Main main = new Main();
        main.setKontrolka(true);
        assertEquals(true, main.kontrolka);
    }

    //czy wczytywanie z pliku aby na pewno działa poprawnie? - to przecież kluczowe!
    @Test
    public void readFromFilesidetest1() throws Exception {
        Main main = new Main();
        main.readFromFile("plansza0.csv");
        assertEquals(7, main.side);
    }

    @Test
    public void readFromFilesidetest2() throws Exception {
        Main main = new Main();
        main.readFromFile("plansza0d.csv");
        assertEquals(5, main.side);
    }

    @Test
    public void readFromFilewartosctest() throws Exception {
        Main main = new Main();
        main.readFromFile("plansza0.csv");
        assertEquals(15, main.wartosc[4][2]);
    }

    @Test
    public void readFromFilehistoryplacementtest() throws Exception {
        Main main = new Main();
        main.readFromFile("plansza0c.csv");
        assertEquals(13, main.whereh);
    }
}