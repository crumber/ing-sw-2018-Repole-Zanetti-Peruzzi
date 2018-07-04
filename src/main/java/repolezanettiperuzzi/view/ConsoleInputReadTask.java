package repolezanettiperuzzi.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<String> {

    private String[] actions;
    private String message;
    private GameView gameView;
    private String lastScene;

    public ConsoleInputReadTask(String[] actions, String message, GameView gameView, String lastScene){
        this.actions = actions;
        this.message = message;
        this.gameView = gameView;
        this.lastScene = lastScene;
    }

    public String call() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean accepted = false;
        do {
            System.out.print(message);
            try {
                // wait until we have data to complete a readLine()
                while (!br.ready()) {
                    Thread.sleep(200);
                    GameViewCLI.globalGameTime -= -200;
                }
                input = br.readLine();
            } catch (InterruptedException e) {
                //System.out.println("Timer expired!");
                return null;
            }
            if(input.equals("q")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gameView.notifyOnExit(lastScene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return null;
            }
            for(int i = 0; i<actions.length; i++){
                if(input.equals(actions[i])){
                    accepted = true;
                    break;
                }
            }
        } while (!accepted);
        return input;
    }
}