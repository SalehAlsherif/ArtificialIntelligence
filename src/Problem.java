import java.util.ArrayList;

public class Problem {
	String[] operators;
	State initialState;
	State goalTest;

	public Problem(String[] operators, State initialState, State goalTest) {
		this.operators = operators;
		this.initialState = initialState;
		this.goalTest = goalTest;
	}

	public static int pathCost() {
		// a function that assigns cost to a sequence of actions. Typically, it
		// is the sum of the costs of individual actions in the sequence.

		return 0;
	}

	// State Space or Transition function
	public ArrayList<Node> Expand(Node n) {
		ArrayList<Node> expansion = new ArrayList<Node>();
		for (int i = 0; i < this.operators.length; i++) {
			if (this.operators[i].equals("North")) {
				if (n.state.JonC - 1 >= 0
						&& n.state.grid[n.state.JonR][n.state.JonC - 1] == 'D') {
					State newState = n.state.clone();
					newState.grid[n.state.JonR][n.state.JonC - 1] = 'J';
					if (n.parentNode != null
							&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
						newState.grid[n.state.JonR][n.state.JonC] = 'D';
					else
						newState.grid[n.state.JonR][n.state.JonC] = '.';
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "North", n.depth + 1,
							n.pathCost + 1));
				} else {

					if (n.state.JonC - 1 >= 0
							&& n.state.grid[n.state.JonR][n.state.JonC - 1] != 'O'
							&& n.state.grid[n.state.JonR][n.state.JonC - 1] != 'W') {

						State newState = n.state.clone();
						newState.grid[n.state.JonR][n.state.JonC - 1] = 'J';
						if (n.parentNode != null
								&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
							newState.grid[n.state.JonR][n.state.JonC] = 'D';
						else
							newState.grid[n.state.JonR][n.state.JonC] = '.';
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "North",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("South")) {
				if (n.state.JonC + 1 < n.state.grid[0].length
						&& n.state.grid[n.state.JonR][n.state.JonC + 1] == 'D') {
					State newState = n.state.clone();
					newState.grid[n.state.JonR][n.state.JonC + 1] = 'J';
					if (n.parentNode != null
							&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
						newState.grid[n.state.JonR][n.state.JonC] = 'D';
					else
						newState.grid[n.state.JonR][n.state.JonC] = '.';
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "South", n.depth + 1,
							n.pathCost + 1));
				} else {
					if (n.state.JonC + 1 < n.state.grid[0].length
							&& n.state.grid[n.state.JonR][n.state.JonC + 1] != 'O'
							&& n.state.grid[n.state.JonR][n.state.JonC + 1] != 'W') {

						State newState = n.state.clone();
						newState.grid[n.state.JonR][n.state.JonC + 1] = 'J';
						if (n.parentNode != null
								&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
							newState.grid[n.state.JonR][n.state.JonC] = 'D';
						else
							newState.grid[n.state.JonR][n.state.JonC] = '.';
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "South",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("East")) {

				if (n.state.JonR + 1 < n.state.grid.length
						&& n.state.grid[n.state.JonR + 1][n.state.JonC] == 'D') {
					State newState = n.state.clone();
					newState.grid[n.state.JonR + 1][n.state.JonC] = 'J';
					if (n.parentNode != null
							&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
						newState.grid[n.state.JonR][n.state.JonC] = 'D';
					else
						newState.grid[n.state.JonR][n.state.JonC] = '.';
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "East", n.depth + 1,
							n.pathCost + 1));
				} else {
					if (n.state.JonR + 1 < n.state.grid.length
							&& n.state.grid[n.state.JonR + 1][n.state.JonC] != 'O'
							&& n.state.grid[n.state.JonR + 1][n.state.JonC] != 'W') {
						State newState = n.state.clone();
						newState.grid[n.state.JonR + 1][n.state.JonC] = 'J';
						if (n.parentNode != null
								&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
							newState.grid[n.state.JonR][n.state.JonC] = 'D';
						else
							newState.grid[n.state.JonR][n.state.JonC] = '.';
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "East",
								n.depth + 1, n.pathCost + 1));
					}
				}

			}
			if (this.operators[i].equals("West")) {

				if (n.state.JonR - 1 >= 0
						&& n.state.grid[n.state.JonR - 1][n.state.JonC] == 'D') {
					State newState = n.state.clone();
					newState.grid[n.state.JonR - 1][n.state.JonC] = 'J';
					if (n.parentNode != null
							&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
						newState.grid[n.state.JonR][n.state.JonC] = 'D';
					else
						newState.grid[n.state.JonR][n.state.JonC] = '.';
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "West", n.depth + 1,
							n.pathCost + 1));
				} else {

					if (n.state.JonR - 1 >= 0
							&& n.state.grid[n.state.JonR - 1][n.state.JonC] != 'O'
							&& n.state.grid[n.state.JonR - 1][n.state.JonC] != 'W') {
						State newState = n.state.clone();
						newState.grid[n.state.JonR - 1][n.state.JonC] = 'J';
						if (n.parentNode != null
								&& n.parentNode.state.grid[n.state.JonR][n.state.JonC] == 'D')
							newState.grid[n.state.JonR][n.state.JonC] = 'D';
						else
							newState.grid[n.state.JonR][n.state.JonC] = '.';
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "West",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("Kill")) {

				State newState = n.state.clone();
				if(newState.numberOfDragonGlassPieces >0)
				{	
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces - 1;

					if (n.state.JonR - 1 >= 0
							&& n.state.grid[n.state.JonR - 1][n.state.JonC] == 'W') {
						newState.grid[n.state.JonR - 1][n.state.JonC] = '.';
					}
					if (n.state.JonR + 1 < n.state.grid.length
							&& n.state.grid[n.state.JonR + 1][n.state.JonC] == 'W') {
						newState.grid[n.state.JonR + 1][n.state.JonC] = '.';
					}
					if (n.state.JonC - 1 >= 0
							&& n.state.grid[n.state.JonR][n.state.JonC - 1] == 'W') {
						newState.grid[n.state.JonR][n.state.JonC - 1] = '.';
					}
					if (n.state.JonR + 1 < n.state.grid[0].length
							&& n.state.grid[n.state.JonR][n.state.JonC + 1] == 'W') {
						newState.grid[n.state.JonR][n.state.JonC + 1] = '.';
					}
					expansion.add(new Node(newState, n, "Kill", n.depth + 1,
							n.pathCost + 1));	
				}				
			}

		}
		return expansion;
	}

	
	public boolean isGoalTest(State s) {
		char[][]clonedGrid=gridFromBitSet(s.row,s.column,s.grid);
		char[][]goalGrid=gridFromBitSet(s.row,s.column,goalTest.grid);
		for (int i = 0; i < clonedGrid.length; i++) {
			for (int j = 0; j < clonedGrid.length; j++) {
				if (clonedGrid[i][j] != goalGrid[i][j] && clonedGrid[i][j] != 'J'
						&& goalGrid[i][j] != 'J') {
					return false;
				}
			}
		}
		return true;
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
		for (int i = 0; i < newgrid.length; i++) {
			for (int j = 0; j < newgrid[i].length; j++) {
				System.out.print(newgrid[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");
		return newgrid;
	}
}
