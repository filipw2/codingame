import java.util.*;



//Bender - Episode 2 100%

public class Dijkstra {

    public void dijkstra(){
        int N=15;
        String[] input = {"0 17 1 2",
        "1 15 3 4",
        "2 15 4 5",
        "3 20 6 7",
        "4 12 7 8",
        "5 11 8 9",
        "6 18 10 11",
        "7 19 11 12",
        "8 12 12 13",
        "9 11 13 14",
        "10 13 E E",
        "11 14 E E",
        "12 17 E E",
        "13 19 E E",
        "14 15 E E"};
        long startTime = System.nanoTime();
        Graph graph=new Graph();
        for(int i=0;i<N;i++){
            graph.addNode(new Node(i));
        }
        for (int i = 0; i < N; i++) {
            String[] room = input[i].split(" ");
            Node node=graph.nodes.get(Integer.valueOf(room[0]));
            node.money=Integer.valueOf(room[1]);
            if(!room[2].equals("E"))
                node.addDestination(graph.nodes.get(Integer.valueOf(room[2])));
            if(!room[3].equals("E"))
                node.addDestination(graph.nodes.get(Integer.valueOf(room[3])));
        }

        calculateMoney(graph,graph.nodes.get(0));
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println("duration: "+duration);
    }


    static class Graph{
        public List<Node> nodes = new ArrayList<>();

        public void addNode(Node node){
            nodes.add(node);
        }
    }

    static class Node{
        int money=0;
        int dist=0;
        int number;

        public void setDist(int dist) {
            this.dist = dist;
        }

        public int getDist() {
            return dist;
        }

        Set<Node> adjacentNodes = new HashSet<>();

        public void addDestination(Node destination){
            adjacentNodes.add(destination);
        }

        public Node(int number){
            this.number=number;
        }


        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public Set<Node> getAdjacentNodes() {
            return adjacentNodes;
        }

        public void setAdjacentNodes(Set<Node> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
        }
    }

    public static void calculateMoney(Graph graph, Node source){
        Graph graph2=calculatePathFromSource(graph,source);

        OptionalInt money=graph2.nodes.stream().mapToInt(n->{
            return n.getDist();

        }).max();
        System.out.println(money.getAsInt());
    }
    public static Graph calculatePathFromSource(Graph graph, Node source) {
        source.setDist(source.money);
        List<Node> settledNodes = new ArrayList<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getHighestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Node adjacencyPair:
                    currentNode.getAdjacentNodes()) {

                    Integer sourceDistance = currentNode.getDist();
                    if (sourceDistance + adjacencyPair.getMoney() > adjacencyPair.getDist()) {
                        adjacencyPair.setDist(sourceDistance + adjacencyPair.getMoney());
                        unsettledNodes.add(adjacencyPair);
                    }

            }
            settledNodes.add(currentNode);
        }
        return graph;
    }
    private static Node getHighestDistanceNode(Set < Node > unsettledNodes) {
        Node highestDistanceNode = null;
        int highestDistance = 0;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDist();
            if (nodeDistance > highestDistance) {
                highestDistance = nodeDistance;
                highestDistanceNode = node;
            }
        }
        return highestDistanceNode;
    }
}
