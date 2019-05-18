package hr.fer.zemris.bachelor;

import hr.fer.zemris.bachelor.Trainer.MuxTrainer;
import hr.fer.zemris.bachelor.Trainer.Trainer;

public class Application {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException();
        }

        int condBits = Integer.parseInt(args[0]);

        Trainer trainer = new MuxTrainer(condBits);

        trainer.train();
    }

}
