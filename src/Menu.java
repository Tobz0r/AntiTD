import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by mattias on 2015-11-27.
 */
public class Menu extends JPanel implements ActionListener {

    public Menu() {
        super();
        JTextField textField = new JTextField(40);
        textField.addActionListener(this);
        add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("Hej");
    }
}
