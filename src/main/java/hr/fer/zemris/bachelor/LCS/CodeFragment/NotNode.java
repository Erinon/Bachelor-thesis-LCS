package hr.fer.zemris.bachelor.LCS.CodeFragment;

public class NotNode extends AbstractNode {

    private AbstractNode child;

    public NotNode(AbstractNode child) {
        this.child = child;
    }

    public boolean getValue(boolean[] input) {
        return !child.getValue(input);
    }

}
