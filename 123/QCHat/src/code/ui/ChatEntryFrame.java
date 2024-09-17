package code.ui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.io.*;

public class ChatEntryFrame {
    private JFrame frame;
    private JTextField nicknameField;
    private JButton enterButton;
    private JButton cancelButton;
    private Socket socket;


    public ChatEntryFrame() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {

        frame = new JFrame("局域网聊天室 - 进入界面");
        frame.setBounds(100, 100, 300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel nameLabel = new JLabel("昵称:");
        nameLabel.setBounds(25, 25, 70, 25);
        frame.getContentPane().add(nameLabel);

        nicknameField = new JTextField();
        nicknameField.setBounds(100, 25, 150, 25);
        frame.getContentPane().add(nicknameField);
        nicknameField.setColumns(10);

        enterButton = new JButton("登录");
        enterButton.addActionListener(e ->{
            String nickname = nicknameField.getText();
            nicknameField.setText("");
            if (!nickname.isEmpty()) {
                // 显示消息或进入聊天室
                //先发送登录消息给服务端程序
                try{
                    login(nickname);
                    frame.dispose();

                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "请输入昵称！");
            }
        });
        enterButton.setBounds(100, 70, 80, 25);
        frame.getContentPane().add(enterButton);

        cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 关闭应用程序
            }
        });
        cancelButton.setBounds(190, 70, 80, 25);
        frame.getContentPane().add(cancelButton);
    }

    public void login(String nickname) throws Exception{
        socket = new Socket(Constant.SERVER_IP, Constant.PORT);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeInt(1);
        dos.writeUTF(nickname);
        dos.flush();
        new ClientChatFrame(nickname, socket);
    }
}