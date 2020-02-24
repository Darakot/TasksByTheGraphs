package dto;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Creating a class we use int[][] as a parameter. Based on it we get hashMap
 * sortByNumberOfParents sorts children based on number of parents.
 * As a return we get the String in form of:
 * “zeroParents:” + number of entities with 0 parents;
 * “oneParent:” + number of entities with one parent;
 * “tooParents:” + number of entities with two parents.
 * fromOneParent method checks if two entities are related with the same parent. This method works based on two other methods:
 * 1) comParentSearch that checks two entities if they share same parent, if not returns “false”
 * 2) cycleComParentSearch looks for the shared parent for two entities, by checking one branch and then another, if this search fails Method gets upper on the graph.
 * And it does so until either it finds shared parent or gets to the end of the graph.
 */

@Log4j
public class GraphService {
    private Map<Integer, Node> mapParentChildPairs = new HashMap<>();

    private Set<Integer> zeroParents = new HashSet<>();
    private Set<Integer> twoParents = new HashSet<>();
    private Set<Integer> oneParent = new HashSet<>();

    public GraphService(int[][] parentChildPairs) {
        String sLog = "";
        int[] numArr = new int[2];
        for (int j = 0; j < parentChildPairs.length; j++) {

            for (int i = 0; i < parentChildPairs[j].length; i++) {
                if(i==0)sLog += "{" + parentChildPairs[j][i] + " ";
                if(i==1)sLog += parentChildPairs[j][i] + "}";
                numArr[i] = parentChildPairs[j][i];
            }

            Node node = new Node();
            node.setIndex(0, numArr[0]);

            if (mapParentChildPairs.containsKey(numArr[1])) {
                Node nodeCopy = mapParentChildPairs.get(numArr[1]);
                nodeCopy.setIndex(1, numArr[0]);
                mapParentChildPairs.put(numArr[1], nodeCopy);
            } else mapParentChildPairs.put(numArr[1], node);
        }
        log.info("inArr: " + sLog);
        log.info("outMaps: " + mapParentChildPairs.toString());
    }

    public String sortByNumberOfParents() {
        String result = "";

        log.info("Method: sortByNumberOfParents" + "\n" +
                "Maps: " + mapParentChildPairs.toString());

        for (Integer key : mapParentChildPairs.keySet()) {

            if (mapParentChildPairs.get(key).getIndex(0) != null && mapParentChildPairs.get(key).getIndex(1) != null) {
                twoParents.add(key);
            }

            if (mapParentChildPairs.get(key).getIndex(0) != null && mapParentChildPairs.get(key).getIndex(1) == null) {
                oneParent.add(key);
            }
            if (mapParentChildPairs.get(key).getIndex(1) != null && mapParentChildPairs.get(key).getIndex(0) == null) {
                oneParent.add(key);
            }

            if (!mapParentChildPairs.containsKey(mapParentChildPairs.get(key).getIndex(0)) &&
                    mapParentChildPairs.get(key).getIndex(0) != null) {
                zeroParents.add(mapParentChildPairs.get(key).getIndex(0));
            }

            if (!mapParentChildPairs.containsKey(mapParentChildPairs.get(key).getIndex(1)) &&
                    mapParentChildPairs.get(key).getIndex(1) != null) {
                zeroParents.add(mapParentChildPairs.get(key).getIndex(1));
            }

        }
        result = "zeroParents: " + zeroParents + "\n" +
                "oneParent: " + oneParent + "\n" +
                "tooParents: " + twoParents;

        log.info(result);

        return result;

    }

    public boolean fromOneParent(int child1, int child2) {
        log.info("Method: fromOneParent " + "child1: " + child1 + " " + "child2: " + child2);
        if(cycleComParentSearch(child1,child2,child1) || (cycleComParentSearch(child2,child1,child2))) {
            log.info("result true");
            return true;
        }
        else {
            log.info("result false");
            return false;
        }
    }

    private boolean cycleComParentSearch(int key1, int key2, int oldKey){
        boolean bBranch = false;
        while (true) {
            if (bBranch == false) {
                if (сomParentSearch(key1, key2)) return true;
                else {
                    try {
                        oldKey = key1;
                        key1 = mapParentChildPairs.get(key1).getIndex(0);
                    }catch (NullPointerException e){
                        break;
                    }
                    bBranch = true;
                }
            } else {
                if (сomParentSearch(key1, key2)) return true;
                else {
                    try {
                        key1 = mapParentChildPairs.get(oldKey).getIndex(1);
                    } catch (NullPointerException e) {
                        break;
                    }
                    bBranch = false;
                }
            }
        }
        return false;
    }

    private boolean сomParentSearch(int key1, int key2) {

        if (!mapParentChildPairs.containsKey(key1) || !mapParentChildPairs.containsKey(key2)) return false;

        if ((mapParentChildPairs.get(key1).getIndex(0) == mapParentChildPairs.get(key2).getIndex(0) ||
                mapParentChildPairs.get(key1).getIndex(1) == mapParentChildPairs.get(key2).getIndex(0)) ||
                (mapParentChildPairs.get(key1).getIndex(0) == mapParentChildPairs.get(key2).getIndex(1) ||
                        mapParentChildPairs.get(key1).getIndex(1) == mapParentChildPairs.get(key2).getIndex(1))) {
            return true;
        }
        return false;
    }

}
