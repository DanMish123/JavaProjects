import java.io.File;
import java.util.Random;
import java.util.Scanner;
public class DN03 {
    public static void main(String[] args) throws Exception{
        if(args.length != 3){
            System.out.println("Napačni argumenti.");
            System.exit(0);
        }
        //odpremo datoteko, preberemo prvo vrstico in določamo velikost tabele
        Scanner datoteka = new Scanner(new File(args[0]));
        int n = datoteka.nextInt();
        String[] besede = new String[n];
        int i = 0;
        while (datoteka.hasNext()){
            besede[i] = datoteka.next();
            i++;
        }
        //vzamemo naključno besedo, potem naključno črko iz te besede in geslu dodamo to črko
        int dolzinaGeslo = Integer.parseInt(args[1]);
        int seme = Integer.parseInt(args[2]);
        Random rand = new Random(seme);
        String geslo = "";
        for (int j=0; j<dolzinaGeslo; j++) {
            int beseda = rand.nextInt(besede.length);
            int crka = rand.nextInt(besede[beseda].length());
            geslo += besede[beseda].charAt(crka);
        }
        System.out.println(geslo);
        datoteka.close();
    }
}
