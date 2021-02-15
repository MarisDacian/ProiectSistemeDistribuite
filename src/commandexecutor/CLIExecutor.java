/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commandexecutor;

import fileexplorer.FileExplorer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Leni
 */
public final class CLIExecutor extends UnicastRemoteObject implements RMICommandExecutor {

    private static final Pattern REGEX = Pattern.compile("^(.+?)([ ](.*))?$");

    private final FileExplorer fileExplorer;

    public CLIExecutor(FileExplorer fileExplorer) throws RemoteException {
        this.fileExplorer = fileExplorer;
    }

    @Override
    public String execute(String command) throws RemoteException {

        Matcher matcher = REGEX.matcher(command);

        if (matcher.matches()) {

            String cmd = matcher.group(1);
            switch (cmd) {
                case "read":
                    return handleRead(matcher.group(3));
                case "ls":
                    return fileExplorer.listAllFiles();
                case "rm":
                    return handleRemove(matcher.group(3));
                case "touch":
                    return handleTouch(matcher.group(3));
                case "append":
                    return processAppend(matcher.group(3));
                case "toucha":
                    return processCreateAndAppend(matcher.group(3));
                default:
                    return "The command does not exist";
            }
        }

        return "Invalid input!";

    }

    private String handleRemove(String fileName) {

        if (isNullOrEmpty(fileName)) {
            return String.format("Error in %s", fileName);
        }
        return fileExplorer.deleteFile(fileName);
    }

    private String handleRead(String fileName) {

        if (isNullOrEmpty(fileName)) {
            return String.format("Error in %s", fileName);
        }
        return fileExplorer.readFileContent(fileName);
    }

    private String handleTouch(String fileName) {

        if (isNullOrEmpty(fileName)) {
            return String.format("Error in %s", fileName);
        }

        return fileExplorer.createFile(fileName);
    }

    private String processAppend(String appendParams) {

        if (isNullOrEmpty(appendParams)) {
            return String.format("Error in %s", appendParams);
        }

        Matcher matcher = REGEX.matcher(appendParams);

        if (matcher.matches()) {
            String argument = matcher.group(3);
            if (isNullOrEmpty(argument)) {

                return "Incomplete params";
            }
            return fileExplorer.writeToFile(matcher.group(1), argument);

        }
        return "The command can't be executed";
    }

    private String processCreateAndAppend(String appendParams) {
        Matcher matcher = REGEX.matcher(appendParams);

        if (matcher.matches()) {
            String argument = matcher.group(3);

            if (isNullOrEmpty(argument)) {

                return "Incomplete params";
            }

            return fileExplorer.createAndWriteToFile(matcher.group(1), argument);
        }

        return "The command can't be executed";
    }

    private boolean isNullOrEmpty(String fileName) {
        return fileName == null || fileName.isEmpty();
    }

}
