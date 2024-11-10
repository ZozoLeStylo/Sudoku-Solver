package Sudoku;

// Factory Pattern for Deduction Rules
public class DeductionRuleFactory {
    public static DeductionRule createRule(String ruleType) {
        switch (ruleType) {
            case "NakedSingle":
                return new NakedSingle();
            case "HiddenSingle":
                return new HiddenSingle();
            case "NakedPairs":
                return new NakedPairs();
            default:
                throw new IllegalArgumentException("Invalid rule type");
        }
    }
}