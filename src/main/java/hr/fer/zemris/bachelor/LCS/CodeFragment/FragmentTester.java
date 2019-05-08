package hr.fer.zemris.bachelor.LCS.CodeFragment;

import hr.fer.zemris.bachelor.LCS.CodeFragment.Node.*;

public class FragmentTester {

    public static void main(String[] args) {
        Node t0 = new TerminalNode(0);
        Node t1 = new TerminalNode(1);
        Node t2 = new TerminalNode(2);
        Node t5 = new TerminalNode(5);

        Node o1 = new OrNode(t0, t1);

        Node a1 = new AndNode(t2, t5);

        Node o2 = new OrNode(o1, a1);

        boolean[] input = {false, false, false, true, false, true};

        System.out.println(o2);
        System.out.println(o2.getValue(input));

        Node nTerm = new TerminalNode(0);
        Node nNot = new NotNode(nTerm);
        Node nOr = new OrNode(nTerm, nNot);

        System.out.println(nOr);
        System.out.println(nOr.getValue(input));

        CodeFragment cf = CodeFragment.getDontCareFragment();

        System.out.println(cf.evaluate(input));
        System.out.println(cf.toString());

        for (int i = 0; i < 5; i++) {
            CodeFragment cf1 = CodeFragment.getRandomFragment(input);
            System.out.println(cf1 + " -> " + cf1.evaluate(input));
        }
    }

}
