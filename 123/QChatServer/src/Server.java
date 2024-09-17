import java.net.*;
import java.util.*;
public class Server {
    public static final Map<Socket, String> onLineSockets = new HashMap<>();
    public static void main(String[] args){
        System.out.println("System awake");
        try{
            ServerSocket serverSocket = new ServerSocket(Constant.SERVER_PORT);

            while(true){
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                new ServerReaderThread(socket).start();
                System.out.println("Client connected");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
