package Operator;

public class OrOperator extends Operator {
    public OrOperator() {
        operatorSymbol = "\\/";
        level = 3;
    }

    @Override
    public boolean booleanResult(boolean left, boolean right) {
        return left || right;
    }
}