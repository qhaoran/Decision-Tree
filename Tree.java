package DecisionTree;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by qhaoran on 16/9/14.
 */
public class Tree {
    String[] attributesName = {"No.", "Size", "Occupied", "Price", "Music", "Location", "VIP", "Favorite Beer", "Enjoy"};
    Map<String, ArrayList<String>> attriNameForEntropy = new HashMap<>();
    Queue<String[][]> newRecordforCurrentNode = new ConcurrentLinkedDeque<>();
    Node root;
    public Queue<String> childIdQueue;
    public Queue<Node> parentQueue;
    public List<Node> childNode = new ArrayList<>();


    public Tree(Map<String, ArrayList<String>> attriName, int rootName, String[][] originalRecord) {
        childIdQueue = new ConcurrentLinkedDeque<>();
        parentQueue = new ConcurrentLinkedDeque<>();
        attriNameForEntropy = attriName;
        setTreeRoot(rootName);
        parentQueue.add(root);
        newRecordforCurrentNode.add(originalRecord);
    }


    public String[][] splitRecord(String[][] currentRecord, String childID, int parentname) {
        Entropy getAttrValueNum = new Entropy(attriNameForEntropy);
        Map<String, int[]> attrValueNum = getAttrValueNum.getAtrrNum(currentRecord, parentname);
        int[] temp = attrValueNum.get(childID);
        String[][] recordSplited = new String[temp[0] + 1][9];
        for (int b = 0; b < 9; b++) {
            recordSplited[0][b] = currentRecord[0][b];
        }
        int num = 1;
        for (int a = 1; a < currentRecord.length; a++) {
            if (currentRecord[a][parentname].equals(childID)) {
                for (int b = 0; b < currentRecord[0].length; b++) {
                    recordSplited[num][b] = currentRecord[a][b];
                }
                num++;
            }
        }
        return recordSplited;
    }

    public void addAllChildren(String[][] currentRecord, Node parentNode) {
        Node newNode = new Node();
        for (int i = 0; i < attriNameForEntropy.get(attributesName[parentNode.nodeName]).size(); i++) {
            String childID = childIdQueue.poll();
            String[][] updatedRecord = splitRecord(currentRecord, childID, parentNode.nodeName);
            Entropy getMaxGain = new Entropy(attriNameForEntropy);
            int nodeName = getMaxGain.maxInfoGain(updatedRecord);
            if (nodeName == 100) {
//                return;
            } else {
                if (nodeName < 8) {
                    newNode = addNode(parentNode, nodeName, childID);
                    parentQueue.add(newNode);
                    childNode.add(newNode);
                    newRecordforCurrentNode.add(updatedRecord);
                    for (int j = 0; j < attriNameForEntropy.get(attributesName[newNode.nodeName]).size(); j++) {
                        childIdQueue.add(attriNameForEntropy.get(attributesName[newNode.nodeName]).get(j));
                    }
                } else {
                    newNode = addNode(parentNode, nodeName, childID);
                    childNode.add(newNode);
                }
            }
        }
        return;
    }

    private void setTreeRoot(int rootName) {
        root = new Node();
        root.rootNode(rootName);
        for (int i = 0; i < attriNameForEntropy.get(attributesName[rootName]).size(); i++) {
            childIdQueue.add(attriNameForEntropy.get(attributesName[rootName]).get(i));
        }
        return;
    }

    public Node addNode(Node parent, int nodename, String childID) {
        Node child = new Node();
        child.addChildNode(nodename, parent.nodeName);
        parent.childName.put(childID, child);
        return child;
    }

    public void drawDT() {
        System.out.print(attributesName[root.nodeName] + "\n");
        int lastLevelSum = 3;
        int nextLevelSum = 0;
        int printSum = 0;
        for (int i = 0; i < childNode.size(); i++) {
            nextLevelSum = nextLevelSum + childNode.get(i).childName.size();
            switch (childNode.get(i).nodeName) {
                case 9:
                    System.out.print("Yes, ");
                    printSum++;
                    break;
                case 10:
                    System.out.print("No, ");
                    printSum++;
                    break;
                default:
                    System.out.print(attributesName[childNode.get(i).nodeName] + ", ");
                    printSum++;
                    break;
            }
            if (printSum == lastLevelSum) {
                System.out.print("\n");
                lastLevelSum = nextLevelSum;
                printSum = 0;
            }
        }
    }


    public void makePrediction(String[] data) {
        System.out.print("\n" + "\n" + "The Prediction of given sequence is: ");
        Node currentNode = root;
        while (currentNode.childName.size() != 0) {
            String link = data[currentNode.nodeName];
            Node nextNode = currentNode.childName.get(link);
            currentNode = nextNode;
        }
        String prediction = null;
        switch (currentNode.nodeName) {
            case 9:
                prediction = "Yes";
                break;
            case 10:
                prediction = "No";
                break;
            default:
                break;
        }
        System.out.print(prediction);
    }
}

