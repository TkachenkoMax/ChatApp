import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class CallListenerThread extends Observable implements Runnable {
    private String localNick;
    private boolean isBusy;
    private Caller.CallStatus callStatus;
    private Thread t;
    private CallListener callListener;
    private volatile boolean isClose;
    private Connection connection;
    private volatile boolean  isReceive, flag;

    private Observable observable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };


    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean isClose) {
        this.isClose = isClose;
    }

    public CallListenerThread() {
        t = new Thread(this);
        setClose(false);
        t.start();
    }

    public CallListenerThread(CallListener callListener) {
        this.callListener = callListener;
        t = new Thread(this);
        setClose(false);
        t.start();
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        callListener.setBusy(isBusy);
        this.isBusy = callListener.isBusy();
    }
    public CallListener getCallListener(){
        return callListener;
    }
    public Connection getConnection() {
        return connection;
    }
    @Override
    public void run() {  //TODO all
        while (!isClose) {
            try {
                connection = callListener.getConnection();
                observable.notifyObservers(callListener);
                waitAnswer();
                if (!isReceive){
                    if (callListener.isBusy()) {
                        callStatus = Caller.CallStatus.valueOf("BUSY");
                    } else {
                        callStatus = Caller.CallStatus.valueOf("REJECT");
                    }
                }
                else {
                    if (callListener.isBusy()){
                        connection.disconnect();
                    }
                    else {
                        setBusy(true);
                    }
                    callStatus= Caller.CallStatus.valueOf("OK");
                    connection.accept();
                    observable.notifyObservers(connection);
                    CommandListenerThread commandListenerThread = new CommandListenerThread(connection);
                    commandListenerThread.addObserver(MainForm.window);
                }
            } catch (IOException e) {
                callStatus = Caller.CallStatus.valueOf("REJECTED");
            }
            //setChanged();
            //notifyObservers();
        }
    }

    private synchronized void waitAnswer(){
        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(t + " Thread interrupted: " + e);
            }
        }
    }

    public void setReceive(boolean isReceive){
        this.isReceive = isReceive;
    }

    public synchronized void resume(){
        flag = true;
        notify();
    }

    public void suspend(){
        flag = false;
    }

    public void addObserver(Observer observer){
        observable.addObserver(observer);
    }
    public void stop() {
        setClose(true);
    }
}
