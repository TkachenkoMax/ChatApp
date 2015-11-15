import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketAddress;

public class CallListener{
    private final int Port = 44444;
    private final String IP = "127.0.0.1";
    private String localNick;
    private SocketAddress listenAddress,remoteAddress;
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
        this.listenAddress=new InetSocketAddress(IP,Port);

    }

    public Connection getConnection() throws IOException {
      ServerSocket servSocket = new ServerSocket(Port);
      Socket socket = servSocket.accept();
        Connection con=new Connection(socket);
        if(isBusy){
            try {
                con.SendNickBusy(localNick);
            } catch (IOException e) {
                e.printStackTrace();
            }
            con.close();
            return null;
        }
        else {
            remoteAddress=socket.getRemoteSocketAddress();
            con.SendNickHello(localNick);
            return con;
        }
    }

    public String getLocalNick(){
        return localNick;
    }

    public boolean isBusy(){
        return servSocket.isBound();
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

    public void setListenAddress(SocketAddress listenAddress){
       this.listenAddress=listenAddress;
    }

    public String toString() {
    return localNick + " " + listenAddress;
    }

    public static void main(String[] args) {

    }

}
