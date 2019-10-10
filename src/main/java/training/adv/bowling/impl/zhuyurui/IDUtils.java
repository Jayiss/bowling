package training.adv.bowling.impl.zhuyurui;

public class IDUtils {

    private static int r = 0;

    public static int createID() {

        Long x=System.currentTimeMillis() + r++;
        int id=(x).intValue();
        return id;

    }


}
