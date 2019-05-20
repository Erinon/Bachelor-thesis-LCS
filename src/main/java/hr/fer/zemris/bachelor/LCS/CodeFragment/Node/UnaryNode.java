package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

import java.util.Objects;

public abstract class UnaryNode extends Node {

    Node child;

    abstract String getOperator();

    @Override
    public String toString() {
        return child.toString() + getOperator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        UnaryNode that = (UnaryNode) obj;

        return getOperator().equals(that.getOperator()) && child.equals(that.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child);
    }

}
