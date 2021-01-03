import java.util.List;
import java.util.Scanner;

public class CtlModelChecking {

    /***
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

    public static void main(String[] args){
        List<GraphNode> smGraphy;
        TreeNode ctlTree = new TreeNode();
        ToStateMachine toStateMachine = new ToStateMachine();
        smGraphy = toStateMachine.readStateMachine();
//        for(GraphNode g : smGraphy){
//            System.out.println(g.id);
//        }
        String  expression;
        Scanner in = new Scanner(System.in);
        expression = in.next();
        ParserCtlExpression parserCtlExpression = new ParserCtlExpression();
//        ctlTree = parserCtlExpression.parser(expression);



        CallOperation callOperation = new CallOperation();
        callOperation.callOperation1(ctlTree, smGraphy);

        printResult(smGraphy, ctlTree.content, expression);

//        cleanTree(ctlTree);

    }

}
