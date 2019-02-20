public class SaveDataBeforeClosing extends Thread {
    @Override
    public void run(){
        ConsoleAplication.writeFile();
    }
}
