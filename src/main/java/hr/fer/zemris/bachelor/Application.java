package hr.fer.zemris.bachelor;

import hr.fer.zemris.bachelor.Trainer.MuxTrainer;
import hr.fer.zemris.bachelor.Trainer.Trainer;

public class Application {

    public static void main(String[] args) {
        Trainer trainer = new MuxTrainer(4);

        trainer.train();
    }

}
