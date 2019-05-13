package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class NorNode extends BinaryNode {

    public NorNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public boolean getValue(boolean[] input) {
        return !(left.getValue(input) || right.getValue(input));
    }

    public Node deepCopy() {
        return new NorNode(left.deepCopy(), right.deepCopy());
    }

    String getOperator() {
        return "r";
    }

}
