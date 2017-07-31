import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import property.FTPClientProperty;

import java.io.FileOutputStream;
import java.io.IOException;

public class FTPClientFunctions {
    private FTPClient ftp;

    public FTPClientFunctions() {

        ftp = new FTPClient();

        System.out.println("FTP URL is:" + ftp.getDefaultPort());

    }

    public void connect(String username, String password) throws IOException {
        String host = FTPClientProperty.getProperty("host");
        int port = Integer.parseInt(FTPClientProperty.getProperty("port"));
        ftp.connect(host, port);
        ftp.login(username, password);

    }

    public void goToParentDirectory() throws IOException {
        ftp.changeToParentDirectory();
    }

    public void showCurrentDir() throws IOException {
        System.out.println(ftp.printWorkingDirectory());
    }

    public void showDirList() throws IOException {

        FTPFile[] files = recieveDirList();
        String details;
        int i = 1;
        for (FTPFile file : files) {
            details = file.getName();
            System.out.println(i++ + ": " + details);
        }
    }


    public void goToFolder(String dir) throws IOException {

        FTPFile[] files = recieveDirList();

        for (FTPFile file : files) {
            if (dir.equals(file.getName())) {
                ftp.changeWorkingDirectory(dir);
            }
        }
    }

    public void downloadFile(String remoteFilePath, String localFilePath) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            this.ftp.retrieveFile(remoteFilePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FTPFile[] recieveDirList() throws IOException {

        FTPFile[] files = ftp.listFiles();
        return files;

    }

    public String recieveCurrentDirFilePath() throws IOException {

        return ftp.printWorkingDirectory();
    }


    public boolean disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {

            }
        }

        return false;
    }


}
