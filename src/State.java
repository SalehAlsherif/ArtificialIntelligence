public class State {
	BitField grid;
	int numberOfDragonGlassPieces;
	int JonC;
	int JonR;
int row;
int column;
	public State(BitField grid) {
		this.grid = grid;
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

	public State clone() {
		State clonedState = new State(BitSetFromgrid(gridFromBitSet(row,column,grid)));
		clonedState.numberOfDragonGlassPieces = numberOfDragonGlassPieces;
		clonedState.JonC = JonC;
		clonedState.JonR = JonR;
		return clonedState;
	}
	public static BitField BitSetFromgrid(char[][] targetGrid) {
		BitField b=new BitField(1);
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
}
