package Operator;

public class AndOperator extends Operator {
    public AndOperator() {
        operatorSymbol = "&";
        level = 2;
    }

    @Override
    public boolean booleanResult(boolean left, boolean right) {
        return left && right;
    }
}
