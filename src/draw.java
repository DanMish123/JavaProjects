import java.util.Comparator;
import java.util.*;

class Tocka implements Comparator<Tocka> {
    int x;
    int y;

    Tocka(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double oddaljenostOdIzhodisca(){
        return Math.sqrt(x*x + y*y);
    }

    @Override
    public String toString(){
        return String.format("Tocka (%3d,%3d)  D = %.1f", x, y, oddaljenostOdIzhodisca());
    }

    @Override
    public int compare(Tocka a1, Tocka a2){
        int primerjava = Double.compare(a2.oddaljenostOdIzhodisca(), a1.oddaljenostOdIzhodisca());
        return primerjava;
    }

}

public class draw {
    public static void main(String[] args) {
        Random rnd = new Random();
        ArrayList<Tocka> seznam = new ArrayList<>();
        int n = rnd.nextInt(5) + 2;
        for (int i = 0; i < n; i++) {
            int x = rnd.nextInt(199) - 99;
            int y = rnd.nextInt(199) - 99;
            seznam.add(new Tocka(x, y));
            Collections.sort(seznam, new Tocka(x,y));
        }
        for (Tocka t : seznam)
            System.out.println(t.toString());
    }
}
