import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Draw extends JFrame {
    Image img;
    public Draw(){
        this.setSize(1500,750);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
    }
    public void Line(int x1,int y1,int x2,int y2){
        Graphics2D g2d= (Graphics2D) img.getGraphics();
        g2d.setColor(Color.black);
        g2d.drawLine(x1,y1,x2,y2);
    }
    public void drawTri(int x1,int y1,int x2,int y2,int x3,int y3,Color color){
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.setColor(color);
        g2d.fillPolygon(new int[]{x1,x2,x3},new int[]{y1,y2,y3},3);
    }
    public void  drawPoint(int x, int y ,int size,Color color){
        Graphics2D g2d= (Graphics2D) img.getGraphics();
        g2d.setColor(color);
        g2d.fillOval(x-size/2,y-size/2,size,size);
    }

    public void clearImg(){
        img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);

        img.getGraphics().fillRect(0,0,getWidth(),getHeight());
    }
    public void paint(Graphics g)
    {
        Graphics2D g2d =(Graphics2D) g;
        g2d.drawImage(img, 0, 0,getWidth(),getHeight(),null);
    }
}

