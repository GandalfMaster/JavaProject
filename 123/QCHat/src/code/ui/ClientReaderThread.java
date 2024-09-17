package code.ui;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.*;
public class ClientReaderThread extends Thread {
    private Socket socket;
    DataInputStream dis;
    private ClientChatFrame win;
    public ClientReaderThread(Socket socket,ClientChatFrame win) {
        this.socket = socket;
        this.win = win;
    }

    @Override
    public void run(){
        try{
            dis = new DataInputStream(socket.getInputStream());
            while(true){
                int type = dis.readInt();
                switch(type){
                    case 1:
                        //在线人数更新消息
                        updateClientOnLineList();
                        break;
                    case 2:
                        //服务端发来的群聊消息
                        getMsgToWin();
                        break;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMsgToWin() throws Exception{
        String msg = dis.readUTF();
        win.setMsgToWin(msg);
    }

    private void updateClientOnLineList() throws Exception{
        //读取有多少个在线用户
        int count = dis.readInt();
        //循环控制用户信息
        String[] names = new String[count];
        for(int i = 0; i < count; i ++ ){
            String nickname = dis.readUTF();
            //用户添加到集合
            names[i] = nickname;
            //System.out.println("接收到用户: " + nickname);
            //用户展示到窗口

        }
//        System.out.println("Receiving online users, count: " + count);
//        for (int i = 0; i < count; i++) {
//            System.out.println("User " + i + ": " + names[i]);
//        }
        //更新到窗口界面右侧展示
        win.updateOnlineUsers(names);
    }


}
