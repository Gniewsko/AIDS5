AiSD2025ZEx5 - Kompresja Huffmana
Projekt realizuje algorytm bezstratnej kompresji danych metodą Huffmana. Główną cechą aplikacji jest możliwość definiowania długości "słowa" (symbolu). Oznacza to, że zamiast standardowego kodowania pojedynczych znaków (1 bajt), program pozwala na traktowanie ciągów n bajtów jako pojedynczego symbolu, co dla specyficznych danych może zwiększyć stopień kompresji.

Wymagania
Java Development Kit (JDK): w wersji 1.8 lub nowszej.

Apache Maven: do zarządzania zależnościami i budowania projektu.

Budowanie projektu
Aby zbudować projekt i wygenerować plik wykonywalny JAR, należy w głównym katalogu projektu (tam gdzie jest pom.xml) wykonać polecenie:
mvn clean package

Po pomyślnym zbudowaniu, plik wynikowy o nazwie AiSD2025ZEx5.jar znajdzie się w katalogu target.

Sposób użycia
Program jest obsługiwany z poziomu wiersza poleceń. Przyjmuje następujące argumenty (kolejność nie ma znaczenia, flagi są rozpoznawane po nazwach):

-m <tryb>: Tryb działania programu. Dostępne opcje:
comp – kompresja.
decomp – dekompresja.

-s <ścieżka>: Ścieżka do pliku źródłowego (wejściowego).
-d <ścieżka>: Ścieżka do pliku docelowego (wyjściowego).
-l <długość>: (Opcjonalne) Długość słowa w bajtach (domyślnie 1).
Przy kompresji: definiuje, po ile bajtów grupowane są dane.
Przy dekompresji: parametr jest opcjonalny (długość słowa jest odczytywana z nagłówka pliku), ale jeśli zostanie podany i różni się od zapisanego w pliku, program wyświetli ostrzeżenie.

Przykłady
1. Standardowa kompresja pliku tekstowego (słowo = 1 bajt):
java -jar AiSD2025ZEx5.jar -m comp -s ksiazka.txt -d ksiazka.huff

2. Dekompresja pliku:
java -jar AiSD2025ZEx5.jar -m decomp -s ksiazka.huff -d ksiazka_odzyskana.txt

3. Kompresja z długością słowa = 2 bajty: (Przydatne dla plików o strukturze 16-bitowej lub specyficznych danych binarnych).
java -jar AiSD2025ZEx5.jar -m comp -s obraz.bmp -d obraz.huff -l 2

4. Dekompresja pliku skompresowanego słowem 2-bajtowym:
java -jar AiSD2025ZEx5.jar -m decomp -s obraz.huff -d obraz_odzyskany.bmp -l 2

Format pliku wyjściowego
Skompresowany plik binarny posiada strukturę zgodną z implementacją w klasie Compression.java:

Nagłówek:
1 bajt: Długość słowa (wordLength).
4 bajty (Int): Całkowita liczba symboli w pliku (totalSymbols). Służy do precyzyjnego zakończenia dekompresji (ignorowania paddingu).
4 bajty (Int): Rozmiar słownika (pairsList.size()).

Słownik (Częstości):
Lista par zapisana sekwencyjnie dla każdego unikalnego symbolu:

4 bajty (Int): Symbol (Wartość znaku/słowa)
4 bajty (Int): Liczba wystąpień (occurrence)

Na podstawie tego słownika odtwarzane jest drzewo Huffmana podczas dekompresji.

Dane:
Strumień bitów zakodowanych algorytmem Huffmana. Ostatni bajt jest dopełniany zerami (padding), jeśli liczba bitów nie jest wielokrotnością 8 (jest to obsługiwane przez licznik totalSymbols).

Testy:
W projekcie znajdują się testy jednostkowe (JUnit 4) weryfikujące poprawność struktur danych, algorytmu oraz obsługę błędów. Użyto również pluginu JaCoCo do raportowania pokrycia kodu.

Aby uruchomić testy:
mvn test

Aby wygenerować raport pokrycia kodu (JaCoCo):
mvn clean test

Raport będzie dostępny w: target/site/jacoco/index.html