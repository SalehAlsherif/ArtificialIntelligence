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

	public static BitField BitFieldFromgrid(char[][] targetGrid) {

		BitField b = new BitField(1);
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

	public ArrayList<Node> Expand21(Node n) {
		ArrayList<Node> expansion = new ArrayList<Node>();
		char[][] gridOfNode = gridFromBitField(n.state.row, n.state.column,
				n.state.grid);
		if (!(n.state.JonR - 1 < 0)) {
			if (gridOfNode[n.state.JonR - 1][n.state.JonC] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR - 1][n.state.JonC] == 'W'
						&& n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR - 1][n.state.JonC] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode),
							n.state.numberOfDragonGlassPieces, n.state.JonC,
							n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR - 1][n.state.JonC] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR - 1][n.state.JonC] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR - 1][n.state.JonC] = 'J';
					} else {
						gridOfNode[n.state.JonR - 1][n.state.JonC] = 'J';
					}
					newState.JonR -= 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("North");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "North", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonR + 1 > n.state.row - 1)) {
			if (gridOfNode[n.state.JonR + 1][n.state.JonC] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR + 1][n.state.JonC] == 'W'
						&& n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR + 1][n.state.JonC] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode),
							n.state.numberOfDragonGlassPieces, n.state.JonC,
							n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR + 1][n.state.JonC] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR + 1][n.state.JonC] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR + 1][n.state.JonC] = 'J';
					} else {
						gridOfNode[n.state.JonR + 1][n.state.JonC] = 'J';
					}
					newState.JonR += 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("South");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "South", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonC + 1 > n.state.column - 1)) {
			if (gridOfNode[n.state.JonR][n.state.JonC + 1] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR][n.state.JonC + 1] == 'W'
						&& n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR][n.state.JonC + 1] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode),
							n.state.numberOfDragonGlassPieces, n.state.JonC,
							n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR][n.state.JonC + 1] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR][n.state.JonC + 1] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR][n.state.JonC + 1] = 'J';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC + 1] = 'J';
					}
					newState.JonC += 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("East");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "East", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonC - 1 < 0)) {
			if (gridOfNode[n.state.JonR][n.state.JonC - 1] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR][n.state.JonC - 1] == 'W'
						&& n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR][n.state.JonC - 1] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode),
							n.state.numberOfDragonGlassPieces, n.state.JonC,
							n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR][n.state.JonC - 1] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR][n.state.JonC - 1] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR][n.state.JonC - 1] = 'J';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC - 1] = 'J';
					}
					newState.JonC -= 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("West");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "West", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}
		return expansion;

	}

	// State Space or Transition function

	public ArrayList<Node> Expand2(Node n) {
		ArrayList<Node> expansion = new ArrayList<Node>();
		char[][] gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonR - 1 < 0)) {
			if (gridOfNode[n.state.JonR - 1][n.state.JonC] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR - 1][n.state.JonC] == 'W' && n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR - 1][n.state.JonC] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode), n.state.numberOfDragonGlassPieces,
							n.state.JonC, n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR - 1][n.state.JonC] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR - 1][n.state.JonC] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR - 1][n.state.JonC] = 'J';
					} else {
						gridOfNode[n.state.JonR - 1][n.state.JonC] = 'J';
					}
					newState.JonR -= 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("North");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "North", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonR + 1 > n.state.row - 1)) {
			if (gridOfNode[n.state.JonR + 1][n.state.JonC] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR + 1][n.state.JonC] == 'W' && n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR + 1][n.state.JonC] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode), n.state.numberOfDragonGlassPieces,
							n.state.JonC, n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR + 1][n.state.JonC] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR + 1][n.state.JonC] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR + 1][n.state.JonC] = 'J';
					} else {
						gridOfNode[n.state.JonR + 1][n.state.JonC] = 'J';
					}
					newState.JonR += 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("South");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "South", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonC + 1 > n.state.column-1)) {
			if (gridOfNode[n.state.JonR][n.state.JonC + 1] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR][n.state.JonC + 1] == 'W' && n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR][n.state.JonC + 1] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode), n.state.numberOfDragonGlassPieces,
							n.state.JonC, n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR][n.state.JonC + 1] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR][n.state.JonC + 1] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR][n.state.JonC + 1] = 'J';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC + 1] = 'J';
					}
					newState.JonC += 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("East");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "East", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}

		gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		if (!(n.state.JonC - 1 < 0)) {
			if (gridOfNode[n.state.JonR][n.state.JonC - 1] != 'O') {
				State newState = n.state.clone();
				if (gridOfNode[n.state.JonR][n.state.JonC - 1] == 'W' && n.state.numberOfDragonGlassPieces > 0) {
					n.state.numberOfDragonGlassPieces--;
					gridOfNode[n.state.JonR][n.state.JonC - 1] = '.';
					newState = new State(BitFieldFromgrid(gridOfNode), n.state.numberOfDragonGlassPieces,
							n.state.JonC, n.state.JonR, n.state.row, n.state.column);
				} else if (gridOfNode[n.state.JonR][n.state.JonC - 1] != 'W') {
					if (gridFromBitField(initialState.row, initialState.column,
							initialState.grid)[n.state.JonR][n.state.JonC] == 'D') {
						gridOfNode[n.state.JonR][n.state.JonC] = 'D';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC] = '.';
					}
					if (gridOfNode[n.state.JonR][n.state.JonC - 1] == 'D') {
						newState.numberOfDragonGlassPieces += 3;
						gridOfNode[n.state.JonR][n.state.JonC - 1] = 'J';
					} else {
						gridOfNode[n.state.JonR][n.state.JonC - 1] = 'J';
					}
					newState.JonC -= 1;
					newState.grid = BitFieldFromgrid(gridOfNode);
				}
				System.out.println("West");
				String gridString = "";
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						gridString += gridOfNode[i][j] + " ";
					}
					gridString += "\n";
				}
				System.out.println(gridString);
				Node newNode = new Node(newState, n, "West", 0, 0);
				if (!n.state.grid.equals(newState.grid))
					expansion.add(newNode);
			}
		}
		return expansion;

	}

	// State Space or Transition function
	public ArrayList<Node> Expand(Node n) {
		char[][] gridOfNode = gridFromBitField(n.state.row, n.state.column, n.state.grid);
		ArrayList<Node> expansion = new ArrayList<Node>();
		for (int i = 0; i < this.operators.length; i++) {
			if (this.operators[i].equals("North")) {
				if (n.state.JonR - 1 >= 0
						&& gridOfNode[n.state.JonR - 1][n.state.JonC] == 'D') {
					State newState = n.state.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);

					newState.JonC = n.state.JonC;
					newState.JonR = n.state.JonR - 1;

					gridOfClonedNode[n.state.JonR - 1][n.state.JonC] = 'J';


					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(n.parentNode.state.row,
								n.parentNode.state.column, n.parentNode.state.grid);
						if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
					} else {
						gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "North", n.depth + 1, n.pathCost + 1));

				} else {
					if (n.state.JonR - 1 >= 0
							&& gridOfNode[n.state.JonR - 1][n.state.JonC] != 'O'
							&& gridOfNode[n.state.JonR - 1][n.state.JonC] != 'W') {

						State newState = n.state.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = n.state.JonC;
						newState.JonR = n.state.JonR - 1;


						gridOfClonedNode[n.state.JonR - 1][n.state.JonC] = 'J';
						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(
									n.parentNode.state.row,
									n.parentNode.state.column,
									n.parentNode.state.grid);

							if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
							} else {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
							}
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "North", n.depth + 1, n.pathCost + 1));
					}
				}

			}
			if (this.operators[i].equals("South")) {

				if (n.state.JonR + 1 < gridOfNode.length
						&& gridOfNode[n.state.JonR + 1][n.state.JonC] == 'D') {
					State newState = n.state.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);
					newState.JonC = n.state.JonC;
					newState.JonR = n.state.JonR + 1;
					gridOfClonedNode[n.state.JonR + 1][n.state.JonC] = 'J';
					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(
								n.parentNode.state.row,
								n.parentNode.state.column,
								n.parentNode.state.grid);

						if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
					} else {
						gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "South", n.depth + 1, n.pathCost + 1));
				} else {

					if (n.state.JonR + 1 < gridOfNode.length
							&& gridOfNode[n.state.JonR + 1][n.state.JonC] != 'O'
							&& gridOfNode[n.state.JonR + 1][n.state.JonC] != 'W') {

						State newState = n.state.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = n.state.JonC;
						newState.JonR = n.state.JonR + 1;
						gridOfClonedNode[n.state.JonR + 1][n.state.JonC] = 'J';
						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(
									n.parentNode.state.row,
									n.parentNode.state.column,
									n.parentNode.state.grid);

							if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
							} else {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
							}
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "South",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("East")) {
				if (n.state.JonC + 1 < gridOfNode[0].length
						&& gridOfNode[n.state.JonR][n.state.JonC + 1] == 'D') {

					State newState = n.state.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);
					newState.JonC = n.state.JonC + 1;
					newState.JonR = n.state.JonR;
					gridOfClonedNode[n.state.JonR][n.state.JonC + 1] = 'J';

					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(n.parentNode.state.row,
								n.parentNode.state.column, n.parentNode.state.grid);
						if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D')
							gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
						else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
					} else {
						gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "East", n.depth + 1, n.pathCost + 1));
				} else {
					if (n.state.JonC + 1 < gridOfNode[0].length
							&& gridOfNode[n.state.JonR][n.state.JonC + 1] != 'O'
							&& gridOfNode[n.state.JonR][n.state.JonC + 1] != 'W') {
						State newState = n.state.clone();
						
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						
						newState.JonC = n.state.JonC + 1;
						newState.JonR = n.state.JonR;
						gridOfClonedNode[n.state.JonR][n.state.JonC + 1] = 'J';


						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(n.parentNode.state.row,
									n.parentNode.state.column, n.parentNode.state.grid);
							if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
							} else {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
							}
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "East", n.depth + 1, n.pathCost + 1));
					}
				}

			}
			if (this.operators[i].equals("West")) {

				if (n.state.JonC - 1 >= 0
						&& gridOfNode[n.state.JonR ][n.state.JonC- 1] == 'D') {
					State newState = n.state.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);

					newState.JonC = n.state.JonC - 1;
					newState.JonR = n.state.JonR;
					gridOfClonedNode[n.state.JonR][n.state.JonC- 1] = 'J';

					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(n.parentNode.state.row,
								n.parentNode.state.column, n.parentNode.state.grid);
						if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}

					} else {
						gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "West", n.depth + 1, n.pathCost + 1));
				} else {
					if (n.state.JonC - 1 >= 0
							&& gridOfNode[n.state.JonR ][n.state.JonC- 1] != 'O'
							&& gridOfNode[n.state.JonR ][n.state.JonC- 1] != 'W') {
						State newState = n.state.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = n.state.JonC - 1;
						newState.JonR = n.state.JonR;
						gridOfClonedNode[n.state.JonR][n.state.JonC- 1] = 'J';

						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(n.parentNode.state.row,
									n.parentNode.state.column, n.parentNode.state.grid);
							if (gridOfClonedParentNode[n.state.JonR][n.state.JonC] == 'D') {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = 'D';
							} else {
								gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
							}
						} else {
							gridOfClonedNode[n.state.JonR][n.state.JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "West", n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("Kill")) {

				State newState = n.state.clone();
				char[][] gridOfClonedNode = gridFromBitField(newState.row, newState.column, newState.grid);
				if (newState.numberOfDragonGlassPieces > 0) {
					newState.numberOfDragonGlassPieces = n.state.numberOfDragonGlassPieces - 1;

					if (n.state.JonR - 1 >= 0 && gridOfNode[n.state.JonR - 1][n.state.JonC] == 'W') {
						gridOfClonedNode[n.state.JonR - 1][n.state.JonC] = '.';
					}
					if (n.state.JonR + 1 < gridOfNode.length && gridOfNode[n.state.JonR + 1][n.state.JonC] == 'W') {
						gridOfClonedNode[n.state.JonR + 1][n.state.JonC] = '.';
					}
					if (n.state.JonC - 1 >= 0 && gridOfNode[n.state.JonR][n.state.JonC - 1] == 'W') {
						gridOfClonedNode[n.state.JonR][n.state.JonC - 1] = '.';
					}
					if (n.state.JonC + 1 < gridOfNode[0].length
							&& gridOfNode[n.state.JonR][n.state.JonC + 1] == 'W') {

						gridOfClonedNode[n.state.JonR][n.state.JonC + 1] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					expansion.add(new Node(newState, n, "Kill", n.depth + 1, n.pathCost + 1));
				}
			}

		}
		return expansion;
	}

	public boolean isGoalTest(State s) {
		char[][] clonedGrid = gridFromBitField(s.row, s.column, s.grid);
		char[][] goalGrid = gridFromBitField(s.row, s.column, goalTest.grid);
		 for (int i = 0; i < clonedGrid.length; i++) {
		 for (int j = 0; j < clonedGrid.length; j++) {
		 if (clonedGrid[i][j] != goalGrid[i][j]	 && clonedGrid[i][j] != 'J' && goalGrid[i][j] != 'J') {
		 return false;
		 }
		 }
		 }

		return true;
	}

}
