package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public abstract class BinaryNode extends Node {

    Node left;
    Node right;

    abstract String getOperator();

    @Override
    public String toString() {
        return "(" + left.toString() + right.toString() + getOperator() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        BinaryNode that = (BinaryNode) obj;

        return getOperator().equals(that.getOperator()) && left.equals(that.left) && right.equals(that.right);
    }

}
