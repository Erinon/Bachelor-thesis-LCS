package hr.fer.zemris.bachelor.LCS.CodeFragment;

public class AndNode extends AbstractNode {

    private AbstractNode left;
    private AbstractNode right;

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
