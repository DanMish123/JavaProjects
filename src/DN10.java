import java.util.TreeSet;

public class DN10 {

    static TreeSet<String> getVsiPodnizi(String niz){
        TreeSet<String> podnizi = new TreeSet<>();
        for(int i = 0; i <= niz.length(); i++)
            for (int j = i; j < niz.length(); j++)
                podnizi.add(niz.substring(i, j+1));
        return podnizi;
    }

    public static void main(String[] args) {
        if(args.length == 0)
            System.out.println("Podaj argument");
        else {
            TreeSet<String> presek = new TreeSet<>(getVsiPodnizi(args[0]));
            for (int i = 1; i < args.length; i++) {
                TreeSet<String> a = getVsiPodnizi(args[i]);
                presek.retainAll(a);
            }
            String najdaljsa = "";
            for(String str: presek)
                if(str.length() > najdaljsa.length())
                    najdaljsa = str;
            System.out.println(najdaljsa);
        }
    }
}
