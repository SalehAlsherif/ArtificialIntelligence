//public class SaveWestrosState extends State {
//	BitField grid;
//	int numberOfDragonGlassPieces;
//	int JonC;
//	int JonR;
//	int row;
//	int column;
//
//	public SaveWestrosState(BitField grid, int row, int column) {
//		super(grid, row, column);
//		this.grid=grid;
//	}
//
//	public SaveWestrosState(BitField grid, int dragonGlass, int jonC, int jonR,
//			int row, int column) {
//		super(grid, row, column);
//		this.grid=grid;
//
//		this.numberOfDragonGlassPieces = dragonGlass;
//		this.JonC = jonC;
//		this.JonR = jonR;
//		this.row = row;
//		this.column = column;
//	}
//
//	// I'm going to assume a bitFeild that better represent the grid the size
//	// of the set is 3*sizeR*sizeC
//	// I will consider encoding for the empty cell as 000
//	// jon snow as 001
//	// D as 010
//	// W as 011
//	// O as 100
//
//	// Decoding
//	public static char[][] gridFromBitField(int R, int C, BitField b) {
//		char[][] newgrid = new char[R][C];
//		int size = newgrid.length * newgrid[0].length * 3;
//		for (int i = 0; i < size; i += 3) {
//			if (!b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
//				newgrid[(i / 3) / C][(i / 3) % C] = '.';
//			} else if (!b.get(i) && !b.get(i + 1) && b.get(i + 2)) {
//
//				newgrid[(i / 3) / C][(i / 3) % C] = 'J';
//			} else if (!b.get(i) && b.get(i + 1) && !b.get(i + 2)) {
//				newgrid[(i / 3) / C][(i / 3) % C] = 'D';
//			} else if (!b.get(i) && b.get(i + 1) && b.get(i + 2)) {
//				newgrid[(i / 3) / C][(i / 3) % C] = 'W';
//			} else if (b.get(i) && !b.get(i + 1) && !b.get(i + 2)) {
//				newgrid[(i / 3) / C][(i / 3) % C] = 'O';
//			} else
//				newgrid[(i / 3) / C][(i / 3) % C] = 'X';
//
//		}
//		return newgrid;
//	}
//
//	// Encoding
//	public static BitField BitFieldFromgrid(char[][] targetGrid) {
//		BitField b = new BitField(3);
//		for (int i = 0; i < targetGrid.length; i++) {
//			for (int j = 0; j < targetGrid[i].length; j++) {
//				if (targetGrid[i][j] == '.') {
//					b.clear(i * 3 * targetGrid[0].length + j * 3);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
//				}
//				if (targetGrid[i][j] == 'J') {
//					b.clear(i * 3 * targetGrid[0].length + j * 3);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
//					b.set(i * 3 * targetGrid[0].length + j * 3 + 2);
//				}
//				if (targetGrid[i][j] == 'D') {
//					b.clear(i * 3 * targetGrid[0].length + j * 3);
//					b.set(i * 3 * targetGrid[0].length + j * 3 + 1);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
//				}
//				if (targetGrid[i][j] == 'W') {
//					b.clear(i * 3 * targetGrid[0].length * 3 + j);
//					b.set(i * 3 * targetGrid[0].length + j * 3 + 1);
//					b.set(i * 3 * targetGrid[0].length + j * 3 + 2);
//				}
//				if (targetGrid[i][j] == 'O') {
//					b.set(i * 3 * targetGrid[0].length + j * 3);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 1);
//					b.clear(i * 3 * targetGrid[0].length + j * 3 + 2);
//				}
//			}
//		}
//		return b;
//	}
//
//	public SaveWestrosState clone() {
//		SaveWestrosState clonedState = new SaveWestrosState(
//				BitFieldFromgrid(gridFromBitField(row, column, grid)), row,
//				column);
//		
//		clonedState.grid=BitFieldFromgrid(gridFromBitField(row, column, grid));
//		clonedState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
//		clonedState.JonC = JonC;
//		clonedState.JonR = JonR;
//		clonedState.row = row;
//		clonedState.column = column;
//		return clonedState;
//	}
//
//}
