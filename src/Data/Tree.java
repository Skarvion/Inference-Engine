package Data;

/**
 * Data.Tree data structure used to hold tree nodes, useful for assessing propositional logic
 */
public class Tree {
    // Data.Tree node as a root of the tree
    private TreeNode rootNode;

    /**
     * Constructor
     * @param node root node
     */
    public Tree(TreeNode node) {
        this.rootNode = node;
    }

    /**
     * Retun root node
     * @return Data.TreeNode root node
     */
    public TreeNode getRootNode() {
        return rootNode;
    }

    /**
     * Set root node
     * @param rootNode Data.TreeNode root node
     */
    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }
}
