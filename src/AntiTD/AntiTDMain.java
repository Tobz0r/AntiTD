package AntiTD;


import java.io.File;

/**
 * Created by dv13tes on 2015-11-27.
 */

public class AntiTDMain {

    public static void main(String[] args){
        String filepath;
        if(args.length==0){
            filepath="levels.xml";
        }
        else {
            filepath=args[0];
        }
        new GUI(new File(filepath));
    }

}
