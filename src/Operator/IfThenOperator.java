package Operator;

public class IfThenOperator extends Operator {
    public IfThenOperator() {
        operatorSymbol = "=>";
        level = 1;
    }

    @Override
    public boolean booleanResult(boolean left, boolean right) {
        return !left || right;
    }
}
