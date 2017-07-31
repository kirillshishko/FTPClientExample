import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FTPClientMenu {


    private FTPClientFunctions ftpFunc;
    private BufferedReader br;

    private static final String SEPARATOR = "==================================";
    private static final String MENU = "MENU:";
    private String username;
    private String password;


    FTPClientMenu() {
        br = new BufferedReader(new InputStreamReader(System.in));
        ftpFunc = new FTPClientFunctions();
    }

    public FTPClientMenu displayLoginMenu() throws IOException {
        menuHeader();
        System.out.println("Please enter your username:");
        username = br.readLine();
        System.out.println("Enter your password");
        password = br.readLine();
        ftpFunc.connect(username, password);

        return this;
    }

    public FTPClientMenu displayStartMenu() throws IOException {
        menuHeader();
        System.out.println(username);
        System.out.println("current dir");
        ftpFunc.showCurrentDir();
        System.out.println("Choose comand:");
        System.out.println("1.Go to folder");
        System.out.println("2.Go to parent directory");
        System.out.println("3.Show folder content");
        System.out.println("4.download file");
        System.out.println("5.exit");

        return this;
    }

    public void displayGoToFolderMenu() throws IOException {
        menuHeader();
        System.out.println("Which folder to choose");
        ftpFunc.showDirList();
        String dir = br.readLine();
        ftpFunc.goToFolder(dir);

    }

    public void displayDownloadFileMenu() throws IOException{
        menuHeader();
        System.out.println("Enter filename");
        String filename = br.readLine();
        ftpFunc.downloadFile(filename,"/ftpservertest" + ftpFunc.recieveCurrentDirFilePath() + filename);
        System.out.println("FTP File downloaded successfully");

    }



    private void doCommand(String command) throws IOException {


        switch (command) {
            case "1":
                displayGoToFolderMenu();
                break;
            case "2":
                ftpFunc.goToParentDirectory();
                break;
            case  "3":
                ftpFunc.showDirList();
                break;
            case "4":

               displayDownloadFileMenu();
                break;
            case "5":
                ftpFunc.disconnect();
        }

    }

    private static void menuHeader() {
        System.out.println(MENU);
        System.out.println(SEPARATOR);
    }

    public static void main(String[] args) {
        FTPClientMenu menu = new FTPClientMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            menu.displayLoginMenu();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (true) {

                menu.displayStartMenu();
                String command = br.readLine();
                menu.doCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
