import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchProblem {

	static char[][] grid;/*
						 * 
						 * Free cells are '.' Jon Snow is of value 'J' White
						 * Walker is of value 'W' Dragon Stone is of value 'D'
						 * Obstacle is of value 'X'
						 */
	static int numberOfDragonGlassPieces = 0;// Lesa msh 3aref eh da !!

	enum searchType {
		DF, BF, UC, ID, GR1, GR2, AS1, AS2
	};

	static ArrayList<State> stateSpace = new ArrayList<State>();
	static String[] operators = new String[5];
	static int jonColumn;
	static int jonRow;
	static State goalTest;
	static State initialState;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenGrid(0, 8, 2, 3);
		// Set of Actions
		operators[0] = "North";

		operators[1] = "South";

		operators[2] = "East";

		operators[3] = "West";

		operators[4] = "Kill";
		// Initial State Creation
		initialState = new State(BitFieldFromgrid(grid));
		initialState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		initialState.JonC = jonColumn;
		initialState.JonR = jonRow;
		initialState.row = grid.length;
		initialState.column = grid[0].length;
		initialState.numberOfDragonGlassPieces = 0;
		// goal test is when all w's disappear
		State clonedState = initialState.clone();
		char[][] clonedgrid = gridFromBitField(grid.length, grid[0].length,
				clonedState.grid);
		for (int i = 0; i < clonedgrid.length; i++) {
			for (int j = 0; j < clonedgrid[0].length; j++) {
				if (clonedgrid[i][j] == 'W') {
					clonedgrid[i][j] = '.';
				}
			}
		}
		clonedState.grid = BitFieldFromgrid(clonedgrid);
		goalTest = clonedState;
		// Encoding the state into the BitField .
		gridFromBitField(grid.length, grid[0].length, BitFieldFromgrid(grid));

		// Creating a Problem instant
		//for (int i = 0; i<=47; i++)
		//	System.out.println(""+i+initialState.grid.get(i));
		Problem p = new Problem(operators, initialState, goalTest);
		
		String gridString = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				gridString += grid[i][j] + " ";
			}
			gridString += "\n";
		}
		System.out.println(gridString);

		visualize(GENERAL_SEARCH(p, searchType.BF));

	}
	// I'm going to assume a bitFeild that better represent the grid the size
			// of the set is 3*sizeR*sizeC
			// I will consider encoding for the empty cell as 000
			// jon snow as 001
			// D as 010
			// W as 011
			// O as 100
	public static BitField BitFieldFromgrid(char[][] targetGrid) {
		BitField b = new BitField(2);
		for (int i = 0; i < targetGrid.length; i++) {
			for (int j = 0; j < targetGrid[i].length; j++) {
				if (targetGrid[i][j] == '.') {
					b.clear(i * 3 * targetGrid.length + j * 3);
					b.clear(i * 3 * targetGrid.length + j * 3 + 1);
					b.clear(i * 3 * targetGrid.length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'J') {
					b.clear(i * 3 * targetGrid.length + j * 3);
					b.clear(i * 3 * targetGrid.length + j * 3 + 1);
					b.set(i * 3 * targetGrid.length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'D') {
					b.clear(i * 3 * targetGrid.length + j * 3);
					b.set(i * 3 * targetGrid.length + j * 3 + 1);
					b.clear(i * 3 * targetGrid.length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'W') {
					b.clear(i * 3 * targetGrid.length * 3 + j);
					b.set(i * 3 * targetGrid.length + j * 3 + 1);
					b.set(i * 3 * targetGrid.length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'O') {
					b.set(i * 3 * targetGrid.length + j * 3);
					b.clear(i * 3 * targetGrid.length + j * 3 + 1);
					b.clear(i * 3 * targetGrid.length + j * 3 + 2);

				}
			}
		}
		return b;
	}

	public static char[][] gridFromBitField(int R, int C, BitField b) {
		char[][] newgrid = new char[R][C];
		int size = newgrid.length * newgrid[0].length * 3;
		for (int i = 0; i < size; i += 3) {
			if (!b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / R][(i / 3) % C] = '.';
			} else if (!b.get(i) && !b.get(i + 1) && b.get(i + 2)) {
				newgrid[(i / 3) / R][(i / 3) % C] = 'J';
			} else if (!b.get(i) && b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / R][(i / 3) % C] = 'D';
			} else if (!b.get(i) && b.get(i + 1) && b.get(i + 2)) {
				newgrid[(i / 3) / R][(i / 3) % C] = 'W';
			} else if (b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / R][(i / 3) % C] = 'O';
			} else
				newgrid[(i / 3) / R][(i / 3) % C] = 'X';
		}
		return newgrid;
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
		// position in bit set is
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
	public static void visualize (Node n){
		String res="";
		res+=" "+n.operator;
		Node p=n.parentNode;
		while(p!=null){
			res+=" "+p.operator;
			p=p.parentNode;
		}
		System.out.println(res);
		
	}

	public static Node Make_Node(State s, Node parent, String operator,
			int depth, int pathCost) {

		return new Node(s, parent, operator, depth, pathCost);
	}

	public static Node BF(Problem p) {
		Queue<Node> Q = (Queue<Node>) new LinkedList<Node>();
		Q.add(Make_Node(p.initialState, null, null, 0, 0));

		int count = 0;
		while (!((Queue<Node>) Q).isEmpty()) {;

			Node First = Q.remove();
			if (p.isGoalTest(First.state)) {
				System.out.println("Sucess State");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int k = 0; k < 4; k++) {
						gridString += gridFromBitField(4, 4, First.state.grid)[i][k] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			
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

	public static Node DF(Problem p) {

		Stack<Node> S = new Stack<Node>();
		S.push(Make_Node(p.initialState, null, null, 0, 0));
		while (!S.isEmpty()) {
			Node First = S.pop();
			if (p.isGoalTest(First.state)) {
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			for (int i = 0; i < expansion.size(); i++)
				S.push(expansion.get(i));
		}
		return null;// Failure
	}

	public static Node UC(Problem p) {
		return null;
	}

	public static Node ID(Problem p) {
		return null;
	}

	public static Node GR1(Problem p) {
		return null;
	}

	public static Node GR2(Problem p) {
		return null;
	}

	public static Node AS1(Problem p) {
		return null;
	}

	public static Node AS2(Problem p) {
		return null;
	}

	public static Node GENERAL_SEARCH(Problem p, searchType t) {
		Node result = null;
		switch (t) {
		case BF:
			result = BF(p);
			break;
		case DF:
			result = DF(p);
			break;
		case UC:
			result = UC(p);
			break;
		case ID:
			result = ID(p);
			break;
		case GR1:
			result = GR1(p);
			break;
		case GR2:
			result = GR2(p);
			break;
		case AS1:
			result = AS1(p);
			break;
		case AS2:
			result = AS2(p);
			break;
		}
		return result;// Failure

	}

}
