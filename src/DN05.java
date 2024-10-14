public class DN05 {
    public static void main(String[] args) {

        StringBuilder niz = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            niz.append(args[i] + " ") ;
        }
        niz = new StringBuilder(niz.toString().trim());

        int[] tabela = new int[10];
        boolean stevka = false;
        for (int i=0; i<niz.length(); i++){
            int c = niz.charAt(i);
            if (c >= '0' && c <= '9'){
                int j = Character.getNumericValue(c);
                tabela[j] += 1;
                stevka = true;
            }
        }

        if (stevka){
            int naj = 0;
            for (int i=1; i<tabela.length; i++) {
                if (tabela[i] > naj) {
                    naj = tabela[i];
                }
            }
            System.out.printf("'%s' -> ", niz);
            for (int i=0; i<tabela.length; i++){
                if (tabela[i] == naj)
                    System.out.printf("%d ",i);
            }
            System.out.printf("(%d)", naj);
        } else
            System.out.printf("V nizu '%s' ni stevk", niz);
    }
}
