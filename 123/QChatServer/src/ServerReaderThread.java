import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ServerReaderThread extends Thread {
    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while(true){
                int type = dis.readInt();
                switch(type){
                    case 1:
                        String nickname = dis.readUTF();
                        Server.onLineSockets.put(socket, nickname);
                        updatecClientOnLineUserList();
                        break;
                    case 2:
                        String msg = dis.readUTF();
                        sendMsgToAll(msg);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }


        } catch (Exception e) {
            System.out.println("客户端下线了," + socket.getInetAddress().getHostAddress());
            Server.onLineSockets.remove(socket);
            updatecClientOnLineUserList();
        }
    }
    private void updatecClientOnLineUserList (){
        Collection<String> onLineUsers = Server.onLineSockets.values();
        System.out.println("更新服务器上的在线用户列表，用户数量: " + onLineUsers.size());
        for(Socket socket : Server.onLineSockets.keySet()){
            try{
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(1);
                dos.writeInt(onLineUsers.size());
                //System.out.println("发送的用户数量: " + onLineUsers.size());
                for(String user : onLineUsers){
                    dos.writeUTF(user);
                    //System.out.println("发送用户: " + user);
                }
//                System.out.println("Sending online users: " + onLineUsers.size());
//                for (String user : onLineUsers) {
//                    System.out.println("User: " + user);
//                }
                dos.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMsgToAll(String msg){
        StringBuilder sb = new StringBuilder();
        String name = Server.onLineSockets.get(socket);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStr = dtf.format(now);
        String msgResult = sb.append(name).append(" ").append(nowStr).append("\r\n")
                .append(msg).append("\r\n").toString();
        for(Socket socket : Server.onLineSockets.keySet()){
            try{
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeInt(2);
                dos.writeUTF(msgResult);
                dos.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
