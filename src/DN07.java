import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DN07 {
    public static void main(String[] args) throws Exception {
        switch (args[0]) {
            case "1" -> izpisi_datoteke(new File(args[1]));
            case "2" -> najvecja_datoteka(new File(args[1]));
            case "3" -> izpis_vsebine(new File(args[1]), Integer.parseInt(args[2]));
            case "4" -> kopiraj_datoteko(args[2], args[3]);
            case "5" -> zdruzi_datoteko(new File(args[1]), args[2]);
            case "6" -> najdiVDatotekah(new File(args[1]), args[2]);
            case "7" -> drevo(new File(args[1]));
            case "8" -> resiMatematicneIzraze(new File(args[1]));
            case "9" -> nNajvecjih(new File(args[1]), Integer.parseInt(args[2]));
            default -> System.out.println("Napacen argument");
        }
    }

    static void izpisi_datoteke(File f) {
        String[] datoteke = f.list();
        for (int i = 0; i < datoteke.length; i++) {
            File datoteka = new File(datoteke[i]);
            if (datoteka.isDirectory())
                System.out.printf("%20s%20s%10.3f\n", datoteka, "Mapa", datoteka.length() / 1000.0);
            else System.out.printf("%20s%20s%10.3f\n", datoteka, "Datoteka", datoteka.length() / 1000.0);
        }
    }

    static void najvecja_datoteka(File f) {
        String[] datoteke = f.list();
        float najmanjsaSize = Float.MAX_VALUE; float najvecjaSize = 0;
        String najvecjaString = ""; String najmanjsaString = "";
        for (int i = 0; i < datoteke.length; i++) {
            File datoteka = new File(datoteke[i]);
            if (datoteka.isFile()) {
                if (datoteka.length() > najvecjaSize) {
                    najvecjaSize = datoteka.length();
                    najvecjaString = datoteka.getName();
                }
                if (datoteka.length() < najmanjsaSize){
                    najmanjsaSize = datoteka.length();
                    najmanjsaString = datoteka.getName();
                }
            }
        }
        System.out.printf("%s %.3f\n", najvecjaString, najvecjaSize/1000.0);
        System.out.printf("%s %.3f", najmanjsaString, najmanjsaSize/1000.0);
    }

    static void izpis_vsebine(File f, int n) throws Exception {
        String[] datoteke = f.list();
        for (int i = 0; i < datoteke.length; i++) {
            File datoteka = new File(datoteke[i]);
            if (datoteka.getName().endsWith(".txt")) {
                int count = 0;
                System.out.println(datoteka);
                Scanner sc = new Scanner(new File(datoteka.toString()));
                while (sc.hasNextLine() && count < n){
                    System.out.println("    " + sc.nextLine());
                    count++;
                }
                sc.close();
            } else if (datoteka.isFile())
                System.out.println(datoteka + " (ni tekstovna datoteka)");
        }
    }

    static void kopiraj_datoteko(String vhodnaDatoteka, String izhodnaDatoteka) throws Exception {
        File datoteka = new File(izhodnaDatoteka);
        Scanner sc = new Scanner(new File(vhodnaDatoteka));
        if (!datoteka.exists()) {
            datoteka.createNewFile();
            PrintWriter pw = new PrintWriter(datoteka);
            while (sc.hasNextLine())
                pw.println(sc.nextLine());
            sc.close();
            pw.close();
        } else if (datoteka.length() == 0) {
            PrintWriter pw = new PrintWriter(datoteka);
            while (sc.hasNextLine())
                pw.println(sc.nextLine());
            sc.close();
            pw.close();
        } else System.out.println("Napaka pri kopiranju, datoteka že vsebuje besedilo");
    }

    static void zdruzi_datoteko(File direktorij, String izhodnaDatoteka) throws Exception {
        PrintWriter pw = new PrintWriter(izhodnaDatoteka);
        String[] datoteke = direktorij.list();
        boolean neVsebuje = true;
        for (int i = 0; i < datoteke.length; i++) {
            File datoteka = new File(direktorij, datoteke[i]);
            if (datoteka.getName().endsWith(".txt")){
                neVsebuje = false;
                Scanner sc = new Scanner(new File(datoteka.toString()));
                while (sc.hasNextLine())
                    pw.println(sc.nextLine());
                sc.close();
            }
        }
        pw.close();
        if(neVsebuje)
            System.out.println("Direktorij ne vsebuje tekstovnih datotek.");
    }

    static  void najdiVDatotekah(File f, String iskanNiz) throws Exception {
        String[] datoteke = f.list();
        for (int i = 0; i < datoteke.length; i++) {
            int count = 0;
            File datoteka = new File(f, datoteke[i]);
            if (datoteka.getName().endsWith(".txt")){
                Scanner sc = new Scanner(new File(datoteka.toString()));
                while (sc.hasNextLine()){
                    String vrstica = sc.nextLine();
                    count++;
                    if (vrstica.contains(iskanNiz))
                        System.out.printf("%s %d: %s%n", datoteka.getName(), count, vrstica);
                }
                sc.close();
            }
        }
    }

    static int prosti = 0;
    public static void drevo(File f) {
        if (f.isDirectory()) {
            System.out.println("    ".repeat(prosti) + "/" + f.getName());
            prosti++;
            File[] datoteke = f.listFiles();
            for (int i = 0; i < datoteke.length; i++)
                drevo(datoteke[i]);
            prosti--;
        } else System.out.println("    ".repeat(prosti) + f.getName());
    }

    static boolean jeVeljavna(String vrstica){
        for (int j = 0; j < vrstica.length(); j++) {
            char znak = vrstica.charAt(j);
            if (!(Character.isDigit(znak) || znak == '+' || znak == '-'))
                return false;
        }
        return true;
    }

    static void resiMatematicneIzraze(File f) throws Exception {
        String[] datoteke = f.list();
        for (int i = 0; i < datoteke.length; i++) {
            File datoteka = new File(f, datoteke[i]);
            if (datoteka.isFile())
                System.out.println(datoteka.getName());
            if (datoteka.getName().endsWith(".txt")) {
                Scanner sc = new Scanner(new File(datoteka.toString()));
                while (sc.hasNextLine()) {
                    String vrstica = sc.nextLine();
                    if (jeVeljavna(vrstica) && vrstica.length() > 0) {
                        int rezultat = Character.getNumericValue(vrstica.charAt(0));
                        char operator = vrstica.charAt(1);
                        for (int j = 2; j < vrstica.length(); j++) {
                            if (Character.isDigit(vrstica.charAt(j))) {
                                int number = Character.getNumericValue(vrstica.charAt(j));
                                if (operator == '+')
                                    rezultat += number;
                                else if (operator == '-')
                                    rezultat -= number;
                            } else operator = vrstica.charAt(j);
                        }
                        System.out.printf("  %s=%d\n", vrstica, rezultat);
                    }
                }
            }
        }
    }

    static void nNajvecjih(File f, int n) {
        seznam(f);
        bubblesort(datoteke);
        for (int i = 0; i < n; i++)
            System.out.println(datoteke.get(i).getName() + " " + datoteke.get(i).length());
    }

    static ArrayList<File> datoteke = new ArrayList<>();
    static void seznam(File f) {
        if (f.isDirectory()) {
            File[] datoteke = f.listFiles();
            for (int i = 0; i < datoteke.length; i++)
                seznam(datoteke[i]);
        } else datoteke.add(f);
    }

    static void bubblesort(ArrayList<File> array) {
        int i,j; File temp;
        for (i = 0; i < (array.size()-1); i++) {
            for (j = 0; j < array.size()-i-1; j++) {
                if (array.get(j).length() < array.get(j+1).length()) {
                    temp = array.get(j);
                    array.set(j, array.get(j+1));
                    array.set(j+1, temp);
                }
            }
        }
    }
}