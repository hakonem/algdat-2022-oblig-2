# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Emma Louise Håkonsen, S362073, s362073@oslomet.no

# Arbeidsfordeling

Jeg har jobbet alene på oppgavene 1, 2, 3, 4, 5, 6 og 8.

# Oppgavebeskrivelse

I **oppgave 1** laget jeg først metoden antall(), som itererte gjennom listen med en for-loop og en teller ble økt med 1 
for hver node i listen. Metoden tom() sjekker bare om hode er satt til null - hvis det er det, så er listen tom og metoden 
returnerer true. Så laget jeg DobbeltLenketListe(T[] a), som itererer gjennom et gitt array i en for-each løkke og 
oppretter en ny node med tilhørende pekere, men bare for ikke-null-verdier i arrayet. Endringer er satt til 0 fordi det 
jo er en helt ny liste. Jeg la inn en teller for antall, som gjorde at jeg kunne forenkle antall() slik at den bare
returnerer verdien i telleren. Da ble indekskontroll mer effektiv.

I **oppgave 2** så laget jeg en tom StringBuilder til å ta imot tegnstrengen. Metoden kaller så på tom() og returnerer 
"[]" hvis listen ikke inneholder noen verdier. Ellers går vi inn i en while-løkke som traverserer fra hode til hale ved 
hjelp av neste-pekere, tar verdien av hver node og legger den til i StringBuilderen. Metoden omvendtString() fungerer på 
akkurat samme måte, men while-løkka traverserer fra hale til hode ved hjelp av forrige-pekere. Så laget jeg leggInn(T verdi)
som oppretter en ny node med den oppgitte verdien og kobler noden til listen ved å sette pekere. Hvis listen var tom før 
innleggingen, så må alle pekerne bli satt til null, ellers må pekerne peker fra og til den gamle halen, og den nye noden 
må bli satt som hale. Tellerne antall og endringer økes begge med 1.

For å lage finnNode(int indeks) i **oppgave 3** delte jeg listen i to ved å finne antall/2, så brukte jeg en while-løkke 
på kun en av halvdelene, ut fra hvor i listen den oppgitte indeksen ligger, og metoden returner noden som finnes i den 
indeksen. Metoden hent(int indeks) sjekker om den oppgitte indeks er gyldig og kaller så på finnNode for å returnere 
nodens verdi. Metoden oppdater(int indeks, T nyverdi) lagrer verdien returnerte av hent() i en temp-variabel og legger så 
inn nyverdien ved hjelp av finnNode. Endringen telles, og verdien i temp-variabelen returneres. Metoden subliste sjekker
først for gyldig intervall og bruker så en for-løkke for å kopiere verdiene til nodene i intervallet og legge dem inn i 
en ny liste.

Jeg laget indeksTil(T verdi) i **oppgave 4** ved å traverserer gjennom listen i en while-løkke som sjekker om verdien i
hver node er lik verdien vi søker på. Hvis listen er tom eller ikke inneholder verdien, returnerer metoden -1, ellers
returnerer den indeksen hvor verdien ble funnet. Denne metoden blir kalt av inneholder(T verdi) som returnerer true hvis
indeksTil returnerer en verdi som ikke er -1.

For å lage leggInn(int indeks, T verdi) i **oppgave 5** sjekket jeg først om indeksen og verdien var gyldige og kastet 
exception hvis ikke. Metoden oppretter en ny node med oppgitt verdi, og den legges i listen på riktig indeks ved å sette 
pekerne ut fra hvor i listen innleggingen skal skje (først, siste eller mellom to noder) og om listen var tom fra før. 
Både tellerne for antall og endringer økes med 1.

For fjern(int indeks) i **oppgave 6** begynte jeg med å sjekke om indeks er gyldig, og så lagret jeg (ved hjelp av finnNode)
noden på den indeksen i en variabel som heter "target". Noden fjernes ved å endre pekerne og evt. flytter hode/hale. Hvis 
noden ligger mellom to andre noder, brukes "target" for å aksessere nodene til venstre og høyre på effektiv måte. For
fjern(T verdi) brukte jeg en while-løkke til å traversere gjennom listen frem til vi finner en node som har samme verdi
som den oppgitte. Da blir pekerne endret og hode/hale er flyttet om nødvendig, og metoden returnere true hvis en node ble
fjernet. I begge fjern-metodene er antall redusert med 1 og endringer økt med 1.

I **oppgave 8** begynte jeg med å sjekke om interatorendringer er lik endringer og om hasNext() er false, og kaster i så fall
en exception. Så blir fjernOK satt til true, verdien av denne er lagret i en variabel slik at den kan returneres, og
pekeren denne er flyttet til neste node. Iterator<T> iterator() returnerer bare en instans av iteratorklassen.
DobbeltLenketListeIterator(int indeks) er lik den andre konstruktøren bortsett fra at denne peker mot den oppgitte indeksen
(ved hjelp av finnNode()). Sistnevnte konstruktøren kalles av Iterator<T> iterator(int indeks) etter at gyldigheten av
indeksen er sjekket. (OBS: I test-filen har jeg kommentert ut linjene 1115-1131 i som viser til nullstill() - denne 
metoden skulle lages i oppgave 7, som ikke er at krav for grupper av en person. Test 8s skal kjøres på en nullstilt liste, 
så jeg opprettet en tom liste "liste2" på linje 1132 slik at tester 8s-y kunne kjøres og resten av koden sjekkes.)