import Data.Parser;
import Inference.BackwardChaining;
import Inference.Converter.CNFConverter;
import Inference.ForwardChaining;
import Inference.ResolutionBased;
import Inference.TruthTable;
import Knowledge.KnowledgeBase;
import Knowledge.Query;
import Knowledge.Sentence;

import java.util.List;

/**
 * Main method to run program
 */
public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Wrong number of arguments, please input the following argument: [ineference method] [filename]");
            System.out.println("List of inference method: ");
            System.out.println("-Truth Table:       TT");
            System.out.println("-Forward Chaining:  FC");
            System.out.println("-Backward Chaining: BC");

        }
        else {
            ReadFile(args[1]);

            switch (args[0].toUpperCase()) {
                case "TT":
                    System.out.println(new TruthTable().execute(new Sentence(Query.getQueryList().get(0))));
                    break;
                case "FC":
                    System.out.println(new ForwardChaining().execute(new Sentence(Query.getQueryList().get(0))));
                    break;
                case "BC":
                    System.out.println(new BackwardChaining().execute(new Sentence(Query.getQueryList().get(0))));
                    break;
                case "RB":
                    System.out.println(new ResolutionBased().execute(new Sentence(Query.getQueryList().get(0))));
                    break;
                case "CNF":
                    for (Sentence s : KnowledgeBase.getSentenceList()) {
                        List<String> process = CNFConverter.process(s.getSentence());
                        System.out.println(process.get(process.size() - 1));
                    }
                    break;
                default:
                    System.out.println("No method is known, please review the readme documentation");
                    System.out.println("List of inference method: ");
                    System.out.println("-Truth Table:       TT");
                    System.out.println("-Forward Chaining:  FC");
                    System.out.println("-Backward Chaining: BC");
                    break;
            }
        }
    }

    private static void ReadFile(String filename) {
        if (!Parser.readTextFile(filename)) {
            System.exit(1);
        }
    }
}
