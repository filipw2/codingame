import java.util.*;

class Surface {
    static int L;
    static int H;
    static char[][] board;
    static Map<Integer,List<Long>> lakes;
    public static void main(String args[]) {
        L = 6;
        H = 6;
        lakes= new HashMap();
        board = new char[H][L];
        String[] b= {
                "OO###O",
                "#O###O",
                "#OO##O",
                "####O#",
                "##OO##",
                "##OO##"
        };
        for (int i = 0; i < H; i++) {
            String row = b[i];
            board[i]=row.toCharArray();
        }
        int N = 1;
        //for (int i = 0; i < N; i++) {
            int X = 1;
            int Y = 0;
            //System.err.println(X+" "+Y);
            System.out.println(check(X,Y));
        //}

        // for (int i = 0; i < N; i++) {

        //     // Write an action using System.out.println()
        //     // To debug: System.err.println("Debug messages...");

        //     System.out.println("answer");
        // }
    }

    public static char pos(int x,int y){
        if(x<0||y<0||x>=L||y>=H)
            return '#';
        return board[y][x];
    }
    public static void set(int x,int y){
        board[y][x]='X';
    }

    static int check(int x,int y){
        int search=y*L+x;
        for(Integer key:lakes.keySet()){
            for(Long value:lakes.get(key)){
                if(value==search)
                    return key;
            }
        }


        if(pos(x,y)=='#')
            return 0;
        int size=0;
        Queue<Node> Q = new LinkedList();
        List<Long> visited=new ArrayList();
        Q.add(new Node(x,y));


        while(!Q.isEmpty()){
            size++;
            Node n=Q.remove();
            set(n.x,n.y);
            long l =n.y*L+n.x;
            visited.add(l);
            if(pos(n.x+1,n.y)=='O'){
                Q.add(new Node(n.x+1,n.y));
                set(n.x+1,n.y);
            }if(pos(n.x-1,n.y)=='O'){
                Q.add(new Node(n.x-1,n.y));
                set(n.x-1,n.y);
            }if(pos(n.x,n.y+1)=='O'){
                Q.add(new Node(n.x,n.y+1));
                set(n.x,n.y+1);
            }if(pos(n.x,n.y-1)=='O'){
                Q.add(new Node(n.x,n.y-1));
                set(n.x,n.y-1);
            }

        }
        System.err.println("---------");
        for(Long i:visited)
            System.err.print(i+" ");
        System.err.println();
        System.err.println("---------");
        if(lakes.containsKey(size))
            lakes.get(size).addAll(visited);
        else
            lakes.put(size,visited);
        return size;
    }


    static class Node{
        public int x;
        public int y;

        Node(int xx,int yy){
            x=xx;
            y=yy;
        }
    }
}