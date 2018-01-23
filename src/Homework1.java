import java.util.Stack;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeCellRenderer;

public class Homework1 extends JPanel
implements TreeSelectionListener{
    static String pf = "251-*32*+";
    static Tree a = new Tree(pf);
	JTree tree;
	JEditorPane htmlPane;
	String get;
	DefaultMutableTreeNode ThisNode;
	DefaultMutableTreeNode top;

	public Homework1(){
		super(new GridLayout(1,0));

		//Create the nodes.
		top = new DefaultMutableTreeNode(a.root.data);
		createNodes(top,a.root);

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);

		tree.putClientProperty("JTree.lineStyle","None");
		ImageIcon NodeIcon =  createImageIcon("middle.gif");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(NodeIcon);
		renderer.setClosedIcon(NodeIcon);
		tree.setCellRenderer(renderer);




		//Create the scroll pane and add the tree to it.
		JScrollPane treeView = new JScrollPane(tree);
		
		htmlPane = new JEditorPane();

		JScrollPane htmlView = new JScrollPane(htmlPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(htmlView);

		Dimension minimumSize = new Dimension(100, 50);
		htmlView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100);
		splitPane.setPreferredSize(new Dimension(500, 300));

		//Add the split pane to this panel.
		add(splitPane);
	}

    public static void main(String[] args) {
 //       pf = "251-*32*+";
        if(args.length>0)pf=args[0];
        a.CreateTree();
        a.inorder(a.root);
        System.out.print("infix : ");
        a.infix(a.root);
        System.out.println("Ans ="+a.calculate(a.root));

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
    }

  //  private String calculate(DefaultMutableTreeNode ThisNode) {

   // }

    public static class Node
    {
        Node left,Right;
        char data;

        Node(Node l,char item,Node r)
        {
            data = item;
            left = l;
            Right = r;
        }
        public String toString()
        {
        return (Right == null && left == null) ? Character.toString(data) : "(" + left.toString()+ data + Right.toString() + ")";
        }
    }

    public static boolean Chckoper(char x)
    {
        if (x == '+' || x == '-' || x == '*' || x == '/')
        {
            return true;
        }
        return false;
    }

    public static class Tree{

    public int sum = 0;
    public String input;
    public Node root;

        Tree(String input){
            if (input == null){
                System.out.println("Error input = null");
            }
            if (input.length() == 0){
                System.out.println("Error input lenght = 0");
            }
            this.input = input;
        }
        public void CreateTree(){
            final Stack<Node> node = new Stack<Node>();
            for (int i = 0; i < input.length(); i++ ){
                char data  = input.charAt(i);
                if (Chckoper(data))
                {
                    Node rightNode = node.pop();
                    Node leftNode = node.pop();
                    node.push(new Node(leftNode, data, rightNode));
                } else
                {
                     node.add(new Node(null, data, null));
                }
            }
            root = node.pop();
        }
        public void inorder(Node n)
        {
            if (n == null)
            { return; }
            inorder(n.left);
            inorder(n.Right);
        }
        public void infix(Node n)
        {
            System.out.println(n);
        }
        public int calculate(Node n){
            if(Chckoper(n.data)){
                int left = calculate(n.left);
                int right = calculate(n.Right);
                switch(n.data){
                    case '+': return left + right;
                    case '*': return left * right;
                    case '-': return left - right;
                    case '/': return left / right;

                }
            }
            else{
                return toDigit(n.data);
            }
            return 0;
        }
    }
    private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("Homework1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		Homework1 newContentPane = new Homework1();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void createNodes(DefaultMutableTreeNode top , Node t) {

		if(t.left!=null)
		{
			DefaultMutableTreeNode L = new DefaultMutableTreeNode(t.left.data);
			top.add(L);
			createNodes(L,t.left);
		}
		if(t.Right!=null)
		{
			DefaultMutableTreeNode R = new DefaultMutableTreeNode(t.Right.data);
			top.add(R);
			createNodes(R,t.Right);
		}
	}



	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Homework1.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

        public int calculate(DefaultMutableTreeNode node) {
		if(node.isLeaf()) return Integer.parseInt(node.toString());
		int sum = 0;
                int left = calculate(node.getNextNode());
                int right = calculate(node.getNextNode().getNextSibling());
		switch(node.toString()) {
                    case "+": return  left + right;
                    case "*": return left * right;
                    case "-": return left - right;
                    case "/": return left / right;
//                    default  : return left + right;
		}
		return 0;
	}



	public void valueChanged(TreeSelectionEvent tse) {

		ThisNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		//            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
		tree.getLastSelectedPathComponent();
		if(ThisNode == null){
			return;
		}
		String text = inorder(ThisNode);
		if(!ThisNode.isLeaf()) text += "=" + calculate(ThisNode);
		htmlPane.setText(text);

	}

    public String inorder(DefaultMutableTreeNode node) {
		if (node == null) return "null";
		if(node == ThisNode && !node.isLeaf()) {
			return 	inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling());
		}else if(Chckoper(node.toString().charAt(0)) && node != top) {
			return "(" + inorder(node.getNextNode()) + node.toString() + inorder(node.getNextNode().getNextSibling()) + ")";
		}else {
			return node.toString();
		}
	}

    private static boolean isDigit(char ch)
    {
        return ch >= '0' && ch <= '9';
    }
    private static int toDigit(char ch)
    {
        return ch - '0';
    }
}
