package hr.fer.zemris.bachelor.LCS.CodeFragment;

public class TerminalNode extends AbstractNode {

    private int index;

    public TerminalNode(int index) {
        this.index = index;
    }

    public boolean getValue(boolean[] input) {
        return input[index];
    }

    public String toString() {
        return 'D' + Integer.toString(index);
    }

}
