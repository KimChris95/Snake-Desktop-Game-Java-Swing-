# Snake Desktop Game (Java Swing)

A classic Snake implementation built with Java Swing.

## Features

- Custom map loader
- SQLite leaderboard system
- Dynamic difficulty scaling
- Pause menu
- Restart system
- Keyboard controls
- Persistent highscore storage

## Tech Stack

- Java
- Swing
- SQLite
- JDBC


## Run locally

### 1. Check if Java is installed

Open a terminal and run:

```bash
java -version
```

and

```bash
javac -version
```

If both commands return a version number like:

```
java version "21.x.x"
javac 21.x.x
```

Java is installed correctly and you can continue.

If not, install Java first (see next step).

---

### 2. Install Java (if missing)

Download and install **OpenJDK 21 (LTS)** from:

https://adoptium.net

Recommended settings during installation:

✔ Select **Windows x64**  
✔ Select **JDK 21 (LTS)**  
✔ Enable **Add to PATH**

After installation, restart your terminal and verify again:

```bash
java -version
javac -version
```

---

### 3. Compile the project

From the project root directory run:

Windows:

```bash
javac -cp "lib/*" -d bin src/com/kimplecker/snake/*.java
```

Linux / macOS:

```bash
javac -cp "lib/*" -d bin src/com/kimplecker/snake/*.java
```

---

### 4. Run the game

Windows:

```bash
java -cp "bin;lib/*" com.kimplecker.snake.Main
```

Linux / macOS:

```bash
java -cp "bin:lib/*" com.kimplecker.snake.Main
```

---

### Optional: run using helper script

If available:

Windows:

```bash
run.bat
```

Linux / macOS:

```bash
./run.sh
```

---

### Project requirements

- Java JDK 17 or newer (recommended: JDK 21)
- No additional setup required
- SQLite database is created automatically on first run

---

### Controls

| Key | Action |
|-----|-------|
Arrow keys | Move snake |
ESC | Pause menu |
Enter | Confirm menu actions |
