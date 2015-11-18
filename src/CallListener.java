import java.net.*;
import java.io.IOException;

public class CallListener{
    private static final int PORT = 28411;
    private final String IP = "127.0.0.1";
    private String localNick;
    private InetAddress listenAddress,remoteAddress;
    private String remoteNick;
    private ServerSocket servSocket;
    private boolean isBusy;

    public CallListener(){

    }

    public CallListener(String localNick){
        this.localNick=localNick;
    }

    public CallListener(String localNick, String lockalIP)throws IOException{
        this.localNick=localNick;
        this.listenAddress=InetAddress.getLocalHost();

    }

    public Connection getConnection() throws IOException {
      ServerSocket servSocket = new ServerSocket(PORT);
      Socket socket = servSocket.accept();
      Connection connection=new Connection(socket);
      connection.SendNickHello(localNick);
      NickCommand command=(NickCommand)connection.receive();
      remoteNick=command.getNick();
      remoteAddress=socket.getInetAddress();
      if(isBusy()){
            try {
                connection.SendNickBusy(localNick);
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.close();
            return null;
      }
      else {
            connection.SendNickHello(localNick);
            return connection;
      }
    }

    public String getLocalNick(){
        return localNick;
    }

    public boolean isBusy(){
        isBusy=servSocket.isBound();
        return isBusy;
    }

    public SocketAddress getListenAddress() throws IOException {
        return servSocket.getLocalSocketAddress();
    }

    public String getRemoteNick()throws IOException {
        return this.remoteNick;
    }

    public SocketAddress getRemoteAdress() throws IOException {
        return servSocket.accept().getRemoteSocketAddress();
    }

    public void setLocalNick(String localNick){
        this.localNick = localNick;
    }

    public void setBusy(boolean isBusy){
        this.isBusy=isBusy;
    }

    public void setListenAddress(InetAddress listenAddress){
       this.listenAddress=listenAddress;
    }

    public String toString() {
    return localNick + " " + listenAddress;
    }

    public static void main(String[] args) throws IOException{
        /*ServerSocket servSocket = new ServerSocket(28411);
        Socket socket = servSocket.accept();
        System.out.println("norm");
        System.out.println(socket.getInetAddress());
        Connection con=new Connection(socket);
        //remoteAddress=socket.getRemoteSocketAddress();
        con.SendNickHello("Alex Butrim huilo");
        con.sendMessage("Alexey Butrim huilo po zhizni");
        System.out.println(con.receive().toString());*/
        CallListener cl=new CallListener("Comp");
        Connection connection=cl.getConnection();
        connection.accept();
        connection.sendMessage("We have connection.");
        System.out.println(connection.receive().toString());
    }

}
