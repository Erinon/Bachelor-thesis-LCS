package hr.fer.zemris.bachelor;

import hr.fer.zemris.bachelor.Trainer.*;

public class Application {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        int num = Integer.parseInt(args[1]);

        AbstractTrainer trainer;

        switch (args[0]) {
            case "mux":
                trainer = new MuxTrainer(num);
                break;
            case "parity":
                trainer = new ParityTrainer(num);
                break;
            case "majority":
                trainer = new MajorityTrainer(num);
                break;
            case "carry":
                trainer = new CarryTrainer(num);
                break;
            default:
                throw new IllegalArgumentException();
        }

        trainer.train();
    }

}
