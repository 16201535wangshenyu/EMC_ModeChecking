import java.util.*;

import static java.lang.System.exit;

public class ToStateMachine {
    public List<GraphNode> readStateMachine(){
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        List<GraphNode> list = new ArrayList<>();
        int N;
        int propN;
        int successorN;
        String prop;
        int id, id1;
        Scanner in = new Scanner(System.in);
        N = in.nextInt();//要输入的节点的数量

        for (int x = 0; x < N; x++) {
            GraphNode g = new GraphNode();
            id1 = in.nextInt();
            g.setId(id1);
            if (g.id > N ) {
                System.out.println("Malformed state machine!");
                exit (1);
            }
            propN = in.nextInt();//原子命题的个数，将该命题设置为true
            while(propN -- > 0) {
                prop = in.next();
                g.properties.put(prop,true);
            }
            g.properties.put("TRUE",true); //将原子命题TRUE也设置为TRUE
            successorN = in.nextInt();//s的后继个数
            while (successorN -- >0 ) {
                id = in.nextInt();
                if (id > N) {
                    System.out.println("Malformed state machine!");
                    exit (1);
                }
                g.next.add(id);//将该状态的后继节点加入
                if (map.get(id) == null){
                    map.put(id,new ArrayList<>());
                }
                map.get(id).add(id1);
            }
            list.add(g);
        }
        for(GraphNode g: list){
            List<Integer> pre = map.get(g.id);
            if ( !pre.isEmpty()){
                for (Integer i : pre){
                    g.isReachedStatesSet.add(i);
                }
            }
        }
        return list;
    }
}
