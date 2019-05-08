package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class OrNode extends BinaryNode {

    public OrNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public boolean getValue(boolean[] input) {
        return left.getValue(input) || right.getValue(input);
    }

    String getOperator() {
        return "|";
    }

}
