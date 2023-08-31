import java.awt.*;
import java.util.Random;
public class Creature2 {
    int SeparationDist=100;
    int AlignmentDist=200;
    int CohesionDist=500;
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
    Color color;

    Creature2(int x, int y,int vX,int vY,int id){
        this.id=id;
        this.posX =x;
        this.posY =y;
        this.vX =vX;
        this.vY =vY;
        this.vT=Math.sqrt((vX*vX)+(vY*vY));
        color=new Color(Main.random.nextInt(0,255),Main.random.nextInt(0,255),Main.random.nextInt(0,255));
    }
    double dist(double x1, double y1,double x2, double y2){
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }
    void Separation(Creature2[] creatures) {
        for (Creature2 c:creatures) {
            double cDist=dist(posX,posY,c.posX,c.posY);
            if(cDist==0)
                continue;
            if(cDist<SeparationDist){
                double langth = Math.abs(c.posX-posX)+Math.abs(c.posY-posY);
                double mod= (SeparationDist-cDist)/SeparationDist;
                vX+=(posX-c.posX)/(langth/mod)/2;
                vY+=(posY-c.posY)/(langth/mod)/2;
            }
        }
    }
    void Alignment(Creature2[] creatures) {
        double aX2=0;
        double aY2=0;
        for (Creature2 c:creatures) {
            double cDist=dist(posX,posY,c.posX,c.posY);
            if(cDist==0)
                continue;
            double mod = SeparationDist/cDist;
            if(cDist<AlignmentDist){// vision cone
                if(dist(vX/vT,vY/vT,(c.posX-posX)/cDist,(c.posY-posY)/cDist)<=0.7) {
                    aX2 += vX / vT * mod;
                    aY2 += vY / vT * mod;
                }
            }
        }
        double scale = dist(aX2,aY2,0,0);
        if(scale==0)
            return;
        aX2/=scale;
        aY2/=scale;
        aX+=aX2;
        aY+=aY2;
    }
    void Cohesion(Creature2[] creatures){
        int closeTo=1;
        double massX=posX;
        double massY=posY;
        for (Creature2 c:creatures) {
            double cDist=dist(posX,posY,c.posX,c.posY);
            if(cDist==0)
                continue;
            if(cDist<CohesionDist){
                if(dist(vX/vT,vY/vT,(c.posX-posX)/cDist,(c.posY-posY)/cDist)<=1.5){

                    closeTo++;
                    massX+=c.posX;
                    massY+=c.posY;
                }
            }
        }
        massX/=closeTo;
        massY/=closeTo;
        massX-=posX;
        massY-=posY;
        double scale=dist(massX,massY,0,0);
        if(scale==0)
            return;
        aX+=massX*scale/CohesionDist*3;
        aY+=massY*scale/CohesionDist*3;
    }

    void Move(double time, Creature2[] creatures){

        // update acceleration
        Alignment(creatures);
        Separation(creatures);
        Cohesion(creatures);
        aT=Math.sqrt((aX*aX)+(aY*aY))/2;
        aX/=aT;
        aY/=aT;
        aT=2;
        // update velocity
        vX+=aX/time;
        vY+=aY/time;
        vT=Math.sqrt((vX*vX)+(vY*vY))/5;
        vX/=vT;
        vY/=vT;
        vT=5;
        //System.out.println(vX+"|"+vY);
        // update pos
        posX += vX/time;
        posY += vY/time;
        if(posX>Main.draw.getWidth()){
            posX=0;
            //vX*=-1;
        }
        else if(posX<0){
            posX=Main.draw.getWidth();
            //vX*=-1;
        }
        if(posY>Main.draw.getHeight()){
            posY=30;
            //vY*=-1;
        }
        else if(posY<30){
            posY=Main.draw.getHeight();
            //vY*=-1;
        }

    }
    void draw(){
        int x1= (int)(posX + vX/vT*size);
        int y1= (int)(posY + vY/vT*size);
        int x2= (int)(posX - vY/vT*size/3);
        int y2= (int)(posY + vX/vT*size/3);
        int x3= (int)(posX + vY/vT*size/3);
        int y3= (int)(posY - vX/vT*size/3);
        Main.draw.drawTri(x1,y1,x2,y2,x3,y3, color);
    }
}

