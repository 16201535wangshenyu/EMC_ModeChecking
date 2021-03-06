import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 9
 0 2 N1 N2 2 1 2
 1 2 T1 N2 2 3 4
 2 2 N1 T2 2 5 6
 3 2 C1 N2 2 0 7
 4 2 T1 T2 1 7
 5 2 T1 T2 1 8
 6 2 N1 C2 1 8
 7 2 C1 T2 1 2
 8 2 T1 C2 1 1

 */
public class Test1 {
    /***(AG((RcvMsg)->(AU((RcvMsg),((!RcvMsg)&(AU((!RcvMsg),(SndMsg))))))))
     用于打印在状态机输入中应用的表达式的结果：
     输入：状态机，修改的表达式，原始表达式
     ***/
    public static void printResult(List<GraphNode> graph, String expression, String initialExpression){
        System.out.println("Expression: " + initialExpression);
        System.out.print("States: ");

        for( GraphNode g : graph){
            if(g.nodes.containsKey(expression) )
                System.out.print(g.id + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        List<GraphNode> smGraphy;
        TreeNode ctlTree = new TreeNode();
        ToStateMachine toStateMachine = new ToStateMachine();
        smGraphy = toStateMachine.readStateMachine();//生成了一个图，该图中的所有的内容已填充
        //System.out.println();
        //(AU((T1),(C1)))
//        for(GraphNode g : smGraphy){
//            System.out.println(g.id);
//        }
        String  expression = "";
        Scanner in = new Scanner(System.in);//输入表达式
        expression = in.next();//表达式

        ParserCtlExpression parserCtlExpression = new ParserCtlExpression();
        ctlTree = parserCtlExpression.parser(expression,true);
        //System.out.println(ctlTree);


        CallOperation callOperation = new CallOperation();
        /**输入如F**/
        List<HashSet<Integer>> F = new ArrayList<>();
//        System.out.println("请输入公平性约束F：");
//        int num = in.nextInt();

        int numlen = 2;
        HashSet<Integer> set1 = new HashSet<>();
        set1.add(3);
        set1.add(7);
        HashSet<Integer> set2 = new HashSet<>();
        set1.add(6);
        set1.add(8);
        F.add(set1);
        F.add(set2);

//        for(int i=0;i<numlen;i++){(AG(AFC1))
//
//
//
//        }

        callOperation.callOperation2(ctlTree, smGraphy,F);
        printResult(smGraphy, ctlTree.content, expression);


    }
}
