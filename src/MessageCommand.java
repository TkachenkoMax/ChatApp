public class MessageCommand extends Command {
    private String message;
    public MessageCommand(String message){
        super(Command.CommandType.valueOf("MESSAGE"));
        this.message = message;
    }
    public CommandType getCommandType(){
        return Command.CommandType.valueOf("MESSAGE");
    }
    @Override
    public String toString() {
        return  message;
    }
}
