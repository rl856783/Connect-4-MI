package org.example.connect4;
import java.util.Scanner;

public class Game {
    private final Board board; // A játéktábla
    private final Player player1; // Az első játékos (ember)
    private final Player player2; // A második játékos (gép)
    private Player currentPlayer; // Az aktuális játékos
    private boolean isComputerTurn; // Jelezze, hogy most a gép jön-e
    private static final String SAVE_FILE = "connect4_save.txt"; // Mentési fájl neve
    private final MinimaxAI minimaxAI;
    /**
     * Konstruktor, amely létrehozza a játékot az emberi és a gépi játékos között.
     * @param player1 Az emberi játékos.
     * @param player2 A gépi játékos.
     */
    public Game(Player player1, Player player2) {
        this.board = new Board(); // Új játéktábla létrehozása
        this.player1 = player1; // Az emberi játékos hozzárendelése
        this.player2 = player2; // A gépi játékos hozzárendelése
        this.currentPlayer = player1; // Az emberi játékos kezd
        this.isComputerTurn = false; // Az első körben az ember játszik
        this.minimaxAI = new MinimaxAI();
    }

    /**
     * Játékos váltása a kör végén.
     */
    public void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == player1) ? player2 : player1;
        this.isComputerTurn = !this.isComputerTurn; // Váltás gép és ember között
    }

    /**
     * Korong elhelyezése a megadott oszlopban.
     * @param column Az oszlop indexe (0-6).
     * @return True, ha sikeres a lépés, különben false.
     */
    public boolean dropDisk(int column) {
        return board.dropDisk(column, currentPlayer.symbol());
    }

    /**
     * A gépi játékos minimax algoritmussal kiválasztott lépése.
     * @return Az oszlop indexe (0-6), ahova a gép lépni fog.
     */
    public int computerMove() {
        GameState state = new GameState(convertBoardForAI(), true);

        int column = minimaxAI.findBestMove(state, 8);

        if (column == -1) {
            throw new IllegalStateException("A gép nem talált érvényes lépést.");
        }

        return column;
    }

    /**
     * Ellenőrzi, hogy a legutóbbi lépés győzelmet eredményezett-e.
     * @param row Az utoljára elhelyezett korong sora.
     * @param col Az utoljára elhelyezett korong oszlopa.
     * @return True, ha győzelem van, különben false.
     */
    public boolean checkWin(int row, int col) {
        char symbol = board.getBoard()[row][col]; // Az aktuális játékos szimbóluma
        return checkDirection(row, col, 1, 0, symbol) || // Vízszintes ellenőrzés
                checkDirection(row, col, 0, 1, symbol) || // Függőleges ellenőrzés
                checkDirection(row, col, 1, 1, symbol) || // Főátló ellenőrzés
                checkDirection(row, col, 1, -1, symbol);   // Mellékátló ellenőrzés
    }
    private char[][] convertBoardForAI() {
        char[][] original = board.getBoard();
        char[][] converted = new char[6][7];

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (original[row][col] == ' ') {
                    converted[row][col] = '.';
                } else {
                    converted[row][col] = original[row][col];
                }
            }
        }

        return converted;
    }
    /**
     * Ellenőrzi, hogy az adott irányban (dx, dy) van-e 4 egymás melletti korong.
     * @param row A kiindulási sor.
     * @param col A kiindulási oszlop.
     * @param dx Az irány sora (0, 1 vagy -1).
     * @param dy Az irány oszlopa (0, 1 vagy -1).
     * @param symbol A játékos szimbóluma.
     * @return True, ha található 4 egymás melletti korong, különben false.
     */
    private boolean checkDirection(int row, int col, int dx, int dy, char symbol) {
        int count = 1; // Az aktuális korongot is beleszámoljuk

        // Előre haladás az adott irányban
        int r = row + dx;
        int c = col + dy;
        while (r >= 0 && r < 6 && c >= 0 && c < 7 && board.getBoard()[r][c] == symbol) {
            count++;
            r += dx;
            c += dy;
        }

        // Visszafelé haladás az adott irányban
        r = row - dx;
        c = col - dy;
        while (r >= 0 && r < 6 && c >= 0 && c < 7 && board.getBoard()[r][c] == symbol) {
            count++;
            r -= dx;
            c -= dy;
        }

        return count >= 4; // Ha 4 vagy több azonos szimbólum van egy sorban, nyer
    }


    /**
     * A játék fő folyamata.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        Database database = new Database();
        database.addPlayer(player1.name());
        database.addPlayer(player2.name());

        System.out.println("Játékosok:");
        System.out.println(player1.color() + player1.name() + " jele: " + player1.symbol() + "\u001B[0m");
        System.out.println(player2.color() + player2.name() + " jele: " + player2.symbol() + "\u001B[0m");
        System.out.println();

        System.out.println("Szeretnél kezdőállást betölteni fájlból? (i/n)");
        String inputFileChoice = scanner.nextLine().toLowerCase();

        if (inputFileChoice.equals("i")) {
            board.loadFromInputFile("input.txt");
        } else {
            board.resetBoard();
        }



        // Mentett játék betöltése
      //  if (board.hasSavedGame(SAVE_FILE)) {
       //     System.out.println("Mentett játékállás található. Be akarod tölteni? (i/n)");
        //    String input = scanner.nextLine().toLowerCase();
         //   if (input.equals("i")) {
          //      board.loadFromFile(SAVE_FILE); // Betöltés
           // } else {
            //    System.out.println("Üres táblával indul a játék.");
             //   board.resetBoard();
            //}
       // } else {
        ///    System.out.println("Nem található mentett állás. Üres táblával indul a játék.");
         //   board.resetBoard();
       // }

        boolean gameWon = false;

        // Játékmenet ciklusa
        while (!gameWon) {
            board.printBoard();
            int column = -1;

            // Gép lépése
            if (isComputerTurn) {
                column = computerMove();
                System.out.println("A gép választotta a " + (char) ('a' + column) + " oszlopot.");
            } else {
                boolean validInput = false;
                while (!validInput) {
                    System.out.println(currentPlayer.name() + ", válassz egy oszlopot (A-G vagy 'mentés'): ");
                    String input = scanner.nextLine().toLowerCase();

                    // Mentés opció kezelése
                    if (input.equals("mentés")) {
                        board.saveToFile(SAVE_FILE); // Mentés fájlba
                        System.out.println("Játékállás mentve a következő fájlba: " + SAVE_FILE);
                        continue;
                    }

                    // Érvényes oszlop választása
                    if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= 'g') {
                        column = input.charAt(0) - 'a';
                        if (board.isColumnAvailable(column)) {
                            validInput = true; // Érvényes választás
                        } else {
                            System.out.println("Ez az oszlop már tele van. Próbálj egy másikat.");
                        }
                    } else {
                        System.out.println("Érvénytelen választás. Kérlek válassz egy oszlopot A-G között.");
                    }
                }
            }

            // Korong elhelyezése és győzelem ellenőrzése
            if (dropDisk(column)) {
                int row = 0;
                while (row < 6 && board.getBoard()[row][column] != currentPlayer.symbol()) {
                    row++;
                }

                if (checkWin(row, column)) {
                    board.printBoard();
                    System.out.println(currentPlayer.name() + " nyert!");
                    database.updateWins(currentPlayer.name());
                    gameWon = true;
                }

                if (!gameWon) {
                    switchPlayer();
                }
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
