import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

class Coord {
    public int x;
    public int y;

    Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord[] getNeighbours() {
        Coord[] n = {new Coord(x, y - 1), new Coord(x + 1, y), new Coord(x, y + 1), new Coord(x - 1, y)};
        return n;
    }

    public boolean equals(Coord c) {
        return this.x == c.x && this.y == c.y;
    }
}


class Player {
    static int height;
    static int width;

    static char[][] board;
    static String[] direction = {"UP", "RIGHT", "DOWN", "LEFT"};
    static Coord kirk;

    private static Scanner in = new Scanner(System.in);

    private static char getCharAtCoord(Coord c) {
        return board[c.y][c.x];
    }

    private static boolean isInMap(Coord s) {
        return 0 <= s.x && s.x < width && 0 <= s.y && s.y < height;
    }

    private static boolean mustBeAvoided(Coord s, char[] avoid) {
        for (char ch : avoid)
            if (getCharAtCoord(s) == ch)
                return true;

        return false;
    }

    private static void readBoard() {
        kirk.x = in.nextInt();
        kirk.y = in.nextInt();


        for (int y = 0; y < height; y++) {
            String ROW = in.next();
            if (kirk.y - 2 <= y && y <= kirk.y + 2)
                for (int x = Math.max(kirk.x - 2, 0); x < Math.min(kirk.x + 3, width); x++)
                    board[y][x] = ROW.charAt(x);
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.err.print(board[y][x]);
            }
            System.err.print("\n");
        }
    }

    static boolean goTo(char target, char[] toAvoid, boolean onlyReveal) {
        int prec[][] = new int[height][width];
        for (int x = 0; x < prec.length; x++)
            Arrays.fill(prec[x], -1);

        LinkedList<Coord> open_list = new LinkedList();

        open_list.add(kirk);

        while (open_list.size() > 0 && getCharAtCoord(open_list.element()) != target) {
            Coord[] neighbours = open_list.remove().getNeighbours();
            for (int i = 0; i < neighbours.length; i++) {
                Coord n = neighbours[i];
                if (isInMap(n) && !mustBeAvoided(n, toAvoid) && prec[n.y][n.x] == -1) {
                    prec[n.y][n.x] = (i + 2) % 4;
                    open_list.add(n);
                }
            }
        }

        if (open_list.size() == 0)
            return false;
        else {
            Coord targetCell = open_list.remove();
            Stack<Integer> path = new Stack();
            Coord iter = targetCell;

            while (!iter.equals(kirk)) {
                path.push((prec[iter.y][iter.x] + 2) % 4);
                iter = iter.getNeighbours()[prec[iter.y][iter.x]];
            }
            while ((onlyReveal && getCharAtCoord(targetCell) == target) ||
                    (!onlyReveal && !path.empty())) {
                System.out.println(direction[path.pop()]);
                readBoard();
            }
            return true;
        }
    }

    public static void main(String args[]) {
        height = in.nextInt();
        width = in.nextInt();
        kirk = new Coord(-1, -1);
        int A = in.nextInt();

        // Initializing the map
        board = new char[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                board[y][x] = '?';
        readBoard();

        // discovers the entire map
        char[] avoidWallsAndControl = {'#', 'C'};
        while (goTo('?', avoidWallsAndControl, true)) ;

        // goes to control room and goes back to start
        char avoidWalls[] = {'#', '?'};
        goTo('C', avoidWalls, false);
        goTo('T', avoidWalls, false);
    }

}