import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(double width, double height, String title, Game game) {

        JFrame frame = new JFrame(title);

        //frame.setPreferredSize(new Dimension(width,height));
        //frame.setMaximumSize(new Dimension(width,height));
        //frame.setMinimumSize(new Dimension(width,height));

        //frame.setUndecorated(true);

        frame.setSize((int) width, (int) height);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(game);
    }
}