package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.AbstractNode;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.NotNode;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.OrNode;
import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.TerminalNode;

public class CodeFragment {

    private static CodeFragment dontCareFragment;
    private AbstractNode rootNode;

    public CodeFragment(AbstractNode rootNode) {
        if (rootNode == null) {
            throw new NullPointerException("Root node must not be null.");
        }

        this.rootNode = rootNode;
    }

    public static CodeFragment getRandomFragment(boolean[] input) {
        //TODO
        return null;
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

    public boolean evaluate(boolean[] input) {
        return rootNode.getValue(input);
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }

}
