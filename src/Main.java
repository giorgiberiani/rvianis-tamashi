import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//განივი, ანუ სრული გარარჩევის ალგორითმი
public class Main {
    public static final int limit = 10000;
    public static boolean failure = false;
    public static int count = 0;
    public static List<Node> listOpen = new ArrayList<>();
    public static List<Node> listClosed = new ArrayList<>();
    public static Node goalNode = null;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //საწყისი მდგომარეობის მატრიცა
        int[][] firsNode = new int[3][3];
        //მიზნის მდგომარეობის მატრიცა
        int[][] lasNode = new int[3][3];
        System.out.println("შეიყვანეთ საწყისი მატრიცა (გამოიყენე 0 ცარიელი ადგილის მაგივრად) ");
        //საწყისი მდგომარეობის მატრიცის შეყვანა
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                firsNode[i][j] = input.nextInt();
            }
        }
        System.out.println("შეიყვანეთ საბოლოო მატრიცა (გამოიყენე 0 ცარიელი ადგილის მაგივრად)");
        //საბოლოო მდგომარეობის მატრიცის შეყვანა
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                lasNode[i][j] = input.nextInt();
            }
        }

        Node root = new Node(firsNode);
        Node goalState = new Node(lasNode);

        listOpen.add(root);
        treeSearch(goalState);

        List<Node> path = new ArrayList<>();
        if (!failure) {
            while (goalNode.getParent() != null) {
                path.add(goalNode);
                goalNode = goalNode.getParent();
            }


            System.out.println(root);
            for (int i = path.size() - 1; i >= 0; --i)
                System.out.println(path.get(i));
        }
    }

    public static void treeSearch(Node goalState) {

        count++;
        System.out.println(count);
        if (listOpen.isEmpty() || count == limit) {
            System.out.println("failure");
            failure = true;
            return;
        }
        Node currentNode = listOpen.get(0);
        listOpen.remove(0);
        listClosed.add(currentNode);
        List<Node> childNodes = action(currentNode);
        for (Node m : childNodes)
            m.setParent(currentNode);
        insertAll(childNodes);
        if (goalTestIsSuccessful(currentNode, goalState)) {
            goalNode = currentNode;
            return;
        } else {
            treeSearch(goalState);
        }

    }


    //ამატებს ყველა ახალ კვანძს list open სიის ბოლოში, რომელიც არ ემთხვევა არცერთ კვანძს არცერთი სიიდან
    public static void insertAll(List<Node> childNodes) {
        for (Node node : childNodes) {
            if (!listOpen.contains(node) && !listClosed.contains(node))
                listOpen.add(node);
        }
    }

    //ამოწმებს მიმდინარე მატრიცა არის თუ არა მიზნის მატრიცა
    public static boolean goalTestIsSuccessful(Node currenNode, Node goalState) {
        return currenNode.equals(goalState);
    }

    //გენერირდება შვილი კომპონენტები
    public static List<Node> action(Node node) {
        int[][] matrix = node.getMatrix();
        int i = 0, j = 0;
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 3; ++j1)
                if (matrix[i1][j1] == 0) {
                    i = i1;
                    j = j1;
                    break;
                }
        }
        List<Node> childNodes = new ArrayList<>();
        if (leftChild(matrix, i, j) != null)
            childNodes.add(new Node(leftChild(matrix, i, j)));
        if (upChild(matrix, i, j) != null)
            childNodes.add(new Node(upChild(matrix, i, j)));
        if (rightChild(matrix, i, j) != null)
            childNodes.add(new Node(rightChild(matrix, i, j)));
        if (bottomChild(matrix, i, j) != null)
            childNodes.add(new Node(bottomChild(matrix, i, j)));

        return childNodes;
    }

    private static int[][] leftChild(int[][] mass, int i, int j) {
        if (j == 0)
            return null;
        int returnArray[][] = new int[3][3];
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 3; ++j1)
                returnArray[i1][j1] = mass[i1][j1];
        }
        int tmp = returnArray[i][j - 1];
        returnArray[i][j - 1] = returnArray[i][j];
        returnArray[i][j] = tmp;

        return returnArray;
    }

    private static int[][] upChild(int[][] mass, int i, int j) {
        if (i == 0)
            return null;
        int returnArray[][] = new int[3][3];
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 3; ++j1)
                returnArray[i1][j1] = mass[i1][j1];
        }
        int tmp = returnArray[i - 1][j];
        returnArray[i - 1][j] = returnArray[i][j];
        returnArray[i][j] = tmp;
        return returnArray;
    }

    private static int[][] rightChild(int[][] mass, int i, int j) {
        if (j == 2)
            return null;
        int returnArray[][] = new int[3][3];
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 3; ++j1)
                returnArray[i1][j1] = mass[i1][j1];
        }
        int tmp = returnArray[i][j + 1];
        returnArray[i][j + 1] = returnArray[i][j];
        returnArray[i][j] = tmp;
        return returnArray;
    }

    private static int[][] bottomChild(int[][] mass, int i, int j) {
        if (i == 2)
            return null;
        int returnArray[][] = new int[3][3];
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int j1 = 0; j1 < 3; ++j1)
                returnArray[i1][j1] = mass[i1][j1];
        }
        int tmp = returnArray[i + 1][j];
        returnArray[i + 1][j] = returnArray[i][j];
        returnArray[i][j] = tmp;
        return returnArray;
    }
}
