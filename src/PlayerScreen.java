import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class PlayerScreen extends JFrame implements Observer {
	private String name;
	private GameState game;
	private JLabel status;
	private PlayerData data;

	public PlayerScreen(String name, GameState game, PlayerData data) {
		super("Battleship");
		this.game = game;
		this.data = data;
		this.name = name;
		this.setLayout(new BorderLayout());
		this.add(new SelfGrid(name, game), BorderLayout.EAST);
		this.add(new AttackGrid(name, game), BorderLayout.WEST);
		this.add(new JLabel(name), BorderLayout.NORTH);
		this.add(status = new JLabel("Status"), BorderLayout.SOUTH);
		JButton next = new JButton("next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.nextState(name);
			}
		});
		this.add(next, BorderLayout.CENTER);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.status.setText(data.getStatus());
		this.setVisible(data.getVisibility());
	}
}