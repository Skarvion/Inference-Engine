package Inference;

import Knowledge.Sentence;

/**
 * Inference.InferenceMethod is the abstract class that defines the basic calling functions to infer logic
 */
public abstract class InferenceMethod {

    /**
     * Execute the inference processing, call entail method and additionalEntail method based on query sentence
     * @param query query sentence
     * @return resulting string whether it is entailed or not, if it is, add additional detail
     */
    public String execute(Sentence query) {
        boolean entails = entails(query);
        String result = entails ? "YES" : "NO";
        String additionalEntailment = additionalEntail();

        return result + (entails ? " " + additionalEntailment : "");
    }

    /**
     * Method that returns additional detail for entailing
     * @return additional entailment details
     */
    public String additionalEntail() { return ""; }

    /**
     * Abstract method to process from both the Knowledge Base static class and the string query
     * @param query sentence query accepted
     * @return boolean whether it is entailed or not
     */
    public abstract boolean entails(Sentence query);
}
