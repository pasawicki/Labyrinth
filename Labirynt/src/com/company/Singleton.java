//Klasa singleton - choć jeden wzorzec projektowy.
//Zwraca źródło pierwszej planszy otwierającej się po włączeniu programu lub wciśnięciu "Nowa gra".

package com.company;

public class Singleton {
    private static Singleton zrodlo = new Singleton();

    private Singleton(){} //prywatny konstruktor, żeby nikt przypadkiem nie tworzył zbędnych instancji na prawo i lewo

    public static Singleton getInstance() {
        return zrodlo;
    }

    public String getZrodlo() {
        return "plansza0.csv";
    }
}
