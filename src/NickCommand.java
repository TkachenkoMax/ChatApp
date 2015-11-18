public class NickCommand extends Command{
    private String nick;
    private String version;
    private boolean busy;
    public NickCommand(String version, String nick, boolean busy){
        super(Command.CommandType.valueOf("NICK"));
        this.version = version;
        this.nick = nick;
        this.busy = busy;
    }
    public String toString(){
        if (busy) return version+" user "+nick+" busy";
        else return version+" user "+nick;
    }

    public String getVersion() {
        return version;
    }

    public boolean isBusy() {
        return busy;
    }

    public String getNick() {
        return nick;
    }

    public CommandType getCommandType(){
        return Command.CommandType.valueOf("NICK");
    }
}
