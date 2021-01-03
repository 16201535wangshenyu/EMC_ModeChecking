public class Test2 {
    public static void main(String[] args) {
        String expression = "(AU((T1),(C1)))";//表达式
        TreeNode ctlTree = new TreeNode();

        ParserCtlExpression parserCtlExpression = new ParserCtlExpression();
//        ctlTree = parserCtlExpression.parser(expression);
//        printTree(ctlTree);



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
