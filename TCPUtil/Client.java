package TCPUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class Client implements Runnable{
    private static ExecutorService exec;
    private static InetAddress adr;
    private static Callable<String> clientTask;
    private static ClientTask task;
    private static ClientTaskCallback<String> ctc;
    private int port;
    public Client(String hostAddress,int port) throws IOException {
        this.adr = InetAddress.getByName(hostAddress);
        this.port = port;
        this.exec = Executors.newCachedThreadPool();
        System.out.println("Adress = " + adr);

    }

    @Override
    public void run() {
        try {
            task = new ClientTask(adr,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctc = new ClientTaskCallback<String>(task);
        exec.execute(ctc);
    }

    class ClientTaskCallback<T> extends FutureTask<T> {
        public ClientTaskCallback(Callable<T> callable) {
            super(callable);
        }

        public void done() {
            String msg = "Wynik: ";
            if (isCancelled())
                msg += "Anulowane.";
            else{
                try {
                    msg += get();
                } catch (Exception exc) {
                    msg += exc.toString();
                }
            }
            System.out.println("Wątek klienta zakonczony." + msg);
        }
    }

    public static void main(String []args) throws IOException {
        Client client = new Client("localhost",5674);
    }
}
