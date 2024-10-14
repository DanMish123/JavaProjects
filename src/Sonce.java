import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Sonce {
    private double x;
    private double y;
    private double r;
    private int n;

    Sonce(double x, double y, double r, int n){
        this.x = x;
        this.y = y;
        this.r = r;
        this.n = n;
    }

    void narisi(){
        StdDraw.setPenColor(Color.yellow);
        StdDraw.circle(x,y,r);
        double kot = 2*Math.PI / (2*n);
        boolean kratki = false;
        for (int i = 0; i < 2*n; i++) {
            double x0,y0,x1,y1;
            if (!kratki){
                x0 = x + r * Math.cos(i*kot);
                y0 = y + r * Math.sin(i*kot);
                x1 = x + 2*r * Math.cos(i*kot);
                y1 = y + 2*r * Math.sin(i*kot);
                kratki = true;
            } else {
                x0 = x + (r + r/3) * Math.cos(i*kot);
                y0 = y + (r + r/3) * Math.sin(i*kot);
                x1 = x + (2*r + r/3) * Math.cos(i*kot);
                y1 = y + (2*r + r/3) * Math.sin(i*kot);
                kratki = false;
            }
            StdDraw.line(x0,y0,x1,y1);
        }
    }

    public static void main(String[] args) {
        StdDraw.setScale(-100,100);
        StdDraw.setPenRadius(0.01);
        Sonce s = new Sonce(0,0,30, 10);
        s.narisi();
    }
}
