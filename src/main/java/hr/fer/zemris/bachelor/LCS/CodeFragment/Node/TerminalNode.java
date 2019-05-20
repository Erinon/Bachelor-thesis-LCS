package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

import java.util.Objects;

public class TerminalNode extends Node {

    private int index;

    public TerminalNode(int index) {
        this.index = index;
    }

    public boolean getValue(boolean[] input) {
        return input[index];
    }

    public String toString() {
        return 'D' + Integer.toString(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TerminalNode that = (TerminalNode) obj;

        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }

    public Node deepCopy() {
        return new TerminalNode(index);
    }

}
