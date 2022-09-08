package com.company;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;


public class Client {

    private Client() {
    }

    static String inputString; // Declaring a string to store the future input
    static String decryptionPassword; // Declaring a string to store the encryption/decryption password
    static Scanner scanMenu; // initialise scanners for input
    static Scanner scanInput; // initialise scanners for input

    public static void main(String[] args) {

        scanMenu = new Scanner(System.in); //Creates a scanner object
        scanInput = new Scanner(System.in); //Creates a scanner object

        // Begin menu
        System.out.println("Welcome, User!");
        System.out.println("This program is able to encrypt and decrypt a given string using an encryption password.");
        System.out.println("\nPlease select which function you would like to use by entering the corresponding number:");
        System.out.println("\nOption 1. Encrypt (1)\nOption 2. Decrypt (2)\nOption 3. Quit application (3)");

        // Validate menu selection with hard coded array of options.
        while (!scanMenu.hasNext("[1,2,3]")) {
            System.out.println("I'm sorry, I can only take one of the options presented.");
            scanMenu.nextLine(); // This is important! Loops otherwise.
        }
        int menuSelect = scanMenu.nextInt(); //Stores menu selection

        // Loads the corresponding functionality, depending on menu selection.
        switch (menuSelect) {
            case 1:

                System.out.println("\nYou have chosen encrypt, please enter the string you would like to encrypt:"); // Report status
                inputString = scanInput.nextLine().trim(); // Take input, trims whitespace

                System.out.println("Thank you, input string is: " + inputString + "\n\nPlease enter the encryption password:");
                decryptionPassword = scanInput.nextLine().trim(); // Take input, trims whitespace

                String host = (args.length < 1) ? null : args[1099]; // Define the server's host
                try {
                    Registry registry = LocateRegistry.getRegistry(host); // Pulls the stub for the registry
                    Interface stub = (Interface) registry.lookup("EncryptionServer"); // Looks up the required binding in the registry
                    String response = stub.EncryptFunc(inputString, decryptionPassword); // Calls the function via the Interface
                    System.out.println("\nEncrypted string:\n" + response); // Reports the return to the user
                } catch (Exception e) {
                    System.err.println("Client exception: " + e);
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.println("\nYou have chosen decrypt, please enter the encrypted string you would like to decrypt:"); // Report status
                inputString = scanInput.nextLine().trim(); // Take input, trims whitespace

                System.out.println("Thank you, your input string is: " + inputString + "\n\nPlease enter the decryption password:");
                decryptionPassword = scanInput.nextLine().trim(); // Take input, trims whitespace

                host = (args.length < 1) ? null : args[1099]; // Define the server's host
                try {
                    Registry registry = LocateRegistry.getRegistry(host); // Pulls the stub for the registry
                    Interface stub = (Interface) registry.lookup("EncryptionServer"); // Looks up the required binding in the registry
                    String response = stub.DecryptFunc(inputString, decryptionPassword); // Calls the function via the Interface
                    System.out.println("\nDecrypted string:\n" + response); // Reports the return to the user
                } catch (Exception e2) {
                    if (Objects.equals(e2.toString(), "org.jasypt.exceptions.EncryptionOperationNotPossibleException")){ // iI the wrong password is entered
                        System.out.println("Unable to decrypt, possible incorrect password. Terminating program."); // Closes the client
                        System.exit(0);
                        break;
                    }
                    System.err.println("Client exception: " + e2);
                    e2.printStackTrace();
                }
                break;
            case 3:
                System.out.println("Thank you, terminating program.");
                System.exit(0); // Closes the client
                break;
        }
    }
}