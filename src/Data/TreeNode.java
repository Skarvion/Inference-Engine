package Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data.TreeNode class used to make a tree for propositional logic assessment
 */
public class TreeNode {
    // A sentence string stored in the node
    private String sentence;

    // An operator string stored here
    private String operator;

    // Parent Data.TreeNode and children list Data.TreeNode
    private TreeNode parentNode;
    private List<TreeNode> childrenList;

    // Boolean value
    private Boolean value;

    /**
     * Constructor that accepts sentence and parent node
     * @param sentence string sentence
     * @param parentNode parent node
     */
    public TreeNode(String sentence, TreeNode parentNode) {
        this.sentence = sentence;
        this.operator = null;
        this.parentNode = parentNode;
        childrenList = new ArrayList<>();
        value = null;
    }

    /**
     * Constructor that accepts sentence
     * @param sentence string sentence
     */
    public TreeNode(String sentence) {
        this(sentence, null);
    }

    /**
     * Default constructor, set all values to null
     */
    public TreeNode() {
        this("NULL", null);
    }

    /**
     * Return string sentence
     * @return sentence
     */
    public String getSentence() {
        return sentence;
    }

    /**
     * Set string sentence
     * @param sentence sentence
     */
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     * Get the children list of Data.TreeNode
     * @return children list
     */
    public List<TreeNode> getChildrenList() {
        return childrenList;
    }

    /**
     * Add child Data.TreeNode to children list and set the child parent to this node
     * @param node child node
     */
    public void addChild(TreeNode node) {
        childrenList.add(node);
        node.setParentNode(this);
    }

    /**
     * Get operator string
     * @return operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Set operator string
     * @param operator operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Get parent Data.TreeNode
     * @return parent node
     */
    public TreeNode getParentNode() {
        return parentNode;
    }

    /**
     * Set parent Data.TreeNode
     * @param parentNode parent node
     */
    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Get the boolean value
     * @return boolean value
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * Set the boolean value
     * @param value boolean value
     */
    public void setValue(Boolean value) {
        this.value = value;
    }
}
