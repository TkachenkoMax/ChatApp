import sun.print.resources.serviceui_zh_CN;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.StringJoiner;


public class Caller {

    private String localNick;
    private InetSocketAddress remoteAddress;
    private String remoteNick;
    private boolean isBusy;
    static final int PORT = 28411;
    private CallStatus callStatus;

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
        remoteNick = "unnamed";
    }

    public Caller(String localNick) {
        this.localNick = localNick;
    }

    public Caller(String localNick, InetSocketAddress remoteAddress) {
        this.localNick = localNick;
        this.remoteAddress = remoteAddress;
    }

    public Caller(String localNick, String ip) {
        this.localNick = localNick;
        this.remoteAddress = new InetSocketAddress(ip, PORT);
    }

    public Connection call() throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(getRemoteAdress(), PORT);
            socket.connect(socket.getRemoteSocketAddress());
            Connection connection = new Connection(socket);
            Command command = connection.receive();
            if (command.getCommandType() == Command.CommandType.valueOf("NICK")) {
                remoteNick = ((NickCommand) command).getNick();
                isBusy = ((NickCommand) command).isBusy();
            } else {
                callStatus = CallStatus.valueOf("NO_SERVICE");
                return null;
            }
            if (isBusy) {
                callStatus = CallStatus.valueOf("BUSY");
                connection.close();
                return null;
            } else {
                connection.SendNickHello(localNick);
                command = connection.receive();
                if (command.getCommandType() == Command.CommandType.valueOf("ACCEPT")) {
                    callStatus = CallStatus.valueOf("OK");
                    return connection;
                } else {
                    if (command.getCommandType() == Command.CommandType.valueOf("REJECT")) {
                        callStatus = CallStatus.valueOf("REJECTED");
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            callStatus = CallStatus.valueOf("NOT_ACCESSIBLE");
            return null;
        }
        return null;
    }

    public String getLocalNick() {
        return localNick;
    }

    public InetAddress getRemoteAdress() {
        return remoteAddress.getAddress();
    }

    public String getRemoteNick() {
        return remoteNick;
    }

    public void setRemoteAdress(String ip) {
        this.remoteAddress = new InetSocketAddress(ip, PORT);
    }

    public void setRemoteNick(String remoteNick) {
        this.remoteNick = remoteNick;
    }

    public void setLocalNick(String localNick) {
        this.localNick = localNick;
    }

    public void setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public CallStatus getStatus() {
        return null;
    }

    @Override
    public String toString() {
        return "Local nick: " + localNick + ", remote nick: " + remoteNick + ", remote address: " + remoteAddress;
    }

    public static void main(String[] args) throws IOException {
        Caller c = new Caller("Kostya", "109.87.26.248");
        Connection connection = c.call();

        System.out.println(connection.receive().toString());
        System.out.println(connection.receive().toString());
        connection.sendMessage("Congratulation!");
    }
}
