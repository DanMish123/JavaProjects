import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

public class IskanjeSlik {
    private ArrayList<Slika> slike = new ArrayList<>();  // seznam najdenih slik

    public void preisci(String pot) {
        File dat = new File(pot);
        if (dat.isFile()) { // podana je datoteka; preveri, ali ustreza formatu in dodaj sliko na seznam
            if (Slika.jePNG(dat.getPath())) { // če je PNG, preberi podatke in jo dodaj na seznam
                slike.add(Slika.preberiPNG(dat.getPath()));
            } else if (Slika.jeJPEG(dat.getPath())) { // če je JPEG, ustvari sliko neznane velikosti in jo dodaj na seznam
                slike.add(new Slika(dat.getPath(), "JPEG"));
            } else if (Slika.jeGIF(dat.getPath())) { // če je GIF, ustvari sliko neznane velikosti in jo dodaj na seznam
                slike.add(new Slika(dat.getPath(), "GIF"));
            }
        } else if (dat.isDirectory()) { // podan je direktorij; preveri vse njegove elemente
            String[] elementi = dat.list();
            for (String el : elementi) {
                preisci(dat.getPath() + File.separator + el);
            }
        }
    }

    public void izpisi() {
        // uredi slike po naraščajoči velikosti oz. po poti (po abecedi)
        slike.sort(new Comparator<Slika>() {
            @Override
            public int compare(Slika s1, Slika s2) {
                int primerjava = Integer.compare(s1.getVelikost(), s2.getVelikost());
                if (primerjava == 0)
                    return s1.getPot().compareTo(s2.getPot());
                return primerjava;
            }
        });
        for (Slika s : slike) {
            System.out.println(s.toString());
        }
    }

    public static void main(String[] args) {
        System.out.println(Slika.jePNG("viri/primer.png"));
        System.out.println(Slika.preberiPNG("viri/primer.png"));
        System.out.println();

        IskanjeSlik iskalnik = new IskanjeSlik();
        iskalnik.preisci("viri");
        iskalnik.izpisi();
    }
}
