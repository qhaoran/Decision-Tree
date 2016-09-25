package DecisionTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhaoran on 16/9/12.
 */
public class Entropy {
    public Map<String, ArrayList<String>> attributesName = new HashMap();

    public Entropy(Map<String, ArrayList<String>> attriName) {
        attributesName = attriName;
    }

    /*
    entropy()
    @param Record
    @return the entropy of "Enjoy"
    */
    public double entropy(String[][] Record) {

        double p1, p2;
        double numYes = 0.0;
        double numNo = 0.0;
        for (int i = 1; i < Record.length; i++) {
            switch (Record[i][8]) {
                case "Yes":
                    numYes++;
                    break;
                case "No":
                    numNo++;
                    break;
                default:
                    break;
            }
        }
        p1 = numYes / (Record.length - 1);
        p2 = numNo / (Record.length - 1);
        double enjoyEntropy;
        if (p1 == 0.0 || p1 == 1.0) {
            enjoyEntropy = 0.0;
        } else {
            enjoyEntropy = (p1) * Math.log(1 / p1) + (p2) * Math.log(1 / p2);
        }
        return enjoyEntropy;
    }

    /*
    getAttrItem(String key, int calculated)
    @param key the name of current attribute e.g "Size","Location"...
    @param calculated current possible choice of the attribute that has been calculated
    @return next choice that should be calculated
     */
    public String getAttrItem(String key, int calculated) {
        ArrayList<String> l = new ArrayList<>();
        l = attributesName.get(key);
        if (calculated > l.size()) {
            return "Done";
        } else {
            return l.get(calculated);
        }
    }

    /*
    getAtrrNum()
    @param Record
    @param currentName current attribute name
    @return the postive and negetive num of "Enjoy" for certain attribute.
     */

    public Map<String, int[]> getAtrrNum(String[][] Record, int currentName) {
        Map<String, int[]> choicesCount = new HashMap<>();
        int itemsControll = 0;
        String itemValue = "null";
        String attribute = Record[0][currentName];
        int num = 0, posNum = 0, negNum = 0;
        // 遍历Record当中的training instances的记录,计算出当前attribute下所有可能取值的个数
        for (int m = 0; m < attributesName.get(attribute).size(); m++) {
            int[] i = new int[3];       //i[0]储存当前attribute下当前取值的个数, i[1]储存当前取值的下enjoy为pos的个数,
            // i[2]储存当前取值的下enjoy为neg的个数
            itemValue = getAttrItem(attribute, itemsControll);

            for (int n = 1; n < Record.length; n++) {
                if (Record[n][currentName].equals(itemValue)) {
                    num++;
                    switch (Record[n][8]) {
                        case "Yes":
                            posNum++;
                            break;
                        case "No":
                            negNum++;
                            break;
                        default:
                            break;
                    }
                }
            }
            i[0] = num;
            i[1] = posNum;
            i[2] = negNum;
            num = posNum = negNum = 0;
            itemsControll++;
            choicesCount.put(itemValue, i);
        }
        return choicesCount;
    }

    /*
    infoGain()
    @param Record
    @param currentNamePos the position of current attribute name in the array
    @return information gain of the attribute
     */
    public double infoGain(String[][] Record, int currentNamePos) {
        double avgEntropy = 0.0;
        double gain;
        String attribute = Record[0][currentNamePos];
        double infoNum = (double) (Record.length - 1);
        int attrCountSize;
        Map<String, int[]> attrCount = getAtrrNum(Record, currentNamePos);
        attrCountSize = attrCount.size();
        double[] entropy = new double[attrCountSize];
        double[] itemP = new double[attrCountSize];

        for (int i = 0; i < attrCountSize; i++) {
            String attrItem = getAttrItem(attribute, i);
            int[] num = attrCount.get(attrItem);
            if (num[0] == 0) {
                entropy[i] = 0.0;
            } else {
                double posP = ((double) num[1] / (double) (num[1] + num[2]));
                double negP = ((double) num[2] / (double) (num[1] + num[2]));
                if (posP == 0.0 || posP == 1.0) {
                    entropy[i] = 0.0;
                } else {
                    entropy[i] = posP * Math.log(1.0 / posP) + negP * Math.log(1.0 / negP);
                }
            }
            itemP[i] = num[0] / infoNum;
        }
        for (int i = 0; i < attrCountSize; i++) {
            avgEntropy = itemP[i] * entropy[i] + avgEntropy;
        }
        double entropyValue = entropy(Record);
        gain = entropyValue - avgEntropy;
        return gain;
    }

    /*
    maxInfoGain()
    @param Record
    @return the attribute with maximum information gain in this record
     */

    public int maxInfoGain(String[][] Record) {
        double maxInfoGain = 0.0;
        String attrWmax = new String();
        int maxAttrPos = 0;
        double eachGain;
        for (int m = 1; m < (Record[0].length - 1); m++) {
            eachGain = infoGain(Record, m);
            if (eachGain > maxInfoGain) {
                maxInfoGain = eachGain;
                attrWmax = Record[0][m];
                maxAttrPos = m;
            }
        }
        if (maxAttrPos == 0) { // 说明此时的熵增为零,已经分到叶子节点,record中只有一个元素
            if (Record.length == 1) { // 说明当前record中的记录为空,没有该节点
                return 100;
            } else {
                switch (Record[1][8]) {
                    case "Yes":
                        return 9; //Yes用9来标记
                    case "No":
                        return 10;// No用10来标记
                    default:
                        break;
                }
            }
        }
        return maxAttrPos;
    }
}

