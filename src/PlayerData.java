import java.util.Observable;

public class PlayerData extends Observable {
	private String name;
	private Enum[][] states;
	private Boolean[][] hitsFired;
	private PlayerData opponent;
	private int shipTotal;
	private int sunkShips;
	private final int BOARD_SIZE = 10;
	private String playerStatus;
	private Boolean screenVisibility;

	public PlayerData(String name) {
		this.name = name;
		states = new Enum[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				states[j][i] = cellStates.WATER;
			}
		}
		hitsFired = new Boolean[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				hitsFired[j][i] = false;
			}
		}
		shipTotal = 0;
		sunkShips = 0;
		screenVisibility = false;
	}

	public void getAllStates() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(states[j][i] + " ");
			}
			System.out.println("");
		}
	}

	public void setVisibility(Boolean visibility) {
		screenVisibility = visibility;
		setChanged();
		notifyObservers();
	}

	public Boolean getVisibility() {
		return screenVisibility;
	}

	public void setStatus(String status) {
		playerStatus = status;
		setChanged();
		notifyObservers();
	}

	public String getStatus() {
		return playerStatus;
	}

	public void calculateShips(int row, int column, Enum position) {
		Enum hitLeft = null;
		Enum hitMiddle = null;
		Enum hitRight = null;
		if (position == cellStates.SHIPLEFT) {
			hitLeft = getState(row, column);
			hitMiddle = getState(row, column + 1);
			hitRight = getState(row, column + 2);
		} else if (position == cellStates.SHIPMIDDLE) {
			hitLeft = getState(row, column - 1);
			hitMiddle = getState(row, column);
			hitRight = getState(row, column + 1);
		} else if (position == cellStates.SHIPRIGHT) {
			hitLeft = getState(row, column - 2);
			hitMiddle = getState(row, column - 1);
			hitRight = getState(row, column);
		}
		if (hitLeft == cellStates.HIT && hitMiddle == cellStates.HIT && hitRight == cellStates.HIT) {
			sunkShips++;
			if (position == cellStates.SHIPLEFT) {
				setState(row, column, cellStates.SUNK);
				setState(row, column + 1, cellStates.SUNK);
				setState(row, column + 2, cellStates.SUNK);
			} else if (position == cellStates.SHIPMIDDLE) {
				setState(row, column - 1, cellStates.SUNK);
				setState(row, column, cellStates.SUNK);
				setState(row, column + 1, cellStates.SUNK);
			} else if (position == cellStates.SHIPRIGHT) {
				setState(row, column - 2, cellStates.SUNK);
				setState(row, column - 1, cellStates.SUNK);
				setState(row, column, cellStates.SUNK);
			}
		}
	}

	public Enum getState(int row, int column) {
		return states[column][row];
	}

	public void setState(int row, int column, Enum state) {
		states[column][row] = state;
	}

	public Boolean getHitsFired(int row, int column) {
		return hitsFired[column][row];
	}

	public void setHitsFired(int row, int column, Boolean state) {
		hitsFired[column][row] = state;
	}

	public int getShips() {
		return shipTotal;
	}

	public int getSunkShips() {
		return sunkShips;
	}

	public void setShips(int num) {
		shipTotal = num;
	}

	public Boolean addShip(int row, int column) {
		if (column > 7) {
			return false;
		}
		if (states[column][row] == cellStates.WATER && states[column + 1][row] == cellStates.WATER
				&& states[column + 2][row] == cellStates.WATER) {
			states[column][row] = cellStates.SHIPLEFT;
			states[column + 1][row] = cellStates.SHIPMIDDLE;
			states[column + 2][row] = cellStates.SHIPRIGHT;
			shipTotal++;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}

	public void setOpponent(PlayerData opponent) {
		this.opponent = opponent;
	}

}
