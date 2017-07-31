import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import property.FTPClientProperty;

import java.io.*;
import java.net.Socket;

public class FTPClientFunc {
    private FTPClient ftp = null;
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;

    public FTPClientFunc() throws IOException {
        //din = new DataInputStream(ClientSoc.getInputStream());
        // dout = new DataOutputStream(ClientSoc.getOutputStream());
        br = new BufferedReader(new InputStreamReader(System.in));
        ftp = new FTPClient();


        System.out.println("FTP URL is:" + ftp.getDefaultPort());


        //  ftp.setFileType(FTP.BINARY_FILE_TYPE);
        //  ftp.enterLocalPassiveMode();
    }

    public void connect(String username, String password) throws Exception {
        String host = FTPClientProperty.getProperty("host");
        int port = Integer.parseInt(FTPClientProperty.getProperty("port"));
        ftp.connect(host, port);
        ftp.login(username, password);

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
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

        FTPFile[] files  = recieveDirList();

        for (FTPFile file : files) {
           if (dir.equals(file.getName())){
               ftp.changeWorkingDirectory(dir);
           }
        }
    }



    private FTPFile[] recieveDirList() throws IOException {

        FTPFile[] files = ftp.listFiles();
        return files;

    }

    public boolean listFTPFiles(String directory, String fileName) throws IOException {
        // lists files and directories in the current working directory
        boolean verificationFilename = false;
        FTPFile[] files = ftp.listFiles(directory);
        for (FTPFile file : files) {
            String details = file.getName();
            System.out.println(details);
            if (details.equals(fileName)) {
                System.out.println("Correct Filename");
                verificationFilename = details.equals(fileName);
                //   assertTrue("Verification Failed: The filename is not updated at the CDN end.",details.equals(fileName));
            }
        }

        return verificationFilename;
    }

    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public static void main(String[] args) {


        try {
            FTPClientFunc ftpobj = new FTPClientFunc();
            // ftpobj.uploadFTPFile("D:\\ftpservertest\\test1\\testfile.txt", "testfile.txt", "/");
            //tpobj.downloadFTPFile("testfile.txt", "/ftpservertest/dsadsd/testfile.txt");
            /// System.out.println("FTP File downloaded successfully");

            FTPFile[] currentFiles = ftpobj.recieveDirList();
            // System.out.println(ftpobj.printWorkDir());
            ftpobj.showDirList();
            //ftpobj.changeDir("test1");


            // System.out.println(ftpobj.printWorkDir());

            ftpobj.showDirList();


            //ftpobj.showDirList(ftpobj.recieveDirList(""));
            // System.out.println(result);
            ftpobj.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
