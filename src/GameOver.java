public class GameOver implements State {

	@Override
	public Boolean hit(int row, int column, PlayerData attacker, PlayerData defender) {
		return false;
	}

	@Override
	public Boolean setup(int row, int column, PlayerData player) {
		return false;
	}

	@Override
	public Enum nextState(int ownShips, int opponentShips, GameState game) {
		return screenState.NONE;
	}

	public String getStateName() {
		return "GAME OVER";
	}
}
