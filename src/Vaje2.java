public class Vaje2 {
    public static void main(String[] args) {
        System.out.println("  n              n!            Stirling(n)      napaka (%)");
        System.out.println("----------------------------------------------------------");
//        for (int i=1; i<=20; i++){
//            float napaka = (((float)fakultetaL(i)-stirlingL(i))/fakultetaL(i))*100;
//            System.out.printf("%3d %20d %20d %11.7f", i, fakultetaL(i), stirlingL(i), napaka);
//            System.out.println();
//        }
        for (int i = 1; i <= 100; i++) {
            double napaka = ((fakultetaD(i) - stirlingD(i)) / fakultetaD(i)) * 100;
            System.out.printf("%3d %17.9E %17.9E %11.7f", i, fakultetaD(i), stirlingD(i), napaka);
            System.out.println();
        }
    }

    static long fakultetaL(int n) {
        long x = 1;
        for (int i = 1; i <= n; i++) {
            x *= i;
        }
        return x;
    }

    static long stirlingL(int n) {
        return Math.round(Math.sqrt(2 * Math.PI * n) * Math.pow(n / Math.E, n));
    }

    static double fakultetaD(int n) {
        double x = 1;
        for (int i = 1; i <= n; i++) {
            x *= i;
        }
        return x;
    }

    static double stirlingD(int n) {
        return Math.sqrt(2 * Math.PI * n) * Math.pow(n / Math.E, n);
    }
}
