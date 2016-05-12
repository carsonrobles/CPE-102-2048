/**
 * Represents a single tile in game board.
 *
 * @author  Carson Robles and Kartik Mendiratta
 * @version Program 7
 */

import java.awt.Color;

public class Tile
{
   private static Color[] colors;

   private Color color;

   private int val;

   // define color array
   static {
       colors = new Color[12];

      colors[0]  = new Color(45, 44, 38);
      colors[1]  = new Color(231, 222, 211);
      colors[2]  = new Color(230, 216, 191);
      colors[3]  = new Color(225, 163, 113);
      colors[4]  = new Color(224, 135, 94);
      colors[5]  = new Color(222, 111, 89);
      colors[6]  = new Color(219, 85, 63);
      colors[7]  = new Color(226, 195, 110);
      colors[8]  = new Color(226, 192, 96);
      colors[9]  = new Color(225, 187, 84);
      colors[10] = new Color(224, 184, 74);
      colors[11] = new Color(224, 180, 66);
   }

   /**
    * Constructs a new Tile with a randomly selected value of 2 or 4.
    */
   public Tile()
   {
      this((Math.random() < 0.5) ? 2 : 4);
   }

   /**
    * Constructs a new Tile with the provided value.
    *
    * @param val Value of the Tile.
    */
   public Tile(int val)
   {
      this.val = val;

      this.updColor();
   }

   /**
    * Returns the color of the Tile.
    *
    * @return Color of the Tile.
    */
   public Color getColor()
   {
      return (this.color);
   }

   /**
    * Returns the value of the Tile.
    *
    * @return Value of the Tile.
    */
   public int getVal()
   {
      return (this.val);
   }

   /**
    * Checks equality of two Tiles.
    *
    * @return True if Tiles' values are the same, false otherwise.
    */
   public boolean equals(Object o)
   {
      if (o == null || !o.getClass().equals(this.getClass())) {
         return (false);
      } else {
         Tile t = (Tile)o;

         return (this.val == t.val);
      }
   }

   private void updColor()
   {
      int n = (int)(Math.log(this.val) / Math.log(2));

      this.color = colors[n % 12];
   }
}
