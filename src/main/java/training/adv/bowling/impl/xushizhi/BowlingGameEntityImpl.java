package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private Integer id;  // DB Game PK
    private Integer finalScore;

    private List<Integer> scores;  // Scores of each turn
    private List<BowlingTurn> bowlingTurns;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public List<BowlingTurn> getTurns() {
        return bowlingTurns;
    }

    public void setTurns(List<BowlingTurn> bowlingTurns) {
        this.bowlingTurns = bowlingTurns;
    }

    public Integer getTotalScore() {
        return finalScore;
    }

    public void setTotalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    @Override
    public Integer getMaxTurn() {
        BowlingRule bowlingRule = new BowlingRuleImpl();
        return bowlingRule.getMaxTurn();
    }

    @Override
    public Integer getMaxPin() {
        BowlingRule bowlingRule = new BowlingRuleImpl();
        return bowlingRule.getMaxPin();
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        BowlingTurnEntity[] turnEntities = turns;
        this.bowlingTurns = new ArrayList<>(turnEntities.length);
        // Add completed bowling turns to turns' entity
        for (int i = 0; i < turnEntities.length; i++) {
            this.bowlingTurns.add(new BowlingTurnImpl(turnEntities[i].getFirstPin(), turnEntities[i].getSecondPin()));
        }
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return (bowlingTurns.toArray(new BowlingTurnEntity[0]));
    }
}
