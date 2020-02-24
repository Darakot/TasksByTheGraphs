package dto;

import org.junit.Assert;
import org.junit.Test;

public class GraphServiceTest {
    int[][] parentChildPairs = new int[][] {
            {10, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 17},
            {4, 5}, {4, 8}, {8, 9},{6,33},{6,22},{17,22},{17,1}
    };

    GraphService workWithGraphs = new GraphService(parentChildPairs);
    @Test
    public void sortByNumberOfParents() {
        String result = "zeroParents: [2, 4, 10]\n" +
                "oneParent: [17, 33, 1, 5, 8, 9]\n" +
                "tooParents: [3, 6, 22]";

        Assert.assertEquals(result,workWithGraphs.sortByNumberOfParents());
    }

    @Test
    public void fromOneParent() {
        Assert.assertEquals(true,workWithGraphs.fromOneParent(9,22));
        Assert.assertEquals(true,workWithGraphs.fromOneParent(33,1));
        Assert.assertEquals(true,workWithGraphs.fromOneParent(9,1));
        Assert.assertEquals(false,workWithGraphs.fromOneParent(3,8));
        Assert.assertEquals(true,workWithGraphs.fromOneParent(3,22));
        Assert.assertEquals(true,workWithGraphs.fromOneParent(9,17));
        Assert.assertEquals(true,workWithGraphs.fromOneParent(2,4));
    }
}