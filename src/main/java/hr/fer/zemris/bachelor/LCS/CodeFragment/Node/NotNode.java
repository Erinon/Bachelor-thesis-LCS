package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class NotNode extends UnaryNode {

    public NotNode(Node child) {
        this.child = child;
    }

    public boolean getValue(boolean[] input) {
        return !child.getValue(input);
    }

    String getOperator() {
        return "~";
    }

}
