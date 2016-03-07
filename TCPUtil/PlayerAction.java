package TCPUtil;

/**
 * Created by Dupasraka on 24.02.2016.
 */
public class PlayerAction {
    private int playerID;
    private String playerName;
    private String action;
    private String arg;

    public PlayerAction(int playerID, String playerName, String action, String arg){
        this.playerID = playerID;
        this.playerName = playerName;
        this.action = action;
        this.arg = arg;
    }

    public String getAction() {
        return action;
    }

    public String getArg() {
        return arg;
    }

    public String getPlayerName() {
        return playerName;
    }

}
