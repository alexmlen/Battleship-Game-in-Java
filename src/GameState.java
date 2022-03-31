import java.util.Observable;

import javax.swing.*;

public class GameState extends Observable {
	private State state;
	private PlayerData player1;
	private PlayerData player2;
	private String winner;

	public GameState(PlayerData player1, PlayerData player2) {
		this.player1 = player1;
		this.player2 = player2;
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		winner = "Nobody";
		setState(new Player1Setup());
		updateStatus("Player1");
		updateStatus("Player2");
	}

	public void updateStatus(String player) {
		PlayerData playerData = getPlayer(player);
		PlayerData opponentData = getOpponent(player);
		String totalShips = "" + playerData.getShips();
		String sunkShipsPlayer = "" + playerData.getSunkShips();
		String sunkShipsOpponent = "" + opponentData.getSunkShips();
		String state = "" + getState().getStateName();
		if (winner.equals("Nobody")) {
			playerData.setStatus("Total Ships: " + totalShips + ", Sunken Ships: " + sunkShipsPlayer
					+ ", Opponent Sunken Ships: " + sunkShipsOpponent + ", " + state);
		} else {
			playerData.setStatus("Total Ships: " + totalShips + ", Own Sunken Ships: " + sunkShipsPlayer
					+ ", Opponent Sunken Ships: " + sunkShipsOpponent + ",  " + winner + " has won!");
		}
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setWinner(String winner) {
		this.winner = winner;
		JOptionPane.showMessageDialog(null, winner + " has won!");
		updateStatus(getOpponentString("Player1"));
		updateStatus(getOpponentString("Player2"));

	}

	public State getState() {
		return state;
	}

	public State getCellState() {
		return state;
	}

	public void nextState(String player) {
		String currentState = state.getStateName();
		int ownShips = getPlayer(player).getShips();
		int opponentShips = getOpponent(player).getSunkShips();
		Enum showState = state.nextState(ownShips, opponentShips, this);
		if (showState == screenState.NONE) {
			return;
		} else if (showState == screenState.SWITCH) {
			if (currentState.equals("Player 1 Setup")) {
				setState(new Player2Setup());
			} else if (currentState.equals("Player 2 Setup")) {
				setState(new Player1Turn());
			} else if (currentState.equals("Player 1's Turn")) {
				setState(new Player2Turn());
			} else if (currentState.equals("Player 2's Turn")) {
				setState(new Player1Turn());
			}
			getPlayer(player).setVisibility(false);
			getOpponent(player).setVisibility(true);
		} else if (showState == screenState.OVER) {
			getPlayer(player).setVisibility(true);
			getOpponent(player).setVisibility(true);
			setState(new GameOver());
		}
		updateStatus(getOpponentString(player));
		setChanged();
		notifyObservers(getOpponentString(player));
	}

	private PlayerData getPlayer(String player) {
		if (player.equals("Player1")) {
			return player1;
		}
		return player2;
	}

	private PlayerData getOpponent(String player) {
		if (player.equals("Player1")) {
			return player2;
		}
		return player1;
	}

	private String getOpponentString(String player) {
		if (player.equals("Player1")) {
			return "Player2";
		}
		return "Player1";
	}

	public void hit(int row, int column, String player) {// Player is person who is attacking
		PlayerData attacker = getPlayer(player);
		PlayerData defender = getOpponent(player);
		Boolean acted = state.hit(row, column, attacker, defender);
		if (acted == true) {
			updateStatus(player);
			setChanged();
			notifyObservers(player);
		}
	}

	public Enum getSelfEnum(int row, int column, String player) {
		PlayerData data = getPlayer(player);
		return data.getState(row, column);
	}

	public Enum getOpponentEnum(int row, int column, String player) {
		PlayerData data = getOpponent(player);
		return data.getState(row, column);
	}

	public boolean getHitsFired(int row, int column, String player) {
		PlayerData data = getPlayer(player);
		return data.getHitsFired(row, column);
	}

	public void setup(int row, int column, String player) {
		int ownShips = getPlayer(player).getShips();
		if (ownShips < 5) {
			Boolean acted = state.setup(row, column, getPlayer(player));
			if (acted == true) {
				updateStatus(player);
				setChanged();
				notifyObservers(player);
			}
		}
	}
}