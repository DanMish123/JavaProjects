interface Lik {
    public double obseg();
}

class Pravokotnik implements Lik {
    private int a;
    private int b;

    Pravokotnik(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double obseg() {
        return 2*a + 2*b;
    }
}

class Kvadrat extends Pravokotnik{
    Kvadrat(int a){
        super(a,a);
    }
}

class Nkotnik implements Lik {
    private int n;
    private int a;

    Nkotnik(int n, int a) {
        this.n = n;
        this.a = a;
    }

    @Override
    public double obseg() {
        return n*a;
    }
}



public class DN09 {
    static int skupniObseg(Lik[] liki){
        int obseg = 0;
        for (int i = 0; i < liki.length; i++) {
            obseg += liki[i].obseg();
        }
        return obseg;
    }

    public static void main(String[] args) {
        Lik[] liki = new Lik[args.length];
        for (int i = 0; i < args.length; i++) {
            String[] figura = args[i].split(":");
            switch (figura[0]){
                case "kvadrat":
                    liki[i] = new Kvadrat(Integer.parseInt(figura[1]));
                    break;
                case "pravokotnik":
                    liki[i] = new Pravokotnik(Integer.parseInt(figura[1]), Integer.parseInt(figura[2]));
                    break;
                case "nkotnik":
                    liki[i] = new Nkotnik(Integer.parseInt(figura[1]), Integer.parseInt(figura[2]));
                    break;
            }
        }
        System.out.println(skupniObseg(liki));
    }
}
