public class DN06 {
    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Napacen argument.");
        else {
            String niz = args[0].toLowerCase();
            String soroden = "";
            for (int i=0; i<niz.length(); i++)
                soroden += 'a';
            int bsdNiz = bsd(niz);
            while (soroden != "") {
                if (bsd(soroden) == bsdNiz) {
                    System.out.println(soroden);
                    break;
                }
                soroden = povecaj(soroden);
            }
        }
    }

    static String povecaj(String niz) {
        for (int i=niz.length()-1; i>=0; i--) {
            char crka = niz.charAt(i);
            if (crka>='a' && crka<'z')
                return niz.substring(0,i) + (char)(crka+1) + "a".repeat(niz.length()-1-i);
        }
        return "";
    }

        static int bsd (String niz){
            int checksum = 0;
            for (int i = 0; i < niz.length(); i++) {
                checksum = (checksum >> 1) + ((checksum & 1) << 15);
                checksum += niz.charAt(i);
                checksum &= 0xffff;
            }
            return checksum;
        }
    }
