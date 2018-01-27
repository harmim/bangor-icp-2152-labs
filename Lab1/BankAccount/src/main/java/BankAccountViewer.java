import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Exercise 6: Providing a GUI for BankAccount
 *
 * @author Dominik Harmim <harmim6@gmail.com>
 */
public class BankAccountViewer
{
	private JPanel mainPanel;
	private JButton withdrawButton;
	private JButton depositButton;
	private JTextField withdrawField;
	private JTextField depositField;
	private JButton showBalanceButton;
	private JLabel balanceLabel;

	private BankAccount account;


	public static void main(String[] args)
	{
		JFrame frame = new JFrame("BankAccountViewer");
		frame.setContentPane(new BankAccountViewer().mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}


	private BankAccountViewer()
	{
		account = new BankAccount();
		showBalance();

		withdrawButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				withdrawAction();
			}
		});
		depositButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				depositAction();
			}
		});
		showBalanceButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				showBalance();
			}
		});
	}


	private void withdrawAction()
	{
		if (!withdrawField.getText().equals("")) {
			try {
				account.withdraw(Double.parseDouble(withdrawField.getText()));
			} catch (NumberFormatException e) {}
		}
	}


	private void depositAction()
	{
		if (!depositField.getText().equals("")) {
			try {
				account.deposit(Double.parseDouble(depositField.getText()));
			} catch (NumberFormatException e) {}
		}
	}


	private void showBalance()
	{
		balanceLabel.setText(Double.toString(account.getBalance()));
	}
}
