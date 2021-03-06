import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class CommandListenerThread extends Observable implements Runnable {
    private Connection connection;
    private Command lastCommand;
    private Thread thread;
    private boolean isDisconnected = false;
    private boolean isStop = false;
    private boolean isStart = false;

    public CommandListenerThread() {
    }

    public CommandListenerThread(Connection connection) {
        this.connection = connection;
        this.start();
    }

    public Connection getConnection() {
        return connection;
    }

    public Command getLastCommand() {
        return lastCommand;
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public void run() {   //TODO all
        while (!this.isStop) {
            try {
                lastCommand = connection.receive();
            } catch (IOException e) {
                this.isDisconnected = false;
            }
            isDisconnected = !connection.isOpen();
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void start() {
        isStart = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        this.isStop = true;
        this.isDisconnected = true;
        this.isStart = false;
    }
    public static void main (String [] args){

    }
}