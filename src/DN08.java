import java.io.File;
import java.util.Scanner;

class Planet{
    private String ime;
    private int radij;

    Planet(String ime, int radij){
        this.ime = ime;
        this.radij = radij;
    }

    public String getIme(){
        return this.ime;
    }

    public double povrsina(){
        return 4 * Math.PI * Math.pow(radij,2);
    }
}

public class DN08 {
    public static Planet[] preberiPlanete(String datoteka) throws Exception{
        Planet[] planeti = new Planet[8];
        Scanner sc = new Scanner(new File(datoteka));
        int i = 0;
        while (sc.hasNextLine() && i<8) {
            String vrstica = sc.nextLine();
            String p = vrstica.split(":")[0];
            int r = Integer.parseInt(vrstica.split(":")[1]);
            Planet planeta = new Planet(p, r);
            planeti[i++] = planeta;
        }
        sc.close();
        return planeti;
    }

    public static void main(String[] args) throws Exception {
        Planet[] planeti = preberiPlanete(args[0]);
        String[] koliko = args[1].split("\\+");
        long povrsinaPlanete = 0;
        for (int j = 0; j < koliko.length; j++) {
            for (int k = 0; k < planeti.length; k++) {
                if (koliko[j].equalsIgnoreCase(planeti[k].getIme())) {
                    povrsinaPlanete += planeti[k].povrsina();
                    break;
                }
            }
        }
        System.out.printf("Povrsina planetov \"%s\" je %d milijonov km2", args[1], povrsinaPlanete/1000000);
    }
}