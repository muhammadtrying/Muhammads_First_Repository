package uz.pdp.chat.util;

import java.util.Scanner;

public interface Input {
    static Integer inputInt(String msg) {
        System.out.print(msg + ": ");
        Scanner scanner = new Scanner(System.in);
        if ( scanner.hasNextInt() ) {
            return scanner.nextInt();
        }
        return inputInt(msg);
    }

    static String inputStr(String msg) {
        System.out.print(msg + ": ");
        return new Scanner(System.in).nextLine();
    }
}
