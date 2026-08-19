# Wordle Game (JavaFX)

![Java](https://img.shields.io/badge/Java-26-blue?logo=openjdk)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/tamer-badawy/wordle/maven.yml)


A desktop clone of the popular Wordle word-guessing game built using JavaFX, Maven, and NetBeans. 
Players get six attempts to guess a secret six-letter word, with color-coded feedback provided for each guess.

## 📋 Table of Contents
- [Prerequisites](#-prerequisites)
- [Project Setup in NetBeans](#-project-setup-in-netbeans)
- [How to Build and Package](#-how-to-build-and-package)
- [Running the Game](#-running-the-game)
- [Project Architecture](#-project-architecture)

---

## 🛠️ Prerequisites

Ensure you have the following installed before running the project:
* **Java Development Kit (JDK):** Version 18 or higher (Make sure your JDK includes or supports JavaFX).
* **Apache NetBeans IDE:** Version 26.0 or higher.
* **Apache Maven:** Integrated into NetBeans or installed separately.

---

## 💻 Project Setup in NetBeans

To open and run this project inside the NetBeans IDE:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/tamer-badawy/wordle.git
   ```
2. Open **NetBeans IDE**.
3. Go to **File** > **Open Project...**
4. Navigate to the cloned `Wordle` folder (NetBeans will automatically recognize the Maven icon next to the folder name).
5. Click **Open Project**. NetBeans will resolve and download all required JavaFX dependencies automatically.

---

## 📦 How to Build and Package

To compile the application and bundle it into a self-contained, executable JAR file:

### Using NetBeans UI:
1. **Right-click** on the `Wordle` project in the left projects panel.
2. Select **Clean and Build**.
3. Maven will create a `target/` directory containing your compiled assets.

### Using the Command Line:
```bash
mvn clean package
```

---

## 🚀 Running the Game

You can launch the Wordle game using any of the options below:

### Option 1: Directly via NetBeans
* Press **F6** or click the green **Run Project** button in the NetBeans toolbar.

### Option 2: Running the Executable JAR
Once packaged, navigate to the `target` folder and launch the standalone executable archive:
```bash
java -jar target/Wordle-1.0-SNAPSHOT.jar
```

### Option 3: Via Maven Command Line
```bash
mvn javafx:run
```

---

## 📂 Project Architecture

```text
├── src/
│   └── main/
│       ├── java/
│       │   └── com/wordle/
│       │       ├── Main.java          # Application entry point
│       │       ├── controller/        # Game logic handlers
│       │       └── ui/                # JavaFX view components
│       └── resources/
│           └── words.txt              # Dictionary containing secret words
├── pom.xml                            # Maven configurations (dependencies for openjfx plugins)
└── README.md                          # Documentation
```
