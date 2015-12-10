import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LocalContactList {
    private Map<String,String> localNickList = new TreeMap<String,String>();
    private String path;
    private File file;
    private String[] nicks = new String[0];

    public LocalContactList(String path){
        file=new File(path);
    }
    public void readContacts() throws IOException {
        String data="";
        String [] parts;
        BufferedReader br = new BufferedReader(new FileReader(file));
        while ((data = br.readLine())!=null){
            parts = data.split("~");
            localNickList.put(parts[0],parts[1]);
            nicks = this.localNickList.keySet().toArray(this.nicks);
        }
        br.close();
    }
    public void writeContacts() throws  IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (int i=0; i<getSize();i++){
            bw.write(this.nicks[i]+"~"+this.getIpFor(this.nicks[i]));
            bw.newLine();
        }
        bw.close();
    }
    public int getSize(){
        return this.localNickList.size();
    }
    public String getNickAt(int i) {
        return this.nicks[i];
    }
    public String getIpFor(String nick) {
        return this.localNickList.get(nick);
    }
    public void add(String nick, String ip) {
        this.localNickList.put(nick, ip);
        this.nicks = this.localNickList.keySet().toArray(this.nicks);
    }
    public boolean containsValue(String ip){
        if (localNickList.containsValue(ip)) return true;
        else return false;
    }
    public boolean containsKey(String nick){
        if (localNickList.containsKey(nick)) return true;
        else return false;
    }
    public boolean delete(String key) {
        boolean b = this.localNickList.containsKey(key);
        this.localNickList.remove(key);
        this.nicks = this.localNickList.keySet().toArray(this.nicks);
        return b;
    }
    public void clear() throws IOException {
        this.localNickList.clear();
        this.nicks = null;
        writeContacts();
    }
}
