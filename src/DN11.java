import java.io.*;
import java.util.*;

public class DN11 {
    public static void main(String[] args) {
        EuroRail e = new EuroRail(new ArrayList<>(), new ArrayList<>());
        switch (Integer.parseInt(args[0])) {
            case 1 -> {
                if (args.length == 3) {
                    e.preberiKraje(args[1]);
                    e.preberiPovezave(args[2]);
                    e.izpisiKraje();
                    System.out.println();
                    e.izpisiPovezave();
                } else System.out.println("Napacno stevilo argumentov");
            }
            case 2 -> {
                if (args.length == 3) {
                    System.out.println("Kraji in odhodi vlakov:");
                    e.preberiKraje(args[1]);
                    e.preberiPovezave(args[2]);
                    e.izpisiKrajeInVlaki();
                } else System.out.println("Napacno stevilo argumentov");
            }
            case 3 -> {
                if (args.length == 3) {
                    System.out.println("Kraji in odhodi vlakov (po abecedi):");
                    e.preberiKraje(args[1]);
                    e.preberiPovezave(args[2]);
                    e.urejenIzpis();
                } else System.out.println("Napacno stevilo argumentov");
            }
            case 4 -> {
                if (args.length >= 5) {
                    e.preberiKraje(args[1]);
                    e.preberiPovezave(args[2]);
                    int k = Integer.parseInt(args[3]);
                    StringBuilder x = new StringBuilder();
                    for (int i = 4; i < args.length; i++)
                        x.append(args[i]).append(" ");
                    String kraj = x.toString().trim();
                    boolean ni = true;
                    for (Kraj n : e.getKraji()) {
                        if (n.getIme().equals(kraj)) {
                            ni = false;
                            if (n.destinacije(k).isEmpty()) {
                                System.out.printf("Iz kraja %s ni nobenih povezav.", n);
                                break;
                            } else {
                                System.out.printf("Iz kraja %s lahko z max %d prestopanji pridemo do naslednjih krajev:\n", n, k);
                                for (Kraj destinacija : n.destinacije(k))
                                    System.out.println(destinacija);
                            }
                        }
                    }
                    if (ni) System.out.printf("NAPAKA: podanega kraja (%s) ni na seznamu krajev.", kraj);
                } else System.out.println("Napacno stevilo argumentov");
            }
            case 5 -> {
                if (args.length == 3) {
                    e.preberiKraje(args[1]);
                    e.preberiPovezaveBinarno(args[2]);
                    e.izpisiPovezave();
                } else System.out.println("Napacno stevilo argumentov");
            }
        }
    }
}






    class Kraj implements Comparable<Kraj>{
        private String ime;
        private String kratica;
        private List<Vlak> odhodi;
        private int id;

        Kraj(String ime, String kratica) {
            this.ime = ime;
            this.kratica = kratica;
            this.odhodi = new ArrayList<>();
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public List<Vlak> getOdhodi() {
            return odhodi;
        }
        public String getIme() {
            return ime;
        }

        @Override
        public String toString() {
            return ime + " " + "(" + kratica + ")";
        }

        boolean dodajOdhod(Vlak vlak) {
            if(odhodi.contains(vlak))
                return false;
            odhodi.add(vlak);
            return true;
        }

        @Override
        public int compareTo(Kraj k) {
            if (this.kratica.compareTo(k.kratica) == 0)
                return this.ime.compareTo(k.ime);
            return this.kratica.compareTo(k.kratica);
        }

        Set<Kraj> dosegljivi = new TreeSet<>();
        Set<Kraj> destinacije(int k){
            if (k == 0) {
                for(Vlak vlak : odhodi)
                    dosegljivi.add(vlak.getKoncni());
                return dosegljivi;
            }
            for (Vlak vlak: odhodi) {
                dosegljivi.add(vlak.getKoncni());
                dosegljivi.addAll(vlak.getKoncni().destinacije(k - 1));
            }
            dosegljivi.remove(this);
            return dosegljivi;
        }
    }







    abstract class Vlak implements Comparable<Vlak>{
        private String id;
        private Kraj zacetni;
        private Kraj koncni;
        private int trajanjeVoznje;

        Vlak(String id, Kraj zacetni, Kraj koncni, int trajanjeVoznje) {
            this.id = id;
            this.zacetni = zacetni;
            this.koncni = koncni;
            this.trajanjeVoznje = trajanjeVoznje;
        }

        public Kraj getKoncni() {
            return koncni;
        }
        public int getTrajanjeVoznje() {
            return trajanjeVoznje;
        }
        abstract String opis();
        abstract double cenaVoznje();

        @Override
        public String toString() {
            if (trajanjeVoznje < 60)
                return String.format("Vlak %s (%s) %s -- %s (%d min, %.2f EUR)", id, opis(), zacetni, koncni, trajanjeVoznje, cenaVoznje());
            else {
                int h = trajanjeVoznje / 60;
                int m = trajanjeVoznje % 60;
                String s = String.format("%d.%02d", h, m);
                return String.format("Vlak %s (%s) %s -- %s (%sh, %.2f EUR)", id, opis(), zacetni, koncni, s, cenaVoznje());
            }
        }

        @Override
        public int compareTo(Vlak vlak) {
            return Double.compare(vlak.cenaVoznje(), this.cenaVoznje());
        }
    }








    class EkspresniVlak extends Vlak {
        private final int HITROST = 110;
        private final double CENA = 0.154;
        private double doplacilo;

        EkspresniVlak(String id, Kraj zacetni, Kraj koncni, int trajanjeVoznje, double doplacilo) {
            super(id, zacetni, koncni, trajanjeVoznje);
            this.doplacilo = doplacilo;
        }

        @Override
        String opis() {
            return "ekspresni";
        }

        @Override
        double cenaVoznje() {
            return ((double) getTrajanjeVoznje() /60) * HITROST * CENA + doplacilo;
        }
    }








    class RegionalniVlak extends Vlak{
        private final int HITROST = 50;
        private final double CENA = 0.068;


        RegionalniVlak(String id, Kraj zacetni, Kraj koncni, int trajanjeVoznje) {
            super(id, zacetni, koncni, trajanjeVoznje);
        }

        @Override
        String opis() {
            return "regionalni";
        }

        @Override
        double cenaVoznje() {
            return ((double) getTrajanjeVoznje() /60) * HITROST * CENA;
        }
    }






    class EuroRail {
        public ArrayList<Kraj> getKraji() {
            return kraji;
        }
        private ArrayList<Kraj> kraji;
        private ArrayList<Vlak> vlaki;
        HashMap<String, String> mesta = new HashMap<>();

        EuroRail(ArrayList<Kraj> kraji, ArrayList<Vlak> vlaki) {
            this.kraji = kraji;
            this.vlaki = vlaki;
        }

        public boolean preberiKraje(String imeDatoteke) {
            try {
                Scanner sc = new Scanner(new File(imeDatoteke));
                int i = 1;
                while (sc.hasNextLine()) {
                    String vrstica = sc.nextLine();
                    if (vrstica.contains(";")) {
                        String[] x = vrstica.split(";");
                        if (x.length == 2) {
                            Kraj kraj = new Kraj(x[0], x[1]);
                            if (!kraji.contains(kraj) && !mesta.containsKey(x[0])) {
                                mesta.put(x[0], x[1]);
                                kraji.add(kraj);
                                kraj.setId(i++);
                            }
                        }
                    }
                }
                sc.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public boolean preberiPovezave(String imeDatoteke) {
            try {
                int voznja;
                Scanner sc = new Scanner(new File(imeDatoteke));
                while (sc.hasNextLine()) {
                    String vrstica = sc.nextLine();
                    Kraj zacetniKraj = null; Kraj koncniKraj = null;
                    if (vrstica.contains(";")) {
                        String[] vlak = vrstica.split(";");
                        if (vlak.length >= 4) {
                            for (Kraj kraj : kraji) {
                                if (kraj.getIme().equals(vlak[1]))
                                    zacetniKraj = kraj;
                                if (kraj.getIme().equals(vlak[2]))
                                    koncniKraj = kraj;
                            }
                            if (zacetniKraj != null && koncniKraj != null && !(zacetniKraj.getIme().equals(koncniKraj.getIme()))) {
                                if (vlak[3].contains(".")) {
                                    String[] s = vlak[3].split("\\.");
                                    int h = Integer.parseInt(s[0]);
                                    int m = Integer.parseInt(s[1]);
                                    voznja = h * 60 + m;
                                } else voznja = Integer.parseInt(vlak[3]);
                                if (vlak.length == 4) {
                                    RegionalniVlak regVlak = new RegionalniVlak(vlak[0], zacetniKraj, koncniKraj, voznja);
                                    if (kraji.contains(zacetniKraj) && kraji.contains(koncniKraj)) {
                                        vlaki.add(regVlak);
                                        for (Kraj kraj : kraji)
                                            if (kraj.getIme().equals(zacetniKraj.getIme()))
                                                kraj.dodajOdhod(regVlak);
                                    }
                                } else if (vlak.length == 5) {
                                    double doplacilo = Double.parseDouble(vlak[4]);
                                    EkspresniVlak expVlak = new EkspresniVlak(vlak[0], zacetniKraj, koncniKraj, voznja, doplacilo);
                                    if (kraji.contains(zacetniKraj) && kraji.contains(koncniKraj)) {
                                        vlaki.add(expVlak);
                                        for (Kraj kraj : kraji)
                                            if (kraj.getIme().equals(zacetniKraj.getIme()))
                                                kraj.dodajOdhod(expVlak);
                                    }
                                }
                            }
                        }
                    }
                }
                sc.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public boolean preberiPovezaveBinarno(String datotekaVlaki){
            try {
                FileInputStream fis = new FileInputStream(datotekaVlaki);
                DataInputStream dis = new DataInputStream(fis);
                byte[] buffer = new byte[6];
                while (dis.available() >= 12) {
                    dis.read(buffer, 0, 6);
                    String id = new String(buffer, 0, 6).trim();
                    int zacetniIndeks = dis.readByte();
                    int koncniIndeks = dis.readByte();
                    Kraj zacetniKraj = null; Kraj koncniKraj = null;
                    for (Kraj kraj : kraji) {
                        if (kraj.getId() == zacetniIndeks)
                            zacetniKraj = kraj;
                        if (kraj.getId() == koncniIndeks)
                            koncniKraj = kraj;
                    }
                    int casVoznje = dis.readShort();
                    int centi = dis.readShort();
                    double doplacilo = (double) centi / 100;
                    if (zacetniKraj != null && koncniKraj != null && !(zacetniKraj.getIme().equals(koncniKraj.getIme()))) {
                        if (centi == 0) {
                            RegionalniVlak reg = new RegionalniVlak(id, zacetniKraj, koncniKraj, casVoznje);
                            vlaki.add(reg);
                        } else {
                            EkspresniVlak eks = new EkspresniVlak(id, zacetniKraj, koncniKraj, casVoznje, doplacilo);
                            vlaki.add(eks);
                        }
                    }
                }
                return true;
            } catch (Exception e){
                return false;
            }
        }

        void izpisiKraje() {
            System.out.println("Kraji, povezani z vlaki:");
            for (Kraj kraj : kraji)
                System.out.println(kraj);
        }

        void izpisiPovezave() {
            System.out.println("Vlaki, ki povezujejo kraje:");
            for (Vlak vlak : vlaki)
                System.out.println(vlak);
        }

        void izpisiKrajeInVlaki(){
            for (Kraj kraj : kraji) {
                System.out.println(kraj);
                System.out.printf("odhodi vlakov (%d):\n", kraj.getOdhodi().size());
                for (Vlak vlak: kraj.getOdhodi())
                    System.out.println(" - " + vlak);
                System.out.println();
            }
        }

        void urejenIzpis(){
            Collections.sort(kraji);
            for (Kraj kraj : kraji)
                Collections.sort(kraj.getOdhodi());
            izpisiKrajeInVlaki();
        }
    }