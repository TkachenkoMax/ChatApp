import sun.plugin2.message.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    private Socket socket;
    public Connection(Socket socket){
        this.socket=socket;
    }
    public void SendNickHello(String nick) throws IOException {
        sendMessage("ChatApp 2015 user "+nick);
    }
    public void SendNickBusy(String nick) throws IOException {
        sendMessage("ChatApp 2015 user "+nick+" busy");
    }
    public void sendMessage(String message)throws IOException{
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.writeUTF(message);
        out.flush();
    }
    public void disconnect()throws IOException{
        sendMessage("Disconnect");
        socket.close();
    }
    public void accept() throws IOException{
        if (socket.isConnected()) sendMessage("Accepted");
    }
    public void reject() throws IOException {
        if (!socket.isConnected()) sendMessage("Connection closed by foreign host.");
    }
    public void close() throws IOException {
        socket.close();
    }
    public Command receive() throws IOException {
        InputStream sin=socket.getInputStream();
        DataInputStream in=new DataInputStream(sin);
        String line=in.readUTF();
        return new Command(line);
    }
    public static void main(String [] args) throws IOException {
        Connection c=new Connection(new Socket("37.139.31.24",28411));
    }
}
