import java.io.IOException;
import java.util.Observable;

public class CallListenerThread extends Observable implements Runnable {
    private String localNick;
    private boolean isBusy;
    private Caller.CallStatus callStatus;
    private Thread t;
    private CallListener callListener;
    private volatile boolean isClose;

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

    @Override
    public void run() {
        while (!isClose) {
            try {
                Connection connection = callListener.getConnection();
                if (connection == null) {
                    callStatus = Caller.CallStatus.valueOf("BUSY");
                } else {
                    callStatus = Caller.CallStatus.valueOf("OK");
                }
            } catch (IOException e) {
                callStatus = Caller.CallStatus.valueOf("REJECTED");
            }
            setChanged();
            notifyObservers();
        }
    }

    public void stop() {
        setClose(true);
    }
}
