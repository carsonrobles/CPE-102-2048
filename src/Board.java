/**
 * Stores board data for 2048.
 *
 * @author  Kartik Mendiratta and Carson Robles
 * @version Program 7
 */

import java.util.ArrayList;

public class Board {
   private Tile[][] tiles;

   /**
    * Constructs an instance of Board with provided number of rows and columns.
    *
    * @param row Number of rows the board will have.
    * @param col Number of columns the board will have.
    */
   public Board(int row, int col)
   {
      this.tiles = new Tile[row][col];

      int i, j;

      for (i = 0; i < row; i++) {
         for (j = 0; j < col; j++) {
            this.tiles[i][j] = null;
         }
      }

      this.spawnTile();
      this.spawnTile();
   }

   /**
    * Constructs an instance of Board populated with provided tile values.
    *
    * @param values 2 dimensional array of integer values that populate the board.
    */
   public Board(int[][] values)
   {
      this.tiles = new Tile[values.length][values[0].length];

      int row, col;

      for (row = 0; row < values.length; row++) {
         for (col = 0; col < values[row].length; col++) {
            if (values[row][col] < 1 || values[row][col] % 2 != 0) {
               this.tiles[row][col] = null;
            } else {
               this.tiles[row][col] = new Tile(values[row][col]);
            }
         }
      }
   }

   /**
    * Returns the number of columns in the board.
    *
    * @return Number of columns.
    */
   public int getColCount()
   {
      return (this.tiles[0].length);
   }

   /**
    * Returns the number of rows in the board.
    *
    * @return Number of rows.
    */
   public int getRowCount()
   {
      return (this.tiles.length);
   }

   /**
    * Returns the tile at the specified row and column.
    *
    * @param row Tile's row location.
    * @param col Tile's column location.
    *
    * @return Tile at specified location.
    */
   public Tile tileAt(int row, int col)
   {
      return (this.tiles[row][col]);
   }

   /**
    * Returns a 2 dimensional array containing the board's tile values
    *
    * @return 2 dimensional array of tile values.
    */
   public int[][] getTileVals()
   {
      int rowCnt = this.tiles.length;
      int colCnt = this.tiles[0].length;

      int[][] vals = new int[rowCnt][colCnt];

      int row, col;

      for (row = 0; row < rowCnt; row++) {
         for (col = 0; col < colCnt; col++) {
            if (this.tiles[row][col] == null) {
               vals[row][col] = 0;
            } else {
               vals[row][col] = this.tiles[row][col].getVal();
            }
         }
      }

      return (vals);
   }

   // returns true if a move down is possible
   private boolean hasDownMove()
   {
      int row, col;

      for (row = 0; row < this.tiles.length - 1; row++) {
         for (col = 0; col < this.tiles[row].length; col++) {
            Tile t0 = this.tileAt(row, col);
            Tile t1 = this.tileAt(row + 1, col);

            if (t0 != null) {
               if (t1 == null) {
                  return (true);
               } else if (t0.equals(t1)) {
                  return (true);
               }
            }
         }
      }

      return (false);
   }

   /**
    * Moves all tiles in the board down.
    *
    * @return increase in score after move
   */
   public int moveDown()
   {
      if (!this.hasDownMove()) {
         return (0);
      }

      int score = 0;

      Tile[][] cols = new Tile[this.tiles[0].length][this.tiles.length];

      int i, j;

      // divide board into columns
      for (i = 0; i < cols.length; i++) {
         for (j = 0; j < cols[0].length; j++) {
            cols[i][j] = this.tiles[j][i];
         }
      }

      // format and combine all columns
      for (i = 0; i < cols.length; i++) {
         score += combineCol(cols[i]);
      }

      // add combined columns back into board
      for (i = 0; i < this.tiles.length; i++) {
         for (j = 0; j < this.tiles[0].length; j++) {
            this.tiles[i][j] = cols[j][i];
         }
      }

      this.spawnTile();

      return (score);
   }

   /**
    * Moves all tiles in the board up.
    *
    * @return Increase in score after move.
   */
   public int moveUp()
   {
      this.rotate90();
      this.rotate90();

      int score = this.moveDown();

      this.rotate90();
      this.rotate90();

      return (score);
   }

   /**
    * Moves all tiles in the board to the left.
    *
    * @return Increase in score after move.
   */
   public int moveLeft()
   {
      this.rotate90();
      this.rotate90();
      this.rotate90();

      int score = this.moveDown();

      this.rotate90();

      return (score);
   }

   /**
    * Moves all tiles in the board to the right.
    *
    * @return Increase in score after move.
   */
   public int moveRight()
   {
      this.rotate90();

      int score = this.moveDown();

      this.rotate90();
      this.rotate90();
      this.rotate90();

      return (score);
   }

   // rotates tile array a quarter turn clockwise
   private void rotate90()
   {
      int rows = this.tiles.length;
      int cols = this.tiles[0].length;

      Tile[][] ret = new Tile[cols][rows];

      int row, col;

      for (row = 0; row < rows; row++) {
         for (col = 0; col < cols; col++) {
            ret[col][(rows - 1) - row] = this.tiles[row][col];
         }
      }

      this.tiles = ret;
   }

   // combines like tiles
   private static int combineCol(Tile[] col)
   {
      int score = 0;

      int i;

      for (i = col.length - 1; i > 0; i--) {
         format(col);

         Tile t0 = col[i - 1];
         Tile t1 = col[i];

         if (t1 != null && t1.equals(t0)) {
            col[i - 1] = null;
            col[i]     = new Tile(t1.getVal() + t1.getVal());

            score += col[i].getVal();
         }
      }

      return (score);
   }

   // moves all tiles down
   private static void format(Tile[] col)
   {
      boolean cont = true;

      int i;

      while (cont) {
         cont = false;

         for (i = 0; i < col.length - 1; i++) {
            Tile t0 = col[i];
            Tile t1 = col[i + 1];

            if (t0 != null && t1 == null) {
               col[i]     = null;
               col[i + 1] = t0;

               cont = true;
            }
         }
      }
   }

   // spawns either a 2 or a 4 Tile in a random available spot
   private void spawnTile()
   {
      BoardPos[] avail = this.emptyLocs();

      if (avail.length > 0) {
         int ind = (int)(Math.random() * avail.length);

         this.tiles[avail[ind].row][avail[ind].col] = new Tile();
      }
   }

   /**
    * Returns true if there is a move, false if there is not a move.
    *
    * @return True if a move is possible, false otherwise.
    */
   public boolean hasMove()
   {
      if (this.emptyLocs().length > 0) {
         return (true);
      } else {
         // check for similar adjacent tiles

         Tile[] adjs;

         Tile t;

         int row, col, i;

         for (row = 0; row < this.tiles.length; row++) {
            for (col = 0; col < this.tiles[row].length; col++) {
               t    = this.tiles[row][col];
               adjs = this.adjacentTiles(row, col);

               for (i = 0; i < adjs.length; i++) {
                  if (t.getVal() == adjs[i].getVal()) {
                     return (true);
                  }
               }
            }
         }

         return (false);
      }
   }

   // returns an array of empty locations in board
   private BoardPos[] emptyLocs()
   {
      ArrayList<BoardPos> avail = new ArrayList<BoardPos>();

      int row, col;

      for (row = 0; row < this.tiles.length; row++) {
         for (col = 0; col < this.tiles[row].length; col++) {
            if (this.tiles[row][col] == null) {
               avail.add(new BoardPos(row, col));
            }
         }
      }

      int i;

      BoardPos[] ret = new BoardPos[avail.size()];

      for (i = 0; i < ret.length; i++) {
         ret[i] = avail.get(i);
      }

      return (ret);
   }

   // returns array of adjacent tiles (not diagonal)
   private Tile[] adjacentTiles(int row, int col)
   {
      ArrayList<Tile> adjs = new ArrayList<Tile>();

      if (row - 1 >= 0) {
         adjs.add(this.tiles[row - 1][col]);
      }

      if (row + 1 < this.tiles.length) {
         adjs.add(this.tiles[row + 1][col]);
      }

      if (col - 1 >= 0) {
         adjs.add(this.tiles[row][col - 1]);
      }

      if (col + 1 < this.tiles[0].length) {
         adjs.add(this.tiles[row][col + 1]);
      }

      Tile[] ret = new Tile[adjs.size()];

      int i;

      for (i = 0; i < ret.length; i++) {
         ret[i] = adjs.get(i);
      }

      return (ret);
   }

   // holds a row and column for a tile
   private class BoardPos {
      public int row;
      public int col;

      public BoardPos(int row, int col)
      {
         this.row = row;
         this.col = col;
      }
   }
}
