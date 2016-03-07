package RMIUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private static int playerCount = 0;
    protected RMIServer() throws RemoteException {
        super();
    }

    @Override
    public String action(String actionArgument) throws RemoteException {
        return null;
    }

    @Override
    public void move(int player_ID, int dx, int dy) throws RemoteException {

    }
}
