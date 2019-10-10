package training.adv.bowling.impl.caoyu;

public class UidUtil {
    private static Integer gameId = 1000, turnId = 0;

    synchronized public static Integer getNewGameId() {
        return gameId++;
    }

    synchronized public static Integer getNewTurnId() {
        return turnId++;
    }
}
