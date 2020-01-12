//Bardzo miła klasa odpowiadająca za rysowanie planszy. Co się stało z naszą klasą?

package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Model{
    char  tab[][] = new char[com.company.Main.getSide()][com.company.Main.getSide()];
}

public class Plansza extends JFrame { //plansza, jak widać, dziedziczy po JFramie
    static JButton[][] tab = new JButton[com.company.Main.getSide()][com.company.Main.getSide()] ;
    JPanel plansza = new JPanel();
    JPanel sterowanie = new JPanel();
    static JTextField t = new JTextField(10);
    JButton leftButton = new JButton("<");
    JButton rightButton = new JButton(">");
    JButton upButton = new JButton("^");
    JButton downButton = new JButton("v");
    JButton undoButton = new JButton("undo");
    JButton redoButton = new JButton("redo");
    //całe mnóstwo elementów planszy
    static JMenuBar mb = new JMenuBar();
    static JMenu gra = new JMenu("Rozgrywka");
    static JMenu pomoc = new JMenu("Pomoc");
    static JMenuItem nowa = new JMenuItem("Nowa gra");
    static JMenuItem wczyt = new JMenuItem("Wczytaj grę");
    static JMenuItem zapis = new JMenuItem("Zapisz grę");
    static JMenuItem pom1 = new JMenuItem("Chcę poznać zasady gry");
    static JMenuItem pom2 = new JMenuItem("Chcę nauczyć się edycji planszy");
    static JMenuItem pom3 = new JMenuItem("Chcę umrzeć");
    static JMenuItem pom4 = new JMenuItem("Chcę mówić po słowacku");
    static String wiad[] = new String[5];

    public static void prepareFields() { //przygotowanie i wstępne kolorowanie pól planszy
        int i,j;
        for (i=0; i < com.company.Main.getSide(); i++)
            for (j=0; j < com.company.Main.getSide(); j++){
                tab[i][j]=new JButton("" );
                tab[i][j].setText(com.company.Main.getWartosc(i, j));
                if((i+j) % 2 == 0) (tab[i][j]).setBackground(Color.magenta);
                else tab[i][j].setBackground(Color.white);
            }

    }
    //funkcje koloryzujące - nie mylić z szamponami koloryzującymi
    public static void magenten(int x, int y) {tab[x][y].setBackground(Color.magenta);}
    public static void whiten(int x, int y) {tab[x][y].setBackground(Color.white);}
    public static void greenen(int x, int y) {tab[x][y].setBackground(Color.green);}
    public static void orangen(int x, int y) {tab[x][y].setBackground(Color.orange);}
    public static void recolour(int x, int y) {if ((x+y)%2==0) magenten(x, y); else whiten(x, y);}
    public static void komunikat(String s) {t.setText(s);} //ażeby móc nawrzucać graczowi

    public Plansza() { //funkcja tworząca planszę
        int i,j;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1,2));
        cp.add(plansza); cp.add(sterowanie);
        sterowanie.setLayout(new GridLayout(com.company.Main.getSide(),1));
        sterowanie.add(t);
        sterowanie.add(leftButton);
        sterowanie.add(rightButton);
        sterowanie.add(upButton);
        sterowanie.add(downButton);
        sterowanie.add(undoButton);
        sterowanie.add(redoButton);
        gra.add(nowa); gra.add(wczyt); gra.add(zapis);
        pomoc.add(pom1); pomoc.add(pom2); //pomoc.add(pom3); pomoc.add(pom4); easter egg był, ale się zmył
        mb.add(gra); mb.add(pomoc);
        this.setJMenuBar(mb); //odtąd mamy pasek u góry
//ustawiamy treść wiadomości dostępnych w Pomocy
        wiad[1]="Przejdź z lewego górnego do dolnego prawego rogu w taki sposób, aby zebrać wszystkie liczby naturalne pomiędzy 1 a tą na końcu ścieżki, każdą dokładnie raz, nie ruszając żadnych innych. Do przemieszczania się używaj przycisków z prawego panelu.";
        wiad[2]="Stwórz w folderze programu plik o rozszerzeniu csv. Wewnątrz niego pierwsza liczba n oznacza rozmiar planszy, kolejne n^2 wartości opisuje rozkład liczb na planszy, a zakończenie pliku obrazuje historię gry - w najprostszym przypadku będą to dwa zera. Dla zorientowania się w interpunkcji sprawdź plik gry plansza0.csv.";
        //wiad[3]="Jeżeli chcesz też zrobić coś innego, upewnij się, aby zrobić to najpierw.";
        //wiad[4]="Kluczowe zwroty słowackie to pozor vlak (strzeż się pociągu) oraz balicek na cestou (prowiant na drogę). Używaj ich dla uniknięcia tragicznej śmierci z głodu lub w wypadku komunikacyjnym. Wędrując rano ze słońcem po prawej ręce, a wieczorem po lewej, wkrótce dotrzesz do Polski. Tam już sobie poradzisz.";

        t.setFont(t.getFont().deriveFont(30.0f));
        plansza.setLayout(new GridLayout(com.company.Main.getSide(), com.company.Main.getSide()));

//relacje pomiędzy elementami planszy już ustalone, a teraz przygotujmy pola
        prepareFields();
        for (i=0; i < com.company.Main.getSide(); i++) for (j=0; j < com.company.Main.getSide(); j++) {
            plansza.add(tab[i][j]);
            (tab[i][j]).addActionListener(new B(i,j));
        }//cała plansza narysowana, ale trzeba jeszcze zmusić przyciski do pracy niewolniczej
        leftButton.addActionListener(new C(3));
        rightButton.addActionListener(new C(1));
        upButton.addActionListener(new C(0));
        downButton.addActionListener(new C(2));
        undoButton.addActionListener(new C(4));
        redoButton.addActionListener(new C(5));

        if (com.company.Main.getStart()==0) {//przed dodaniem tego warunku program zachowywał się jak paciorkowiec!!!
            pom1.addActionListener(new D(1));
            pom2.addActionListener(new D(2));
            //pom3.addActionListener(new D(3));
            //pom4.addActionListener(new D(4));
            zapis.addActionListener(new ZAP());
            nowa.addActionListener(new WC(true));
            wczyt.addActionListener(new WC(false));
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE); //zapobieganie wyciekom pamięci
    }

    class B implements ActionListener { //dodatkowa opcja w programie, zresztą niezbyt wartościowa
        int i,j;
        B(int i,int j){this.i=i;this.j=j;}
        public void actionPerformed(ActionEvent e) {
            t.setText("Współrzędne tego pola to "+i+", "+j+".");
        }
    }

    class C implements ActionListener { //wykonujemy ruch o jedno pole, cofamy lub ponawiamy ruch
        int i;
        C(int i){this.i=i;}
        public void actionPerformed(ActionEvent e) {
            com.company.Main.ruch(i);}
    }

    class D implements ActionListener { //pomagamy użytkownikowi zrozumieć program
        int i;
        D(int i){this.i=i;}
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, wiad[i], "POMOC", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class ZAP implements ActionListener { //wyświetlamy okno dialogowe i zapisujemy do pliku
        ZAP(){}
        public void actionPerformed(ActionEvent e) {
            String dokad = JOptionPane.showInputDialog(null, "Wpisz nazwę pliku, rozszerzenie zostanie dodane automatycznie.", "Zapis gry", JOptionPane.QUESTION_MESSAGE);
            com.company.Main.writeInFile(dokad+".csv");
        }
    }

    class WC implements ActionListener { //wyświetlamy okno dialogowe i wczytujemy z pliku
        boolean b;
        String skad;
        WC(boolean b){this.b=b;}
        public void actionPerformed(ActionEvent e) {
            Singleton object2 = Singleton.getInstance();
            if (b) skad = object2.getZrodlo();
            else skad = JOptionPane.showInputDialog(plansza, "Wpisz nazwę pliku, rozszerzenie zostanie dodane automatycznie.", "Wczytywanie gry", JOptionPane.QUESTION_MESSAGE);
            if (!b) skad = skad+".csv"; //przy pobieraniu z singletona rozszerzenie już istnieje
            com.company.Main.setKontrolka(true); //kopiemy pod stołem funkcję main, aby wyświetliła nową planszę
            com.company.Main.readFromFile(skad);
        }
    }
}
