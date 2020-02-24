import dto.GraphService;

public class Main {

    public static void main(String[] args) {

        int[][] parentChildPairs = new int[][] {
                {10, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 17},
                {4, 5}, {4, 8}, {8, 9},{6,33},{6,22},{17,22},{17,1}
        };


        GraphService workWithGraphs = new GraphService(parentChildPairs);
        //Task 1
        System.out.println(workWithGraphs.sortByNumberOfParents());
        //Task 2
        System.out.println(workWithGraphs.fromOneParent(33,1));
    }
}
