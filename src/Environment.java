/**
 * Created by mattias on 2015-11-27.
 */
public class Environment implements Runnable{

    @Override
    public void run() {
        try {
            this.wait(1);
        } catch (InterruptedException e ) {
            e.printStackTrace();
        } catch (IllegalMonitorStateException e ) {

        }

    }
}
