public class DN02 {
    public static void main(String args[]) {
        if (args.length == 0){
            System.out.println("Napaka pri uporabi programa!");
            System.exit(1);
        }
        String beseda = "";
        for (int i = 0; i < args.length; i++){
            beseda += " " + args[i];
        }
        for (int i = 0; i < beseda.length() + 3; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("* " + beseda.trim() + " *");
        for (int i = 0; i < beseda.length() + 3; i++) {
            System.out.print("*");
        }
    }
}
