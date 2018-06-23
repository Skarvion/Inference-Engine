package Inference;

import Knowledge.KnowledgeBase;
import Knowledge.Sentence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An implementation of Inference.InferenceMethod class where it uses Forward Chaining algorithm to infer logic
 */
public class ForwardChaining extends InferenceMethod {

    // An array list of entailed symbol
    private ArrayList<String> entailedSymbol;

    /**
     * Override method for entails which uses the Forward Chaining inference logic
     * @param query sentence query accepted
     * @return boolean whether entails or not
     */
    @Override
    public boolean entails(Sentence query) {
        // Reset entailed symbol
        entailedSymbol = new ArrayList<>();
        // Map of string symbols that have been assessed or inferred and count the number of symbol in each sentence
        HashMap<Sentence, Integer> count = new HashMap<>();
        HashMap<String, Boolean> inferred = new HashMap<>();
        // Array of agenda of to be assessed sentence
        ArrayList<Sentence> agenda = new ArrayList<>();

        // Put all possible symbol in the inferred map with false value
        for (String symbol : KnowledgeBase.getAllSymbols()) {
            inferred.put(symbol, false);
        }

        // Loop through sentences in knowledge base, if it's a fact, add it to agenda, otherwise count the number of
        // symbol and store it to the map
        for (Sentence s : KnowledgeBase.getSentenceList()) {
            if (s.isFact()) agenda.add(s);
            else {
                count.put(s, s.getPremiseSentence().getSymbols().size());
            }
        }

        // While agenda is not empty, select the first sentence as selectedSentence
        while (!agenda.isEmpty()) {
            Sentence selected = agenda.remove(0);

            // Add the selected premise to entailed symbol
            entailedSymbol.add(selected.getPremise());

            // If the selected sentence is equal to query, return true
            if (selected.equals(query)) return true;

            // Process if the symbol has not yet been inferred
            if (inferred.get(selected.getSentence()) == false) {
                inferred.replace(selected.getSentence(), true);

                // Loop through clause in knowledge base, if the clause is not a fact and contains the symbol, then
                // decrement count of that sentence. If a sentence's count is 0, then add the conclusion of the sentence
                // to agenda
                for (Sentence clause : KnowledgeBase.getSentenceList()) {
                    if (!clause.isFact() && clause.getPremise().contains(selected.getPremise())) {
                        count.replace(clause, count.get(clause) - 1);
                        if (count.get(clause) == 0) agenda.add(new Sentence(clause.getConclusion()));
                    }
                }
            }
        }

        // Return false when entailment is not found
        return false;
    }

    /**
     * Override method to print additional entail
     * @return list of entailed symbol
     */
    @Override
    public String additionalEntail() {
        String result = "";
        for (int i = 0; i < entailedSymbol.size(); i++) {
            result += entailedSymbol.get(i);
            if (i < entailedSymbol.size() - 1) result += ", ";
        }

        return result;
    }
}
