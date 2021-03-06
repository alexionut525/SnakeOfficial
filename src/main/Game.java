package main;


import main.org.alexs.Gameplay;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {



    Game() {


        JFrame obj = new JFrame();
        obj.setBounds(10,10,905,700);
        add(new Gameplay());
        setResizable(false);
        pack();

        setTitle("Snake: Return");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {


        // Creates a new thread so our GUI can process itself
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Game();
                frame.setVisible(true);
            }
        });
    }
}
