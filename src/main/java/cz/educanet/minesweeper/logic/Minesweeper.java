package cz.educanet.minesweeper.logic;

import java.util.Random;

public class Minesweeper {

    private int rowsCount;
    private int columnsCount;

    private boolean bombs[][];

    private int field[][];

    public Minesweeper(int rows, int columns) {
        this.rowsCount = rows;
        this.columnsCount = columns;
        field = new int[columns][rows];
        bombs = new boolean[columns][rows];
        Random r = new Random();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                field[i][j] = 0;     //celé pole bude na začátku 0 A.K.A Hidden
                if (r.nextFloat() < 0.1) {
                    bombs[i][j] = true;
                } else {
                    bombs[i][j] = false;
                }
            }

        }
    }


    /**
     * 0 - Hidden
     * 1 - Visible
     * 2 - Flag
     * 3 - Question mark
     *
     * @param x X
     * @param y Y
     * @return field type
     */
    public int getField(int x, int y) {
        return this.field[x][y];                   //this znamemá z tohoto objektu
    }

    /**
     * Toggles the field state, ie.
     * 0 -> 1,
     * 1 -> 2,
     * 2 -> 3 and
     * 3 -> 0
     *
     * @param x X
     * @param y Y
     */
    public void toggleFieldState(int x, int y) {
        if(field[x][y] == 0) {
            field[x][y] = 2;
        } else {
            field[x][y] = field[x][y] + 1;
            if (field[x][y] == 4) {
                field[x][y] = 0;
            }
        }
        System.out.println("Toggle Reveal");
    }

    /**
     * Reveals the field and all fields adjacent (with 0 adjacent bombs) and
     * all fields adjacent to the adjacent fields... ect.
     *
     * @param x X
     * @param y Y
     */
    public void reveal(int x, int y) {
        if(x >= 0 && y >= 0 && x < columnsCount && y < rowsCount){
        if (field[x][y] == 0) {
            field[x][y] = 1;
            if(getAdjacentBombCount(x, y) == 0) {
                System.out.println("Reveal");
                reveal(x - 1, y -1);
                reveal(x, y- 1);
                reveal(x + 1, y - 1);
                reveal(x - 1, y);
                reveal(x + 1, y);
                reveal(x - 1, y + 1);
                reveal(x, y + 1);
                reveal(x + 1, y + 1);
            }
        }
    }
    }

    /**
     * Returns the amount of adjacent bombs
     *
     * @param x X
     * @param y Y
     * @return number of adjacent bombs
     */
    public int getAdjacentBombCount(int x, int y) {    //počítám okolní bomby
        int b = 0;
        if (x > 0) {
            if (isBombOnPosition(x - 1, y)) { //vlevo
                b++;
            }
            if (y > 0) {
                if (isBombOnPosition(x - 1, y - 1)) { //vlevo nahoře
                    b++;
                }
            }
            if (y < rowsCount - 1) {    //vlevo dole
                if (isBombOnPosition(x - 1, y + 1)) {
                    b++;
                }
            }
        }
        if (x < columnsCount - 1) {    //vpravo
            if(isBombOnPosition(x + 1, y)) {
                b++;
            }
            if(y > 0) {
                if (isBombOnPosition(x + 1, y - 1)) {   //vpravo nahoře
                    b++;
                }
            }
            if(y < rowsCount - 1) {
                if(isBombOnPosition(x + 1, y + 1)){   //vpravo dole
                    b++;
                }
            }
        }
        if(y > 0) {
            if(isBombOnPosition(x, y - 1)){    //nahoře
                b++;
            }
        }
        if(y < rowsCount - 1){
            if(isBombOnPosition(x, y + 1)) {
                b++;
            }
        }
            return b;
    }

    /**
     * Checks if there is a bomb on the current position
     *
     * @param x X
     * @param y Y
     * @return true if bomb on position
     */
    public boolean isBombOnPosition(int x, int y) {
        return bombs[x][y];
    }

    /**
     * Returns the amount of bombs on the field
     *
     * @return bomb count
     */
    public int getBombCount() {
        int b = 0;                          //počet bomb
        for (int i = 0; i < columnsCount; i++) {    //počítám bomby
            for (int j = 0; j < rowsCount; j++) {
                if (isBombOnPosition(i, j)) {
                    b++;
                }
            }
        }
        System.out.println("Počet bomb: " + b);
        return b;
    }

    /**
     * total bombs - number of flags
     *
     * @return remaining bomb count
     */
    public int getRemainingBombCount() {    //počítám vlajky
        int flags = 0;
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] == 2) {
                    flags++;
                }
            }

        }
        return flags - getBombCount();
    }

    /**
     * returns true if every flag is on a bomb, else false
     *
     * @return if player won
     */
    public boolean didWin() {
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] == 2 && !bombs[i][j]) {   //pokud je tam vlajka ale není tam bomba //false
                    return false;
                }
                if (field[i][j] != 2 && bombs[i][j]) {   //pokud tam vlajka není ale je tam bomba   //true
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * returns true if player revealed a bomb, else false
     *
     * @return if player lost
     */
    public boolean didLoose() {
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] == 1 && bombs[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }

}
