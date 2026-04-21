public class Spieler {
	private String Name;
	private int posX;
	private int posY;
	private int count;

	public Spieler(String Name, int posX, int posY, int count) {
		this.Name = Name;
		this.posX = posX;
		this.posY = posY;
		this.count = count;

	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
