import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class HistoryModel extends Observable{

 class Message{
     private Date date;
     private String text,nick;

     Message(String nick,Date date,String text){
         this.nick=nick;
         this.date=date;
         this.text=text;
     }

     String getNick(){
         return nick;
     }

     String getText(){
         return text;
     }

     Date getDate(){
         return date;
     }
 }

    private List<Message> ms;

    HistoryModel (){
        ms = new ArrayList<Message>();
    }

    void addMessage(HistoryModel.Message mod){
        ms.add(mod);
        setChanged();
        notifyObservers();
    }

    void addMessage(String nick,Date date,String text){
        HistoryModel.Message m=new HistoryModel.Message(nick,date,text);
        ms.add(m);
        setChanged();
        notifyObservers();
    }

    void clear(){
        ms.clear();
        setChanged();
        notifyObservers();
    }

    HistoryModel.Message getMessage(int pos){
        return ms.get(pos);
    }

    int getSize(){
        return ms.size();
    }

}
