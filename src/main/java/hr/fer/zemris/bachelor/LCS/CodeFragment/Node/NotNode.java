package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class NotNode extends UnaryNode {

    public NotNode(Node child) {
        this.child = child;
    }

    public boolean getValue(boolean[] input) {
        return !child.getValue(input);
    }

    public Node deepCopy() {
        return new NotNode(child.deepCopy());
    }

    String getOperator() {
        return "~";
    }

}
