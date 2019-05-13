package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class AndNode extends BinaryNode {

    public AndNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public boolean getValue(boolean[] input) {
        return left.getValue(input) && right.getValue(input);
    }

    public Node deepCopy() {
        return new AndNode(left.deepCopy(), right.deepCopy());
    }

    String getOperator() {
        return "&";
    }

}
