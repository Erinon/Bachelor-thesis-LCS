package hr.fer.zemris.bachelor;

import hr.fer.zemris.bachelor.Trainer.AbstractTrainer;
import hr.fer.zemris.bachelor.Trainer.MuxTrainer;
import hr.fer.zemris.bachelor.Trainer.ParityTrainer;

public class Application {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int condBits = Integer.parseInt(args[0]);

        //condBits = 2;

        AbstractTrainer trainer = new ParityTrainer(condBits);

        trainer.train();
    }

}
