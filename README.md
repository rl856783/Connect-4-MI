# Connect-4-MI
Connect 4 MI26
Connect4 – AI (Minimax) alapú játék
Leírás

Ez a projekt egy klasszikus Connect4 játék Java nyelven megvalósítva, amely egy mesterséges intelligenciával (AI) rendelkező gépi ellenfelet tartalmaz.

A gép döntéshozatala minimax algoritmuson alapul, amelyet alfa–béta vágás optimalizál.

Főbb funkciók:
- Konzolos Connect4 játék
- Ember vs gép játékmód
- AI döntéshozatal:
    - minimax algoritmus
    - alfa–béta vágás
    - heurisztikus értékelő függvény
- Kezdőállás betöltése fájlból (input.txt)
- Játékállás mentése és betöltése
- Highscore mentése SQLite adatbázisba
Futtatás:
  - Projekt megnyitása IntelliJ IDEA-ban (Maven projekt)
  - Futtasd a következő osztályt: org.example.connect4.Connect4
- Játékmenet:
    - A játékos oszlopot választ A–G között
    - A gép automatikusan lép az AI segítségével
    - A játék addig tart, amíg valaki nyer
- Kezdőállás betöltése:
  A program induláskor megkérdezi: Szeretnél kezdőállást betölteni fájlból? (i/n)
  Ha i, akkor a input.txt fájlból tölt.

input.txt formátum: 6 sor, 7 karakter soronként
. → üres mező
X → gép
O → játékos

Példa:
  .......
  .......
  .......
  ..O....
  ..O....
  ..O....

- AI működés:
  - Állapottér reprezentáció: GameState
  - Kereső algoritmus: minimax
  - Optimalizálás: alfa–béta vágás
Mélység: 8 lépés
Értékelés: mintafelismerés (4-es ablakok)
Főbb osztályok
GameState – állapot reprezentáció
MinimaxAI – döntéshozó algoritmus
Game – játékmenet vezérlés
Board – tábla kezelése
Database – statisztika tárolás
Megjegyzés

A projekt a Mesterséges Intelligencia tantárgy beadandójaként készült, és a keresőalgoritmusok gyakorlati alkalmazását demonstrálja.
