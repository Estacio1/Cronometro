package project.timers;

import java.net.URL;
import java.util.Scanner;
import javax.swing.ImageIcon;

public class Timers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Cronometro");
            System.out.println("2. Secuencia");
            System.out.println("0. Salir");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    new sevenOne();
                    break;
                case "2":
                    sevenTwo viewer = new sevenTwo();
                    viewer.setVisible(true);
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }
}
