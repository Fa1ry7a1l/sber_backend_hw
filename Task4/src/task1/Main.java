package task1;

public class Main {
    public static void main(String[] args) {
        TerminalFrontImpl terminalFront = new TerminalFrontImpl();
        PinValidator pinValidator = new PinValidator();
        ServerImpl server = new ServerImpl(15_300);
        TerminalImpl terminal = new TerminalImpl(terminalFront,pinValidator,server);
        terminal.doWork();
    }
}