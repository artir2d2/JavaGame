package RMIUtil;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    String action(String actionArgument) throws RemoteException;

    /**
     * Method wchich gonna decide, what direction playes has moved, and if he is allowed to move that direction anyway.
     * @param player_ID - this integer is unique identification for every player in game
     * @param dx - the x direction. Allowed values are: -1, 0, 1
     * @param dy - the y direction. Allowed values are: -1, 0, 1
     * @throws RemoteException
     */
    void move(int player_ID, int dx, int dy) throws RemoteException;
}