# 🌻 JPvZ - Plants vs. Zombies in Java

> Progetto sviluppato per il corso di **Interfacce Grafiche e Programmazione ad Eventi** (Ingegneria Informatica - 2° anno).  
> Una reimplementazione desktop in Java Swing del celebre tower defense *Plants vs. Zombies*, arricchita con animazioni custom, gestione audio, livelli di difficoltà e sistema di punteggi.

---

## 📄 Documentazione di Progetto

È disponibile la relazione tecnica completa in PDF con l'analisi dei requisiti, i diagrammi UML (Model, View, Controller) e la descrizione dei problemi affrontati:  

---

## 🎮 Caratteristiche Principali

* **Gameplay Completo**:
  * Meccanica classica a corsie (grid-based).
  * Generazione e raccolta dei soli (tramite click o caduta periodica).
  * Tagliaerba difensivi di riserva per corsia.
* **Ampia Varietà di Piante**:
  * *Peashooter*, *Girasole*, *Muro di Noci*, *Cactus*, *Fungo*, *Fungo Solare*, *Catapulta Mais*, *Catapulta Peperoni*, *Cocco Cannon*, *Potato Mine*, *Pugile*, *Triple Peashooter*.
* **Ondate di Zombie Differenziati**:
  * *Zombie Normale*, *Rugbysta*, *Zombie con Elmetto*, *Zombie Egiziano*, *Zombie Far West*, *Zombie del Futuro*, *Zombie Clown*, *Zombie Caveman*, *Zombie Nano*.
  * Ciascun tipo di zombie dispone di statistiche proprie (velocità, salute, frequenza e potenza di attacco) e frame di animazione dedicati (camminata, attacco, morte).
* **Audio & Effetti Sonori**:
  * Colonna sonora in loop ed effetti sonori gestiti tramite `AudioManager`.
  * Regolazione dinamica del volume dalle impostazioni.
* **Configurazione e Persistenza**:
  * Impostazione difficoltà (Facile, Normale, Difficile) e selezione mappa.
  * Classifica locale dei punteggi massimi persistita su file (`conf/score.txt`).
  * File di configurazione dedicato (`conf/config.txt`).

---

## 🏛️ Architettura Software

Il progetto è strutturato seguendo scrupolosamente i principi dell'**Object-Oriented Programming (OOP)** e i principali **Design Pattern**:

* **Model-View-Controller (MVC)**:
  * **Model** (`jpvz.model`): mantiene lo stato della griglia, delle entità (piante, zombie, proiettili, soli, tagliaerba) e la logica di collisione e progresso di gioco.
  * **View** (`jpvz.view`): gestisce l'interfaccia utente Swing/AWT, la navigazione a schermate tramite `CardLayout` (`MenuPanel`, `SelectPlantPanel`, `GamePanel`, `SettingPanel`, `ScorePanel`, `EndGamePanel`) e il rendering grafico custom.
  * **Controller** (`jpvz.controller`): disaccoppia la vista dal modello mediante interfacce dedicate (`IControllerForModel`, `IControllerForView`).
* **Singleton Pattern**:
  * Applicato a controller e manager condivisi (`ControllerForView`, `ControllerForModel`, `Model`, `View`, `AudioManager`, `SpriteManager`, `Config`).
* **Game Loop & Timer ad Eventi**:
  * Utilizzo coordinato di `javax.swing.Timer` per garantire fluidità di frame, sincronizzazione thread-safe sull'**Event Dispatch Thread (EDT)** ed evitare blocchi della GUI.
* **Animation & Sprite Management**:
  * Pre-caricamento e caching in memoria delle risorse grafiche per ottimizzare le prestazioni di disegno.

---

## 📂 Struttura del Progetto

```plaintext
JPvZ/
├── conf/                # File di configurazione e classifica (config.txt, score.txt)
├── resources/           # Asset multimediali (immagini, sprite animati, suoni .wav)
│   ├── audio/
│   └── images/
│       ├── plants/
│       ├── zombies/
│       └── ...
├── src/jpvz/            # Codice sorgente Java
│   ├── controller/      # Controller MVC e classe Main
│   ├── model/           # Modello dati, entità (Plant, Zombie, Proiettili)
│   │   ├── plants/
│   │   └── zombies/
│   ├── utils/           # Manager (Audio, Sprite, Config, Punteggi, Animazioni)
│   └── view/            # GUI Swing, layout e pannelli
└── .gitignore
```

---

## 🚀 Come Eseguire il Progetto

### Prerequisiti
* **Java Development Kit (JDK)** 8 o superiore (consigliato JDK 17 o 21).

### Da un IDE (Eclipse, IntelliJ IDEA, VS Code, NetBeans)
1. Clona il repository:
   ```bash
   git clone https://github.com/francescopatacca/JPvZ.git
   ```
2. Apri la cartella `JPvZ` nel tuo IDE preferito come progetto Java.
3. Assicurati che `src/` sia impostata come cartella dei sorgenti e `resources/` sia inclusa nel classpath.
4. Esegui la classe `Main`:
   ```plaintext
   jpvz.controller.Main
   ```

---

## ⚠️ Disclaimer e Crediti

* Questo progetto è stato realizzato **esclusivamente a scopi didattici ed educativi**, senza fini di lucro, per l'esame di *Interfacce Grafiche*.
* Tutti i marchi, i nomi dei personaggi, le musiche e le risorse grafiche originali sono di proprietà di **PopCap Games** e **Electronic Arts (EA)**.
