package AntiTD;

import javax.swing.*;
import javax.swing.border.Border;

import AntiTD.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * @author dv13trm
 */
public class GUI  {
    private Menu menu;
    private Thread gameThread;
    private Environment env;
    private JFrame frame;
    private JPanel buyPanel;
    private JButton buyButton;
    private JButton buyTeleport;

    public GUI () {
        env = new Environment();
        env.start();
        frame = new JFrame("AntiTTD");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //menu = new Menu(frame);
        menu = new Menu(frame, this);
        menu.startMenu();

        menu = new Menu(frame, this);
        menu.startMenu();
        menu.statMenu();
        buildBuyPanel();
        frame.setVisible(true);
        frame.add(env);

        frame.pack();

    }

    public void startGame() {
        frame.setVisible(true);

    }
    public void restartGame(){
        //restart
    }

    private void buildBuyPanel(){
        buyPanel = new JPanel();
        buyPanel.setBorder(BorderFactory.createLineBorder(Color.green));
        buyPanel.setBackground(Color.magenta);
        //basictropp button
        buyButton = new JButton("Basic troops");
        buyButton.setBackground(Color.white);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("ELIASHEJ");
            }
        });
        //teleport troop button
        buyTeleport = new JButton("Teleport Troop");
        buyTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("TELEPORTELIAS");
            }
        });
        buyPanel.add(buyTeleport);
        buyPanel.add(buyButton, FlowLayout.LEFT);
        frame.add(buyPanel, BorderLayout.SOUTH);
    }

}
