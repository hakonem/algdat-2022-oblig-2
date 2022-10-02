package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {}

    public DobbeltLenketListe(T[] a) {
       Objects.requireNonNull(a,"Tabellen a er null!");
       hode = hale = null;                          //initialisere med null-verdier
       for (int i = 0; i < a.length; i++) {
           if (a[i] != null) {                      //tar ikke med null-verdier fra tabellen a
              Node node = new Node(a[i]);           //oppretter node med verdi, ikke festet til noe ennå
              if (hode == null) {
                  hode = hale = node;               //første node i lenket liste
              } else {
                  hale.neste = node;                //første halen peker mot ny node
                  node.forrige = hale;              //ny node peker tilbake til forrige hale
                  hale = node;                      //flytter halen til ny node
              }
           }
       }
    }
    
    // Programkode 1.2.3 a) fra kompendiet
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");
        
        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");
        
        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }
    
    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);
        Liste<T> sub = new DobbeltLenketListe<>();
        
        for (int i = fra; i <= til; i++) {
            sub.leggInn(hent(i));
        }
        return sub;
    }

    @Override
    public int antall() {
        Node current = hode;
        int antall = 0;
     
        while (current != null) {
            antall++;
            current = current.neste;
        }
        return antall;
    }

    @Override
    public boolean tom() {
        return hode == null;
    }
    
    public static void main(String[] args) {
    
        Character[] c = {'A','B','C','D','E','F','G','H','I','J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.subliste(3,8)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5,5)); // []
        System.out.println(liste.subliste(8,liste.antall())); // [I, J]
        // System.out.println(liste.subliste(0,11)); // skal kaste unntak
    
    
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi,"Null-verdier er ikke tillatt");
        Node node = new Node(verdi);
        if (tom()) {
            hode = hale = node;
            node.forrige = node.neste = null;
        } else {
            hale.neste = node;
            node.forrige = hale;
            hale = node;
        }
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }
    
    private Node<T> finnNode(int indeks) {
        int length = antall();
        int mid = length/2;
        if (indeks < mid) {
            Node current = hode;
            for (int i = 0; i < mid; i++) {
                current = current.neste;
            }
            return current;
        } else {
            Node current = hale;
            for (int i = length-1; i > mid; i--) {
                current = current.forrige;
            }
            return current;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi);
        T temp = hent(indeks);
        finnNode(indeks).verdi = nyverdi;
        endringer++;
        return temp;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        Node current = hode;                                //starter med hode
        StringBuilder output = new StringBuilder("[");      //initialiserer Stringbuilder-variabel
        if (tom()) {                                        //håndterer tomme lister
            return "[]";
        } else {
            while (current.neste != null) {                 //looper gjennom listen til vi når den siste noden
                output.append(current.verdi + ", ");        //verdien i hver node legges til StringBuilder-variabelen
                current = current.neste;                    //flytter til neste node
            }
            output.append(current.verdi + "]");
            return output.toString();
        }
        
    }

    public String omvendtString() {
        Node current = hale;                                //starter med hale
        StringBuilder output = new StringBuilder("[");      //initialiserer Stringbuilder-variabel
        if (tom()) {                                        //håndterer tomme lister
            return "[]";
        } else {
            while (current.forrige != null) {               //looper gjennom listen baklengs til vi når den første noden
                output.append(current.verdi + ", ");        //verdien i hver node legges til StringBuilder-variabelen
                current = current.forrige;                  //flytter til forrige node
            }
            output.append(current.verdi + "]");
            return output.toString();
        }
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


