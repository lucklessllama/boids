import java.awt.*;
import java.util.Random;

public class Creature {
    int sepDist=50;
    int align=300;
    int cohesiondist=4000;
    int id;
    int size=20;
    double posX;
    double posY;
    double vT;//total velocity
    double vX;//x velocity
    double vY;//y velocity
    double aT;// total acceleration
    double aX;// x acceleration
    double aY;// y acceleration

    Creature(int x, int y,int vX,int vY,int id){
        this.id=id;
        this.posX =x;
        this.posY =y;
        this.vX =vX;
        this.vY =vY;
        this.vT=Math.sqrt((vX*vX)+(vY*vY));

    }
    double dist(double x1, double y1,double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    void Separation(Creature[] creatures){
        int closeTo=1;
        for(Creature c:creatures)
            if(c.id!=id){
                double d=dist(c.posX,c.posY,posX,posY);

                if(d<sepDist){
                    aX+=(sepDist-d)*(posX-c.posX)/d;
                    aY+=(sepDist-d)*(posY-c.posY)/d;
                    closeTo+=1;
                }

            }
        aX/=closeTo;
        aY/=closeTo;

    }
    void Alignment(Creature[] creatures) {
        int closeTo = 1;
        double alx = 0;
        double aly = 0;
        for (Creature c : creatures){
            double d = dist(c.posX, c.posY, posX, posY);
            if (d < align) {
                alx += c.vX;
                aly += c.vY;
                closeTo += 1;
            }
        }
        alx/=closeTo;
        aly/=closeTo;
        aX*=alx/(vT)+0.5;
        aY*=aly/(vT)+0.5;
    }
    void Cohesion(Creature[] creatures){
        int closeTo=1;
        double smx=0;
        double smy=0;
        for(Creature c:creatures) {
            double d = dist(c.posX, c.posY, posX, posY);
            if (d < cohesiondist) {

                smx += c.posX;
                smy += c.posY;

                closeTo += 1;
            }

        }
        smx/=closeTo;
        smy/=closeTo;
        aX+=(smx-posX);
        aY+=(smy-posY);
    }
    void avoidEdge(){
        if(posX>1000){
            if(aX>0)
                aX=-vX-aX;
        }
        if(posX<100){
            if(aX<0)
                aX=-vX-aX;
        }
        if(posY>700){
            if(aY>0)
                aY=-vY-aY;
        }
        if(posY<50){
            if(aY<0)
                aY=-vY-aY;
        }
    }

    void Move(double time, Creature[] creatures){

        // update acceleration
        aX=0;
        aY=0;
        Separation(creatures);
        Cohesion(creatures);
        Alignment(creatures);
        System.out.print(aX+"|");
        avoidEdge();
        System.out.println(aX);
        aT=Math.sqrt((aX*aX)+(aY*aY))/3;
        aX/=aT;
        aY/=aT;
        aT=3;
        // update velocity
        vX+=aX/time;
        vY+=aY/time;
        vT=Math.sqrt((vX*vX)+(vY*vY))/10;
        vX/=vT;
        vY/=vT;
        vT=10;
        // update pos
        posX += vX/time;
        posY += vY/time;
    }
    void draw(){
        int x1= (int)(posX + vX/vT*size);
        int y1= (int)(posY + vY/vT*size);
        int x2= (int)(posX - vY/vT*size/3);
        int y2= (int)(posY + vX/vT*size/3);
        int x3= (int)(posX + vY/vT*size/3);
        int y3= (int)(posY - vX/vT*size/3);
        Main.draw.drawTri(x1,y1,x2,y2,x3,y3, Color.BLUE);
    }
}
