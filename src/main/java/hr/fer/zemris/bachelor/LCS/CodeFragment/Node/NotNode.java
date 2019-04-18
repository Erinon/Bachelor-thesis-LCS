package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class NotNode extends UnaryNode {

    public NotNode(AbstractNode child) {
        this.child = child;
    }

    public boolean getValue(boolean[] input) {
        return !child.getValue(input);
    }

    public String toString() {
        return child.toString() + '~';
    }

}
