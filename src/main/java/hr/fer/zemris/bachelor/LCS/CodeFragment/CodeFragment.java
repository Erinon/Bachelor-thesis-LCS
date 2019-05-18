package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.*;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class CodeFragment {

    private static CodeFragment dontCareFragment;
    private Node rootNode;

    public CodeFragment(Node rootNode) {
        if (rootNode == null) {
            throw new NullPointerException("Root node must not be null.");
        }

        this.rootNode = rootNode;
    }

    public CodeFragment deepCopy() {
        return new CodeFragment(rootNode.deepCopy());
    }

    private static Node getRandomSubtree(int depth, int inputSize, CodeFragment[] reusedFragments, int rfLen) {
        if (depth <= 0) {
            if (RandomNumberGenerator.nextDouble() < 0.5 && rfLen > 0) {
                return reusedFragments[RandomNumberGenerator.nextInt(0, rfLen - 1)].rootNode;
            } else {
                return new TerminalNode(RandomNumberGenerator.nextInt(0, inputSize - 1));
            }
        }

        Node node = null;

        switch (RandomNumberGenerator.nextInt(0, 2)) {
            case 0:
                switch (RandomNumberGenerator.nextInt(0, 3)) {
                    case 0:
                        node = new AndNode(
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen),
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen)
                        );
                        break;
                    case 1:
                        node = new NandNode(
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen),
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen)
                        );
                        break;
                    case 2:
                        node = new NorNode(
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen),
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen)
                        );
                        break;
                    case 3:
                        node = new OrNode(
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen),
                                getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen)
                        );
                        break;
                    default:
                        break;
                }
                break;
            case 1:
                node = new NotNode(getRandomSubtree(depth - 1, inputSize, reusedFragments, rfLen));
                break;
            case 2:
                if (RandomNumberGenerator.nextDouble() < 0.5 && rfLen > 0) {
                    node = reusedFragments[RandomNumberGenerator.nextInt(0, rfLen - 1)].rootNode;
                } else {
                    node = new TerminalNode(RandomNumberGenerator.nextInt(0, inputSize - 1));
                }
                break;
            default:
                break;
        }

        return node;
    }

    public static CodeFragment getRandomFragment(boolean[] input, CodeFragment[] reusedFragments, int rfLen) {
        return new CodeFragment(getRandomSubtree(Constants.MAXIMUM_TREE_DEPTH, input.length, reusedFragments, rfLen));
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
        if (this == dontCareFragment) {
            return true;
        }

        return equals(dontCareFragment);
    }

    public boolean evaluate(boolean[] input) {
        return rootNode.getValue(input);
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }

    public Node getRootNode() {
        return rootNode;
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

        //return rootNode.equals(that.rootNode);
        return toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
