package GUItest;
import java.awt.*;
import java.io.*;
import java.awt.Window;
public class test {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setSize(500, 300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int)((screenSize.getWidth() - frame.getWidth()) / 2);
        int y = (int)((screenSize.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setVisible(true);

        TextField field = new TextField();
        field.setBounds(20, 50, 200, 25);
        frame.add(field);

        Button button = new Button("Click Me");
        button.setBounds(20, 80, 100, 50);
        button.addActionListener(e -> System.out.println(field.getText()));
        frame.add(button);
    }


}
