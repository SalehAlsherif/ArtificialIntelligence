
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
	State goalTest;
	public SaveWestros() {
		super(null, null, null);

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
		
//		SaveWestrosState initialState = new SaveWestrosState(
//				BitFieldFromgrid(grid), grid.length, grid[0].length);
		State initialState = new State(
			BitFieldFromgrid(grid));
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

	public int heuristicCost1(Node n) {

		char[][] grid = gridFromBitField(n.state.row, n.state.column,
				n.state.grid);
		int value = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'W')
					value++;
			}
		}
		return value;
	}

	public int heuristicCost2(Node n) {
		char[][] grid = gridFromBitField(n.state.row, n.state.column,
				n.state.grid);
		int value = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 'W')
					value++;
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

//	public ArrayList<Node> Expand(Node n) {
//
//		char[][] gridOfNode = gridFromBitField(n.state.row, n.state.column,
//				n.state.grid);
//		ArrayList<Node> expansion = new ArrayList<Node>();
//		for (int i = 0; i < this.operators.length; i++) {
//			if (this.operators[i].equals("Kill")) {
//
//				SaveWestrosState newState = ((SaveWestrosState) n.state)
//						.clone();
//				char[][] gridOfClonedNode = gridFromBitField(newState.row,newState.column, newState.grid);
//				
//				if (((SaveWestrosState) n.state).numberOfDragonGlassPieces > 0) {
//					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces - 1;
//
//					if (((SaveWestrosState) n.state).JonR - 1 >= 0
//							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] == 'W') {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
//							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] == 'W') {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					if (((SaveWestrosState) n.state).JonC - 1 >= 0
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] == 'W') {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = '.';
//					}
//					if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] == 'W') {
//
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = '.';
//					}
//					newState.grid = BitFieldFromgrid(gridOfClonedNode);
//					expansion.add(new Node(newState, n, "Kill", n.depth + 1,
//							n.pathCost + 3));
////					char [][]c=gridFromBitField(newState.row,newState.column,newState.grid);
////					for(int j=0;j<c.length;j++){
////						for(int k=0;k<c[0].length;k++){
////							System.out.print(c[j][k]);
////						}
////						System.out.println();
////					}
////					System.out.println();
////					System.out.println();
//
//				}
//			}
//			if (this.operators[i].equals("North")) {
//				if (((SaveWestrosState) n.state).JonR - 1 >= 0
//						&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] == 'D') {
//					SaveWestrosState newState = ((SaveWestrosState) n.state)
//							.clone();
//					char[][] gridOfClonedNode = gridFromBitField(newState.row,
//							newState.column, newState.grid);
//
//					newState.JonC = ((SaveWestrosState) n.state).JonC;
//					newState.JonR = ((SaveWestrosState) n.state).JonR - 1;
//
//					gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = 'J';
//
//					if (n.parentNode != null) {
//						char[][] gridOfClonedParentNode = gridFromBitField(
//								n.parentNode.state.row,
//								n.parentNode.state.column,
//								n.parentNode.state.grid);
//						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//					} else {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					newState.grid = BitFieldFromgrid(gridOfClonedNode);
//					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
//					expansion.add(new Node(newState, n, "North", n.depth + 1,
//							n.pathCost + 1));
//
//				} else {
//					if (((SaveWestrosState) n.state).JonR - 1 >= 0
//							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] != 'O'
//							&& gridOfNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] != 'W') {
//						SaveWestrosState newState = ((SaveWestrosState) n.state)
//								.clone();
//						char[][] gridOfClonedNode = gridFromBitField(
//								newState.row, newState.column, newState.grid);
//						newState.JonC = ((SaveWestrosState) n.state).JonC;
//						newState.JonR = ((SaveWestrosState) n.state).JonR - 1;
//
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR - 1][((SaveWestrosState) n.state).JonC] = 'J';
//						if (n.parentNode != null) {
//							char[][] gridOfClonedParentNode = gridFromBitField(
//									n.parentNode.state.row,
//									n.parentNode.state.column,
//									n.parentNode.state.grid);
//
//							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//							} else {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//							}
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//						newState.grid = BitFieldFromgrid(gridOfClonedNode);
//						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
//						expansion.add(new Node(newState, n, "North",
//								n.depth + 1, n.pathCost + 1));
//					}
//				}
//
//			}
//			if (this.operators[i].equals("South")) {
//
//				if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
//						&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] == 'D') {
//					SaveWestrosState newState = ((SaveWestrosState) n.state)
//							.clone();
//					char[][] gridOfClonedNode = gridFromBitField(newState.row,
//							newState.column, newState.grid);
//					newState.JonC = ((SaveWestrosState) n.state).JonC;
//					newState.JonR = ((SaveWestrosState) n.state).JonR + 1;
//					gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = 'J';
//					if (n.parentNode != null) {
//						char[][] gridOfClonedParentNode = gridFromBitField(
//								n.parentNode.state.row,
//								n.parentNode.state.column,
//								n.parentNode.state.grid);
//
//						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//					} else {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					newState.grid = BitFieldFromgrid(gridOfClonedNode);
//					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
//					expansion.add(new Node(newState, n, "South", n.depth + 1,
//							n.pathCost + 1));
//				} else {
//
//					if (((SaveWestrosState) n.state).JonR + 1 < gridOfNode.length
//							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] != 'O'
//							&& gridOfNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] != 'W') {
//
//						SaveWestrosState newState = ((SaveWestrosState) n.state)
//								.clone();
//						char[][] gridOfClonedNode = gridFromBitField(
//								newState.row, newState.column, newState.grid);
//						newState.JonC = ((SaveWestrosState) n.state).JonC;
//						newState.JonR = ((SaveWestrosState) n.state).JonR + 1;
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR + 1][((SaveWestrosState) n.state).JonC] = 'J';
//						if (n.parentNode != null) {
//							char[][] gridOfClonedParentNode = gridFromBitField(
//									n.parentNode.state.row,
//									n.parentNode.state.column,
//									n.parentNode.state.grid);
//
//							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//							} else {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//							}
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//						newState.grid = BitFieldFromgrid(gridOfClonedNode);
//						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
//						expansion.add(new Node(newState, n, "South",
//								n.depth + 1, n.pathCost + 1));
//
//					}
//				}
//
//			}
//			if (this.operators[i].equals("East")) {
//				if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
//						&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] == 'D') {
//
//					SaveWestrosState newState = ((SaveWestrosState) n.state)
//							.clone();
//					char[][] gridOfClonedNode = gridFromBitField(newState.row,
//							newState.column, newState.grid);
//					newState.JonC = ((SaveWestrosState) n.state).JonC + 1;
//					newState.JonR = ((SaveWestrosState) n.state).JonR;
//					gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = 'J';
//
//					if (n.parentNode != null) {
//						char[][] gridOfClonedParentNode = gridFromBitField(
//								n.parentNode.state.row,
//								n.parentNode.state.column,
//								n.parentNode.state.grid);
//						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D')
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//						else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//					} else {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					newState.grid = BitFieldFromgrid(gridOfClonedNode);
//					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
//					expansion.add(new Node(newState, n, "East", n.depth + 1,
//							n.pathCost + 1));
//				} else {
//					if (((SaveWestrosState) n.state).JonC + 1 < gridOfNode[0].length
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] != 'O'
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] != 'W') {
//						SaveWestrosState newState = ((SaveWestrosState) n.state)
//								.clone();
//
//						char[][] gridOfClonedNode = gridFromBitField(
//								newState.row, newState.column, newState.grid);
//
//						newState.JonC = ((SaveWestrosState) n.state).JonC + 1;
//						newState.JonR = ((SaveWestrosState) n.state).JonR;
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC + 1] = 'J';
//
//						if (n.parentNode != null) {
//							char[][] gridOfClonedParentNode = gridFromBitField(
//									n.parentNode.state.row,
//									n.parentNode.state.column,
//									n.parentNode.state.grid);
//							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//							} else {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//							}
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//						newState.grid = BitFieldFromgrid(gridOfClonedNode);
//						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
//						expansion.add(new Node(newState, n, "East",
//								n.depth + 1, n.pathCost + 1));
//					}
//				}
//
//			}
//			if (this.operators[i].equals("West")) {
//
//				if (((SaveWestrosState) n.state).JonC - 1 >= 0
//						&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] == 'D') {
//					SaveWestrosState newState = ((SaveWestrosState) n.state)
//							.clone();
//					char[][] gridOfClonedNode = gridFromBitField(newState.row,
//							newState.column, newState.grid);
//
//					newState.JonC = ((SaveWestrosState) n.state).JonC - 1;
//					newState.JonR = ((SaveWestrosState) n.state).JonR;
//					gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = 'J';
//
//					if (n.parentNode != null) {
//						char[][] gridOfClonedParentNode = gridFromBitField(
//								n.parentNode.state.row,
//								n.parentNode.state.column,
//								n.parentNode.state.grid);
//						if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//
//					} else {
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//					}
//					newState.grid = BitFieldFromgrid(gridOfClonedNode);
//					newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces + 1;
//					expansion.add(new Node(newState, n, "West", n.depth + 1,
//							n.pathCost + 1));
//				} else {
//					if (((SaveWestrosState) n.state).JonC - 1 >= 0
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] != 'O'
//							&& gridOfNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] != 'W') {
//						SaveWestrosState newState = ((SaveWestrosState) n.state)
//								.clone();
//						char[][] gridOfClonedNode = gridFromBitField(
//								newState.row, newState.column, newState.grid);
//						newState.JonC = ((SaveWestrosState) n.state).JonC - 1;
//						newState.JonR = ((SaveWestrosState) n.state).JonR;
//						gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC - 1] = 'J';
//
//						if (n.parentNode != null) {
//							char[][] gridOfClonedParentNode = gridFromBitField(
//									n.parentNode.state.row,
//									n.parentNode.state.column,
//									n.parentNode.state.grid);
//							if (gridOfClonedParentNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] == 'D') {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = 'D';
//							} else {
//								gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//							}
//						} else {
//							gridOfClonedNode[((SaveWestrosState) n.state).JonR][((SaveWestrosState) n.state).JonC] = '.';
//						}
//						newState.grid = BitFieldFromgrid(gridOfClonedNode);
//						newState.numberOfDragonGlassPieces = ((SaveWestrosState) n.state).numberOfDragonGlassPieces;
//						expansion.add(new Node(newState, n, "West",
//								n.depth + 1, n.pathCost + 1));
//
//					}
//				}
//
//			}
//
//		}
//		return expansion;
//	}
//
//	
//	
	public boolean goalTest(State s) {
		
//		char[][] grid = gridFromBitField(s.row, s.column, s.grid);
//		for (int i = 0; i < grid.length; i++) {
//			for (int j = 0; j < grid[0].length; j++) {
//				if (grid[i][j] == 'W') {
//					return false;
//				}
//			}
//		}
//		return true;
		char[][] clonedGrid = gridFromBitField(s.row, s.column, s.grid);
		char[][] goalGrid = gridFromBitField(s.row, s.column, goalTest.grid);
		 for (int i = 0; i < clonedGrid.length; i++) {
		 for (int j = 0; j < clonedGrid[i].length; j++) {
		 if (clonedGrid[i][j] != goalGrid[i][j]	 && clonedGrid[i][j] != 'J' && goalGrid[i][j] != 'J') {
		 return false;
		 }
		 }
		 }

		return true;
		
	}

}
