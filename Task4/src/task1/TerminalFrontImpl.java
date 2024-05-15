package task1;

public class TerminalFrontImpl  implements TerminalFront {
    @Override
    public void ShowMessage(Object o) {
        System.out.println(o.toString());
    }
}
