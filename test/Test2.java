import java.util.List;

public class Test2 {
    /***(AG((RcvMsg)->AU((RcvMsg),((-RcvMsg)&AU((!RcvMsg),(SndMsg))))))
     用于打印在状态机输入中应用的表达式的结果：
     输入：状态机，修改的表达式，原始表达式
     10
     0 3 SndMsg Smsg Rmsg 2 1 9
     1 1 Rmsg 1 2
     2 1 RcvMsg 1 3
     3 0 1 4
     4 1 SndMsg 2 5 6
     5 0 2 2 4
     6 1 Smsg 2 4 7
     7 3 RcvMsg Smsg Rmsg 1 8
     8 2 Smsg Rmsg 1 0
     9 2 Smsg Rmsg 2 0 7
     (AG((RcvMsg)->(AU((RcvMsg),((!RcvMsg)&(AU((!RcvMsg),(SndMsg))))))))
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
        String expression = "(AU((T1),(C1)))";//表达式
        //(AG((RcvMsg)->F3)))
        //F1(AU((!RcvMsg),(SndMsg)))
        //F2 (!RcvMsg)
        //F3 (AU((RcvMsg),(F2&F1)))
        //F4 ((RcvMsg)->F3)
        //F5 (AG(RcvMsg)->F3))
        //F6
        //(AG(((SndMsg)&(Smsg))->AU((SndMsg),((!SndMsg)&AU((!SndMsg),((RcvMsg)&(Rmsg)))))))
        //(AG(((SndMsg)&(!Smsg))->AU((SndMsg),((!SndMsg)&AU((!SndMsg),((RcvMsg)&(!Rmsg)))))))
//        (AG(((SndMsg)&(Smsg))->(AU((SndMsg),((!SndMsg)&(AU((!SndMsg),((RcvMsg)&(Rmsg)))))))))
//        (AG((RcvMsg)->(AU((RcvMsg),((!RcvMsg)&(AU((!RcvMsg),(SndMsg))))))))
        String ex = "(AF(C1))";
        TreeNode ctlTree = new TreeNode();

        ParserCtlExpression parserCtlExpression = new ParserCtlExpression();
        ctlTree = parserCtlExpression.parser(ex,true);
        System.out.println(ctlTree);


//(AU(true,C1))  (!(EU((!C1),((!true)&(!C1))))|(EG(!C1)))
               //(!(EU((!(C1)),((!(TRUE))&(!(C1)))))|(EG(!(C1))))
    }
//    public static void printTree(TreeNode head) {
//        System.out.println("Binary Tree:");
//        printInOrder(head, 0, "H", 17);
//        System.out.println();
//    }
//
//    public static void printInOrder(TreeNode head, int height, String to, int len) {
//        if (head == null) {
//            return;
//        }
//        printInOrder(head.right, height + 1, "v", len);
//        String val = to + head.content + to;
//        int lenM = val.length();
//        int lenL = (len - lenM) / 2;
//        int lenR = len - lenM - lenL;
//        val = getSpace(lenL) + val + getSpace(lenR);
//        System.out.println(getSpace(height * len) + val);
//        printInOrder(head.left, height + 1, "^", len);
//    }
//
//    public static String getSpace(int num) {
//        String space = " ";
//        StringBuffer buf = new StringBuffer("");
//        for (int i = 0; i < num; i++) {
//            buf.append(space);
//        }
//        return buf.toString();
//    }
}
