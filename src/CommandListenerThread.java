import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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
                        observable.notifyObservers(lastCommand);
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
                //this.setChanged();
                //this.notifyObservers();
                //this.clearChanged();

            } catch (IOException e) {
                System.out.println(e);
            }
            observable.notifyObservers(lastCommand);
        }
    }

    private Observable observable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void start() {
        isDisconnected = false;
        thread = new Thread(this);
        thread.start();
    }

    public void addObserver(Observer observer){
        observable.addObserver(observer);
    }

    public void stop() {
        this.isDisconnected = true;
    }
    public static void main (String [] args){
        CallListener c=new CallListener();
        CallListenerThread clt=new CallListenerThread(c);
        CommandListenerThread comlt=new CommandListenerThread(clt.getConnection());
    }
}