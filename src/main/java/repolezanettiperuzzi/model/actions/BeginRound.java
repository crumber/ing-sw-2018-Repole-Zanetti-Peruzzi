package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

//Un'idea che ho avuto per creare e lanciare le azioni e' questa:
//le azioni andrebbero lanciate direttamente dal controller attraverso un metodo, oppure
//il controller ha un metodo che a sua volta chiama un metodo nella board che chiama l'azione. Poi decideremo quale delle
//due fare quando implementiamo il Controller.
//Prima di lanciare un'azione però andrebbe creata, passandogli da costruttore i dati che gli servono. In questo caso
//solo la GameBoard. Poi andrebbe chiamato il metodo doAction sull'oggetto Azione creato.
//Questo va fatto perche' il metodo doAction viene ereditato dalla Interfaccia Action e i suoi parametri non possono essere modificati.
//Pero' la cosa che non mi piace di questa soluzione è che questa classe dovrebbe avere la GameBoard tra i suoi parametri che non
//mi sembra una buona cosa da fare.
//Altrimenti si potrebbero slegare tutte le Azioni dalla classe Action, la quale diventerebbe inutile. In questo modo la firma della
//doAction potrebbe essere personalizzata per ogni classe Azione.
public class BeginRound implements Action{

    GameBoard board;

    public BeginRound(GameBoard board){
        this.board = board;
    }

    @Override
    public void doAction(){
        board.incrRound();

        //resetto i turni di tutti i giocatori
        for(int i = 0; i<board.getNPlayers(); i++){
            board.getPlayer(i).resetTurn();
        }
        //FARE PARTIRE IL TIMER PER QUESTO ROUND DALLA CLASSE TIMER CHE DOBBIAMO ANCORA CREARE

        //si suppone che il draft si stato pulito alla fine del turno precedente
        //in futuro si potrebbe voler aspettare l'input da parte del giocatore che dovrebbe pescare i dadi
        //per questo round in modo da rendere la cosa un po' piu' fedele rispetto alle regole del gioco
        for(int i = 0; i<((board.getNPlayers()*2)+1); i++) {
            board.addDieToDraft(board.takeDieFromBag());
        }
    }

}
