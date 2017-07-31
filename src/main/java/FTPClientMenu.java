import java.io.IOException;

public class FTPClientMenu {


    private FTPClientFunc ftpFuncs;

    private static final String SEPARATOR = "==================================";
    private static final String MENU = "MENU:";
    private String username;
    private  String password;

    FTPClientMenu() throws Exception {

        ftpFuncs = new FTPClientFunc();
    }

    public FTPClientMenu displayLoginMenu() throws Exception {
        System.out.println(MENU);
        System.out.println("Please enter your username:");
         username = ftpFuncs.br.readLine();
        System.out.println("Enter your password");
         password = ftpFuncs.br.readLine();
         ftpFuncs.connect(username, password);
        ftpFuncs.showDirList();
        ftpFuncs.showCurrentDir();
        ftpFuncs.goToFolder("test1");
        ftpFuncs.showDirList();
        ftpFuncs.showCurrentDir();
        ftpFuncs.goToParentDirectory();
        ftpFuncs.showDirList();
        ftpFuncs.showCurrentDir();
        return this;
    }

    public FTPClientMenu displayStartMenu() throws IOException {
        System.out.println(MENU);
        System.out.println(SEPARATOR);
        System.out.println(username);
        //System.out.println("current dir");?
        System.out.println("Choose comand:");
        System.out.println("1.Go to folder");
        System.out.println("2.Go to parent directory");
        System.out.println("3.Show folder content");
        System.out.println("4.download file");
        System.out.println("5.exit");
        String command  = ftpFuncs.br.readLine();

        return this;
    }


    private enum  Commands {
        GO_TO_FOLDER,GO_TO_PARENT_DIR,SHOW_FOLDER_CONTENT;
    }

    private void chooseCommand() throws IOException {
        //Commands command =  //ftpFuncs.br.readLine();

    }
    public static void main(String[] args) {
        try {
            while (true) {
                FTPClientMenu menu = new FTPClientMenu();
                menu.displayLoginMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
