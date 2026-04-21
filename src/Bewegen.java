import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Bewegen {
	private static LinkedList<Integer> a = new LinkedList<Integer>();
	
	public static void Bewege(Spieler spieler, int richtung, int[][] loadedMap)  {
		int count = spieler.getCount();
		int lastY = spieler.getPosY();
		int lastX = spieler.getPosX();
		a.addFirst(spieler.getPosX());
		a.add(1, spieler.getPosY());
		a.subList(count + 2, a.size()).clear(); // Array möglicht effizient halten
		loadedMap[a.get(count)][a.get(1 + count)] = 0; // Ehemalige Position nach der Spur löschen

		if (count > 1) { // Spur ziehen
			loadedMap[spieler.getPosX()][spieler.getPosY()] = 3;
		}

		switch (richtung) {

		case 1: // LINKS
			if (loadedMap[spieler.getPosX()][spieler.getPosY() - 1] == 2) {
				spieler.setCount(spieler.getCount() + 2);
			}
			spieler.setPosY(spieler.getPosY() - 1);
			break;

		case 2: // RECHTS
			if (loadedMap[spieler.getPosX()][spieler.getPosY() + 1] == 2) {
				spieler.setCount(spieler.getCount() + 2);
			}
			spieler.setPosY(spieler.getPosY() + 1);

			break;

		case 3: // OBEN
			if (loadedMap[spieler.getPosX() - 1][spieler.getPosY()] == 2) {
				spieler.setCount(spieler.getCount() + 2);
			}
			spieler.setPosX(spieler.getPosX() - 1);

			break;

		case 4: // UNTEN
			if (loadedMap[spieler.getPosX() + 1][spieler.getPosY()] == 2) {
				spieler.setCount(spieler.getCount() + 2);
			}
			spieler.setPosX(spieler.getPosX() + 1);
			break;
		default:
			return;
		}

		if (loadedMap[spieler.getPosX()][spieler.getPosY()] != 1
				&& loadedMap[spieler.getPosX()][spieler.getPosY()] != 3) {
			loadedMap[spieler.getPosX()][spieler.getPosY()] = 4;
		} else {
			// Verhindern das eine Wand überschrieben wird
			spieler.setPosX(lastX);
			spieler.setPosY(lastY);
			//
			boolean wahl1 = false;
			Object[] options = { "Neuer Versuch", "Beenden" };
			do {
				try {
					Ranglisten.insertData(spieler.getName(), spieler.getCount());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int wahl = JOptionPane.showOptionDialog(null, "Verloren...", "Leider verloren",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				if (wahl == 0) {
					spieler.setPosX(1);
					spieler.setPosY(1);
					for (int i = 0; i < loadedMap.length; i++) {
						for (int j = 0; j < loadedMap[0].length; j++) {
							if (!(i == 1 && j == 1) && loadedMap[i][j] != 1) {
								loadedMap[i][j] = 0;
							}
						}
					}
					spieler.setCount(0);
					
					boolean keyPressed = false;
					while (!keyPressed) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					wahl1 = true;
				} else {
					System.exit(0);
				}
			} while (!wahl1);
		}
	}

	public static void Remove() {
		a.clear();
	}
}
