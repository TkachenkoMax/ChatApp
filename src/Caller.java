import java.io.IOException;
import java.net.SocketAddress;
import java.util.StringJoiner;

public class Caller {

    private String localNick;
    private SocketAddress remoteAddress;
    private String remoteNick;
    private String ip;

    public static enum CallStatus {
        BUSY, NO_SERVICE, NOT_ACCESSIBLE, OK, REJECTED
    }

    public static CallStatus[] values() {
        return CallStatus.values();
    }

    public static CallStatus valueOf(String name) throws IllegalArgumentException, NullPointerException {
        return CallStatus.valueOf(name);
    }

    public Caller() {
        localNick = "username1";
        remoteNick = "username2";
        ip = "8.8.4.4";
    }

    public Caller(String localNick) {
        this.localNick = localNick;
        remoteNick = "username2";
        ip = "8.8.4.4";
    }

    public Caller(String localNick, SocketAddress remoteAddress) {
        this.localNick = localNick;
        remoteNick = "username2";
        ip = "8.8.4.4";
        this.remoteAddress = remoteAddress;
    }

    public Caller(String localNick, String ip) {
        this.localNick = localNick;
        this.ip = ip;
        remoteNick = "username2";
    }

    public Connection call() throws IOException {
        return null;
    }


    public String getLicalNick() {
        return localNick;
    }

    public SocketAddress getRemoteAdress() {
        return remoteAddress;
    }

    public String getRemoteNick() {
        return remoteNick;
    }

    public void setRemoteAdress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setLocalNick(String localNick) {
        this.localNick = localNick;
    }

    public CallStatus getStatus() {
        return null;
    }

    @Override
    public String toString() {
        return "Some text here";
    }
}