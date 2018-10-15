public class SaveWestrosState extends State {
	BitField grid;
	int numberOfDragonGlassPieces;
	int JonC;
	int JonR;
	int row;
	int column;

	public SaveWestrosState(BitField grid) {
		this.grid = grid;
	}

	public SaveWestrosState(BitField grid, int dragonGlass, int jonC, int jonR,
			int row, int column) {
		this.grid = grid;
		this.numberOfDragonGlassPieces = dragonGlass;
		this.JonC = jonC;
		this.JonR = jonR;
		this.row = row;
		this.column = column;
	}

	public SaveWestrosState clone() {
		SaveWestrosState clonedState = new SaveWestrosState(
				BitFieldFromgrid(gridFromBitField(row, column, grid)));
		clonedState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		clonedState.JonC = JonC;
		clonedState.JonR = JonR;
		clonedState.row = row;
		clonedState.column = column;
		return clonedState;
	}

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

	public boolean sameState(State s2) {
		char[][] grid1 = gridFromBitField(row, column, grid);
		char[][] grid2 = gridFromBitField(((SaveWestrosState) s2).row,
				((SaveWestrosState) s2).column, ((SaveWestrosState) s2).grid);
		if (((SaveWestrosState) s2).JonC != JonC
				|| ((SaveWestrosState) s2).JonR != JonR
				|| ((SaveWestrosState) s2).numberOfDragonGlassPieces != numberOfDragonGlassPieces)
			return false;
		else {
			for (int i = 0; i < grid1.length; i++) {
				for (int j = 0; j < grid1[0].length; j++) {
					if (grid1[i][j] != grid2[i][j])
						return false;
				}
			}
			return true;
		}
	}

}
