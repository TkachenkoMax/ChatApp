import java.io.IOException;
import java.net.SocketAddress;

public class Caller {

    private String localNick;
    private SocketAddress remoteAddress;
    private String remoteNick;

    public Caller() {

    }

    public Caller(String localNick) {

    }

    public Caller(String localNick, SocketAddress remoteAddress) {

    }

    public Caller(String localNick, String ip) {

    }

  /*  public Connection call() throws IOException{

    }
*/

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

    @Override
    public String toString() {
        return "Some text here";
    }
}
