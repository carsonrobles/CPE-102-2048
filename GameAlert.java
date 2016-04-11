/**
  * Represents an alert window for all user alerts relating to 2048.
  *
  * @author Kartik Mendiratta and Carson Robles
  * @version Program 7
  */

import javax.swing.JOptionPane;

public class GameAlert
{
   /**
     * Creates a new GameAlert object with the specified message.
     *
     * @param message The message to display within the alert window.
     */
	public GameAlert(String message)
	{
		JOptionPane.showMessageDialog(null, message);
	}
}
