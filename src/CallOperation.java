import java.util.*;

public class CallOperation {
    void opAdd (List<GraphNode> graph, String value) {

        for(GraphNode g : graph){
            if(g.properties.containsKey(value))
                    g.nodes.put(value,true);
        }
    }

    /***
     Function to add a property of a  b oh the graph
     input: graph, a, b, and the property (a  b)
     ***/
    void opAnd (List<GraphNode> graph, String a, String b, String value) {

        for(GraphNode g : graph){
            if(g.nodes.containsKey(a) && g.nodes.containsKey(b))
                g.nodes.put(value,true);
        }

    }

    /***
     Function to add a property of a | b on the graph
     input: graph, a, b and the property (a | b)
     ***/
    void opOr (List<GraphNode> graph, String a, String b, String value) {
        
        for(GraphNode g : graph){
            for(Map.Entry<String , Boolean> entry : g.nodes.entrySet()){
                if (g.nodes.containsKey(a) || g.nodes.containsKey(b)) {
                    g.nodes.put(value, true);
                    break;
                }
            }
        }
        
    }

    /***
     Function to add a property of !a on the graph
     input: graph, a and the property !a
     ***/
    void opNot (List<GraphNode> graph, String a, String value) {
        for(GraphNode g : graph){
            if( ! g.nodes.containsKey(a))
                g.nodes.put(value,true);
        }

    }

    /*** Function to add a property of EX(a) on the graph
     input: graph, a and the property EX(a)

     Search for nodes that have a next node with the property a
     and insert the property value on these nodes
     ***/
    void opEX (List<GraphNode> graph, String a, String value) {
        for( GraphNode g : graph){
            if ( !g.nodes.containsKey(value)){//如果g的节点中没有该公式
                if (!g.next.isEmpty()){//如果g的下一个节点不为空
                    for (Integer id : g.next){ //得到g中所有的下一个节点，
                        for(GraphNode next : graph){//
                            if (next.id == id){
                                if (next.nodes.containsKey(a)){//如果该节点中有该子公式
                                    g.nodes.put(value,true);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    Boolean bfs(Map<Integer, List<Integer>> graph, int a, int strongComponents){
        Boolean visited[] = new Boolean[10000];
        LinkedList<Integer> queue = new LinkedList<>();
        visited[a] = true;
        queue.offer(a);
        while ( queue.isEmpty() ){
            int temp = queue.poll();
            if(temp == strongComponents)
                return true;
            List<Integer> next = graph.get(temp);
            if (next.size() != 0){
                for(Integer i : next){
                    if ( !visited[i] )
                        queue.offer(i);
                }
            }
        }
        return false;
    }

    void opEG(List<GraphNode> graph,String a, String value, List<HashSet<Integer>> F){
        List<GraphNode> eliminatedG = new ArrayList<>();
        /** 复制一个新的图 */
        for(int i = 0; i < graph.size(); i++){
            eliminatedG.add(graph.get(i));
        }
        Map<Integer, List<Integer>> newG = new HashMap<>();
        Iterator<GraphNode> iter = eliminatedG.iterator();
        while (iter.hasNext()){
            GraphNode g = iter.next();
            if(!g.nodes.get(a)){
                iter.remove();
            }
        }
        for (GraphNode g : eliminatedG){
            int id = g.id;
            List<Integer> nextList = g.next;
            if (nextList.size()!=0){
                for (Integer i : nextList){
                    if(getStateById(eliminatedG,i) != null){
                        if (newG.get(id) == null)
                            newG.put(id,new ArrayList<>());
                        newG.get(id).add(i);
                    }
                }
            }
        }
//        HashMap<Integer,Boolean> Q = new HashMap<>();
        Tarjan tarjan = new Tarjan(eliminatedG);
        List<ArrayList<Integer>> strongComponents = tarjan.run();
        HashSet<ArrayList<Integer>> FairStrongComponents = new HashSet<>();
        for(ArrayList<Integer> strongComponent : strongComponents) {//遍历每一个强连通集合
            Boolean isFair = true;
            for(HashSet<Integer> f:  F){//遍历F中每一个集合

                Boolean exist = false;
                for(int i : f){ //遍历f中每一个元素
                    if(strongComponent.contains(i)){
                        exist = true;
                        break;
                    }
                }
                if(!exist){ //如果有某一个公平性F中的元素不满足
                    isFair  = false;
                    break;
                }

            }
            if(isFair){
                FairStrongComponents.add(strongComponent);
                for (Integer i : strongComponent){
                    GraphNode state = getStateById(eliminatedG, i);
                    if (state != null)
                        state.nodes.put(value, true);
                }
            }
        }
        /**将公平性强连通分量中的节点以及能够到达强连通分量的状态全部设置为Q为TRUE**/
        for(ArrayList<Integer> states: FairStrongComponents){
            int state_id = states.get(0);
            for (GraphNode g : eliminatedG){
                int id = g.id;
                if(bfs(newG,id,state_id)){
                    g.nodes.put(value,true);             }
            }
        }
    }

    /***
     Function to add a property of EU(a, b) oh the graph
     input: graph, a, b and the property EU(a, b)

     Search for nodes that has the property b
     and insert the property value on these nodes

     Then search for nodes that have the property a and a next node with the property value
     and insert the property value on these nodes, recursively
     ***/
    void opEU (List<GraphNode> graph, String a, String b, String value ,HashMap<Integer,Boolean> Q) {


        for(GraphNode g: graph){
            if (Q != null) {
                if(g.nodes.containsKey(b) && Q.keySet().contains(g.id) ){
                    g.nodes.put(value, true);
                }

            }else{
                if(g.nodes.containsKey(b) ){
                    g.nodes.put(value, true);
                }
            }

        }

        Boolean hasChanged = true;
        while (hasChanged){
            hasChanged = false;
            for (GraphNode g : graph){
                if((g.nodes.containsKey(a) || a.equals("TRUE")) && !g.nodes.containsKey(value)){
                    if (!g.next.isEmpty()) {
                        for (Integer id : g.next){
                            for (GraphNode next : graph){
                                if (next.id == id){
                                    if (next.nodes.containsKey(value)){
                                        g.nodes.put(value, true);
                                        hasChanged =true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    void opAU(List<GraphNode> graph, String a, String b, String value){
        HashMap<GraphNode, Boolean> isVisited = new HashMap<GraphNode, Boolean>();
        for (GraphNode n : graph) {
            isVisited.put(n, false);
        }
        for(GraphNode node:graph){
            if(!isVisited.get(node)){//如果当前得节点
                AU(value,a,b,node,isVisited,graph);

            }
        }
//        HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
//        for (Node n : nodes) {
//            visited.put(n, false);
//        }

    }

    private boolean AU(String value, String a, String b, GraphNode node, HashMap<GraphNode, Boolean> isVisited, List<GraphNode> graph) {
        if (isVisited.get(node)) {//如果该状态已经被访问过
            if (node.nodes.containsKey(value)) {//该状态中含有公式s，满足AU
                return true;
            } else {
                return false;
            }
        }
        isVisited.put(node, true);
        if (node.nodes.containsKey(b) ||node.properties.containsKey(b) ) {//如果公式s2满足，则成立
            node.nodes.put(value,true);
            return true;
        } else if (!(node.nodes.containsKey(a) ||node.properties.containsKey(a))) {
            return false;
        }
        List<GraphNode> nodeChildList = getChildNodes(node.next,graph);
        for (GraphNode n1 : nodeChildList) {
            if (!AU(value, a, b, n1, isVisited,graph)) {
                return false;
            }
        }
        node.nodes.put(value,true);
        return true;

    }
    private List<GraphNode> getChildNodes(List<Integer> nodelist,List<GraphNode> graph){
        List<GraphNode> childNodes = new ArrayList<>();
        for(int i :nodelist ){
            for(GraphNode node:graph){
                if(node.id == i){
                    childNodes.add(node);
                    break;
                }
            }


        }
        return childNodes;

    }



    /***
     Function to add a property of AF(a) oh the graph
     input: graph, a and the property AF(a)

     Search for nodes that has the property a
     and insert the property value on these nodes

     Then search for nodes that have all the nexts nodes with the property value
     and insert the property value on these nodes, recursively
     ***/
//    void opAF (List<GraphNode> graph, String a, String value) {
//
//        for (GraphNode g : graph){
//            if(g.nodes.containsKey(a))
//                    g.nodes.put(value,true);
//        }
//
//        Boolean hasChanged = true;
//        Boolean allNextHave;
//        while (hasChanged) {
//            hasChanged = false;
//            for (GraphNode g : graph) {
//                if (!g.nodes.containsKey(value)) {
//                    allNextHave = true;
//                    if (!g.next.isEmpty()) {
//                        for ( Integer id : g.next){
//                            for (GraphNode next : graph){
//                                if (next.id == id){
//                                    if (next.nodes.containsKey(value)){
//                                        allNextHave = false;
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (allNextHave && !g.next.isEmpty()) {
//                        g.nodes.put(value, true);
//                        hasChanged = true;
//                    }
//                }
//            }
//        }
//    }

    /***
     * 模型检查1 不带公平性的
     Function to call the right operation for given expression
     input: expression tree, state machine
     ***/
    void callOperation1(TreeNode node, List<GraphNode> graph){

        LinkedList<TreeNode> origNodes = new LinkedList<>();
        origNodes.push(node);//

        LinkedList<TreeNode> postNodes = new LinkedList<>();
        TreeNode aux;

        while (!origNodes.isEmpty()) //如果链表不为空
        {
            TreeNode current = origNodes.peek(); //将节点出postNodes栈
            origNodes.pop();

            postNodes.push(current); //将节点入postNodes栈

            if (current.left != null)
                origNodes.push(current.left);//将左孩子节点入origNodes栈

            if (current.right != null)
                origNodes.push(current.right);////将右孩子节点入origNodes栈
        }

        while (!postNodes.isEmpty())//将二叉树中所有的节点放入postNodes栈，先序遍历
        {
            aux = postNodes.peek();

            if(aux.type.equals("function")){

                if(aux.op.equals("EX")){
                    opEX(graph, aux.left.content, aux.content);
                }
//                else if(aux.op.equals("AF")){
//                    opAF(graph, aux.left.content, aux.content);
//                }
                else if(aux.op.equals("EG")){
                    opEG(graph, aux.left.content, aux.content,null);
                }
                else if(aux.op.equals("EU")){
                    opEU(graph, aux.left.content, aux.right.content, aux.content,null);
                }
                else if(aux.op.equals("AU")){
                    opAU(graph, aux.left.content, aux.right.content, aux.content);
                }
                else if(aux.op.equals("!")){
                    opNot(graph, aux.left.content, aux.content);
                }
                else if(aux.op.equals("")){//原子命题
                    opAnd(graph, aux.left.content, aux.right.content, aux.content);
                }
                else if(aux.op.equals("|")){
                    opOr(graph, aux.left.content, aux.right.content, aux.content);
                }
                else System.out.println("Error while calling the algorithms!");

            }
            else{
                opAdd(graph, aux.content);
            }

            postNodes.pop();
        }
    }

    /**
     * 带公平性的模型检查1,EMC
     */
    void callOperation2(TreeNode node,List<GraphNode> graph,List<HashSet<Integer>> F){
        /**
         * 1、首先遍历图找到图中所有的强连通分量。
         */
        HashMap<Integer,Boolean> Q = new HashMap<>();
        Tarjan tarjan = new Tarjan(graph);
        List<ArrayList<Integer>> strongComponents = tarjan.run();
        HashSet<ArrayList<Integer>> FairStrongComponents = new HashSet<>();
        for(ArrayList<Integer> strongComponent : strongComponents) {//遍历每一个强连通集合
            boolean isFair = true;
            for(HashSet<Integer> f:  F){//遍历F中每一个集合

                boolean exist = false;
                for(int i : f){ //遍历f中每一个元素
                    if(strongComponent.contains(i)){
                        exist = true;
                        break;
                    }
                }
                if(!exist){ //如果有某一个公平性F中的元素不满足
                    isFair  = false;
                    break;
                }

            }
            if(isFair){
                FairStrongComponents.add(strongComponent);
            }
        }
        /**将公平性强连通分量中的节点以及能够到达强连通分量的状态全部设置为Q为TRUE**/
        for(ArrayList<Integer> states: FairStrongComponents){

            for(int state_id : states){
                HashSet<Integer> isReachedStateSet = getStateById(graph,state_id).isReachedStatesSet;
                for(int id : isReachedStateSet)
                    Q.put(state_id,true);
            }
        }
        /**2 **/

    }

    /**
     * 根据id号得到状态节点
     * @param states
     * @return
     */
    private GraphNode getStateById (List<GraphNode> states, int stateId){
        for(GraphNode state: states){
            if(state.id == stateId){
                return state;
            }
        }
        return null;

    }
}
