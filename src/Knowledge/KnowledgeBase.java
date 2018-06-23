package Knowledge;

import Knowledge.Sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Static class to hold the knowledge base of sentences
 */
public class KnowledgeBase {
    // List of sentences
    private static List<Sentence> senteceList;

    // Initialize sentence
    static {
        senteceList = new ArrayList<>();
    }

    /**
     * Add array of String to sentence list
     * @param sentenceArray array of sentence string
     */
    public static void addSentenceArray(String[] sentenceArray) {
        for (String s : sentenceArray) {
            addSentence(new Sentence(s));
        }
    }

    /**
     * Add sentence to knowledge base
     * @param sentence sentence
     */
    public static void addSentence(Sentence sentence) { senteceList.add(sentence); }

    /**
     * Get list of sentences for knowledge base
     * @return list of sentences
     */
    public static List<Sentence> getSentenceList() { return senteceList; }

    /**
     * Get clause list that is not fact
     * @return list of clauses
     */
    public static List<Sentence> getClauseList() {
        List<Sentence> result = new ArrayList<>();
        for (Sentence s : senteceList) {
            if (!s.isFact()) result.add(s);
        }

        return result;
    }

    /**
     * Clear sentence list
     */
    public static void clearSentenceList() { senteceList.clear(); }

    /**
     * Get all symbols of all sentences
     * @return List of unique symbols from sentence lsit
     */
    public static List<String> getAllSymbols() {
        List<String> result = new ArrayList<>();

        for (Sentence s : senteceList) {
            List<String> sentenceSymbol = s.getSymbols();
            for (String st : sentenceSymbol) {
                if (!result.contains(st)) result.add(st);
            }
        }

        return result;
    }

    /**
     * Assess all sentences whether they satisfies the propositional logic given the map of string and boolean
     * @param symbols map of variables of strings and booleans
     * @return true if all sentences satisfies PL
     */
    public static boolean knowledgeBasePLTrue(Map<String, Boolean> symbols) {
        for (Sentence s : senteceList) {
            if (!s.propositionalLogic(symbols)) return false;
        }

        return true;
    }

    /**
     * If knowledge base contains a sentence
     * @param sentence sentence being checked
     * @return true if an identical sentence is found
     */
    public static boolean containSentence(Sentence sentence) {
        return senteceList.contains(sentence);
    }
}
