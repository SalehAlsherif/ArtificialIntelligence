import java.util.ArrayList;
import java.util.StringTokenizer;

public class SaveWestros extends Problem {

	static char[][] grid;/*
						 * 
						 * Free cells are '.' Jon Snow is of value 'J' White
						 * Walker is of value 'W' Dragon Stone is of value 'D'
						 * Obstacle is of value 'X'
						 */
	static int numberOfDragonGlassPieces = 0;// Lesa msh 3aref eh da !!
	static int jonColumn;
	static int jonRow;

	public SaveWestros() {
		super(null, null);

		String[] operators = new String[5];
		// GenGrid(int maxLimitGrid, int maxLimitWhiteWalkers,
		// int maxLimitDragonStone, int maxLimitObstacle)
		GenGrid(0, 4, 6, 8);
		// Set of Actions
		operators[0] = "North";

		operators[1] = "South";

		operators[2] = "East";

		operators[3] = "West";

		operators[4] = "Kill";
		// Custom generated Case to test DFS
		// grid[0][0]='.';
		// grid[0][1]='.';
		// grid[0][2]='O';
		// grid[0][3]='.';
		//
		// grid[1][0]='.';
		// grid[1][1]='.';
		// grid[1][2]='O';
		// grid[1][3]='W';
		//
		// grid[2][0]='.';
		// grid[2][1]='.';
		// grid[2][2]='O';
		// grid[2][3]='D';
		//
		// grid[3][0]='.';
		// grid[3][1]='.';
		// grid[3][2]='O';
		// grid[3][3]='J';

		// Initial State Creation

		// SaveWestrosState initialState = new SaveWestrosState(
		// BitFieldFromgrid(grid), grid.length, grid[0].length);
		SaveWestrosState initialState = new SaveWestrosState(
				BitFieldFromgrid(grid));
		initialState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		initialState.JonC = jonColumn;
		initialState.JonR = jonRow;
		initialState.row = grid.length;
		initialState.column = grid[0].length;
		initialState.numberOfDragonGlassPieces = 0;

		// Encoding the state into the BitField .
		gridFromBitField(grid.length, grid[0].length, BitFieldFromgrid(grid));

		this.operators = operators;
		this.initialState = initialState;

		String gridString = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				gridString += grid[i][j] + " ";
			}
			gridString += "\n";
		}
		System.out.println(gridString);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Creating a SaveWestros instance
		SaveWestros sv = new SaveWestros();

		// This is for visualization
		jonColumn = sv.jonColumn;
		jonRow = sv.jonRow;
		grid = sv.grid;

		// Informed
		// search((sv), searchType.GR1, false);
		// search((sv), searchType.GR2, false);
		// search((sv), searchType.AS1, false);
		search((sv), searchType.AS2, true);

		// Uninformed
		// search((sv), searchType.BF, false);
		// search((sv), searchType.DF, false);
		// search((sv), searchType.UC, false);

		// I made the Maximum depth as 15 as
		// it's longest plan action I can
		// imagine
		// search((sv), searchType.ID, false);

	}

	public static void search(Problem p, searchType s, boolean visualize) {
		Node n = general_search(p, s);
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

	public int heuristicCost1(Node n) {

		char[][] grid = gridFromBitField(((SaveWestrosState) n.state).row,
				((SaveWestrosState) n.state).column,
				((SaveWestrosState) n.state).grid);
		int value = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'W')
					value++;
			}
		}
		return value * ((SaveWestrosState) n.state).row
				* ((SaveWestrosState) n.state).column / 3;
	}

	public int heuristicCost2(Node n) {
		char[][] grid = gridFromBitField(((SaveWestrosState) n.state).row,
				((SaveWestrosState) n.state).column,
				((SaveWestrosState) n.state).grid);
		int value = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'W') {
					value++;
					if (i + 1 < grid.length && j + 1 < grid[0].length
							&& grid[i + 1][j + 1] == 'W' && !(grid[i+1][j] == 'O' && grid[i][j+1] == 'O')) {
						value += ((SaveWestrosState) n.state).row
								* ((SaveWestrosState) n.state).column / 3;
					} else {
						if (i + 1 < grid.length && j - 1 >= 0
								&& grid[i + 1][j - 1] == 'W' && !(grid[i+1][j] == 'O' && grid[i][j-1] == 'O')) {
							value += ((SaveWestrosState) n.state).row
									* ((SaveWestrosState) n.state).column / 3;
						} else {
							if (i - 1 >= 0 && j + 1 < grid[0].length
									&& grid[i - 1][j + 1] == 'W' && !(grid[i-1][j] == 'O' && grid[i][j+1] == 'O')) {
								value += ((SaveWestrosState) n.state).row
										* ((SaveWestrosState) n.state).column
										/ 3;
							} else {

								if (i - 1 >= 0 && j - 1 >= 0
										&& grid[i - 1][j - 1] == 'W' && !(grid[i-1][j] == 'O' && grid[i][j-1] == 'O')) {
									value += ((SaveWestrosState) n.state).row
											* ((SaveWestrosState) n.state).column
											/ 3;
								} else {

									if (i - 2 >= 0 && grid[i - 2][j] == 'W' && !(grid[i-1][j] == 'O')) {
										value += ((SaveWestrosState) n.state).row
												* ((SaveWestrosState) n.state).column
												/ 3;
									} else {
										if (i + 2 < grid.length
												&& grid[i + 2][j] == 'W' && !(grid[i+1][j] == 'O')) {
											value += ((SaveWestrosState) n.state).row
													* ((SaveWestrosState) n.state).column
													/ 3;
										} else {
											if (j - 2 >= 0
													&& grid[i][j - 2] == 'W' && !(grid[i][j-1] == 'O')) {
												value += ((SaveWestrosState) n.state).row
														* ((SaveWestrosState) n.state).column
														/ 3;
											} else {
												if (j + 2 < grid[0].length
														&& grid[i][j + 2] == 'W' && !(grid[i][j+1] == 'O')) {
													value += ((SaveWestrosState) n.state).row
															* ((SaveWestrosState) n.state).column
															/ 3;
												} else {
													value += ((SaveWestrosState) n.state).row
															* ((SaveWestrosState) n.state).column;
												}

											}

										}

									}

								}
							}
						}
					}

				}
			}
		}

		return value;
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

	// State Space or Transition function
	public ArrayList<Node> Expand(Node n) {

		char[][] gridOfNode = gridFromBitField(
				((SaveWestrosState) n.state).row,
				((SaveWestrosState) n.state).column,
				((SaveWestrosState) n.state).grid);
		ArrayList<Node> expansion = new ArrayList<Node>();
		for (int i = 0; i < this.operators.length; i++) {
			if (this.operators[i].equals("North")) {
				if (((SaveWestrosState) n.state).JonR - 1 >= 0
						&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] == 'D') {
					SaveWestrosState newState = ((SaveWestrosState) n.state)
							.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);

					newState.JonC = ((SaveWestrosState) n.state).JonC;
					newState.JonR = ((SaveWestrosState) n.state).JonR - 1;

					gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = 'J';

					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(
								((SaveWestrosState) n.parentNode.state).row,
								((SaveWestrosState) n.parentNode.state).column,
								((SaveWestrosState) n.parentNode.state).grid);
						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
					} else {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "North", n.depth + 1,
							n.pathCost + 1));

				} else {
					if (((SaveWestrosState) n.state).JonR - 1 >= 0
							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] != 'O'
							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] != 'W') {

						SaveWestrosState newState = ((SaveWestrosState) n.state)
								.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = ((SaveWestrosState) n.state).JonC;
						newState.JonR = ((SaveWestrosState) n.state).JonR - 1;

						gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = 'J';
						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(
									((SaveWestrosState) n.parentNode.state).row,
									((SaveWestrosState) n.parentNode.state).column,
									((SaveWestrosState) n.parentNode.state).grid);

							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
							} else {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
							}
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "North",
								n.depth + 1, n.pathCost + 1));
					}
				}

			}
			if (this.operators[i].equals("South")) {

				if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
						&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] == 'D') {
					SaveWestrosState newState = ((SaveWestrosState) n.state)
							.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);
					newState.JonC = ((SaveWestrosState) n.state).JonC;
					newState.JonR = ((SaveWestrosState) n.state).JonR + 1;
					gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = 'J';
					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(
								((SaveWestrosState) n.parentNode.state).row,
								((SaveWestrosState) n.parentNode.state).column,
								((SaveWestrosState) n.parentNode.state).grid);

						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
					} else {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "South", n.depth + 1,
							n.pathCost + 1));
				} else {

					if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] != 'O'
							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] != 'W') {

						SaveWestrosState newState = ((SaveWestrosState) n.state)
								.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = ((SaveWestrosState) n.state).JonC;
						newState.JonR = ((SaveWestrosState) n.state).JonR + 1;
						gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = 'J';
						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(
									((SaveWestrosState) n.parentNode.state).row,
									((SaveWestrosState) n.parentNode.state).column,
									((SaveWestrosState) n.parentNode.state).grid);

							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
							} else {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
							}
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "South",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("East")) {
				if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
						&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] == 'D') {

					SaveWestrosState newState = ((SaveWestrosState) n.state)
							.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);
					newState.JonC = ((SaveWestrosState) n.state).JonC + 1;
					newState.JonR = ((SaveWestrosState) n.state).JonR;
					gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = 'J';

					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(
								((SaveWestrosState) n.parentNode.state).row,
								((SaveWestrosState) n.parentNode.state).column,
								((SaveWestrosState) n.parentNode.state).grid);
						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D')
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
						else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
					} else {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "East", n.depth + 1,
							n.pathCost + 1));
				} else {
					if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] != 'O'
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] != 'W') {
						SaveWestrosState newState = ((SaveWestrosState) n.state)
								.clone();

						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);

						newState.JonC = ((SaveWestrosState) n.state).JonC + 1;
						newState.JonR = ((SaveWestrosState) n.state).JonR;
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = 'J';

						if (n.parentNode != null) {

							char[][] gridOfClonedParentNode = gridFromBitField(
									((SaveWestrosState) n.parentNode.state).row,
									((SaveWestrosState) n.parentNode.state).column,
									((SaveWestrosState) n.parentNode.state).grid);
							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
							} else {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
							}
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "East",
								n.depth + 1, n.pathCost + 1));
					}
				}

			}
			if (this.operators[i].equals("West")) {

				if (((SaveWestrosState) n.state).JonC - 1 >= 0
						&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] == 'D') {
					SaveWestrosState newState = ((SaveWestrosState) n.state)
							.clone();
					char[][] gridOfClonedNode = gridFromBitField(newState.row,
							newState.column, newState.grid);

					newState.JonC = ((SaveWestrosState) n.state).JonC - 1;
					newState.JonR = ((SaveWestrosState) n.state).JonR;
					gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = 'J';

					if (n.parentNode != null) {
						char[][] gridOfClonedParentNode = gridFromBitField(
								((SaveWestrosState) n.parentNode.state).row,
								((SaveWestrosState) n.parentNode.state).column,
								((SaveWestrosState) n.parentNode.state).grid);
						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}

					} else {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
					expansion.add(new Node(newState, n, "West", n.depth + 1,
							n.pathCost + 1));
				} else {
					if (((SaveWestrosState) n.state).JonC - 1 >= 0
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] != 'O'
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] != 'W') {
						SaveWestrosState newState = ((SaveWestrosState) n.state)
								.clone();
						char[][] gridOfClonedNode = gridFromBitField(
								newState.row, newState.column, newState.grid);
						newState.JonC = ((SaveWestrosState) n.state).JonC - 1;
						newState.JonR = ((SaveWestrosState) n.state).JonR;
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = 'J';

						if (n.parentNode != null) {
							char[][] gridOfClonedParentNode = gridFromBitField(
									((SaveWestrosState) n.parentNode.state).row,
									((SaveWestrosState) n.parentNode.state).column,
									((SaveWestrosState) n.parentNode.state).grid);
							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
							} else {
								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
							}
						} else {
							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
						}
						newState.grid = BitFieldFromgrid(gridOfClonedNode);
						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
						expansion.add(new Node(newState, n, "West",
								n.depth + 1, n.pathCost + 1));

					}
				}

			}
			if (this.operators[i].equals("Kill")) {

				SaveWestrosState newState = ((SaveWestrosState) n.state)
						.clone();
				char[][] gridOfClonedNode = gridFromBitField(newState.row,
						newState.column, newState.grid);
				if (newState.numberOfDragonGlassPieces > 0) {
					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces - 1;

					if (((SaveWestrosState) n.state).JonR - 1 >= 0
							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] == 'W') {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = '.';
					}
					if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] == 'W') {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = '.';
					}
					if (((SaveWestrosState) n.state).JonC - 1 >= 0
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] == 'W') {
						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = '.';
					}
					if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] == 'W') {

						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = '.';
					}
					newState.grid = BitFieldFromgrid(gridOfClonedNode);
					expansion.add(new Node(newState, n, "Kill", n.depth + 1,
							n.pathCost + (newState.row * newState.column)));
				}
			}

		}
		return expansion;
	}

	public boolean goalTest(State s) {
		char[][] grid = gridFromBitField(((SaveWestrosState) s).row,
				((SaveWestrosState) s).column, ((SaveWestrosState) s).grid);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'W') {
					return false;
				}
			}
		}
		return true;
	}

	// I'm going to assume a bitFeild that better represent the grid the size
	// of the set is 3*sizeR*sizeC
	// I will consider encoding for the empty cell as 000
	// jon snow as 001
	// D as 010
	// W as 011
	// O as 100

	// Decoding
	public static char[][] gridFromBitField(int R, int C, BitField b) {
		char[][] newgrid = new char[R][C];
		int size = newgrid.length * newgrid[0].length * 3;
		for (int i = 0; i < size; i += 3) {

			if (!b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / C][(i / 3) % C] = '.';
			} else if (!b.get(i) && !b.get(i + 1) && b.get(i + 2)) {

				newgrid[(i / 3) / C][(i / 3) % C] = 'J';
			} else if (!b.get(i) && b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / C][(i / 3) % C] = 'D';
			} else if (!b.get(i) && b.get(i + 1) && b.get(i + 2)) {
				newgrid[(i / 3) / C][(i / 3) % C] = 'W';
			} else if (b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
				newgrid[(i / 3) / C][(i / 3) % C] = 'O';
			} else
				newgrid[(i / 3) / C][(i / 3) % C] = 'X';

		}
		return newgrid;
	}

	// Encoding
	public static BitField BitFieldFromgrid(char[][] targetGrid) {
		BitField b = new BitField(3);
		for (int i = 0; i < targetGrid.length; i++) {
			for (int j = 0; j < targetGrid[i].length; j++) {
				if (targetGrid[i][j] == '.') {
					b.clear(i * 3 * targetGrid[0].length + j * 3);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'J') {
					b.clear(i * 3 * targetGrid[0].length + j * 3);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
					b.set(i * 3 * targetGrid[0].length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'D') {
					b.clear(i * 3 * targetGrid[0].length + j * 3);
					b.set(i * 3 * targetGrid[0].length + j * 3 + 1);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'W') {
					b.clear(i * 3 * targetGrid[0].length * 3 + j);
					b.set(i * 3 * targetGrid[0].length + j * 3 + 1);
					b.set(i * 3 * targetGrid[0].length + j * 3 + 2);
				}
				if (targetGrid[i][j] == 'O') {
					b.set(i * 3 * targetGrid[0].length + j * 3);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
				}
			}
		}
		return b;
	}

}
