package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.*;

public class FragmentTester {

    public static void main(String[] args) {
        AbstractNode t0 = new TerminalNode(0);
        AbstractNode t1 = new TerminalNode(1);
        AbstractNode t2 = new TerminalNode(2);
        AbstractNode t5 = new TerminalNode(5);

        AbstractNode o1 = new OrNode(t0, t1);

        AbstractNode a1 = new AndNode(t2, t5);

        AbstractNode o2 = new OrNode(o1, a1);

        boolean[] input = {false, false, false, true, false, true};

        System.out.println(o2);
        System.out.println(o2.getValue(input));

        AbstractNode nTerm = new TerminalNode(0);
        AbstractNode nNot = new NotNode(nTerm);
        AbstractNode nOr = new OrNode(nTerm, nNot);

        System.out.println(nOr);
        System.out.println(nOr.getValue(input));

        CodeFragment cf = CodeFragment.getDontCareFragment();

        System.out.println(cf.evaluate(input));
        System.out.println(cf.toString());

        for (int i = 0; i < 5; i++) {
            System.out.println(CodeFragment.getRandomFragment(input));
        }
    }

}
