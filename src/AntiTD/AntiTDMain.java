package AntiTD;


import javax.swing.*;
import java.io.File;

/**
 * @author Tobias Estefors
 * Mainclass for AntiTD, uses the premade levels.xml
 * if not argument has been added and then creates a gui
 */
public class AntiTDMain {

    public static void main(final String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String filepath;
                if(args.length==0){
                    filepath="levels.xml";
                }
                else {
                    filepath=args[0];
                }

                new GUI(new File(filepath));
            }
        });

    }

}
