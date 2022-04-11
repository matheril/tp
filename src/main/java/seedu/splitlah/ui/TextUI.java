package seedu.splitlah.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents a user interface that reads from and writes to the command line.
 *
 * @author Saurav
 */
public class TextUI {
    
    Scanner in;
    PrintStream out;

    public TextUI(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public TextUI() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    public void printMessage(String message) {
        out.print(message);
    }

    public void printlnMessage(String message) {
        out.println(message);
    }

    public void printlnMessageWithDivider(String message) {
        out.println(message);
        printDivider();
    }

    public String readNextLine() {
        printMessage(Message.PROMPT_TEXTUI_AWAITING_INPUT);
        return in.nextLine();
    }

    public void printWelcome() {
        printDivider();
        out.println(Message.ASCII_TEXTUI_LOGO);
        out.println("Welcome to SplitLah!");
        printDivider();
    }

    private void printDivider() {
        out.println("============================================================");
    }

    public void printHelpMenu() {
        printlnMessageWithDivider(Message.MESSAGE_TEXTUI_HELP_MENU);
    }

    public void printFarewell() {
        printDivider();
        out.println(Message.ASCII_TEXTUI_LOGO);
        out.println("Goodbye!");
        printDivider();
    }
}
