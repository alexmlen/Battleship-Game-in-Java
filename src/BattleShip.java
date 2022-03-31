public class BattleShip {
	public static void main(String[] args) {
		PlayerData player1 = new PlayerData("Player1");
		PlayerData player2 = new PlayerData("Player2");
		GameState game = new GameState(player1, player2);
		PlayerScreen player1Screen = new PlayerScreen("Player1", game, player1);
		PlayerScreen player2Screen = new PlayerScreen("Player2", game, player2);
		player1.addObserver(player1Screen);
		player2.addObserver(player2Screen);
		player1.setVisibility(true);
	}
}