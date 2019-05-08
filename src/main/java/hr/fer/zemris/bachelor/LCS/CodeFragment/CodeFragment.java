package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.*;
import hr.fer.zemris.bachelor.NumberGenerator.NumberGenerator;

public class CodeFragment {

    private static CodeFragment dontCareFragment;
    private Node rootNode;

    public CodeFragment(Node rootNode) {
        if (rootNode == null) {
            throw new NullPointerException("Root node must not be null.");
        }

        this.rootNode = rootNode;
    }

    private static Node getRandomSubtree(int depth, int inputSize) {
        if (depth <= 0) {
            return new TerminalNode(NumberGenerator.nextInt(0, inputSize - 1));
        }

        Node node = null;

        switch (NumberGenerator.nextInt(0, 2)) {
            case 0:
                switch (NumberGenerator.nextInt(0, 3)) {
                    case 0:
                        node = new AndNode(
                                getRandomSubtree(depth - 1, inputSize),
                                getRandomSubtree(depth - 1, inputSize)
                        );
                        break;
                    case 1:
                        node = new NandNode(
                                getRandomSubtree(depth - 1, inputSize),
                                getRandomSubtree(depth - 1, inputSize)
                        );
                        break;
                    case 2:
                        node = new NorNode(
                                getRandomSubtree(depth - 1, inputSize),
                                getRandomSubtree(depth - 1, inputSize)
                        );
                        break;
                    case 3:
                        node = new OrNode(
                                getRandomSubtree(depth - 1, inputSize),
                                getRandomSubtree(depth - 1, inputSize)
                        );
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                node = new NotNode(getRandomSubtree(depth - 1, inputSize));
                break;
            case 2:
                node = new TerminalNode(NumberGenerator.nextInt(0, inputSize - 1));
                break;
            default:
                break;
        }

        return node;
    }

    public static CodeFragment getRandomFragment(boolean[] input) {
        return new CodeFragment(getRandomSubtree(Constants.MAXIMUM_TREE_DEPTH, input.length));
    }

    public static CodeFragment getDontCareFragment() {
        if (dontCareFragment == null) {
            TerminalNode term = new TerminalNode(0);

            dontCareFragment = new CodeFragment(
                    new OrNode(term, new NotNode(term))
            );
        }

        return dontCareFragment;
    }

    public boolean isDontCareFragment() {
        return this == dontCareFragment;
    }

    public boolean evaluate(boolean[] input) {
        return rootNode.getValue(input);
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CodeFragment that = (CodeFragment) obj;

        return rootNode.equals(that.rootNode);
    }

}
