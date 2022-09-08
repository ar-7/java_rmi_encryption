package com.company;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor; // importing Jasypt PBE library

// This class acts as the server for the encryption and decryption service
// The service uses password based encryption (PBE)
// The server must be running for the client to function

public class Server implements Interface {

    String output; // Declaring string for storing the output of the methods

    // Function that encrypts a given input string using a given password
    public String EncryptFunc(String inputString, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); // New instance of the PBE encryptor
        encryptor.setPassword(password); // Calling the set password function of the encryptor instance
        output = encryptor.encrypt(inputString); // Saving the encrypted output to a string
        return output; // Sending the output back to the caller
    }

    // Function that decrypts a given input string using a given password
    public String DecryptFunc(String inputString, String password) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();// New instance of the PBE encryptor
        decryptor.setPassword(password); // Calling the set password function of the encryptor instance
        output = decryptor.decrypt(inputString); // Saving the decrypted output to a string
        return output; // Sending the output back to the caller
    }

    // Main function that defines the server settings
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Creates an entry in the RMI registry
            Server obj = new Server(); // Starts a server object
            Interface stub = (Interface) UnicastRemoteObject.exportObject(obj, 1099); // Creates an instance of the interface RMI object

            // Bind the RMI object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("EncryptionServer", stub);

            System.err.println("Server ready"); // Report server active
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
