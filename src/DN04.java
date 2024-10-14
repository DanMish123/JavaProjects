public class DN04 {
    public static void main(String[] args) {
        String beseda = ascii(args[0]);
        System.out.println(beseda);
    }
    static String ascii(String beseda){
        String binarnaCrka, result = "";
        //dokler binarna beseda vsebuje znake
        while(beseda.length()>7){
            //prvih 8 znakov
            binarnaCrka = beseda.substring(0,8);
            //pretvori iz binarni v desetiski, poten pretstavi stevilo (ascii) kot crko
            result += (char) Integer.parseInt(binarnaCrka, 2);
            //pobrisi prvih 8 znakov in ponovi
            beseda = beseda.substring(8);
        }
        return result;
    }
}