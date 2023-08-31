import java.util.Random;
import java.util.TreeMap;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static final Random  random = new Random();
    public static final Draw draw = new Draw();

    public static void main(String[] args) {

        Creature2[] creatures=new Creature2[100];
        for(int x =0;x<100;x++){
            creatures[x]=new Creature2(random.nextInt(0,draw.getWidth()),random.nextInt(0,draw.getHeight()),1,1,x);
        }
        while (true){
            //draw.clearImg();
            for(Creature2 c:creatures){
                c.draw();
                c.Move(1,creatures);
            }
            draw.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}