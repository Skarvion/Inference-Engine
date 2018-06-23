package Operator;

public class IfAndOnlyIfOperator extends Operator {
    public IfAndOnlyIfOperator() {
        operatorSymbol = "<=>";
        level = 1;
    }

    @Override
    public boolean booleanResult(boolean left, boolean right) {
        return (left && right) || (!left && !right);
    }
}
