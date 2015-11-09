import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketAddress;

public class CallListener{
  public String localNick;
  public String localAdress;
    private Socket socket;
    private ServerSocket servSocket;
   Connection con=new Connection(servSocket.accept(),localNick);
    private boolean isBusy;

    public CallListener(){

    }

    public CallListener(String localNick){
        this.localNick=localNick;
    }

    public CallListener(String localNick, String localAdress)throws IOException{
        this.localNick=localNick;
        this.localAdress=localAdress;

    }

    public Connection getConnection() throws IOException{
        return Connection();//в разработке
    }

    public String getLocalNick(){
        return localNick;
    }

    public boolean isBusy(){
        return servSocket.isBound();
    }

    public SocketAddress getListenAddress() throws IOException {
        return null;
    }

    public String getRemoteNick()throws IOException {
        return null;
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

    }

    public String toString() {
    return localNick + localAdress;
    }

    public static void main(String[] args) {

    }

}
