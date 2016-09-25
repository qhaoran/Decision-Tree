package DecisionTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhaoran on 16/9/15.
 */
public class ReadFile {
    public static String[][] record = new String[22][9];
    Map<String, ArrayList<String>> attributesName = new HashMap();
    String[] size = {"Large", "Medium", "Small"};
    String[] occupied = {"High", "Moderate", "Low"};
    String[] price = {"Expensive", "Normal", "Cheap"};
    String[] music = {"Loud", "Quiet"};
    String[] location = {"Talpiot", "City-Center", "German-Colony", "Ein-Karem", "Mahane-Yehuda"};
    String[] VIP = {"Yes", "No"};
    String[] favoriteBeer = {"Yes", "No"};
    String[] enjoyed = {"Yes", "No"};

    public  String[][] getRecord() {
        // The name of the file to open.
        String fileName = "dt-data.txt";
        // This will reference one line at a time
        String line = null;
        int j = 0;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split(", ");
                for (int i = 0; i <= 8; i++) {
                    record[j][i] = array[i];
                }
                j++;
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
        return record;
    }

    public Map<String, ArrayList<String>> getAttributesName(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < size.length; i++) {
            temp.add(i, size[i]);
        }
        attributesName.put("Size", temp);
        ArrayList<String> temp1 = new ArrayList<>();
        for (int i = 0; i < occupied.length; i++) {
            temp1.add(i, occupied[i]);
        }
        attributesName.put("Occupied", temp1);
        ArrayList<String> temp2 = new ArrayList<>();
        for (int i = 0; i < price.length; i++) {
            temp2.add(i, price[i]);
        }
        attributesName.put("Price", temp2);
        ArrayList<String> temp3 = new ArrayList<>();
        for (int i = 0; i < music.length; i++) {
            temp3.add(i, music[i]);
        }
        attributesName.put("Music", temp3);
        ArrayList<String> temp4 = new ArrayList<>();
        for (int i = 0; i < location.length; i++) {
            temp4.add(i, location[i]);
        }
        attributesName.put("Location", temp4);
        ArrayList<String> temp5 = new ArrayList<>();
        for (int i = 0; i < VIP.length; i++) {
            temp5.add(i, VIP[i]);
        }
        attributesName.put("VIP", temp5);
        ArrayList<String> temp6 = new ArrayList<>();
        for (int i = 0; i < favoriteBeer.length; i++) {
            temp6.add(i, favoriteBeer[i]);
        }
        attributesName.put("Favorite Beer", temp6);
        ArrayList<String> temp7 = new ArrayList<>();
        for (int i = 0; i < enjoyed.length; i++) {
            temp7.add(i, enjoyed[i]);
        }
        attributesName.put("Enjoy", temp7);
        return attributesName;
    }
}
