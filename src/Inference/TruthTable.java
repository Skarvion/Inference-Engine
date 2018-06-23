package Inference;

import Knowledge.KnowledgeBase;
import Knowledge.Sentence;

import java.util.*;

/**
 * An implementation of Inference.InferenceMethod class where it uses Truth Table algorithm to infer logic
 */
public class TruthTable extends InferenceMethod {

    // The number of sentence that is true from the knowledge base and query sentence
    private int queryTrue;

    // Inference.TruthTable constructor that sets the queryTrue number
    public TruthTable() {
        queryTrue = 0;
    }

    /**
     * Reset queryTrue number
     */
    public void reset() {
        queryTrue = 0;
    }

    /**
     * Override method for entails which uses the Truth Table inference logic
     * @param query sentence query accepted
     * @return boolean whether entails or not
     */
    @Override
    public boolean entails(Sentence query) {
        // List of symbol in the sentences in knowledge base
        List<String> symbols = KnowledgeBase.getAllSymbols();

        // Loop for all possible boolean combinations for all symbol by calculating 2^(number of symbol)
        long iteration = (long) Math.pow(2, symbols.size());
        for (long i = 0; i < iteration; i++) {
            // Generate a binary string based on iteration number
            String binaryString = Long.toBinaryString(i);

            // Initialize a map of symbol string with boolean values and generate the boolean values based on binary string
            Map<String, Boolean> variables = new HashMap<>();
            for (int j = binaryString.length() - 1; j >= 0; j--) {
                if (binaryString.charAt(j) == '0') variables.put(symbols.get(j), false);
                else variables.put(symbols.get(j), true);
            }

            // If knowledge base is true based on a certain model, check if the sentence query is true with model
            // If it is, then add to queryTrue, else return false
            if (KnowledgeBase.knowledgeBasePLTrue(variables)) {
                if (query.propositionalLogic(variables)) {
                    queryTrue++;
                }
                else {
                    return false;
                }
            }
        }

        // If queryTrue is more than 1 (because if all symbol is true, which is always counts), then return true, else return false
        if (queryTrue > 0) return true;
        else return false;
    }


    /**
     * Override method to print additional entail
     * @return queryTrue in String
     */
    @Override
    public String additionalEntail() {
        return Integer.toString(queryTrue);
    }
}
