public class TreeNode {
    public String content;
    public String type;
    public String op;
    public TreeNode left = null;
    public TreeNode right = null;

    public TreeNode(String content, String type, String op, TreeNode left, TreeNode right) {
        this.content = content;
        this.type = type;
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public TreeNode() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        String left = (this.left == null)?"":this.left.content;
        String right = (this.right == null)?"":this.right.content;
        return "TreeNode{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", op='" + op + '\'' +
                ", left=" +left +
                ", right=" +right +
                '}';
    }
}
