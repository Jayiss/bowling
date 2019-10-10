package training.adv.bowling.impl.zhuyurui;

public class IDUtils {

    private static int r = 1001;

    public static int createID() {

//        Long x=(System.currentTimeMillis() + r++)%Integer.MAX_VALUE ;
//        int id=x.intValue();
        return r++;

    }


}
