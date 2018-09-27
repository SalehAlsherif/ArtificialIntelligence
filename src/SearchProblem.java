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
		GenGrid(0, 0, 2, 12);
//		grid=new char[2][2];
//		numberOfDragonGlassPieces=1;
//		jonRow=1;
//		jonColumn=1;
//		grid[1][1]='J';
//		grid[0][0]='W';
//		grid[1][0]='.';
//		
//		grid[0][1]='.';
//		grid[1][0]='.';
//		grid[1][1]='.';
//		grid[1][2]='.';
//		
//		grid[2][1]='.';
//		grid[1][3]='.';
//		grid[3][1]='.';
//		grid[2][3]='.';
//		grid[2][2]='.';
//		grid[3][0]='.';
//		grid[2][0]='.';
//		grid[0][3]='.';
//		grid[0][2]='.';
//		
		
		// Set of Actions
		operators[0] = "North";

		operators[1] = "South";

		operators[2] = "East";

		operators[3] = "West";

		operators[4] = "Kill";
		// Initial State Creation
		initialState = new State(grid);
		initialState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		initialState.JonC = jonColumn;
		initialState.JonR = jonRow;

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
		//Encoding the state into the BitField .
		gridFromBitSet(grid.length,grid[0].length,BitSetFromgrid(grid));

		// Creating a Problem instant
		Problem p = new Problem(operators, initialState, goalTest);

		PrintWriter pw = new PrintWriter(System.out);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				pw.print(grid[i][j] + " ");
			}
			pw.println("");
		}
		
		// System.out.println(GENERAL_SEARCH(p, searchType.BF).operator);

		pw.flush();
		pw.close();

	}
	public static BitField BitSetFromgrid(char[][] targetGrid) {
		BitField b=new BitField(3*targetGrid.length*targetGrid[0].length);
		for (int i = 0; i < targetGrid.length; i++) {
			for (int j = 0; j < targetGrid[i].length; j++) {
				if(targetGrid[i][j]=='.'){
					b.clear(i*3*targetGrid.length+j*3);
					b.clear(i*3*targetGrid.length+j*3+1);
					b.clear(i*3*targetGrid.length+j*3+2);
				}
				if(targetGrid[i][j]=='J'){
					b.clear(i*3*targetGrid.length+j*3);
					b.clear(i*3*targetGrid.length+j*3+1);
					b.set(i*3*targetGrid.length+j*3+2);					
				}
				if(targetGrid[i][j]=='D'){
					b.clear(i*3*targetGrid.length+j*3);
					b.set(i*3*targetGrid.length+j*3+1);
					b.clear(i*3*targetGrid.length+j*3+2);
				}
				if(targetGrid[i][j]=='W'){
					b.clear(i*3*targetGrid.length*3+j);
					b.set(i*3*targetGrid.length+j*3+1);
					b.set(i*3*targetGrid.length+j*3+2);
				}
				if(targetGrid[i][j]=='O'){
					b.set(i*3*targetGrid.length+j*3);
					b.clear(i*3*targetGrid.length+j*3+1);
					b.clear(i*3*targetGrid.length+j*3+2);
		
				}
//				System.out.print(b.get(i*3*targetGrid.length+j*3)+" "+(i*3*targetGrid.length+j*3));
//				System.out.print(b.get(i*3*targetGrid.length+j*3+1)+" "+(i*3*targetGrid.length+j*3+1));
//				System.out.print(b.get(i*3*targetGrid.length+j*3+2)+" "+(i*3*targetGrid.length+j*3+2));
//				System.out.println("");
			}			
		}
//		System.out.println("Bit Field after Loop");
//		for(int i=0;i<3*targetGrid.length*targetGrid[0].length;i++){
//			System.out.print(b.get(i)+" "+i);
//			if(i%3==2)
//				System.out.println("");
//		}
//		System.out.println("the Gird");
//		for (int i = 0; i < targetGrid.length; i++) {
//			for (int j = 0; j < targetGrid[i].length; j++) {
//				System.out.print(targetGrid[i][j] + " ");
//			}
//			System.out.println("");
//		}
		return b;	
	}
	
	public static char[][] gridFromBitSet(int R, int C, BitField b) {
		char[][] newgrid=new char[R][C];
		int size=newgrid.length*newgrid[0].length*3;
		for (int i = 0; i < size; i += 3) {
			if (!b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i/3)/R][(i/3)%C]='.';
			}else
			if (!b.get(i) && !b.get(i + 1) && b.get(i + 2)) {
				newgrid[(i/3)/R][(i/3)%C]='J';
			}else
			if (!b.get(i) && b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i/3)/R][(i/3)%C]='D';
			}else
			if (!b.get(i) && b.get(i + 1) && b.get(i + 2)) {
				newgrid[(i/3)/R][(i/3)%C]='W';
			}else
			if (b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i/3)/R][(i/3)%C]='O';
			}else
				newgrid[(i/3)/R][(i/3)%C]='X';
		}
//		System.out.println("recieved bitField");
//		for(int i=0;i<3*newgrid.length*newgrid[0].length;i++){
//			System.out.print(b.get(i)+" ");
//			if(i%3==2)
//				System.out.println("");
//		}
//
//		
//		System.out.println("");
//		for (int i = 0; i < newgrid.length; i++) {
//			for (int j = 0; j < newgrid[i].length; j++) {
//				System.out.print(newgrid[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		System.out.println("");
		return newgrid;
	}

	public static void GenGrid(int maxLimitGrid, int maxLimitWhiteWalkers,
			int maxLimitDragonStone, int maxLimitObstacle) {
		int sizeC = 4 + (int) (Math.random() * maxLimitGrid);// size of columns
		int sizeR = 4 + (int) (Math.random() * maxLimitGrid);// size of rows
		// I'm going to assume a bitSet that better represent the grid the size
		// of the set is 3*sizeR*sizeC
		BitSet b = new BitSet(3 * sizeC * sizeR);
		// I will consider encoding for the empty cell as 000
		// jon snow as 001
		// D as 010
		// W as 011
		// O as 100

		grid = new char[sizeR][sizeC];
		// initially everything is empty i.e. '.'
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = '.';
			}
		}
		// loop over BitSet
		// bit set is all 0's

		// Jon Snow initial Position
		jonColumn = sizeC - 1;
		jonRow = sizeR - 1;
		// position in bit set is
		b.set(b.size() - 1);
		grid[jonRow][jonColumn] = 'J';

		// Placement of White Walkers
		int whiteWalkers = 1 + (int) (Math.random() * maxLimitWhiteWalkers);// minimum
																			// 1
		for (int i = 0; i < whiteWalkers; i++) {
			int randomColumn = (int) (Math.random() * sizeC);
			int randomRow = (int) (Math.random() * sizeR);
			// if (!b.get(randomRow * randomColumn)
			// && !b.get(randomRow * randomColumn + 1)
			// && !b.get(randomRow * randomColumn + 2)) {
			// b.set(randomRow * randomColumn);
			// } else {
			// i--;
			// }
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
			//
			// if (!b.get(randomRow * randomColumn)
			// && !b.get(randomRow * randomColumn + 1)
			// && !b.get(randomRow * randomColumn + 2)) {
			// b.set(randomRow * randomColumn + 2);
			// b.set(randomRow * randomColumn + 1);
			// } else {
			// i--;
			// }
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
			// if (!b.get(randomRow * randomColumn)
			// && !b.get(randomRow * randomColumn + 1)
			// && !b.get(randomRow * randomColumn + 2)) {
			// b.set(randomRow * randomColumn + 2);
			// b.set(randomRow * randomColumn);
			// } else {
			// i--;
			// }
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

	public static Node BF(Problem p) {

		Queue<Node> Q = (Queue<Node>) new LinkedList<Node>();
		Q.add(Make_Node(p.initialState, null, null, 0, 0));
		while (!((Queue<Node>) Q).isEmpty()) {
			Node First = Q.remove();
			if (p.isGoalTest(First.state)) {
				return First;
			}
			ArrayList<Node> expansion = p.Expand(First);
			for(int i=0;i<expansion.size();i++)
				Q.add(expansion.get(i));
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
			for(int i=0;i<expansion.size();i++)
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
		Node result=null;
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
