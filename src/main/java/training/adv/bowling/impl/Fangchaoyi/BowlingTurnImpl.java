package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

import java.io.*;

public class BowlingTurnImpl implements BowlingTurn {
    private int firstPin, secondPin;
    private BowlingTurnEntity entity;

    public BowlingTurnImpl(int[] Pin){
        if(Pin[0] != 0) this.firstPin = Pin[0];
        if(Pin[1] != 0) this.secondPin = Pin[1];
        entity = new BowlingTurnEntityImpl(this);
    }
    public BowlingTurnImpl(int[] Pin, TurnKey key){
        if(Pin[0] != 0) this.firstPin = Pin[0];
        if(Pin[1] != 0) this.secondPin = Pin[1];
        entity = new BowlingTurnEntityImpl(this);
        entity.setId(key);
    }
    public BowlingTurnImpl(){}
    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return entity;
    }

//    public void writeId(File file){
//        try{
//            if(!file.exists()) file.createNewFile();
//            FileWriter writer = new FileWriter(file);
//            writer.write(String.valueOf(turnId));
//            writer.flush();
//            writer.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    public int readId(File file){
        String Id = "";
        try{
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            Id = br.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return Integer.parseInt(Id);
    }

}
