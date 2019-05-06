package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.*;

import java.util.Random;

public class CodeFragment {

    private static CodeFragment dontCareFragment;
    private static final Random r = new Random();
    private AbstractNode rootNode;

    public CodeFragment(AbstractNode rootNode) {
        if (rootNode == null) {
            throw new NullPointerException("Root node must not be null.");
        }

        this.rootNode = rootNode;
    }

    private static AbstractNode getRandomSubtree(int depth, int inputSize) {
        if (depth <= 0) {
            return new TerminalNode((r.nextInt() % inputSize + inputSize) % inputSize);
        }

        AbstractNode node = null;

        switch ((r.nextInt() % 3 + 3) % 3) {
            case 0:
                switch ((r.nextInt() % 4 + 4) % 4) {
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
                node = new TerminalNode((r.nextInt() % inputSize + inputSize) % inputSize);
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
//System.out.println(node);
    public boolean evaluate(boolean[] input) {
        return rootNode.getValue(input);
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }

}
