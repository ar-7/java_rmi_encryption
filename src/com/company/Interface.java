package com.company;

import java.rmi.Remote;
import java.rmi.RemoteException;

// This is the remote method interface in which the remote methods are declared

public interface Interface extends Remote {
    // Encryption function defined in the server
    String EncryptFunc(String inputString, String password) throws RemoteException;

    // Decryption method defined in the server
    String DecryptFunc(String inputString, String password) throws RemoteException;
}
