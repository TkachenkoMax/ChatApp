import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/**
 * Created by Макс on 26.11.2015.
 */

public class UserListModel extends Observable {
    private Map<String, String> contacts = new TreeMap<String, String>();
    private String[] nicks = new String[0];

    public String[] getNicks() {
        return this.nicks;
    }

    public int getSize() {
        return this.contacts.size();
    }

    public String getNickAt(int i) {
        return this.nicks[i];
    }

    public String getIpFor(String nick) {
        return this.contacts.get(nick);
    }

    public void add(String nick, String ip) {
        this.contacts.put(nick, ip);
        this.nicks = this.contacts.keySet().toArray(this.nicks);
        this.setChanged();
        this.notifyObservers();
    }
    public boolean containsValue(String ip){
        if (contacts.containsValue(ip)) return true;
        else return false;
    }
    public boolean containsKey(String nick){
        if (contacts.containsKey(nick)) return true;
        else return false;
    }
    public boolean delete(String key) {
        boolean ret = this.contacts.containsKey(key);
        this.contacts.remove(key);
        this.nicks = this.contacts.keySet().toArray(this.nicks);
        this.setChanged();
        this.notifyObservers();
        return ret;
    }

    public void clear() {
        this.contacts.clear();
        this.setChanged();
        this.notifyObservers();
    }
}