import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SearchProblem {

	static char[][] grid;/*
						 * 
						 * Free cells are '.' Jon Snow is of value 'J' White
						 * Walker is of value 'W' Dragon Stone is of value 'D'
						 * Obstacle is of value 'X'
						 */
	static int numberOfDragonGlassPieces = 0;// Lesa msh 3aref eh da !!

	static ArrayList<State> stateSpace = new ArrayList<State>();
	static String[] operators = new String[5];
	static int jonColumn;
	static int jonRow;
	static State goalTest;
	static State initialState;

	static Queue<Node> Q= new LinkedList<Node>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenGrid(1, 3, 5, 10);
		// Set of Actions
		operators[0] = "North";

		operators[1] = "South";

		operators[2] = "East";

		operators[3] = "West";
		
		operators[4] = "Kill";
		//Initial State Creation
		initialState = new State(grid);
		initialState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		initialState.JonC=jonColumn;
		initialState.JonR=jonRow;

		// goal test is when all w's disappear		
		State clonedState = initialState.clone();		
		for (int i = 0; i < clonedState.grid.length; i++) {
			for (int j = 0; j < clonedState.grid[i].length; j++) {
				if (clonedState.grid[i][j] == 'W') {
					clonedState.grid[i][j] = '.';
				}
			}
		}
		goalTest = clonedState;

		// Creating a Problem instant
		Problem p = new Problem(operators, initialState, goalTest);
		
		PrintWriter pw = new PrintWriter(System.out);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				pw.print(grid[i][j] + " ");
			}
			pw.println("");
		}
		System.out.println(GENERAL_SEARCH(p));

		pw.flush();
		pw.close();

	}

	public static void GenGrid(int maxLimitGrid, int maxLimitWhiteWalkers,
			int maxLimitDragonStone, int maxLimitObstacle) {
		int sizeC = 4 + (int) (Math.random() * maxLimitGrid);// size of columns
		int sizeR = 4 + (int) (Math.random() * maxLimitGrid);// size of rows
		grid = new char[sizeR][sizeC];
		// initially everything is empty i.e. '.'
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = '.';
			}
		}
		// Jon Snow initial Position
		jonColumn = sizeC - 1;
		jonRow = sizeR - 1;
		grid[jonRow][jonColumn] = 'J';

		// Placement of White Walkers
		int whiteWalkers = 1 + (int) (Math.random() * maxLimitWhiteWalkers);// minimum
																			// 1
		for (int i = 0; i < whiteWalkers; i++) {
			int randomColumn = (int) (Math.random() * sizeC);
			int randomRow = (int) (Math.random() * sizeR);
			if (grid[randomRow][randomColumn] == '.') {
				grid[randomRow][randomColumn] = 'W';
			} else {
				i--;
			}
		}
		// Placement of Dragon Stones
		int dragonStones = 1 + (int) (Math.random() * maxLimitDragonStone);// minimum
																			// 1
		for (int i = 0; i < dragonStones; i++) {
			int randomColumn = (int) (Math.random() * sizeC);
			int randomRow = (int) (Math.random() * sizeR);
			if (grid[randomRow][randomColumn] == '.') {
				grid[randomRow][randomColumn] = 'D';
			} else {
				i--;
			}
		}
		// Placement of Obstacles
		int obstacles = 1 + (int) (Math.random() * maxLimitObstacle);// minimum
																		// 1
		for (int i = 0; i < obstacles; i++) {
			int randomColumn = (int) (Math.random() * sizeC);
			int randomRow = (int) (Math.random() * sizeR);
			if (grid[randomRow][randomColumn] == '.') {
				grid[randomRow][randomColumn] = 'O';
			} else {
				i--;
			}
		}

	}

	public static Node Make_Node(State s, Node parent, String operator,
			int depth, int pathCost) {
		
		return new Node(s, parent, operator, depth, pathCost);
	}


	public static Node GENERAL_SEARCH(Problem p) {
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		while (!Q.isEmpty()) {
			Node First = Q.remove();
			if (p.isGoalTest(First.state)) {
				return First;
			}
			ArrayList<Node> expansion=p.Expand(First);			
			Q.addAll(expansion);
		}
		return null;// Failure
	}

}
