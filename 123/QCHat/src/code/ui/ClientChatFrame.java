package code.ui;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;
import java.io.*;

public class ClientChatFrame extends JFrame implements Runnable {
    public JTextArea smsContent = new JTextArea(23, 50);
    private JTextArea smsSend = new JTextArea(4, 40);
    public JList<String> onLineUsers = new JList<>();
    private JButton sendBn = new JButton("发送");
    private Socket socket;

//    public static void main(String[] args) {
//        new ClientChatFrame();
//    }

    public ClientChatFrame() {
        initView();
        this.setVisible(true);
    }

    public ClientChatFrame(String nickname, Socket socket) {
        this(); // 调用无参构造器
        // 初始化数据
        this.setTitle(nickname + "的聊天窗口");
        this.socket = socket;
        //System.out.println("Client GUI Initialized for: " + nickname);
        new ClientReaderThread(socket, this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void initView() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        this.getContentPane().setBackground(new Color(0xf0, 0xf0, 0xf0));
        Font font = new Font("SimKai", Font.PLAIN, 16);

        smsContent.setFont(font);
        smsContent.setBackground(new Color(0xdd, 0xdd, 0xdd));
        smsContent.setEditable(false);

        smsSend.setFont(font);
        smsSend.setWrapStyleWord(true);
        smsSend.setLineWrap(true);

        onLineUsers.setFont(font);
        onLineUsers.setFixedCellWidth(120);
        onLineUsers.setVisibleRowCount(13);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(0xf0, 0xf0, 0xf0));

        JScrollPane smsSendScrollPane = new JScrollPane(smsSend);
        smsSendScrollPane.setBorder(BorderFactory.createEmptyBorder());
        smsSendScrollPane.setPreferredSize(new Dimension(600, 40));

        sendBn.setFont(font);
        sendBn.setBackground(Color.decode("#009688"));
        sendBn.setForeground(Color.WHITE);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btns.setBackground(new Color(0xf0, 0xf0, 0xf0));
        btns.add(sendBn);
        sendBn.addActionListener(e ->{
            String msg = smsSend.getText();
            smsSend.setText("");
            sendMsgToServer(msg);
        });

        bottomPanel.add(smsSendScrollPane, BorderLayout.CENTER);
        bottomPanel.add(btns, BorderLayout.EAST);

        JScrollPane userListScrollPane = new JScrollPane(onLineUsers);
        userListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        userListScrollPane.setPreferredSize(new Dimension(120, 560));

        JScrollPane smsContentScrollPane = new JScrollPane(smsContent);
        smsContentScrollPane.setBorder(BorderFactory.createEmptyBorder());

        this.add(smsContentScrollPane, BorderLayout.CENTER);
        this.add(userListScrollPane, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);



    }

    private void sendMsgToServer(String msg) {
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeInt(2);
            dos.writeUTF(msg);
            dos.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateOnlineUsers(String[] onLineNames) {
        SwingUtilities.invokeLater(() -> {
            onLineUsers.setListData(onLineNames);  // 确保在事件调度线程更新
        });
    }

    public void setMsgToWin(String msg) {
        smsContent.append(msg);
    }
}