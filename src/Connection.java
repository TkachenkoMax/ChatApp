import sun.plugin2.message.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Connection {
    private Socket socket;
    private static final String VERSION = "ChatApp 2015";
    private DataInputStream in;
    private DataOutputStream out;
    private InputStream sin;
    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void SendNickHello(String nick) throws IOException {
        OutputStream sout = socket.getOutputStream();
        out = new DataOutputStream(sout);
        out.write(("ChatApp 2015 user " + nick).getBytes("UTF-8"));
        out.write(0x0a);
        out.flush();
    }

    public void SendNickBusy(String nick) throws IOException {
        OutputStream sout = socket.getOutputStream();
        out = new DataOutputStream(sout);
        out.write(("ChatApp 2015 user " + nick + " busy").getBytes("UTF-8"));
        out.write(0x0a);
        out.flush();
    }

    public void sendMessage(String message) throws IOException {
        OutputStream sout = socket.getOutputStream();
        out = new DataOutputStream(sout);
        out.write(("Message").getBytes("UTF-8"));
        out.write(0x0a);
        out.write((message).getBytes("UTF-8"));
        out.write(0x0a);
        out.flush();
    }

    public void disconnect() throws IOException {
        OutputStream sout = socket.getOutputStream();
        out = new DataOutputStream(sout);
        out.write(("Disconnect").getBytes("UTF-8"));
        out.write(0x0a);
        out.flush();
        socket.close();
    }

    public void accept() throws IOException {
        if (socket.isConnected()) {
            OutputStream sout = socket.getOutputStream();
            out = new DataOutputStream(sout);
            out.write(("Accepted").getBytes("UTF-8"));
            out.write(0x0a);
            out.flush();
        }
    }

    public void reject() throws IOException {
        if (!socket.isConnected()) {
            OutputStream sout = socket.getOutputStream();
            out = new DataOutputStream(sout);
            out.write(("Rejected").getBytes("UTF-8"));
            out.write(0x0a);
            out.flush();
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    public Command receive() throws IOException{
        String text = "";
        int b;
        StringBuffer stringBuffer = new StringBuffer();
        sin = socket.getInputStream();
        in = new DataInputStream(sin);
        while (true) {
            if ((b = in.read()) == 0x0a) {
                text = stringBuffer.toString().toUpperCase();
                if (text.equals("MESSAGE")) {
                    stringBuffer = new StringBuffer();
                    long beginT = System.currentTimeMillis();
                    long endT;
                    while (true) {
                        endT = System.currentTimeMillis();
                        if ((endT-beginT)>=2000){
                            return new MessageCommand(stringBuffer.toString()+"   ...(broken)");
                        }
                        if ((b = in.read()) == 0x0a) {
                            break;
                        } else
                            stringBuffer.append((char)b);
                    }
                    return new MessageCommand(stringBuffer.toString());
                } else {
                    if (((text.length()==8)&&(text.startsWith("ACCEPT")||text.startsWith("REJECT"))&&text.endsWith("ED"))||(text.startsWith("DISCONNECT")&&text.length()==10)) {
                        if (text.endsWith("ED")){
                            text=text.replace("ED","");
                            return new Command(Command.CommandType.valueOf(text));
                        }
                        else
                            return new Command(Command.CommandType.valueOf(text));
                    } else {
                        if (text.startsWith(VERSION.toUpperCase()+" USER ")){
                            if (text.endsWith("BUSY")){
                                String nick=stringBuffer.toString().substring(stringBuffer.toString().indexOf("user ")+"user ".length(),stringBuffer.toString().indexOf(" busy"));
                                //String nick=text.substring(text.indexOf("USER ")+"USER ".length(),text.indexOf(" BUSY"));
                                return new NickCommand(VERSION,nick.toLowerCase(),true);
                            }
                            else{
                                String nick=stringBuffer.toString().substring(stringBuffer.toString().indexOf("user ")+"user ".length());
                                //String nick=text.substring(text.indexOf("USER ")+"USER ".length());
                                return new NickCommand(VERSION,nick.toLowerCase(),false);
                            }
                        }
                    }
                }
            text="";
            stringBuffer = new StringBuffer();
            } else {
                stringBuffer.append((char)b);
            }
        }
    }
    public boolean isOpen(){
        if (!socket.isConnected()){
            return false;
        }
        else{
            if (socket.isClosed()){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {

    }
}

