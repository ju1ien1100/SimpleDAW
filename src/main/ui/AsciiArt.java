package ui;

public class AsciiArt {

    //EFFECTS: Makes my logo for FLJuJu in the command
    static void displayAsciiArt() {
        System.out.println("  ______ _          _            _       ");
        timeOut(400);
        System.out.println(" |  ____| |        | |          | |      ");
        timeOut(300);
        System.out.println(" | |__  | |        | |_   _     | |_   _ ");
        timeOut(300);
        System.out.println(" |  __| | |    _   | | | | |_   | | | | |");
        timeOut(300);
        System.out.println(" | |    | |___| |__| | |_| | |__| | |_| |");
        timeOut(300);
        System.out.println(" |_|    |______\\____/ \\__,_|\\____/ \\__,_|");
        System.out.println();
        System.out.println();
    }

    //EFFECTS: This is a timeout where the time is in milliseconds
    private static void timeOut(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
