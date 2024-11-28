# Juganaut

![Game Screenshot](https://github.com/user-attachments/assets/0fb92e87-efd8-4a21-bd1f-7eaaec892102)


A very simple 2D game for kids who want to learn programming without starting a project from scratch.

Documentation is in german since it's meant for german kids. :-) 

## Deutsch

Juganaut ist ein sehr einfaches 2D-Spiel. 

So einfach, dass der Code ohne viel Programmier-Erfahrung verstanden, verändert und erweitert werden kann.

Gedacht ist das Spiel für Programmier-Anfänger, um erste Erfahrungen zu sammeln, ohne ein Projekt komplett von
vorn beginnen zu müssen. Stattdessen gibt es ein Spiel, das bereits fertig spielbar ist, so dass eigene
Erweiterungen und Änderungen direkt erlebbar sind.

Das Spiel ist in Kotlin programmiert (das recht ähnlich zu Java ist).
* Dokumentation zu Kotlin: https://kotlinlang.org/docs/home.html

Viel Spaß beim Hacken :-)

## Installation

Voraussetzung ist nur ein installiertes JDK (Java Development Kit).

Ich empfehle zum Programmieren IntelliJ IDEA Community Edition: https://www.jetbrains.com/idea/download

Um eine Programmier-Umgebung mit diesem Spiel einzurichten:
* Clonen dieses Repositories: `git clone https://github.com/tobrup/Juganaut.git`
    * Dadurch sollte das Repository, d.h. der Programm-Code des Spiels, auf der Festplatte gespeichert werden.
* Importieren in IntelliJ über: File -> Open -> Verzeichnis mit dem lokalen Repository auswählen
* Starten des Spiels in IntelliJ: Run -> Run "MainKt" oder einfach über den entsprechenden Button in der Toolbar

## Aufbau des Projekts

Der Programm-Code ist ganz grob in zwei Teile aufgeteilt:

* BL (in `src/main/kotlin/de/glueckstobi/juganaut/bl`): Business Logic
  * Enthält die gesamte Logik des Spiels mit allen Regeln, möglichen Spiel-Elementen mit ihren Bewegungen etc. 
* UI (in `src/main/kotlin/de/glueckstobi/juganaut/ui/swing`): User Interface
  * Enthält die gesamte graphische Benutzer-Oberfläche. Hier wird alles gerendert, d.h. auf den Bilschirm angezeigt.
* Resourcen (in `src/main/resources`)
  * Enthält Bilder, die für die Anzeige im Spiel benutzt werden 

## Wie gehts weiter?

Das Spiel könnte ein paar neue Features vertragen:

* Wäre gut, wenn der Spieler einen Stein verschieben könnte (falls im Feld dahinter Platz ist).
Das ist reiner Teil der Spiel-Logik, kann also vollständig in der BL programmiert werden.

* Geräusche bzw. Musik wären noch nett

* Für das Menü (definiert in `MenuGui` in der UI) könnte man noch ein schönes Hintergrundbild malen und einfügen

* Wie wär's mit Bomben, die der Benutzer anzünden kann (z.B. mit `Enter`) und die dann nach 3 Runden explodieren?
