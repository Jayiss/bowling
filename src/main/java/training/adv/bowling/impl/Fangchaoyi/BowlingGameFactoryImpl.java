package training.adv.bowling.impl.Fangchaoyi;

import training.adv.bowling.api.*;

import java.io.*;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    private File file = new File("..\\gameId.txt");

    @Override
    public BowlingGame getGame() {
        int gameId = 0;
        if(file.exists()) gameId = readId(file);
        gameId++;
        writeId(file, gameId);
        BowlingRule rule = new BowlingRuleImpl();
        return new BowlingGameImpl(rule, gameId);
    }

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

    public void writeId(File file, int id){
        try{
            if(!file.exists()) file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(id));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
