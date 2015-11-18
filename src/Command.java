
public class Command {
    private CommandType commandType;
    public Command(CommandType t){
        this.commandType = t;
    }
    public static enum CommandType{
        ACCEPT,DISCONNECT,MESSAGE,NICK,REJECT;
        public static boolean isIncluded(String name){
            /*for (CommandType c : CommandType.values()){
                if (c.equals(name)) return true;
            }
            return false;*/
            if (valueOf(name)!=null) return true;
            else return false;
        }
    }
    public CommandType getCommandType(){
        return commandType;
    }
    @Override
    public String toString() {
        return  commandType.toString();
    }
}
