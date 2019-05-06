package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public class NorNode extends BinaryNode {

    public NorNode(AbstractNode left, AbstractNode right) {
        this.left = left;
        this.right = right;
    }

    public boolean getValue(boolean[] input) {
        return !(left.getValue(input) || right.getValue(input));
    }

    String getOperator() {
        return "r";
    }

}
