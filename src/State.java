public interface State {
	public Boolean hit(int row, int column, PlayerData attacker, PlayerData defender);

	public Boolean setup(int row, int column, PlayerData player);

	public Enum nextState(int ownShips, int opponentSunkShips, GameState game);

	public String getStateName();
}