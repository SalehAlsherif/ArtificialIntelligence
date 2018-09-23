public class State {
	char[][] grid;
	int numberOfDragonGlassPieces;
	int JonC;
	int JonR;

	public State(char[][] grid) {
		this.grid = grid;
	}

	public State clone() {
		char[][] clonedGrid = new char[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				clonedGrid[i][j] = grid[i][j];
			}
		}
		State clonedState = new State(clonedGrid);
		clonedState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		clonedState.JonC = JonC;
		clonedState.JonR = JonR;
		return clonedState;

	}
}
