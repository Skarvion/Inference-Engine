package Knowledge;

import Data.Tree;
import Data.TreeNode;
import Operator.Operator;

import java.util.*;

// Reference: http://snipplr.com/view/56296/ai-forward-chaining-implementation-for-propositional-logic-horn-form-knowledge-bases/
// Reference: https://stackoverflow.com/questions/6091772/forward-chaining-and-backward-chaining-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
public class Sentence {
    // Raw sentence string
    private String rawSentence;

    /**
     * Constructor, accepting string sentence, the whitespace in the sentence will be removed
     * @param sentence rawSentence, remove the whitespace
     */
    public Sentence(String sentence) {
        this.rawSentence = sentence.replaceAll("\\s+", "");
    }

    /**
     * If sentence does not contain implication, return true
     * @return sentence is a fact
     */
    public boolean isFact() {
        return !rawSentence.contains("=>");
    }

    /**
     * Get the premise of sentence
     * @return premise
     */
    public String getPremise() {
        return isFact() ? rawSentence : rawSentence.split("=>|<=>")[0];
    }

    /**
     * Get the premise in sentence object
     * @return premise sentence
     */
    public Sentence getPremiseSentence() {
        return new Sentence(getPremise());
    }

    /**
     * Get the conclusion of sentence in sentence object
     * @return conclusion sentence
     */
    public Sentence getConclusionSentence() {
        return new Sentence(getConclusion());
    }

    /**
     * Get the sentence in sentence
     * @return conclusion
     */
    public String getConclusion() {
        return isFact() ? rawSentence : rawSentence.split("=>|<=>")[1];
    }

    /**
     * Get all unique symbol in the sentence
     * @return list of symbol in sentence
     */
    public List<String> getSymbols() {
        ArrayList<String> result = new ArrayList<>();

        String[] symbolArray = rawSentence.split("=>|&|\\/|<=>");

        for (String s : symbolArray) {
            if (!result.contains(s)) result.add(s);
        }

        return result;
    }

    /**
     * Get list of conjunctions separated by & symbol
     * @return
     */
    public List<String> getConjunctions() {
        String premise = getPremise();
        List<String> result = Arrays.asList(premise.split("&"));
        return result;
    }

    /**
     * See if conjunction list contain a string
     * @param find string to find / symbol
     * @return if contained, then true
     */
    public boolean conjunctionContains(String find) {
        for (String s : getConjunctions()) {
            if (s.equals(find)) return true;
        }
        return false;
    }

    /**
     * Assess the propositional logic of the sentence for a given variable through splitting it into a binary tree
     * @param symbols a map of symbol string along with its boolean value
     * @return true if PL is satisfied given the symbols
     * @throws IllegalStateException if the tree given is not a binary tree in the process
     */
    public boolean propositionalLogic(Map<String, Boolean> symbols) throws IllegalStateException {
        // Initialize a tree with root node contain raw sentence
        Tree sentenceTree = new Tree(new TreeNode(rawSentence));

        // Frontier list, then add root node
        LinkedList<TreeNode> frontier = new LinkedList<>();
        frontier.add(sentenceTree.getRootNode());

        // List to be assessed later
        LinkedList<TreeNode> assessLater = new LinkedList<>();
        assessLater.add(sentenceTree.getRootNode());

        // Poll through the frontier list, assigning it to the selectednode
        TreeNode selectedNode;
        while ((selectedNode = frontier.pollFirst()) != null) {

            // Get operator of selected node
            Operator operator = Operator.containOperator(selectedNode.getSentence());
            if (operator != null) {
                // Split the string based on operator into 2, then put the content into new child list
                String[] split = selectedNode.getSentence().split(operator.getOperatorSymbol(), 2);

                selectedNode.setOperator(operator.getOperatorSymbol());
                for (String s : split) {
                    TreeNode discoveredNode = new TreeNode(s);
                    selectedNode.addChild(discoveredNode);
                    // Add to frontier and assess later list
                    frontier.add(discoveredNode);

                    assessLater.addFirst(discoveredNode);
                }
            }

            // If the node only contain a literal, get the boolean value based on symbol
            else {
                Boolean result = symbols.get(selectedNode.getSentence());
                if (result == null) result = false;

                if (selectedNode.getSentence().contains("~")) result = !result;
                selectedNode.setValue(result);
            }
        }

        // Loop through the assess later list, set the parent node value based on children node value and parent's operator
        for (TreeNode tn : assessLater) {
            if (tn.getChildrenList().size() == 0) continue;
            else {
                if (tn.getChildrenList().size() != 2) throw new IllegalStateException();

                Boolean left = tn.getChildrenList().get(0).getValue();
                if (left == null) left = false;
                Boolean right = tn.getChildrenList().get(1).getValue();
                if (right == null) right = false;

                tn.setValue(Operator.getOperator(tn.getOperator()).booleanResult(left, right));
            }
        }

        // Get the value of root node
        return sentenceTree.getRootNode().getValue();
    }

    /**
     * Get sentence
     * @return sentence
     */
    public String getSentence() { return rawSentence; }

    /**
     * Override method to check if this equal with another sentence
     * @param obj sentence object to be assessed
     * @return true if raw sentence is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sentence) return getSentence().equals(((Sentence) obj).getSentence());
        else return false;
    }
}
