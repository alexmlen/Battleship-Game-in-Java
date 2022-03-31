import javax.swing.*;
import java.awt.*;

public abstract class BattleGrid extends JPanel {
	protected JPanel[][] panelGrid;

	public BattleGrid(String name, GameState context) {
		panelGrid = new JPanel[10][10];
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel self = new JPanel();
		self.setLayout(new GridLayout(0, 10));
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				self.add(getCell(i, j, name, context));
			}
		this.add(self);
	}

	protected abstract JPanel getCell(int row, int column, String name, GameState context);
}