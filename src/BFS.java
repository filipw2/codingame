import java.util.*;
public class BFS {


    public void bfs(Node startNode){
        Queue<Node> open_set=new LinkedList<>();
        Set<Node> closed_set = new HashSet<>();

        open_set.add(startNode);

        while(!open_set.isEmpty()){
            Node currentNode = open_set.element();


        }

    }


    class Node{
        int c;
        int l;
        Node parent;

        public Node(Node node){
            parent=node;
        }

        public List<Node> nodes = new ArrayList();

    }
}
