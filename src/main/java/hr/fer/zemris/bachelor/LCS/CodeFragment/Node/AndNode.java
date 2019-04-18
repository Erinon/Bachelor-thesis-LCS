package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class AndNode extends BinaryNode {

    public AndNode(AbstractNode left, AbstractNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean getValue(boolean[] input) {
        return left.getValue(input) && right.getValue(input);
    }

    public String toString() {
        return left.toString() + right.toString() + '&';
    }

}
