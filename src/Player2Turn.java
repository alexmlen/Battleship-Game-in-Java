public class Player2Turn implements State {
	private boolean moved = false;

	@Override
	public Boolean hit(int row, int column, PlayerData attacker, PlayerData defender) {
		if (moved == true) {
			return false;
		}
		Enum state = defender.getState(row, column);
		if (state == cellStates.HIT || state == cellStates.MISS) {
			return false;
		} else if (state == cellStates.SHIPLEFT || state == cellStates.SHIPMIDDLE || state == cellStates.SHIPRIGHT) {
			defender.setState(row, column, cellStates.HIT);
			attacker.setHitsFired(row, column, true);
			defender.calculateShips(row, column, state);
			moved = true;
			return true;
		} else if (state == cellStates.WATER) {
			defender.setState(row, column, cellStates.MISS);
			attacker.setHitsFired(row, column, true);
			moved = true;
			return true;
		}
		return false;
	}

	@Override
	public Boolean setup(int row, int column, PlayerData player) {
		return false;
	}

	@Override
	public Enum nextState(int ownShips, int opponentSunkShips, GameState game) {
		if (opponentSunkShips == 5) {
			game.setWinner("Player2");
			return screenState.OVER;
		}
		if (moved == false) {
			return screenState.NONE;
		}
		return screenState.SWITCH;
	}

	@Override
	public String getStateName() {
		return "Player 2's Turn";
	}
}
