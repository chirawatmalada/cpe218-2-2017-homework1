import java.util.Stack;
public class Homework1 {

	public static void main(String[] args) {
		String pf = "251-*32*+";
		if(args.length>0)pf=args[0];
		Tree a = new Tree(pf);
		a.CreateTree();
		a.inorder(a.root);
		System.out.print("infix : ");
		a.infix(a.root);
		System.out.println("Ans ="+a.calculate(a.root));

	}

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
//                    default  : return left + right;
				}
			}
			else{
				return toDigit(n.data);
			}
			return 0;
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
