package DecisionTree;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by qhaoran on 16/9/12.
 */
public class Main {

    public static void main(String[] args) {
        // write all the record from the training set into a 2D array,
        // and all the attributes name with its possible choices in a map, respectively.
        ReadFile originalRecord = new ReadFile();
        Map<String, ArrayList<String>> attributesName;
        attributesName = originalRecord.getAttributesName();
        String[][] record = originalRecord.getRecord();

        // get root node first:
        Entropy dtEntropy = new Entropy(attributesName);
        int rootName = dtEntropy.maxInfoGain(record);
        Tree DecisionTree = new Tree(attributesName, rootName, record);

        // Create Decision Tree:
        while (DecisionTree.parentQueue.size() != 0) {
            Node parentNode = DecisionTree.parentQueue.poll();
            String[][] CurrentRecord = DecisionTree.newRecordforCurrentNode.poll();
            DecisionTree.addAllChildren(CurrentRecord, parentNode);
        }

        // Print tree structure and make prediction:
        DecisionTree.drawDT();
        String[] sequence = {"No.", "Large", "Moderate", "Cheap", "Loud", "City-Center", "No", "No", " "};
        DecisionTree.makePrediction(sequence);
    }


}

