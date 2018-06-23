package Inference.Converter;

import Data.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * CNF Converter static class that is used to convert clauses into CNF. At the moment, it can only successfully convert
 * Horn-form clauses to CNF, it has problem converting other form
 */
public abstract class CNFConverter {
    protected String operatorSymbol;
    protected int level;

    private List<String> partSentence;

    public CNFConverter() {
        partSentence = new ArrayList<>();
    }

    public static List<String> process(String input) {
        List<String> result = new ArrayList<>();

        TreeNode rootNode = new TreeNode(input);

        LinkedList<TreeNode> frontier = new LinkedList<>();
        LinkedList<TreeNode> secondFrontier = new LinkedList<>();
        frontier.addFirst(rootNode);
        secondFrontier.addFirst(rootNode);

        TreeNode selectedNode;
        while ((selectedNode = frontier.pollFirst()) != null) {
            String[] split;
            if (selectedNode.getSentence().contains("<=>")) {
                selectedNode.setOperator("<=>");

                split = selectedNode.getSentence().split("<=>", 2);

                for (String s : split) {
                    TreeNode childNode = new TreeNode(s);
                    selectedNode.addChild(childNode);
                    frontier.add(childNode);
                    secondFrontier.add(childNode);
                }
            }

            else if (selectedNode.getSentence().contains("=>")) {
                selectedNode.setOperator("=>");

                split = selectedNode.getSentence().split("=>", 2);

                for (int i = 0; i < split.length; i++) {
                    if (i == 1) split[i] = "~" + split[i];
                    TreeNode childNode = new TreeNode(split[i]);
                    selectedNode.addChild(childNode);
                    frontier.add(childNode);
                    secondFrontier.add(childNode);
                }
            }

            else if (selectedNode.getSentence().contains("&")) {
                selectedNode.setOperator("&");

                split = selectedNode.getSentence().split("&");

                for (String s : split) {
                    TreeNode childNode = new TreeNode(s);
                    selectedNode.addChild(childNode);
                    frontier.add(childNode);
                    secondFrontier.add(childNode);
                }
            }

            else if (selectedNode.getSentence().contains("\\/")) {
                selectedNode.setOperator("\\/");

                split = selectedNode.getSentence().split("\\/");

                for (String s : split) {
                    TreeNode childNode = new TreeNode(s);
                    selectedNode.addChild(childNode);
                    frontier.add(childNode);
                    secondFrontier.add(childNode);
                }
            }

        }

        solveImplication(secondFrontier);
        String transformation = treeTraversal(rootNode);
        result.add(transformation);

        return result;
    }

    private static void solveImplication(LinkedList<TreeNode> frontier) {
        boolean left = true;
        TreeNode leftNode = null;

        TreeNode selectedNode;
        while ((selectedNode = frontier.pollFirst()) != null) {
            if (selectedNode.getOperator() == null) continue;

            if (selectedNode.getOperator().equalsIgnoreCase("=>")) {
                selectedNode.setOperator("\\/");

                for (TreeNode child : selectedNode.getChildrenList()) {
                    if (child.getOperator() != null) {
                        if (child.getOperator().equalsIgnoreCase("&")) {
                            child.setOperator("\\/");

                            for (TreeNode smallChild : child.getChildrenList()) {
                                if (smallChild.getSentence().contains("~")) {
                                    smallChild.setSentence(smallChild.getSentence().replace("~", ""));
                                } else {
                                    smallChild.setSentence("~" + smallChild.getSentence());
                                }
                            }
                        } else if (child.getOperator().equalsIgnoreCase("\\/")) {
                            child.setOperator("&");

                            for (TreeNode smallChild : child.getChildrenList()) {
                                if (smallChild.getSentence().contains("~")) {
                                    smallChild.setSentence(smallChild.getSentence().replace("~", ""));
                                } else {
                                    smallChild.setSentence("~" + smallChild.getSentence());
                                }
                            }
                        } else if (child.getOperator().equalsIgnoreCase("=>")) {
                            boolean childNode = true;
                            child.setOperator("&");

                            for (TreeNode smallChild : child.getChildrenList()) {
                                if (childNode) {
                                    if (smallChild.getSentence().contains("~")) {
                                        smallChild.setSentence(smallChild.getSentence().replace("~", ""));
                                    } else {
                                        smallChild.setSentence("~" + smallChild.getSentence());
                                    }

                                    childNode = false;
                                }
                            }
                        }
                    }
                }
            }

            if (selectedNode.getOperator().equalsIgnoreCase("&")) {
                selectedNode.setOperator("\\/");

                for (TreeNode smallChild : selectedNode.getChildrenList()) {
                    if (smallChild.getSentence().contains("~")) {
                        smallChild.setSentence(smallChild.getSentence().replace("~", ""));
                    } else {
                        smallChild.setSentence("~" + smallChild.getSentence());
                    }
                }
            }
        }
    }

    private static String treeTraversal(TreeNode node) {
        if (node.getChildrenList().size() > 0) {

            String result = "";
            for (int i = 0; i < node.getChildrenList().size(); i++) {
                result += treeTraversal(node.getChildrenList().get(i));
                if (i < node.getChildrenList().size() - 1) result += node.getOperator();
            }

            return result;
        } else {
            return node.getSentence();
        }
    }

    private static List<CNFConverter> converterList = new ArrayList<>();

    static {
        converterList.add(new IfThenConverter());
    }

    public static CNFConverter containOperator(String clause) {
        for (CNFConverter conv : converterList) {
            if (clause.contains(conv.operatorSymbol)) {
                return conv;
            }
        }
        return null;
    }

    public abstract String[] normalize(String clause);

    public String getOperatorSymbol() {
        return operatorSymbol;
    }

    public void setOperatorSymbol(String operatorSymbol) {
        this.operatorSymbol = operatorSymbol;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
