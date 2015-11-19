import java.io.IOException;
import java.util.Observable;

public class CommandListenerThread extends Observable implements Runnable {
    private Connection connection;
    private Command lastCommand;
    private Thread thread;
    private boolean isDisconnected;

    public CommandListenerThread() {
        thread = new Thread(this);
        thread.start();
    }

    public CommandListenerThread(Connection connection) {
        this.connection = connection;
        thread = new Thread(this);
        thread.start();
    }

    public Command getLastCommand() {
        return lastCommand;
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public void run() {
        while (!isDisconnected) {
            try {
                lastCommand = connection.receive();
                switch (lastCommand.getCommandType()) {
                    case ACCEPT: {
                        this.isDisconnected = false;
                        break;
                    }
                    case DISCONNECT: {
                        this.connection.close();
                        this.isDisconnected = true;
                        break;
                    }
                    case REJECT: {
                        this.isDisconnected = true;
                        break;
                    }
                    case MESSAGE:
                        this.isDisconnected = false;
                        break;
                    case NICK:
                        this.isDisconnected = false;
                        break;
                    default: {
                        this.connection.reject();
                        this.isDisconnected = true;
                        break;
                    }
                }
                this.setChanged();
                this.notifyObservers();
                this.clearChanged();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void start() {
        isDisconnected = false;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isDisconnected = true;
    }
}