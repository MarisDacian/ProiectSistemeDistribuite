/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandexecutor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Leni
 */
public interface RMICommandExecutor extends Remote {
    
    String execute(String command) throws RemoteException; 
    
}
