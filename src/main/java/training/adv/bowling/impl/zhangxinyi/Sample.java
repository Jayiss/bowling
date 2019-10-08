package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;

import java.util.*;

public class Sample {
    public static void main(String[] args) {
        List<Integer> li = new ArrayList<Integer>();
        li.add(3);
        li.add(2);
        li.add(4);
        Integer[] lili = li.toArray(new Integer[0]);
        System.out.println(Arrays.toString(li.toArray(new Integer[0])));
        List<Integer> liList = Arrays.asList(lili);
        System.out.println(liList.toString());
        jjj(1);
        jjj(1,2,3);
        jjj();
    }

    public static void jjj(Integer... a) {
        System.out.println(a.length);
    }
}
