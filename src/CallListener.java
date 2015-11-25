import java.net.*;
import java.io.IOException;

public class CallListener {
    private static final int PORT = 28411;
    private String localNick;
    private InetSocketAddress listenAddress, remoteAddress;
    private String remoteNick;
    private ServerSocket serverSocket;
    private volatile boolean isBusy;

    public CallListener() {
        this.localNick = "unnamed";
    }

    public CallListener(String localNick) {
        this.localNick = localNick;
    }

    public CallListener(String localNick, String lockalIp) throws IOException {
        this.localNick = localNick;
        this.listenAddress = new InetSocketAddress(lockalIp, PORT);

    }

    public Connection getConnection() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        Socket socket = serverSocket.accept();
        Connection connection = new Connection(socket);
        connection.SendNickHello(localNick);
        Command command = connection.receive();
        if (command.getCommandType() == Command.CommandType.valueOf("NICK")) {
            remoteNick = ((NickCommand) command).getNick();
            remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            return connection;
        } else return null;
        /*if (isBusy()) {
            connection.SendNickBusy(localNick);
            connection.close();
            return null;
        } else {
            setBusy(true);
            return connection;
        }*/
    }

    public String getLocalNick() {
        return localNick;
    }

    public boolean isBusy() {
        //isBusy = serverSocket.isBound();
        return isBusy;
    }

    public InetSocketAddress getListenAddress() throws IOException {
        return listenAddress;
    }

    public String getRemoteNick() throws IOException {
        return this.remoteNick;
    }

    public SocketAddress getRemoteAdress() throws IOException {
        return remoteAddress;
    }

    public void setLocalNick(String localNick) {
        this.localNick = localNick;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public void setListenAddress(String lockalIp, int port) {
        this.listenAddress = new InetSocketAddress(lockalIp, PORT);
    }
    public void setListenAddress(SocketAddress listenAddress){
        this.listenAddress = (InetSocketAddress) listenAddress;
    }
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }
    public String toString() {
        return localNick + " " + listenAddress;
    }

    public static void main(String[] args) throws IOException {
        /*ServerSocket servSocket = new ServerSocket(28411);
        Socket socket = servSocket.accept();
        System.out.println("norm");
        System.out.println(socket.getInetAddress());
        Connection con=new Connection(socket);
        //remoteAddress=socket.getRemoteSocketAddress();

        System.out.println(con.receive().toString());
        System.out.println(con.receive().toString());
        System.out.println(con.receive().toString());
        System.out.println(con.receive().toString());*/
        /*CallListener cl = new CallListener("Comp");
        Connection connection = cl.getConnection();
        //connection.accept();
        connection.sendMessage("We have connection.");
        System.out.println(connection.receive().toString());*/

        Socket s=new Socket("files.litvinov.in.ua",28411);
        Connection c=new Connection(s);
        Command command=c.receive();
        System.out.println(command.toString());
        c.SendNickHello("Kostya");
        command=c.receive();
        System.out.println(command.toString());
        c.sendMessage("Hello");
        command=c.receive();
        System.out.println(command.toString());


    }


}
