import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Represents the player's own grid
 */
public class AttackGrid extends BattleGrid implements Observer {
	private String name;
	private GameState game;

	public AttackGrid(String name, GameState game) {
		super(name, game);
		this.name = name;
		this.game = game;
		game.addObserver(this);
	}

	@Override
	protected JPanel getCell(int row, int column, String name, GameState game) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.red, 5));
		panel.setPreferredSize(new Dimension(20, 20)); // for demo purposes only
		panelGrid[column][row] = panel;
		panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				game.hit(row, column, name);
			}
		});

		return panel;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String player = (String) arg1;
		if (player.equals(name)) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					Boolean hit = game.getHitsFired(i, j, player);
					if (hit == true) {
						Enum state = game.getOpponentEnum(i, j, player);
						if (state == cellStates.HIT) {
							panelGrid[j][i].setBackground(Color.green);
							panelGrid[j][i].setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
						} else if (state == cellStates.SUNK) {
							panelGrid[j][i].setBackground(Color.green);
							panelGrid[j][i].setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
						} else if (state == cellStates.MISS) {
							panelGrid[j][i].setBackground(Color.red);
						} else if (state == cellStates.SHIPLEFT || state == cellStates.SHIPMIDDLE
								|| state == cellStates.SHIPRIGHT) {
							panelGrid[j][i].setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
						}
					}
				}
			}
		}
	}
}