package Operator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An abstract operator class used to infer logic based on boolean. It has multiple child classes that helps to infer this
 */
public abstract class Operator {
    protected String operatorSymbol;
    protected int level;

    // List of child operators
    private static List<Operator> operatorList;

    static {
        operatorList = new ArrayList<>();

        operatorList.add(new AndOperator());
        operatorList.add(new OrOperator());
        operatorList.add(new IfThenOperator());
        operatorList.add(new IfAndOnlyIfOperator());

        operatorList.sort(new OperatorComparator());
    }

    /**
     * Used to sort operators based on their level of significance and ordering
     */
    private static class OperatorComparator implements Comparator<Operator> {

        @Override
        public int compare(Operator o1, Operator o2) {
            return o1.getLevel() - o2.getLevel();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    /**
     * Get all child operators
     * @return operators
     */
    public static List<Operator> getOperatorList() { return operatorList; }

    /**
     * Check if string contains operator
     * @param sentence input string
     * @return operator contained, else null
     */
    public static Operator containOperator(String sentence) {
        for (Operator o : operatorList) {
            if (sentence.contains(o.getOperatorSymbol())) {
                return o;
            }
        }
        return null;
    }

    /**
     * Get operator based from symbol
     * @param operatorSymbol string symbol
     * @return operator
     */
    public static Operator getOperator(String operatorSymbol) {
        for (Operator o : operatorList) {
            if (operatorSymbol.equalsIgnoreCase(o.getOperatorSymbol()))
                return o;
        }
        return null;
    }

    /**
     * Method for child operator to return symbol
     * @return operator symbol string
     */
    public String getOperatorSymbol() { return operatorSymbol; }

    /**
     * Return level of ordering
     * @return order level
     */
    public int getLevel() { return level; }

    /**
     * Abstract method to assess the boolean logic
     * @param left left boolean
     * @param right right boolean
     * @return boolean logic based on child operator
     */
    public abstract boolean booleanResult(boolean left, boolean right);
}
