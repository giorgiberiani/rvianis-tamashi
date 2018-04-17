public class Node {
    private int[][] node;
    private Node parent = null;

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node(int[][] node) {
        this.node = node;
    }

    public boolean equals(Node o) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (o.getMatrix()[i][j] != node[i][j])
                    return false;
            }
        }
        return true;
    }


    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j)
                s += node[i][j] + " ";
            s += "\n";
        }
        return s;
    }

    public int[][] getMatrix() {
        return node;
    }
}
