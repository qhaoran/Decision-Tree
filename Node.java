package DecisionTree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhaoran on 16/9/15.
 */
public class Node {
    public int nodeName;
    public int parentName;
    public Map<String, Node> childName = new HashMap<>();

    public void rootNode(int node) {
        nodeName = node;
        parentName = 100;
    }

    public void addChildNode(int nodename, int parentname) {
        nodeName = nodename;
        parentName = parentname;
    }
}
