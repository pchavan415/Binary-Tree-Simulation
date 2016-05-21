import acm.gui.*;

import acm.program.*;
import acm.graphics.*;
import java.util.TreeSet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

import java.util.Scanner;

public class BinaryTree extends GraphicsProgram {

	public void run() {
		setup();
		
	}

	/** Initialize the graphical user interface */
	public void setup() {

		insertField = new IntField();
		insertField.setActionCommand("insert");
		insertField.addActionListener(this);

		deleteField = new IntField();
		deleteField.setActionCommand("delete");
		deleteField.addActionListener(this);

		searchField = new IntField();
		searchField.setActionCommand("search");
		searchField.addActionListener(this);

		add(insertField, NORTH);
		add(new JButton("insert"), NORTH);

		add(deleteField, NORTH);
		add(new JButton("delete"), NORTH);

		add(searchField, NORTH);
		add(new JButton("search"), NORTH);
        add(inorder);
        add(preorder);
        add(postorder);
		addActionListeners();
	}
	
	public void insert(int value){
	
		
		TreeNode tNode = new TreeNode(value,rootX,rootY,circleSize);
		node = new Node(tNode);
		node.insert(value);
		
		add(tNode.getCircle());
		add(tNode.getData());	
		
		if(first == false){
			double x1 = node.preX;
			double y1 = node.preY;
			double x2 = node.tempX;
			double y2 = node.tempY;
		
			if(x2 > x1) {
				double cx1 = x1 + circleSize/2;
				double cy1 = y1 + circleSize/2;
				double cx2 = x2 + circleSize/2;
				double cy2 = y2 + circleSize/2;
				
				double deg1 = -315 * (Math.PI/180);
				double deg2 = -135 * (Math.PI/180);
				
				int rad = circleSize/2;
				x1 = cx1 + (rad * Math.cos(deg1));
				y1 = cy1 + (rad * Math.sin(deg1));
				                       
				x2 = cx2 + (rad * Math.cos(deg2));
				y2 = cy2 + (rad * Math.sin(deg2));
				
			}
			else {
				double cx1 = x1 + circleSize/2;
				double cy1 = y1 + circleSize/2;
				double cx2 = x2 + circleSize/2;
				double cy2 = y2 + circleSize/2;
				
				double deg1 = -225 * ((Math.PI)/180);
				double deg2 = -45 * ((Math.PI)/180);
				
				int rad = circleSize/2;
				x1 = cx1 + (rad * Math.cos(deg1));
				y1 = cy1 + (rad * Math.sin(deg1));
				                       
				x2 = cx2 + (rad * Math.cos(deg2));
				y2 = cy2 + (rad * Math.sin(deg2));
			
			}
			line = new GLine(x1,y1,x2,y2);
			lines[lineCount++] = line;
			node.getLine(line);
			add(line);
		}
		else{
			first = false;
			root = node;
		}
		insertField.setText("");
	}
	
	public void clearScreen(Node n)
	{
		if(n == null) return;
		remove(n.tn.dataLabel);
		remove(n.tn.node);
		clearScreen(n.left);
		clearScreen(n.right);
	}
	
    public void display(Node root){
		if(root==null)
             return ;
		add(root.tn.dataLabel);
		add(root.tn.node);
		add(root.line);
		
			display(root.left);
			
			display(root.right);
		
    }
	
	public void removeLines(){
		for(int i=0;i<lineCount;i++)
		{
			remove(lines[i]);
		}
	}

	public void getInorder(Node root,Node nodes[]){
		if(root==null)
            return ;	        
		    getInorder(root.left,nodes);
			nodes[nodeCount++] = root; 
			getInorder(root.right,nodes);
	}
	
	public void getInorder2(Node root,Node nodes[]){
		if(root==null)
            return ;	        
		    getInorder2(root.left,nodes);
			nodes[eleCount++] = root; 
			getInorder2(root.right,nodes);
	}
	
	public void getPreorder(Node root,Node nodes[]){
		if(root==null)
            return ;	      
			nodes[eleCount++] = root; 
		    getPreorder(root.left,nodes);
			getPreorder(root.right,nodes);
	}
	public void getPostorder(Node root,Node nodes[]){
		if(root==null)
            return ;	        
		    getPostorder(root.left,nodes);		 
		    getPostorder(root.right,nodes);
			nodes[eleCount++] = root;
	}
	public void setLabels(){
		eleCount = 0;
		
		getInorder2(Node.root,inArray);
		eleCount = 0;
		getPreorder(Node.root,preArray);
		eleCount = 0;
		getPostorder(Node.root,postArray);
		String in = "INORDER : ";
		String pre = "PREORDER : ";
		String post = "POSTORDER : ";
		for(int i=0;i<eleCount;i++){
			in = in+" "+inArray[i].data;
			post = post+" "+postArray[i].data;
			pre = pre+" "+preArray[i].data;
		}
		inorder.setLabel(in);
		preorder.setLabel(pre);
		postorder.setLabel(post);
	}
	
	public void createThreadLine(int x1,int y1,int x2,int y2){
		GLine line = new GLine(x1,y1,x2,y2);
		line.setColor(Color.red);
		add(line);
		threadLines[threadLinesCount++] = line;
	}
	
	public void createThreads(){
		for(int i=0;i<threadLinesCount;i++){
			remove(threadLines[i]);
		}
		threadLinesCount = 0;
		nodeCount = 0;
		Node inorder[] = new Node[100];
		getInorder(Node.root, inorder);
		
		for(int i=0;i<nodeCount;i++){
			
			if(i!=0 && inorder[i].left == null){
				 
				int x1 = inorder[i].tn.x;
				int y1 = inorder[i].tn.y;
				int x2 = inorder[i-1].tn.x;
				int y2 = inorder[i-1].tn.y;
				createThreadLine(x1,y1+circleSize/2,x2+circleSize/2,y2+circleSize);
			}
			if(i!= nodeCount-1 && inorder[i].right == null){
				int x1 = inorder[i].tn.x;
				int y1 = inorder[i].tn.y;
				int x2 = inorder[i+1].tn.x;
				int y2 = inorder[i+1].tn.y;
				createThreadLine(x1+circleSize,y1+circleSize/2,x2+circleSize/2,y2+circleSize);
			}
		}
		
	}
	
	/** Listen for a button action */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		label.setLabel("");
		if(firstsearch == false){
			col.tn.node.setColor(Color.black);
		}
		if (cmd.equals("insert")) {
			if(Node.root == null) first = true;
			String str = insertField.getText();
			int value = Integer.parseInt(str);
			insert(value);
	//		createThreads();
			setLabels();
		}
		else if (cmd.equals("delete")) {
			String str = deleteField.getText();
			int data = Integer.parseInt(str);
			 
		    if(Node.root.find(data) == false)
		    {
		    	label.setLabel("element not found!!");
		    	add(label);
		    	
		    	return;
		    }
		    clearScreen(Node.root);
			   removeLines();
		    
		    Node.root.delete(data);
		    
		    Node.pocnt = 0;
		    Node.root.preorder(Node.root);
		    lineCount = 0;
		    first = true;
		    Node.root = null;
		   for(int i=0;i<Node.pocnt;i++)
		    {
		    	insert(Node.preorder[i]);
		    }
//		   createThreads();
		   setLabels();
		   deleteField.setText("");
		}
		else if (cmd.equals("search")) {
			String str = searchField.getText();
			int data = Integer.parseInt(str);
			
			if(Node.root.find(data) == false){ 
				label.setLabel("element not found");
				add(label);
				return;
			}
			
			col = Node.root.element;
			Node.root.element.tn.node.setColor(Color.BLUE);
			
			if(firstsearch)firstsearch = false;
			searchField.setText("");
		}
	}

	/* Private instance variables */
	Node col;
	boolean firstsearch = true;
	private IntField insertField;
	private IntField deleteField;
	private IntField searchField;
	public int rootX = 600;
	public int rootY = 20;
	public int width = 600;
	public int height = 80;
	private int circleSize = 40;
	public int preX;
	public int preY;
	GLabel label = new GLabel("",1000,30);
	boolean first = true;
	Node node;
	Node root;
	GLine line;
	int lineCount = 0;
    GLine lines[] = new GLine[100];
    int nodeCount = 0;
    GLine threadLines[] = new GLine[100];
    int threadLinesCount = 0;
    GLabel inorder = new GLabel("INORDER : ",100,500);
    GLabel preorder = new GLabel("PREORDER : ",100,550);
    GLabel postorder = new GLabel("POSTORDER : ",100,600);
    Node inArray[] = new Node[100];
    Node preArray[] = new Node[100];
    Node postArray[] = new Node[100];
    int eleCount = 0;
}

class TreeNode {

	TreeNode(int value, int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.value = value;
	}

	GOval getCircle() {
		node = new GOval(x, y, size, size);
		return node;
	}

	GLabel getData() {
		String str = value + "";
		dataLabel = new GLabel(str, x + (size / 2) - 5, y + (size / 2) + 5);
		return dataLabel;
	}

	GLabel dataLabel;
	public GOval node;
	public int value;
	public int x, y, size;

}



class Node{
	public static  Node root;
	GLine line = new GLine(100,100,100,100);
	int data;
	static int pocnt = 0;
	public Node left;
	public Node right;	
	TreeNode tn;
	int preX;
	int preY;
	int tempX;
	int tempY;
	static int preorder[] = new int[100];
	public Node(TreeNode tn){
		this.data = tn.value;
		left = null;
		right = null;
		this.tn = tn;
		
	}
	
	public void getLine(GLine line){
		this.line = line;
	}
	
	public void insert(int id){
		Node newNode = new Node(this.tn);
		 tempX = tn.x;
		 tempY = tn.y;
		int width = 600;
		int height = 80;
		preX = tempX;
		preY = tempY;
		if(root==null){
			root = newNode;
			return;
		}
		Node current = root;
		Node parent = null;
		while(true){
			parent = current;
			if(id<current.data){				
				current = current.left;
				if(current == null){
					width = width/2;
					preX = tempX;
					preY = tempY;
					tempX = tempX - width;
					tempY = tempY + height;
					parent.left = newNode;
					tn.x = tempX;
					tn.y = tempY;
					return;
				}
				width = width/2;
				preX = tempX;
				preY = tempY;
				tempX = tempX - width;
				tempY = tempY + height;
				
			}else {
			  
				current = current.right;
				if(current == null){
					parent.right = newNode;
					width = width/2;
					preX = tempX;
					preY = tempY;
					tempX = tempX + width;
					tempY = tempY + height;
					tn.x = tempX;
					tn.y = tempY;
					return;
				}
				width = width/2;
				preX = tempX;
				preY = tempY;
				tempX = tempX + width;
				tempY = tempY + height;
				
			}
			tn.x = tempX;
			tn.y = tempY;
		}
		
	}
	
	public void display(Node root){
		if(root!=null){
			display(root.left);
			System.out.print(" " + root.data);
			display(root.right);
		}
	}
	
	public void preorder(Node root){
		if(root == null) return;
			preorder[pocnt++] = root.data;
		preorder(root.left);
		preorder(root.right);
		
	}
	
	public boolean delete(int id){
		Node parent = root;
		Node current = root;
		boolean isLeftChild = false;
		while(current.data!=id){
			parent = current;
			if(current.data>id){
				isLeftChild = true;
				current = current.left;
			}else{
				isLeftChild = false;
				current = current.right;
			}
			if(current ==null){
				return false;
			}
		}
		//if i am here that means we have found the node
		//Case 1: if node to be deleted has no children
		if(current.left==null && current.right==null){
			if(current==root ){
				root = null;
			}
			if(isLeftChild ==true){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
		//Case 2 : if node to be deleted has only one child
		else if(current.right==null){
			if(current==root){
				root = current.left;
			}else if(isLeftChild){
				parent.left = current.left;
			}else{
				parent.right = current.left;
			}
		}
		else if(current.left==null){
			if(current==root){
				root = current.right;
			}else if(isLeftChild){
				parent.left = current.right;
			}else{
				parent.right = current.right;
			}
		}else if(current.left!=null && current.right!=null){
			
			//now we have found the minimum element in the right sub tree
			Node successor	 = getSuccessor(current);
			if(current==root){
				root = successor;
			}else if(isLeftChild){
				parent.left = successor;
			}else{
				parent.right = successor;
			}			
			successor.left = current.left;
		}		
		return true;		
	}
	
	public Node getSuccessor(Node deleleNode){
		Node successsor =null;
		Node successsorParent =null;
		Node current = deleleNode.right;
		while(current!=null){
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		//check if successor has the right child, it cannot have left child for sure
		// if it does have the right child, add it to the left of successorParent.
//		successsorParent
		if(successsor!=deleleNode.right){
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
		}
		return successsor;
	}
	
	public boolean find(int id){
		Node current = root;
		Node temp = null;
		while(current!=null){
			try{
				if(temp!=null){
					temp.tn.node.setColor(Color.BLACK);
				}
				
				current.tn.node.setColor(Color.BLUE);
				
			}catch(Exception e){}
			temp = current;
			if(current.data==id){
				element = current;
				return true;
			}else if(current.data>id){
				current = current.left;
			}else{
				current = current.right;
			}
			
		}
		return false;
	}
	Node element;

}
