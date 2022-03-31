public class Player2Setup implements State {
	@Override
	public Boolean hit(int row, int column, PlayerData attacker, PlayerData defender) {
		return false;
	}

	@Override
	public Boolean setup(int row, int column, PlayerData player) {
		return player.addShip(row, column);
	}

	@Override
	public Enum nextState(int ownShips, int opponentSunkShips, GameState game) {
		if (ownShips == 5) {
			return screenState.SWITCH;
		}
		return screenState.NONE;
	}

	@Override
	public String getStateName() {
		return "Player 2 Setup";
	}
}
