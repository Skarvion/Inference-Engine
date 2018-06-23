package Inference.Converter;

/**
 * Unused child class of CNF Converter
 */
public class IfThenConverter extends CNFConverter {
    public IfThenConverter() {
        super();
        operatorSymbol = "=>";
        level = 1;
    }

    @Override
    public String[] normalize(String clause) {
        String[] result = new String[3];
        String[] split = clause.split(operatorSymbol, 2);
        result[0] = "~(" + split[0] + ")";
        result[1] = "(" + split[1] + ")";
        result[2] = "\\/";

        return result;
    }
}