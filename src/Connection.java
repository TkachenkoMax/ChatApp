import sun.plugin2.message.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private static final String VERSION = "ChatApp 2015";

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void SendNickHello(String nick) throws IOException {
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(("ChatApp 2015 user " + nick + 0x0a).getBytes());
    }

    public void SendNickBusy(String nick) throws IOException {
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(("ChatApp 2015 user " + nick + " busy" + 0x0a).getBytes());
    }

    public void sendMessage(String message) throws IOException {
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(("Message" + 0x0a).getBytes());
        out.write(message.getBytes());
        out.flush();
    }

    public void disconnect() throws IOException {
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(("Disconnect" + 0x0a).getBytes());
        socket.close();
    }

    public void accept() throws IOException {
        if (socket.isConnected()) {
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);
            out.write(("Accepted" + 0x0a).getBytes());
        }
    }

    public void reject() throws IOException {
        if (!socket.isConnected()) {
            OutputStream sout = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(sout);
            out.write(("Rejected" + 0x0a).getBytes());
        }
    }

    public void close() throws IOException {
        socket.close();
    }

    public Command receive() throws IOException {
        String text = "";
        String textOriginalcase = "";
        int b;
        InputStream sin = socket.getInputStream();
        DataInputStream in = new DataInputStream(sin);
        while (true) {
            if ((b = in.read()) == +0x0a) {
                text = text.toUpperCase();
                if (text.equals("MESSAGE")) {
                    text = "";
                    while (true) {
                        if ((b = in.read()) == +0x0a) {
                            break;
                        } else
                            text += (char) b;
                    }
                    return new MessageCommand(text);
                } else {
                    if (text.equals("ACCEPT") || text.equals("DISCONNECT") || text.equals("REJECT")) {
                        return new Command(Command.CommandType.valueOf(text));
                    } else {
                        String VERSIONupperCase = VERSION.toUpperCase(); //my version in UpperCase
                        /*for (int i = 0; i < VERSION.length(); i++) {
                          if (VERSIONcase.charAt(i) != text.charAt(i)) {
                            return null;
                            }
                        }*/
                        text = text + " ";
                        textOriginalcase = textOriginalcase + " ";
                        String s[] = text.split(" ");
                        if (s.length == 5) {
                            if (VERSIONupperCase.equals(s[0] + " " + s[1]) && s[2].equals("USER") && s[4].equals("BUSY")) {
                                int ind = (VERSION+" "+"USER"+" ").length();
                                String nick = "";
                                while (textOriginalcase.charAt(ind) != ' ') {
                                    nick += textOriginalcase.charAt(ind);
                                    ind++;
                                }
                                return new NickCommand(VERSION, nick, true);
                            }
                        } else {
                            if (s.length == 4) {
                                if (VERSIONupperCase.equals(s[0] + " " + s[1]) && s[2].equals("USER")) {
                                    int ind = (VERSION+" "+"USER"+" ").length();
                                    String nick = "";
                                    while (textOriginalcase.charAt(ind) != ' ') {
                                        nick += textOriginalcase.charAt(ind);
                                        ind++;
                                    }
                                    return new NickCommand(VERSION, nick, false);
                                }
                            }
                        }
                    }
                }
                text = "";
                textOriginalcase = "";
            } else {
                text += (char) b;
                textOriginalcase += (char) b;
            }
        }
    }

    public static void main(String[] args) throws IOException {
    }
}
