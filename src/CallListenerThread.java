import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;

public class CallListenerThread extends Observable implements Runnable {
    private Caller.CallStatus callStatus;
    private Thread t;
    private CallListener callListener;
    private Connection connection;
    private boolean isDisconnected = false;
    private boolean isStop = false;
    private boolean isStart = false;

    public CallListenerThread() {
        this.callListener = new CallListener();
        t = new Thread(this);
    }

    public CallListenerThread(CallListener callListener) {
        this.callListener = callListener;
        t = new Thread(this);
    }

    public void start() {
        this.isStart = true;
        this.isStop = false;
        this.t.start();
    }

    public void stop() {
        this.isStop = true;
        ;
    }

    public boolean isBusy() {
        return this.callListener.isBusy();
    }

    public void setBusy(boolean isBusy) {
        callListener.setBusy(isBusy);
    }

    public CallListener getCallListener() {
        return callListener;
    }

    public String getLocalNick() {
        return this.callListener.getLocalNick();
    }

    public void setLocalNick(String localNick) {
        callListener.setLocalNick(localNick);
    }

    public void setListenAddress(InetSocketAddress listenAddress) {
        this.callListener.setListenAddress(listenAddress.getAddress().toString(), listenAddress.getPort());
    }

    public InetSocketAddress getListenAddress() throws IOException {
        return this.callListener.getListenAddress();
    }

    public String getRemotelNick() throws IOException {
        return this.callListener.getRemoteNick();
    }

    public InetSocketAddress getRemoteAddress() {
        return this.callListener.getRemoteAddress();
    }

    public Connection getLastConnection() {
        return connection;
    }

    @Override
    public void run() {  //TODO all
        while (!isStop) {
            try {
                connection = callListener.getConnection();
                if (callListener.isBusy()) {
                    callStatus = Caller.CallStatus.valueOf("BUSY");
                } else {
                    callStatus = Caller.CallStatus.valueOf("REJECT");
                }
                if (callListener.isBusy()) {
                    connection.disconnect();
                } else {
                    setBusy(true);
                }
                callStatus = Caller.CallStatus.valueOf("OK");
                //connection.accept();
            } catch (IOException e) {
                callStatus = Caller.CallStatus.valueOf("REJECTED");
            }
            setChanged();
            notifyObservers(connection);
        }
    }
}
