package model;

import java.util.Arrays;

/**
 * Created by kasia on 11.02.2016.
 */
public class RuleSet {

    private boolean[][] rules;

    public RuleSet() {
        rules = new boolean[2][9];
        rules[0][3] = true;
        rules[1][2] = true;
        rules[1][3] = true;
    }

    public RuleSet(boolean[][] rules) {
        this.rules = rules;
    }

    public boolean nextState(boolean state, int neighbours)  {
            return rules[(state) ? 1 : 0][neighbours];

    }

    public boolean[][] getRules() {
        return rules;
    }

    public void setRule(boolean currentState, int neighbours, boolean nextState) {
        rules[(currentState) ? 1 : 0][neighbours] = nextState;
    }

    @Override
    public String toString() {
        return "RuleSet{" +
                "rules=" + Arrays.toString(rules[0]) + Arrays.toString(rules[1]) +
                '}';
    }
}
