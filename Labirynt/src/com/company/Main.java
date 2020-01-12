//AUTOR: Paweł Sawicki
//Na podstawie łamigłówki Marka Penszki ("Wiedza i Życie", 10/1998)

package com.company;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static int wherex = 0; //położenie
    static int wherey = 0; //położenie
    static int whereh = 0; //aktualne miejsce w historii
    static int side = 0; //bok kwadratu
    static boolean visited[][] = new boolean[100][100]; //czy pole było odwiedzone
    static int licznik[] = new int[10000]; //ile razy odwiedzono pola z daną liczbą
    static int wartosc[][] = new int [100][100]; //liczby na polach
    public static List<Integer> history = new ArrayList<Integer>(); //historia rozgrywki
    public static boolean kontrolka = false; //aktywuje działanie głównej pętli
    public static int start = 0; //licznik plansz

    public static void ruch(int kier) {
        if (kier<4) { //ruch za pomocą strzałek w panelu
            int newx = wherex;
            int newy = wherey;
            if (kier == 0) newx--;
            if (kier == 1) newy++;
            if (kier == 2) newx++;
            if (kier == 3) newy--;
            if (isitLegal(newx, newy)) {
                Plansza.orangen(wherex, wherey);
                wherex = newx;
                wherey = newy;
                Plansza.greenen(wherex, wherey);
                visited[wherex][wherey] = true;
                whereh++;
                for (int i = history.size()-1; i >= 2*whereh; i--) {history.remove(i);}
                history.add(wherex); history.add(wherey);
                licznik[wartosc[wherex][wherey]]++;
                if (licznik[wartosc[wherex][wherey]]>1) Plansza.komunikat("Wartość zdublowana - zalecane cofnięcie ruchu!");
                else if (winCondition()) Plansza.komunikat("Partia wygrana - gratulacje!");
                else Plansza.komunikat(" ");
            } else Plansza.komunikat("Ruch w wybraną stronę nie jest możliwy.");
        }
        if (kier==4) { //cofnij ruch
            if (whereh>0) {
                Plansza.recolour(wherex, wherey);
                visited[wherex][wherey] = false;
                licznik[wartosc[wherex][wherey]]--;
                whereh--;
                wherex = history.get(2 * whereh);
                wherey = history.get(2 * whereh + 1);
                Plansza.greenen(wherex, wherey);
                Plansza.komunikat(" ");
            }
            else Plansza.komunikat("Operacja niewykonalna.");
        }
        if (kier==5) { //ponów ruch
            if (whereh < history.size() / 2 - 1) {
                Plansza.orangen(wherex, wherey);
                whereh++;
                wherex = history.get(2 * whereh);
                wherey = history.get(2 * whereh + 1);
                Plansza.greenen(wherex, wherey);
                licznik[wartosc[wherex][wherey]]++;
                if (licznik[wartosc[wherex][wherey]]>2) Plansza.komunikat("Wartość zdublowana - zalecane cofnięcie ruchu!");
                else Plansza.komunikat(" ");
            }
            else Plansza.komunikat("Operacja niewykonalna.");
        }
    }

    public static boolean isitLegal(int x, int y) { //czy ruch jest legalny?
        if ((x<0) || (y<0) || (x>=side) || (y>=side) || (visited[x][y])) return(false);
        else return(true);
    }

    public static String getWartosc(int x, int y) {
        return(String.valueOf(wartosc[x][y]));
    }

    public static int getSide() {
        return(side);
    }

    public static int getStart() {
        return(start);
    }

    public static boolean winCondition() { //czy przypadkiem właśnie nie wygrałeś?
        int k=1; for (int i=1; i<=wartosc[side-1][side-1]; i++) if (licznik[i] != 1) {k = 0; break;}
        if ((k==1) && (wherex==side-1) && (wherey==side-1) && (whereh+1==wartosc[side-1][side-1])) return(true);
        else return(false);
    }

    public static void setKontrolka(boolean b) {
        kontrolka = b;
    }

    public static void readFromFile(String nazwa) { //wczytanie danych z pliku i reset wszystkich możliwych wartości
        File file = new File(nazwa);
        try {
            Scanner scanner = new Scanner(file);
            String[] record = scanner.nextLine().split(";");
            side = Integer.parseInt(record[0]);
            for (int i = 0; i < side; i++) {
                String[] record2 = scanner.nextLine().split(";");
                for (int j = 0; j < side; j++) {
                    wartosc[i][j] = Integer.parseInt(record2[j]);
                    visited[i][j]=false;
                }
            }
            whereh=-1; for (int i = 0; i < 10000; i++) licznik[i]=0; history.clear();
            while (scanner.hasNext()) {
                record = scanner.nextLine().split(";");
                String[] record2 = scanner.nextLine().split(";");
                history.add(Integer.parseInt(record[0])); history.add(Integer.parseInt(record2[0]));
                licznik[wartosc[Integer.parseInt(record[0])][Integer.parseInt(record2[0])]]++; whereh++;
                visited[Integer.parseInt(record[0])][Integer.parseInt(record2[0])]=true;
            }
            scanner.close();
            wherex = history.get(2 * whereh);
            wherey = history.get(2 * whereh + 1);
            Plansza.komunikat(" ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeInFile(String nazwa) { //zapis danych do pliku w rozsądnie czytelnym formacie
        File file = new File(nazwa);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(side);
            for (int i = 0; i < side; i++) {
                for (int j = 0; j < side; j++) printWriter.print(wartosc[i][j] + ";");
                printWriter.println();
            }
            for (int i = 0; i <= whereh; i++) {
                printWriter.println(history.get(2*i));
                printWriter.println(history.get(2*i+1));
            }
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame f = null;
        Singleton object = Singleton.getInstance();
        readFromFile(object.getZrodlo());
        kontrolka = true;

        while (true) { //pętla nieskończona, która w razie potrzeby zamyka starą planszę, tworzy nową, koloruje pola
            System.out.println(kontrolka);
            if (kontrolka) {
                System.out.println("Tworzę planszę...");
                if (start>0) {f.setVisible(false); f.dispose();}
                f = new Plansza();
                f.setSize(1350, 900);
                f.setLocation(100, 100);
                f.setVisible(true);
                for (int i=0; i<whereh; i++) Plansza.orangen(history.get(2*i), history.get(2*i+1));
                Plansza.greenen(history.get(2*whereh), history.get(2*whereh+1));
                kontrolka = false; start++;
            }
        }
    }
}
