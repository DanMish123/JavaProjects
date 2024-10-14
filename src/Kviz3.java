import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kviz3 {
    public static void main(String[] args) {
        try{
            switch (args[0]) {
                case "izpisi" -> {
                    int[][] datot = preberiSliko(args[1]);
                    assert datot != null;
                    izpisiSliko(datot);
                }
                case "histogram" -> {
                    int[][] datot = preberiSliko(args[1]);
                    assert datot != null;
                    izpisiHistogram(datot);
                }
                case "svetlost" -> {
                    int[][] datot = preberiSliko(args[1]);
                    assert datot != null;
                    System.out.printf("Srednja vrednost sivine na sliki %s je: %.2f\n", args[1], svetlostSlike(datot));
                }
            }
        } catch (Exception ignored){}
    }
    public static boolean jeCeloStevilo(String n) {
        if (n == null)
            return true;
        try {
            Integer.parseInt(n);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static void izpisiSliko(int[][] datot) {
        System.out.printf("velikost slike: %d x %d\n", datot[0].length, datot.length);
        for (int[] ints : datot) {
            for (int anInt : ints) {
                System.out.printf("%3d ", anInt);
            }
            System.out.println();
        }
    }

    public static void izpisiHistogram(int[][] datot) {
        Hashtable<Integer, Integer> histogram = new Hashtable<>();
        for (int[] vrstica : datot) {
            for (int broj : vrstica) {
                histogram.put(broj, histogram.getOrDefault(broj, 0) + 1);
            }
        }
        List<Integer> newdict = new ArrayList<>(histogram.keySet());
        Collections.sort(newdict);
        System.out.println("sivina : # pojavitev");
        for (int kljuc : newdict) {
            System.out.printf("%5d  :   %d\n", kljuc, histogram.get(kljuc));
        }
    }

    public static double svetlostSlike(int[][] datot) {
        int sirina = datot[0].length;
        int visina = datot.length;
        double svetlost = 0;

        for (int[] vrstica : datot)
            for (int broj: vrstica)
                svetlost += broj;
        return svetlost / (sirina * visina);
    }

    public static int[][] preberiSliko(String Datoteka) throws FileNotFoundException {
        File datoteka = new File(Datoteka);
        if (!datoteka.exists()) {
            System.out.printf("Napaka: datoteka %s ne obstaja.", Datoteka);
            return null;
        } else if (datoteka.length() == 0) {
            System.out.printf("Napaka: Datoteka %s je prazna.", Datoteka);
            return null;
        } else {
            Scanner bralnik = new Scanner(datoteka);
            if (!bralnik.hasNextLine()){
                System.out.printf("Napaka: datoteka %s ni v formatu P2.", Datoteka);
                bralnik.close();
                return null;
            }
            String linija = bralnik.nextLine();
            String[] razdeljenaPrvaVrstica = linija.split("\\s");
            if (!linija.startsWith("P2") || !razdeljenaPrvaVrstica[2].equals("x")) {
                System.out.printf("Napaka: datoteka %s ni v formatu P2.", Datoteka);
                bralnik.close();
                return null;
            }
            if (!bralnik.hasNextLine()){
                System.out.printf("Napaka: datoteka %s vsebuje premalo podatkov.", Datoteka);
                bralnik.close();
                return null;
            }
            String drugaLinija = bralnik.nextLine();
            Pattern vzorec = Pattern.compile("P2:\\s*\\d+\\s*x\\s*\\d+\\s*");
            Matcher ujemanje = vzorec.matcher(linija);

            String[] sV = linija.split("\\s");
            if (sV.length != 4 || jeCeloStevilo(sV[1]) || jeCeloStevilo(sV[3])) {
                System.out.printf("Napaka: datoteka %s ni v formatu P2 (velikost slike ni pravilna).", Datoteka);
                bralnik.close();
                return null;
            } else if (!ujemanje.matches()){
                System.out.printf("Napaka: datoteka %s ni v formatu P2.", Datoteka);
                bralnik.close();
                return null;
            } else if (Integer.parseInt(sV[1]) <= 0 || Integer.parseInt(sV[3]) <= 0) {
                System.out.printf("Napaka: datoteka %s ni v formatu P2 (velikost slike je 0 ali negativna).", Datoteka);
                bralnik.close();
                return null;
            } else {
                int w = Integer.parseInt(sV[1]);
                int h = Integer.parseInt(sV[3]);
                String[] brojevi = drugaLinija.split("\\s");
                int stPikslov = w * h;
                if (brojevi.length < stPikslov) {
                    System.out.printf("Napaka: datoteka %s vsebuje premalo podatkov.", Datoteka);
                    bralnik.close();
                    return null;
                }
                for (String brojStr : brojevi) {
                    int broj = Integer.parseInt(brojStr);
                    if (broj < 0 || broj > 255) {
                        System.out.printf("Napaka: datoteka %s vsebuje podatke izven obsega 0 do 255.", Datoteka);
                        bralnik.close();
                        return null;
                    }
                }
                int[][] rezultat = new int[h][w];
                int indeks = 0;
                for (int vr = 0; vr < h; vr++) {
                    for (int st = 0; st < w && indeks < brojevi.length; st++) {
                        int broj = Integer.parseInt(brojevi[indeks++]);
                        rezultat[vr][st] = broj;
                    }
                }
                bralnik.close();
                return rezultat;
            }
        }
    }


}
