import sun.print.resources.serviceui_zh_CN;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.StringJoiner;

public class Caller {

    private String localNick;
    private SocketAddress remoteAddress;
    private String remoteNick;
    private boolean isBusy;
    private String ip;
    private static final int PORT = 28411;

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
        remoteNick = "Username";
        ip = "8.8.4.4";
    }

    public Caller(String localNick) {
        this.localNick = localNick;
        ip = "8.8.4.4";
    }

    public Caller(String localNick, SocketAddress remoteAddress) {
        this.localNick = localNick;
        this.ip = "8.8.4.4";
        this.remoteAddress = remoteAddress;
    }

    public Caller(String localNick, String ip) {
        this.localNick = localNick;
        this.ip = ip;
    }

    public Connection call() throws IOException {
        try {
            Socket socket = new Socket(ip, PORT);
            socket.connect(socket.getRemoteSocketAddress());
            Connection connection = new Connection(socket);
            NickCommand command = (NickCommand) connection.receive();
            remoteNick = command.getNick();
            isBusy = command.isBusy();
            if (isBusy) {
                connection.close();
            } else
                connection.SendNickHello(localNick);
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLocalNick() {
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
        return "Local nick: " + localNick + ", IP: " + ip + ", remote nick: " + remoteNick + ", remote address: " + remoteAddress;
    }

    public static void main(String[] args) throws IOException {
        Caller c = new Caller("Kostya", "109.87.26.248");
        Connection connection = c.call();

        System.out.println(connection.receive().toString());
        System.out.println(connection.receive().toString());
        connection.sendMessage("Congratulation!");
    }
}
