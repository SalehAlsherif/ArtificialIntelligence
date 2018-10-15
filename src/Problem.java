import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

enum searchType {
	DF, BF, UC, ID, GR1, GR2, AS1, AS2
};

public abstract class Problem {
	String[] operators;
	State initialState;

	public Problem(String[] operators, State initialState) {
		this.operators = operators;
		this.initialState = initialState;
	}

	public static int pathCost(String actions) {
		// a function that assigns cost to a sequence of actions. Typically, it
		// is the sum of the costs of individual actions in the sequence.

		return 0;
	}

	public static Node Make_Node(State initialState, Node parent,
			String operator, int depth, int pathCost) {

		return new Node(initialState, parent, operator, depth, pathCost);
	}

	public static Node general_search(Problem p, searchType s) {
		for (int depth = 0; depth < 15; depth++) {
			LinkedList<Node> Q = new LinkedList<Node>();
			Q.add(Make_Node(p.initialState, null, null, 0, 0));
			switch (s) {
			case AS1:
				Q.getFirst().heuristic = p.heuristicCost1(Q.getFirst())
						+ Q.getFirst().pathCost;
				break;
			case AS2:
				Q.getFirst().heuristic = p.heuristicCost2(Q.getFirst())
						+ Q.getFirst().pathCost;
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
			while (!Q.isEmpty()) {
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

					Q.sort(new Comparator<Node>() {
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
				// Removing Repeated States
				LinkedList<Node> newExpansion = new LinkedList<Node>();
				for (int j = 0; j < expansion.size(); j++) {
					newExpansion.add(expansion.get(j));
					for (int k = 0; k < Q.size(); k++)
						if ((expansion.get(j).state).sameState(Q.get(k).state)) {
							newExpansion.removeLast();
							break;
						}
				}
				for (int i = 0; i < newExpansion.size(); i++) {

					switch (s) {
					case AS1:
						newExpansion.get(i).heuristic = p
								.heuristicCost1(newExpansion.get(i))
								+ newExpansion.get(i).pathCost;
						break;
					case AS2:
						newExpansion.get(i).heuristic = p
								.heuristicCost2(newExpansion.get(i))
								+ newExpansion.get(i).pathCost;
						break;
					case GR1:
						newExpansion.get(i).heuristic = p
								.heuristicCost1(newExpansion.get(i));
						break;
					case GR2:
						newExpansion.get(i).heuristic = p
								.heuristicCost2(newExpansion.get(i));
						break;
					default:
						break;
					}

					if (s.equals(searchType.ID)) {
						if (newExpansion.get(i).depth <= depth) {
							Q.add(newExpansion.get(i));
						}
					} else {
						Q.add(newExpansion.get(i));
					}

				}

			}
		}
		return null;// Failure
	}

	public abstract ArrayList<Node> Expand(Node n);

	public abstract int heuristicCost1(Node n);

	public abstract int heuristicCost2(Node n);

	public abstract boolean goalTest(State s);

}
