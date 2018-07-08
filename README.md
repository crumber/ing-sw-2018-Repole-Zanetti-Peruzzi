SAGRADA

• Gruppo:

	- Peruzzi Alessandro 828379 10493654
	- Repole Giampiero 849085 10543357
	- Zanetti Andrea 846901 10527115

• Coverage test :
	
	- Model 97,8% (2,2% non testabile perchè metodi che vengono eseguiti quando si fa partire il progetto da jar)

• UML : https://www.lucidchart.com/documents/view/6b7fbe65-86ba-4f5c-9911-90f8060f67d2

• Funzionalità implementate : regole complete + CLI + GUI + RMI + Socket + Carte Schema Dinamiche

• Alcuni dei pattern utilizzati: State, MVC, Observer, Strategy, Proxy
	
• Funzionalità aggiuntive:

	- implementata una fase di registrazione/login e waiting room per la scelta delle finestre
	- gestite riconnessioni dei giocatori nella waiting room, sala di scelta delle finestre e sala di gioco
	- implementato un protocollo per la comunicazione via Socket come richiesto durante le prime fasi di sviluppo del progetto (non e' stata usata nessuna Serializable per Socket)
	
• Limitazioni del gioco:

	- attualmente il gioco è supportato solo da sistemi UNIX
	- per una dimenticanza del gruppo, il jar è stato caricato con il timer per il turno della durata di soli 10 secondi (per questioni di debug). I timer sono comunque modificabili da un file di testo presente nel jar e saranno portati a 120 secondi per la presentazione del progetto

• Istruzioni per l'esecuzione del Jar:

	- Per eseguire il Server da jar, spostarsi nella cartella dove è presente il jar Game.jar e digitare il comando da terminale 
	>java -cp Game.jar repolezanettiperuzzi.controller.MasterGame
	
	- Per eseguire il Client da jar, spostarsi nella cartella dove è presente il jar Game.jar e digitare il comando da terminale 
	>java -cp Game.jar repolezanettiperuzzi.view.GameView
	
