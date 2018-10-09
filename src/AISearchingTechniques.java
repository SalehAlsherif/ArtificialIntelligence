import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class AISearchingTechniques {

	static char[][] grid;/*
						 * 
						 * Free cells are '.' Jon Snow is of value 'J' White
						 * Walker is of value 'W' Dragon Stone is of value 'D'
						 * Obstacle is of value 'X'
						 */
	static int jonColumn;
	static int jonRow;

	enum searchType {
		DF, BF, UC, ID, GR1, GR2, AS1, AS2
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Creating a SaveWestros instance
		SaveWestros sv = new SaveWestros();

		// This is for visualization
		jonColumn = sv.jonColumn;
		jonRow = sv.jonRow;
		grid = sv.grid;

		// Informed
		// search((sv), searchType.BF, false);
		// search((sv), searchType.DF, false);
		// search((sv), searchType.UC, false);
		// search((sv), searchType.ID, false);
		// I made the Maximum depth as 15 as
		// it's longest plan action I can
		// imagine
		// Uninformed
		// search((sv), searchType.GR1, false);
		// search((sv), searchType.GR2, false);
		search((sv), searchType.BF, false);
		search((sv), searchType.AS1, false);
		search((sv), searchType.UC, false);
		search((sv), searchType.ID, false);
	//	search((sv), searchType.AS2, false);

	}

	public static Node Make_Node(State initialState, Node parent,
			String operator, int depth, int pathCost) {

		return new Node(initialState, parent, operator, depth, pathCost);
	}

	public static Node BF(Problem p) {
		Queue<Node> Q = (Queue<Node>) new LinkedList<Node>();
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			;
			Node First = Q.remove();
			if (p.goalTest(First.state)) {
				System.out.println("Number Of Expanded Nodes in BF: "
						+ numberOfExpandedNodes);
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;
			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static Node general_search(Problem p, searchType s) {
		for (int depth = 0; depth < 15; depth++) {
			LinkedList<Node> Q = new LinkedList<Node>();
			Q.add(Make_Node(p.initialState, null, null, 0, 0));
			switch (s) {
			case AS1:
				Q.getFirst().heuristic = p.heuristicCost1(Q.getFirst()) + Q.getFirst().pathCost;
				break;
			case AS2:
				Q.getFirst().heuristic = p.heuristicCost2(Q.getFirst()) + Q.getFirst().pathCost;
				break;
			case GR1:
				Q.getFirst().heuristic = p.heuristicCost1(Q.getFirst());
				break;
			case GR2:
				Q.getFirst().heuristic = p.heuristicCost2(Q.getFirst());
				break;
			default:
				break;
			}
			int numberOfExpandedNodes = 0;
			while (!((Queue<Node>) Q).isEmpty()) {
				Node First = null;
				switch (s) {
				case BF:
					First = Q.removeFirst();
					break;
				case AS1:		
					Q.sort(new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.heuristic - n2.heuristic;
						}
					});
					
//					System.out.println(Q.getFirst().heuristic);	
//					System.out.println(Q.getLast().heuristic+" Lasy");
					First = Q.removeFirst();
					break;
				case AS2:
					Q.sort(new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.heuristic - n2.heuristic;
						}
					});
					First = Q.removeFirst();
					break;
				case DF:
					First = Q.removeLast();
					break;
				case GR1:
					
					Q.sort( new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.heuristic - n2.heuristic;
						}
					});
					First = Q.removeFirst();
					break;
				case GR2:
					Q.sort(new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.heuristic - n2.heuristic;
						}
					});
					First = Q.removeFirst();
					break;
				case ID:
					First = Q.removeLast();
					break;
				case UC:
					Q.sort(new Comparator<Node>() {
						@Override
						public int compare(Node n1, Node n2) {
							return n1.pathCost - n2.pathCost;
						}
					});
					First = Q.removeFirst();
					break;
				}
				if (p.goalTest(First.state)) {
					switch (s) {
					case BF:
						System.out.println("Number Of Expanded Nodes in BF: "
								+ numberOfExpandedNodes);
						break;
					case AS1:
						System.out.println("Number Of Expanded Nodes in AS1: "
								+ numberOfExpandedNodes);
						break;
					case AS2:
						System.out.println("Number Of Expanded Nodes in AS2: "
								+ numberOfExpandedNodes);
						break;
					case DF:
						System.out.println("Number Of Expanded Nodes in DF: "
								+ numberOfExpandedNodes);
						First = Q.removeLast();
						break;
					case GR1:
						System.out.println("Number Of Expanded Nodes in GR1: "
								+ numberOfExpandedNodes);
						break;
					case GR2:
						System.out.println("Number Of Expanded Nodes in GR2: "
								+ numberOfExpandedNodes);
						break;
					case ID:
						System.out.println("Number Of Expanded Nodes in ID: "
								+ numberOfExpandedNodes);
						break;
					case UC:
						System.out.println("Number Of Expanded Nodes in UC: "
								+ numberOfExpandedNodes);
						break;
					}
					return First;
				}
				ArrayList<Node> expansion = p.Expand(First);
				numberOfExpandedNodes++;

				if (expansion.isEmpty()) {
					System.out.println("No availble moves!");
					return First;
				}
				for (int i = 0; i < expansion.size(); i++) {

					switch (s) {
					case AS1:
						expansion.get(i).heuristic = p.heuristicCost1(expansion
								.get(i)) + expansion.get(i).pathCost;
						break;
					case AS2:
						expansion.get(i).heuristic = p.heuristicCost2(expansion
								.get(i)) + expansion.get(i).pathCost;
						break;
					case GR1:
						expansion.get(i).heuristic = p.heuristicCost1(expansion
								.get(i));
						break;
					case GR2:
						expansion.get(i).heuristic = p.heuristicCost2(expansion
								.get(i));
						break;
					default:
						break;
					}
					if (s.equals(searchType.ID)) {
						if (expansion.get(i).depth <= depth)
							Q.add(expansion.get(i));
					} else {
						Q.add(expansion.get(i));
					}

				}

			}
		}
		return null;// Failure
	}
	
	public static Node DF(Problem p) {

		Stack<Node> S = new Stack<Node>();
		S.push(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!S.isEmpty()) {
			Node First = S.pop();
			if (p.goalTest(First.state)) {

				System.out.println("Number Of Expanded Nodes in DF: "
						+ numberOfExpandedNodes);

				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				S.add(expansion.get(i));
			}
		}
		return null;// Failure
	}

	public static Node UC(Problem p) {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.pathCost - n2.pathCost;
			}
		};
		PriorityQueue<Node> Q = new PriorityQueue<>(comparator);
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			;
			Node First = Q.remove();
			if (p.goalTest(First.state)) {

				System.out.println("Number Of Expanded Nodes in UC : "
						+ numberOfExpandedNodes);
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static Node ID(Problem p) {
		for (int depth = 0; depth < 15; depth++) {
			Stack<Node> S = new Stack<Node>();
			S.push(Make_Node(p.initialState, null, null, 0, 0));
			int numberOfExpandedNodes = 0;

			while (!S.isEmpty()) {
				Node First = S.pop();
				if (p.goalTest(First.state)) {

					System.out.println("Number Of Expanded Nodes in ID : "
							+ numberOfExpandedNodes);

					return First;
				}
				ArrayList<Node> expansion = p.Expand(First);
				numberOfExpandedNodes++;

				if (expansion.isEmpty()) {
					System.out.println("No availble moves!");
					return First;
				}
				for (int i = 0; i < expansion.size(); i++) {
					if (expansion.get(i).depth <= depth)
						S.add(expansion.get(i));
				}
			}
		}
		return null;// Failure
	}

	public static Node GR1(Problem p) {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.heuristic - n2.heuristic;
			}
		};
		PriorityQueue<Node> Q = new PriorityQueue<>(comparator);
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			;
			Node First = Q.remove();
			if (p.goalTest(First.state)) {

				System.out.println("Number Of Expanded Nodes in GR1: "
						+ numberOfExpandedNodes);

				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				expansion.get(i).heuristic = p.heuristicCost1(expansion.get(i));
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static Node GR2(Problem p) {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.heuristic - n2.heuristic;
			}
		};
		PriorityQueue<Node> Q = new PriorityQueue<>(comparator);
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			;
			Node First = Q.remove();
			if (p.goalTest(First.state)) {

				System.out.println("Number Of Expanded Nodes in GR2: "
						+ numberOfExpandedNodes);
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				expansion.get(i).heuristic = p.heuristicCost2(expansion.get(i));
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static Node AS1(Problem p) {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.heuristic - n2.heuristic;
			}
		};
		PriorityQueue<Node> Q = new PriorityQueue<>(comparator);
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			Node First = Q.remove();
//			System.out.println(First.operator);
//			char[][] c=p.gridFromBitField(First.state.row,First.state.column,First.state.grid);
//			for(int i=0;i<c.length;i++){
//				for(int j=0;j<c[i].length;j++)
//								System.out.print(c[i][j]);
//			System.out.println();
//			}
//			System.out.println();System.out.println();
			if (p.goalTest(First.state)) {
				System.out.println("Number Of Expanded Nodes in AS1 : "
						+ numberOfExpandedNodes);
				System.out.println(First.operator);
				return First;
				
			}

			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				expansion.get(i).heuristic = p.heuristicCost1(expansion.get(i))	+ expansion.get(i).pathCost;
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static Node AS2(Problem p) {
		Comparator<Node> comparator = new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.heuristic - n2.heuristic;
			}
		};
		PriorityQueue<Node> Q = new PriorityQueue<>(comparator);
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		int numberOfExpandedNodes = 0;

		while (!((Queue<Node>) Q).isEmpty()) {
			;
			Node First = Q.remove();
			if (p.goalTest(First.state)) {

				System.out.println("Number Of Expanded Nodes in AS2: "
						+ numberOfExpandedNodes);

				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			numberOfExpandedNodes++;

			if (expansion.isEmpty()) {
				System.out.println("No availble moves!");
				return First;
			}

			for (int i = 0; i < expansion.size(); i++) {
				expansion.get(i).heuristic = p.heuristicCost2(expansion.get(i))
						+ expansion.get(i).pathCost;
				Q.add(expansion.get(i));
			}

		}
		return null;// Failure
	}

	public static void search(Problem p, searchType s, boolean visualize) {
		 Node n = null;
		 switch(s){
		case AS1:
			n = AS1(p);
			break;
		case AS2:
			n = AS2(p);
			break;
		case BF:
			n = BF(p);
			break;
		case DF:
			n = DF(p);
			break;
		case GR1:
			n = GR1(p);
			break;
		case GR2:
			n = GR2(p);
			break;
		case ID:
			n = ID(p);
			break;
		case UC:
			n = UC(p);
			break;
		default:
			break;
		 
		 }
//		Node 
		System.out.println("Path Cost: " + n.pathCost);
		System.out.print("Action Plan: ");
		String res = "";
		res = n.operator + " " + res;
		Node pa = n.parentNode;
		while (pa != null && pa.operator != null) {
			res = pa.operator + " " + res;
			pa = pa.parentNode;
		}
		System.out.println(res);
		StringTokenizer st = new StringTokenizer(res);
		char[][] g = new char[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				g[i][j] = grid[i][j];
			}
		}
		if (visualize) {
			while (st.hasMoreTokens()) {
				String q = st.nextToken();
				System.out.println(q);
				switch (q) {
				case "North":
					if (grid[jonRow][jonColumn] != 'D')
						g[jonRow][jonColumn] = '.';
					else
						g[jonRow][jonColumn] = 'D';
					jonRow--;
					g[jonRow][jonColumn] = 'J';
					for (int i = 0; i < g.length; i++) {
						for (int j = 0; j < g[0].length; j++) {
							System.out.print(g[i][j] + " ");
						}
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");
					break;
				case "South":
					if (grid[jonRow][jonColumn] != 'D')
						g[jonRow][jonColumn] = '.';
					else
						g[jonRow][jonColumn] = 'D';
					jonRow++;
					g[jonRow][jonColumn] = 'J';
					for (int i = 0; i < g.length; i++) {
						for (int j = 0; j < g[0].length; j++) {
							System.out.print(g[i][j] + " ");
						}
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");
					break;
				case "East":
					if (grid[jonRow][jonColumn] != 'D')
						g[jonRow][jonColumn] = '.';
					else
						g[jonRow][jonColumn] = 'D';
					jonColumn++;
					g[jonRow][jonColumn] = 'J';
					for (int i = 0; i < g.length; i++) {
						for (int j = 0; j < g[0].length; j++) {
							System.out.print(g[i][j] + " ");
						}
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");
					break;
				case "West":
					if (grid[jonRow][jonColumn] != 'D')
						g[jonRow][jonColumn] = '.';
					else
						g[jonRow][jonColumn] = 'D';
					jonColumn--;
					g[jonRow][jonColumn] = 'J';
					for (int i = 0; i < g.length; i++) {
						for (int j = 0; j < g[0].length; j++) {
							System.out.print(g[i][j] + " ");
						}
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");

					break;
				case "Kill":
					if (jonColumn + 1 < grid[0].length
							&& g[jonRow][jonColumn + 1] == 'W')
						g[jonRow][jonColumn + 1] = '.';
					if (jonColumn - 1 >= 0 && g[jonRow][jonColumn - 1] == 'W')
						g[jonRow][jonColumn - 1] = '.';
					if (jonRow + 1 < grid.length
							&& g[jonRow + 1][jonColumn] == 'W')
						g[jonRow + 1][jonColumn] = '.';
					if (jonRow - 1 >= 0 && g[jonRow - 1][jonColumn] == 'W')
						g[jonRow - 1][jonColumn] = '.';
					for (int i = 0; i < g.length; i++) {
						for (int j = 0; j < g[0].length; j++) {
							System.out.print(g[i][j] + " ");
						}
						System.out.println("");
					}
					System.out.println("");
					System.out.println("");
					break;
				}
			}
		}
	}

}
