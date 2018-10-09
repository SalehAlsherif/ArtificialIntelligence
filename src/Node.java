public class Node {
	State state;
	Node parentNode;
	String operator;// Operator Applied to generate this Node
	int depth;
	int pathCost;// The cost from the root
	int heuristic;
	public Node(State state,Node parentNode,String operator,int depth,int pathCost){
		this.state=state;
		this.parentNode=parentNode;
		this.operator=operator;
		this.depth=depth;
		this.pathCost=pathCost;
	}
	
}
