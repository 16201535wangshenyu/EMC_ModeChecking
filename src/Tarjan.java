import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Tarjan {
        private int numOfNode;
        private List<GraphNode> graph;// 图
        private List<ArrayList<Integer>> result;// 保存极大强连通图

        private boolean[] inStack;// 节点是否在栈内
        private Stack<Integer> stack;
        private int[] dfn;
        private int[] low;
        private int time;

        public Tarjan(List<GraphNode> graph) {
            this.graph = graph;
            this.numOfNode = graph.size();
            this.inStack = new boolean[numOfNode];
            this.stack = new Stack<Integer>();
            dfn = new int[numOfNode];
            low = new int[numOfNode];

            Arrays.fill(dfn, -1);// 将dfn所有元素都置为1，其中dfn[i]=-1代表这个点没有被访问过
            Arrays.fill(low, -1);

            result = new ArrayList<ArrayList<Integer>>();
        }

        public  void tarjan(GraphNode current) {
            dfn[current.id] = low[current.id] = time++;
            inStack[current.id] = true;
            stack.push(current.id);

            for (int i = 0; i < current.next.size(); i++) {
                int next = current.next.get(i);

                if (dfn[next] == -1) {
                    tarjan(getGraphNodeByid(this.graph,next));
                    low[current.id] = Math.min(low[current.id], low[next]);
                } else if (inStack[next]) {
                    low[current.id] = Math.min(low[current.id], dfn[next]);
                }
            }
            if (low[current.id] == dfn[current.id]) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                int j = -1;
                while (current.id != j) {
                    j = stack.pop();
                    inStack[j] = false;
                    temp.add(j);
                }
                result.add(temp);
            }
        }
        public GraphNode getGraphNodeByid(List<GraphNode> nodes , int id){
            for(GraphNode node:nodes){
                if(node.id== id){
                    return node;
                }
            }
            return null;

        }

        public List<ArrayList<Integer>> run() {
            for (int i = 0; i < numOfNode; i++) {
                GraphNode node = graph.get(i);
                if (dfn[i] == -1)
                    tarjan(node);
            }
            return result;
        }

        public static void main(String[] args) {

            // 创建图
            int numOfNode = 6;
            List<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
//            List<GraphNode> nodes = new ArrayList<>();
            for (int i = 0; i < numOfNode; i++) {
                graph.add(new ArrayList<Integer>());
            }
            graph.get(0).add(1);
            graph.get(0).add(2);
            graph.get(1).add(3);
            graph.get(2).add(3);
            graph.get(2).add(4);
            graph.get(3).add(0);
            graph.get(3).add(5);
            graph.get(4).add(5);
//            Tarjan t = new Tarjan(graph);
//            List<ArrayList<Integer>> result = t.run();
//            // 打印结果
//
//            for (int i = 0; i < result.size(); i++) {
//                for (int j = 0; j < result.get(i).size(); j++) {
//                    System.out.print(result.get(i).get(j) + " ");
//                }
//                System.out.println();
//            }
        }
    }

