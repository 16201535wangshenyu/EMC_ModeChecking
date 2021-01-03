import static java.lang.System.exit;

public class ParserCtlExpression {
    /***
     Function to get the property from given expression
     input: expression, property position
     ***/
    String getProp(String expression, int pos) {
        try {
            String result = "(";
            int level = 1;
            for (int i = pos + 1; level != 0; ++i) {
                result += expression.charAt(i);
                if (expression.charAt(i) == '(') level++;
                if (expression.charAt(i) == ')') level--;
            }
            return result;
        }catch (Exception e){
            System.out.println("expression:"+expression);
            exit(0);
            return "";
        }
    }

    /***
     Function to get the current function from given expression
     input: expression, function position
     ***/
    String getFunction(String expression, int pos){
        String result = "";
        while(expression.charAt(pos) != '('){
            result += expression.charAt(pos);
            pos++;
        }
        return result;
    }

    /***
     Function to verify if the current expression is a function (TRUE) or a property (FALSE)
     input: expression, position to verify
     ***/
    Boolean isFunction(String expression, int pos) {
        while(expression.charAt(pos) != '('){
            if (expression.charAt(pos) == ')')
                return false;
            pos++;
        }
        return true;
    }

    /***
     Function to get the function position from given expression. Return (-1) if the expression 
     is a property
     input: expression
     ***/
    int findFunction(String expression){
        int level = 0;
        for (int i = 0; i < (int)expression.length(); ++i) {
            if(expression.charAt(i) == '(') level++;
            else if(expression.charAt(i) == ')') level--;
            else if(level == 1 && isFunction(expression, i)) return i;
        }
        return -1;
    }

    /***
     Function to transform AX(p) into !EX(!p)
     input: p
     ***/
    String ax2ex(String p){
        String result = "(!(EX(!";
        result += p;
        result += ")))";
        return result;
    }

    /***
     Function to transform EF(p) into EU(TRUE, p)
     input: p
     ***/
    String ef2eu(String p){
        String result = "(EU((TRUE),";
        result += p;
        result += "))";
        return result;
    }

    /***
     Function to transform AF(p) into AU(TRUE, p)
     input: p
     ***/
    String af2au(String p){
        String result = "(AU((TRUE),";
        result += p;
        result += "))";
        return result;
    }

    /***
     Function to transform AG(p) into !EU(TRUE, !p)
     input: p
     ***/
    String ag2eu(String p){
        String result = "(!(EU((TRUE),(!";
        result += p;
        result += "))))";
        return result;
    }

    /***
     Function to transform EG(p) into !AF(!p)
     input: p
     ***/
    String eg2af(String p){
        String result = "(!(AF(!";
        result += p;
        result += ")))";
        return result;
    }

    /***
     Function to transform AU(p, q) into  !(EU(!q,!))              ((AF(q) & (!(EU((q, !q & !p)
     input: p, q
     ***/
    String au2eueg(String left, String right){
        String result = "(!(EU((!";
        result += right;
        result += "),((!";
        result += left;
        result += ")&(!";
        result += right;
        result += "))))|(EG(!";
        result += right;
        result += ")))";
        return result;
    }

    /***
     Function to transform p->q into !p | q
     input: p, q
     ***/
    String impl2or(String left, String right){
        String result = "((!";
        result += left;
        result += ")|";
        result += right;
        result += ")";
        return result;
    }

    /***
     Function to transform p<->q into (!p | q) & (!q | p)
     input: p, q
     ***/
    String if2and(String left, String right){
        String result = "(((!";
        result += left;
        result += ")|";
        result += right;
        result += ")&((!";
        result += right;
        result += ")|";
        result += left;
        result += "))";
        return result;
    }

    /***
     Function to transform (p) into p (without parenthesis)
     input: p
     ***/
    String removeParenthesisInLeaf(String leaf){
        String result = "";
        for(int i = 1; i < (int)leaf.length()-1; ++i){
            result += leaf.charAt(i);
        }
        return result;
    }

//    /***
//     Function to free the allocated tree
//     input: tree
//     ***/
//    void cleanTree(TreeNode node){
//        if(node == null) return;
//        cleanTree(node.left);
//        cleanTree(node.right);
//        ;
//    }

/***
 Function to parse the CTL expression into a tree
 input: expression (AU((T1),(C1)))
 ***/
    public TreeNode parser(String expression,Boolean isFair) {
        String left, right;
        TreeNode node = new TreeNode();

        int posFunc = findFunction(expression);
//        System.out.println("posFunc:"+posFunc);
        if (posFunc == -1){
            node.content = removeParenthesisInLeaf(expression);
//            System.out.println("node.content:"+node.content);
            node.type = "prop";
            node.op = "add";
            node.left = null;
            node.right = null;

            return node;
        }
        String function = getFunction(expression, posFunc);

        if(function.equals("AX")){
            posFunc += 2;
            left = getProp(expression, posFunc);

            expression = ax2ex(left);
            function = "!";
            posFunc = 1;
        }
        else if(function.equals("EF")){
            posFunc += 2;
            left = getProp(expression, posFunc);

            expression = ef2eu(left);
            function = "EU";
            posFunc = 1;
        }
        else if (function.equals("AF")){
            //System.out.println("hhhhhhhhhhhhhhhhhhhhhhh");
            posFunc += 2;
            left = getProp(expression, posFunc);

            expression = af2au(left);
            function = "AU";


            if(isFair) {//如果是公平性的
                //parser(expression, isFair);
                String child = getProp(expression, posFunc);
                int posColon = findFunction(child);
                left = getProp(child, 1);
                right = getProp(child, ++posColon);
                expression = au2eueg(left, right);
                function = "&";
                posFunc = findFunction(expression);
            }else{
                posFunc = 1;
            }




        }
        else if(function.equals("AG")){
            posFunc += 2;
            left = getProp(expression, posFunc);

            expression = ag2eu(left);
//            System.out.println("expression:"+expression);
            function = "!";
            posFunc = 1;
        }
        else if(function.equals("EG") && !isFair){

                posFunc += 2;
                left = getProp(expression, posFunc);

                expression = eg2af(left);
                function = "!";
                posFunc = 1;


        }
        else if(function.equals("AU") && isFair){

            posFunc += 2;
            String child = getProp(expression, posFunc);
            int posColon = findFunction(child);
            left = getProp(child, 1);
            right = getProp(child, ++posColon);
            expression = au2eueg(left, right);
            function = "&";
            posFunc = findFunction(expression);



        }
        else if(function.equals("->")){
            posFunc += 2;
            left = getProp(expression, 1);
            right = getProp(expression, posFunc);

            expression = impl2or(left, right);
            function = "|";
            posFunc = findFunction(expression);
        }
        else if(function.equals("<->")){
            posFunc += 3;
            left = getProp(expression, 1);
            right = getProp(expression, posFunc);

            expression = if2and(left, right);
            function = "&";
            posFunc = findFunction(expression);
        }

        if(function.equals("EX")){
            posFunc += 2;
            left = getProp(expression, posFunc);

            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = null;
        }
        else if(function.equals("AU") && isFair==false){
            posFunc += 2;
            String child = getProp(expression, posFunc);

            int posColon = findFunction(child);
            left = getProp(child, 1);
            right = getProp(child, ++posColon);


            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left, false);
            node.right = parser(right, false);


        }
        else if(function.equals("EG") && isFair){
            posFunc += 2;
            left = getProp(expression, posFunc);
            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = null;

        }
        else if(function.equals("AF")){
            posFunc += 2;
            left = getProp(expression, posFunc);

            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = null;
        }
        else if(function.equals("EU")){
            posFunc += 2;
            String child = getProp(expression, posFunc);

            int posColon = findFunction(child);
            left = getProp(child, 1);
            right = getProp(child, ++posColon);

            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = parser(right,isFair);
        }
        else if(function.equals("&")){
            left = getProp(expression, 1);
            right = getProp(expression, ++posFunc);

            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = parser(right,isFair);
        }
        else if(function.equals("|")){
            left = getProp(expression, 1);
            right = getProp(expression, ++posFunc);

            node.content = expression;
            node.type = "function";
            node.op = function;
            node.left = parser(left,isFair);
            node.right = parser(right,isFair);
        }
        else if(function.equals("!")){
            left = getProp(expression, ++posFunc);

            node.content = expression;
            node.type = "function";
            node.op = function;
//            System.out.println("left:"+left);
            node.left = parser(left,isFair);
            node.right = null;
        }
        else {
            System.out.println("function" + function + "出现问题  Malformed state machine!");
            exit (1);
        }

        return node;
    }
}
