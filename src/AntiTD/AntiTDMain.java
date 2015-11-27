package AntiTD;

import java.io.IOException;

/**
 * Created by dv13tes on 2015-11-27.
 */

public class AntiTDMain {

    public static void main(String[] args){
        System.out.println("Hello w1orld!");
        new GUI();
        try {
            new ReadXML();
        } catch (IOException e) {
            System.out.println("det funkar icke");
        }
    }

}
