import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Spieler snake = new Spieler("Snake", 1, 1, 0);
		Karte mapLoader = new Karte();
		int[][] loadedMap = mapLoader.loadMap("G:\\Meine Ablage\\BFW\\Hüttinger\\Workspace Java\\Snake\\src\\Map.txt");

		Font Überschrift = new Font("Harlow Solid Italic", Font.PLAIN, 70);
		Font Fragen = new Font("Arial", Font.BOLD, 30);

		JPanel Hauptmenü = new JPanel(new GridBagLayout());
		Hauptmenü.setBackground(Color.black);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel Willkommen = new JLabel("Willkommen Spieler!");
		Willkommen.setFont(Überschrift);
		Willkommen.setForeground(Color.white);
		Hauptmenü.add(Willkommen, gbc);

		gbc.gridy++;

		JLabel nameLabel = new JLabel("Wie lautet dein Name?");
		nameLabel.setFont(Fragen);
		nameLabel.setForeground(Color.white);
		Hauptmenü.add(nameLabel, gbc);

		gbc.gridy++;

		JTextField nameField = new JTextField(15);
		nameField.setForeground(Color.BLUE);
		nameField.setBackground(Color.WHITE);
		Hauptmenü.add(nameField, gbc);

		gbc.gridy++;

		JButton Spielen = new JButton("Spielen");
		Spielen.setForeground(Color.white);
		Spielen.setBackground(Color.black);
		Spielen.setBorder(null);
		Spielen.setFont(Fragen);
		gbc.anchor = GridBagConstraints.WEST;
		Spielen.addActionListener(e -> {
			Name(nameField.getText(), snake);
		});
		Hauptmenü.add(Spielen, gbc);

		JButton Rangliste = new JButton("Ranglisten");
		Rangliste.setBorder(null);
		Rangliste.setForeground(Color.white);
		Rangliste.setBackground(Color.black);
		Rangliste.setFont(Fragen);
		gbc.anchor = GridBagConstraints.CENTER;
		Hauptmenü.add(Rangliste, gbc);

		JButton Close = new JButton("Beenden");
		Close.setBorder(null);
		Close.setForeground(Color.white);
		Close.setBackground(Color.black);
		Close.setFont(Fragen);
		Close.addActionListener(e -> {
			System.exit(0);
		});
		gbc.anchor = GridBagConstraints.EAST;
		Hauptmenü.add(Close, gbc);

		JPanel Game = new JPanel() {

			private static final long serialVersionUID = 1L;
			private int CELL_SIZE = 25;

			public void paintComponent(Graphics g) {
				setPreferredSize(new Dimension(loadedMap[0].length * CELL_SIZE, loadedMap.length * CELL_SIZE));
				super.paintComponent(g);
				for (int i = 0; i < loadedMap.length; i++) {
					for (int j = 0; j < loadedMap[0].length; j++) {
						if (loadedMap[i][j] == 0) { // Map
							g.setColor(Color.BLACK);
							g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
						}
						if (loadedMap[i][j] == 1) { // Wand
							g.setColor(Color.gray);
							g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

						}
						if (loadedMap[i][j] == 2) { // Item
							g.setColor(Color.BLUE);
							g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
						}
						if (loadedMap[i][j] == 4) { // Spieler Weg
							g.setColor(Color.GREEN);
							g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
						}
					}
				}
			}

		};

		Game.addKeyListener(new KeyAdapter() {
			Timer timer = new Timer();
			Object[] options = { "Weiter", "Aufgeben", "Neustart" };

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				boolean wahl1 = false;
				if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT
						|| keyCode == KeyEvent.VK_RIGHT) {
					int Code = getDirection(keyCode);
					startTimer(keyCode, Code);
//Test					System.out.println(snake.getName());
				} else if (keyCode == KeyEvent.VK_ESCAPE) {
					timer.cancel();
					do {
						int wahl = JOptionPane.showOptionDialog(null, "Spiel Pausiert", "Pause",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//						Weiter 
						if (wahl == 0) {
							wahl1 = true;
//						Close 							
						} else if (wahl == 1) {
							try {
								Ranglisten.insertData(snake.getName(), snake.getCount());
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							System.out.println(Ranglisten.readHighscoreFromFile());
							System.exit(0);
//						Neustart							
						} else if (wahl == 2) {
							snake.setPosX(1);
							snake.setPosY(1);
							loadedMap[1][1] = 4;
							for (int i = 0; i < loadedMap.length; i++) {
								for (int j = 0; j < loadedMap[0].length; j++) {
									if (!(i == 1 && j == 1) && loadedMap[i][j] != 1) {
										loadedMap[i][j] = 0;
									}
								}
							}
							snake.setCount(0);
							Bewegen.Remove();
							Game.repaint();
							wahl1 = true;
						}
					} while (!wahl1);
				}
			}

			private int getDirection(int keyCode) {
				switch (keyCode) {
				case KeyEvent.VK_UP:
					return 3;
				case KeyEvent.VK_DOWN:
					return 4;
				case KeyEvent.VK_LEFT:
					return 1;
				case KeyEvent.VK_RIGHT:
					return 2;
				default:
					return 0;
				}
			}

			private void Snakebutton() {
				int posx = (int) (Math.random() * loadedMap.length);
				int posy = (int) (Math.random() * loadedMap[0].length);
				for (int i = 0; i < loadedMap.length; i++) {
					for (int j = 0; j < loadedMap[0].length; j++) {
						if (loadedMap[i][j] == 2) {
							return;
						}
					}
				}
				while (loadedMap[posx][posy] == 1 || loadedMap[posx][posy] == 4 || loadedMap[posx][posy] == 3) {
					posx = (int) (Math.random() * loadedMap.length);
					posy = (int) (Math.random() * loadedMap[0].length);
				}
				int count = 2;
				loadedMap[posx][posy] = count;
			}

			private void startTimer(int direction, int code) {
				timer.cancel();
				int delay = 0;
				int count = Math.min(snake.getCount(), 300); // mit jedem Item wird der Timer schneller
				int period = 400 - count; // Min 100 Millisekunden
				timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask() {
					public void run() {
						Bewegen.Bewege(snake, code, loadedMap);
						Snakebutton();
						Game.repaint();

					}
				}, delay, period);
			}
		});

		CardLayout cardLayout = new CardLayout();
		JPanel cardPanel = new JPanel(cardLayout);
		cardPanel.add(Hauptmenü, "Hauptmenü");
		cardPanel.add(Game, "Game");
		Game.setFocusable(true);
		JFrame frame = new JFrame("Snake");
		frame.add(cardPanel, BorderLayout.CENTER);
		frame.setSize(1125, 625);
		frame.setBackground(Color.BLACK);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.revalidate();
		frame.repaint();

		Spielen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Game");// Zum Panel für das Spiel wechseln
				Game.requestFocusInWindow(); // Fokus auf das Game Panel setzen
			}
		});

		// KeyListener für den Spielen Button
		Spielen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cardLayout.show(cardPanel, "Game");// Zum Panel für das Spiel wechseln
					Game.requestFocusInWindow(); // Fokus auf das Game Panel setzen
				}
			}
		});

		// KeyListener für den "Close" Button
		Close.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.exit(0);

				}
			}
		});

	}
	private static void Name(String eingabe, Spieler snake) {
		snake.setName(eingabe);
	}

	
	

}