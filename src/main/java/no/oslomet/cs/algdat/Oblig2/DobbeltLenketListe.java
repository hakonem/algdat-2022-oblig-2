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
        for (T t : a) {
            if (t != null) {                      //tar ikke med null-verdier fra tabellen a
                Node node = new Node(t);           //oppretter node med verdi, ikke festet til noe ennå
                if (hode == null) {
                    hode = hale = node;               //første node i lenket liste
                } else {
                    hale.neste = node;                //første halen peker mot ny node
                    node.forrige = hale;              //ny node peker tilbake til forrige hale
                    hale = node;                      //flytter halen til ny node
                }
                antall++;
            }
        }
        endringer = 0;
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
        fratilKontroll(antall, fra, til);         //kontrollerer at intervallet er lovlig
        endringer = 0;
        Liste<T> sub = new DobbeltLenketListe<>();  //initialiserer tom liste som skal ta imot substring
        
        for (int i = fra; i < til; i++) {           //looper gjennom intervallet
            T verdi = hent(i);                      //henter ut verdien lagret i hver node
            sub.leggInn(verdi);                     //legger verdiene inn i ny liste (substring)
        }
        return sub;                                 //returnerer substring
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return hode == null;
    }
    
    public static void main(String[] args) {
        String[] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste = new DobbeltLenketListe<>(navn);
        liste.forEach(s -> System.out.print(s + " "));
        System.out.println();
        for (String s : liste) System.out.print(s + " ");
        // Utskrift:
        // Lars Anders Bodil Kari Per Berit
        // Lars Anders Bodil Kari Per Berit
    
    
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
        Objects.requireNonNull(verdi, "Null-verdier ikke tillatt!");
        if (indeks < 0 || indeks > antall) throw new IndexOutOfBoundsException(indeks);
        
        Node node = new Node(verdi);
        
        if (tom()) {
            hode = hale = node;
            node.forrige = node.neste = null;
        }
    
        if (indeks == 0) {
            hode.forrige = node;
            node.neste = hode;
            hode = node;
        } else if (indeks == antall) {
            hale.neste = node;
            node.forrige = hale;
            hale = node;
        } else {
            Node prev = finnNode(indeks-1);
            Node next = finnNode(indeks);
            prev.neste = node;
            node.neste = next;
            node.forrige = prev;
            next.forrige = node;
        }
        antall++;
        endringer++;
        
    }
    
    private Node<T> finnNode(int indeks) {
        if (indeks < antall/2) {
            if (indeks == 0) return hode;
            Node current = hode;
            int pos = 0;
            while (pos < indeks) {
                current = current.neste;
                pos++;
            }
            return current;
        } else {
            if (indeks == antall-1) return hale;
            Node current = hale;
            int pos = antall-1;
            while (pos > indeks) {
                current = current.forrige;
                pos--;
            }
            return current;
        }
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        Node current = hode;
        int index = 0;
        if (hode == null) {
            return -1;
        }
        while ((!current.verdi.equals(verdi)) && current.neste != null) {
            current = current.neste;
            index++;
        }
        if (current.verdi.equals(verdi)) {
            return index;
        }
        return -1;
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
        Node node = hode;
        while (node != null) {
            if (node.verdi.equals(verdi)) {
                if (node == hode) {
                    if (antall == 1) {
                        hode = hale = null;
                    } else {
                        hode.neste.forrige = null;
                        hode = node.neste;
                    }
                } else if (node == hale) {
                    node.forrige.neste = null;
                    hale = node.forrige;
                } else {
                    node.neste.forrige = node.forrige;
                    node.forrige.neste = node.neste;
                }
                antall--;
                endringer++;
                return true;
            }
            node = node.neste;
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
        Node target = finnNode(indeks);
        T temp = (T)target.verdi;
        if (indeks == 0) {
            if (antall == 1) {
                hode = hale = null;
            } else {
                hode.neste.forrige = null;
                hode = hode.neste;
            }
        } else if (indeks == antall-1) {
            hale.forrige.neste = null;
            hale = hale.forrige;
        } else {
            Node prev = target.forrige;
            Node next = target.neste;
            prev.neste = next;
            next.forrige = prev;
        }
        antall--;
        endringer++;
        return temp;
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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return iterator(indeks);
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
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer) throw new ConcurrentModificationException("Feil i opptelling av endringer!");
            if (!hasNext()) throw new NoSuchElementException("Finnes ingen verdier!");
            fjernOK = true;
            T temp = denne.verdi;
            denne = denne.neste;
            return temp;
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


