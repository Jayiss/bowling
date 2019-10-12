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
        return new BowlingRuleImpl().getMaxTurn();
    }

    @Override
    public Integer getMaxPin() {
        BowlingRule bowlingRule = new BowlingRuleImpl();
        return bowlingRule.getMaxPin();
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntity[bowlingTurns.size()];

        int i = 0;
        for (BowlingTurn bowlingTurn : bowlingTurns) {
            turnEntities[i++] = bowlingTurn.getEntity();
        }
        return turnEntities;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.bowlingTurns = new ArrayList<>(turns.length);
        // Add completed bowling turns to turns' entity array
        for (BowlingTurnEntity turn : turns) {
            BowlingTurn tempTurn = new BowlingTurnImpl(null, null);
            BowlingTurnEntity tempTurnEntity = tempTurn.getEntity();

            tempTurnEntity.setId(turn.getId());
            tempTurnEntity.setFirstPin(turn.getFirstPin());
            tempTurnEntity.setSecondPin(turn.getSecondPin());

            this.bowlingTurns.add(tempTurn);
        }
    }
}
