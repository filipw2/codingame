import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class TheLabyrinth {

    static String actions[] = {"SPEED","JUMP","UP","DOWN","SLOW"};
    static String[] L=new String[4];
    static int V;
    static int M;
    static int length;

    public static void main(String args[]) {
        M = 4; // the amount of motorbikes to control
        V = 4; // the minimum amount of motorbikes that must survive
        L[0] = ".............0.............0.........................................................."; // L0 to L3 are lanes of the road. A dot character . represents a safe space, a zero 0 represents a hole in the road.
        L[1] = "..............0.............0.........................................................";
        L[2] = "...............0.............0........................................................";
        L[3] = "................0..........0000.......................................................";
        length=L[0].length()-20;
        Bike[] bikes=new Bike[M];
        for (int i = 0; i < M; i++) {
            bikes[i]= new Bike(0,i,false,6);
        }
        // game loop
        //while (true) {

            backtrack(bikes);
        Collections.reverse(results);
            for(int i: results)
                System.out.println(actions[i]);
        //}
    }
    static List<Integer> results= new ArrayList();
    static boolean backtrack(Bike[] bikes){
        boolean foundIt = false;
        //System.err.println(move);
        if (bikes[0].x>length)
        {
            //results.add(move);
            return true;
        }
        int n=0;
        Bike[]b=new Bike[bikes.length];
        for(int i=0;i<bikes.length;i++){
                if(!bikes[i].isDestroyed)
                    n++;
            b[i]=new Bike(bikes[i].x,bikes[i].y,bikes[i].isDestroyed,bikes[0].speed);
        }

        if(n<V)
            return false;
        for(int i=0;i<5;i++)
        {
            foundIt = backtrack(simulate(b,i));
            if (foundIt){
                results.add(i);
                return true;
            }

        }
        return false;
    }
    static Bike[] simulate(Bike[] bikes, int move){
        Bike[]b=new Bike[bikes.length];

        for(int i=0;i<bikes.length;i++){
            b[i]=new Bike(bikes[i].x,bikes[i].y,bikes[i].isDestroyed,bikes[0].speed);
        }
        switch(move){
            case 0: if(b[0].speed<49)b[0].speed++;break;
            case 2: moveUP(b);break;
            case 3: moveDown(b);break;
            case 4: if(b[0].speed>1)b[0].speed--;break;

        }
        return check(b,move==1 ? true:false);
    }

    static Bike[] check(Bike[] b, boolean isJump){
        // System.err.println("check");

        if(isJump){
            for(int i=0;i<b.length;i++)
                if(!b[i].isDestroyed)
                {

                    String sub=L[b[i].y].substring(b[i].x,b[i].x+b[0].speed);
                    System.err.println(sub+" "+b[0].speed+" "+(b[i].x+b[0].speed));
                    if(L[b[i].y].charAt(b[i].x+b[0].speed)=='0')
                        b[i].isDestroyed=true;

                    b[i].x+=b[0].speed;
                }
        }else{
            for(int i=0;i<b.length;i++)
            {
                //System.err.println(b[i].isDestroyed);
                if(!b[i].isDestroyed)
                {
                    String sub=L[b[i].y].substring(b[i].x+1,b[i].x+b[0].speed+1);
                    System.err.println(sub+" "+b[0].speed+" "+(b[i].x+b[0].speed));
                    if(sub.contains("0")){

                        b[i].isDestroyed=true;
                    }
                    b[i].x+=b[0].speed;
                }
            }
        }
        return b;
    }



    static void moveUP(Bike[] b){
        if(b[0].y!=0)
            for(int i=0;i<b.length;i++)
                if(!b[i].isDestroyed)
                    b[i].y-=1;

    }
    static void moveDown(Bike[] b){
        if(b[b.length-1].y!=3)
            for(int i=0;i<b.length;i++)
                if(!b[i].isDestroyed)
                    b[i].y+=1;

    }

    static class Bike{
        public int x;
        public int y;
        public int speed;
        public boolean isDestroyed= false;

        public Bike(int xx,int yy, boolean status, int speed){
            x=xx;
            y=yy;
            this.speed=speed;
            isDestroyed=status;
        }
    }

    static int getNumberOfBikes(Bike[] bikes){
        int n=0;
        for(int i=0;i<bikes.length;i++)
            if(!bikes[i].isDestroyed)
                n++;
        return n;
    }
}