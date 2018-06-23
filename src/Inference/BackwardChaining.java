package Inference;

import Knowledge.KnowledgeBase;
import Knowledge.Sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An implementation of Inference.InferenceMethod class where it uses Backward Chaining algorithm to infer logic
 */
public class BackwardChaining extends InferenceMethod {

    // An array list of entailed symbol
    private ArrayList<String> entailedSymbol;

    /**
     * Override method for entails which uses the Backward Chaining inference logic
     * @param query sentence query accepted
     * @return boolean whether entails or not
     */
    @Override
    public boolean entails(Sentence query) {
        // Reset entailed symbol
        entailedSymbol = new ArrayList<>();
        // Map of string symbols that have been assessed or inferred
        HashMap<String, Boolean> inferred = new HashMap<>();
        // Array of agenda of to be assessed sentence
        ArrayList<Sentence> agenda = new ArrayList<>();

        // Put all possible symbol in the inferred map with false value
        for (String symbol : KnowledgeBase.getAllSymbols()) {
            inferred.put(symbol, false);
        }

        // Add query to the initial agenda
        agenda.add(query);

        // While agenda is not empty, select the first sentence as selectedSentence
        while (!agenda.isEmpty()) {
            Sentence selected = agenda.remove(agenda.size() - 1);

            // If a symbol is already inferred, skip, else set the inferred map for that symbol to be true
            if (inferred.get(selected.getSentence())) continue;
            else inferred.replace(selected.getSentence(), true);

            // Add the selected premise to entailed symbol
            entailedSymbol.add(selected.getPremise());

            // If selected sentence contained in the knowledge base clauses, return true
            if (KnowledgeBase.containSentence(selected)) return true;

            // If the sentence is in the knowledge base and is not a fact
            if (!(KnowledgeBase.containSentence(selected) && selected.isFact())) {

                // Get all symbol in the selected sentence
                List<String> symbols = selected.getSymbols();

                // For each clause in knowledge base that contains implication of the selected premise, add all other symbol in the premise of the clause to symbol
                for (Sentence clause : KnowledgeBase.getClauseList()) {
                    if (clause.getConclusionSentence().getSymbols().contains(selected.getPremise())) {
                        List<String> clauseSymbol = clause.getPremiseSentence().getSymbols();
                        for (String s : clauseSymbol) symbols.add(s);
                    }
                }

                // If there is no symbol found, then return false
                if (symbols.size() == 0) return false;
                else {
                    // If symbol is just 1 and it matches with the query, then return false as it not entailed
                    if (symbols.size() == 1 && symbols.get(0).equalsIgnoreCase(query.getSentence())) return false;

                    // For all gathered symbol, if it's not yet inferred, add it to the agenda list
                    for (String s : symbols) {
                        if (!inferred.get(s)) {
                            agenda.add(new Sentence(s));
                        }
                    }
                }
            }
        }

        // Return false
        return false;
    }

    /**
     * Override method to print additional entail
     * @return list of entailed symbol
     */
    @Override
    public String additionalEntail() {
        String result = "";
        for (int i = entailedSymbol.size() - 1; i >= 0; i--) {
            result += entailedSymbol.get(i);
            if (i > 0) result += ", ";
        }

        return result;
    }
}
