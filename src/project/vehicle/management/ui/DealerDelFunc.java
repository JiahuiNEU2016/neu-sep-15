package project.vehicle.management.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.omg.PortableServer.RequestProcessingPolicyValue;

import project.vehicle.management.data.Car;
import project.vehicle.management.data.access.CarManager;
import project.vehicle.management.ui.DearlerMainScreen.MyTableModel;

@SuppressWarnings("serial")
public class DealerDelFunc extends JFrame {
	private JButton yes;
	private JButton no;
	private JLabel confirm;
	private JPanel temp;
	private JPanel button;
	private JPanel label;
	private JTable table;
	private Container con;
	String dealerid;
	public List<Integer> ret;
	public CarManager dealer;
	private MyTableModel tableM;

//	public DealerDelFunc(CarManager dealerRes, List<Car> res, JTable table) {
	public DealerDelFunc(CarManager dealerRes, List<Integer> res, MyTableModel tableM) {
		setTitle("Delete");
		create();
		add();
		addListeners();
		setSize(400, 130);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		this.tableM = tableM;
		this.ret = res;
		this.dealer = dealerRes;
		this.table = table;
		
	}

	private void addListeners() {
		ButtonClick bc = new ButtonClick();
		yes.addActionListener(bc);
		no.addActionListener(bc);
		
	}
	
	private void create() {
		yes = new JButton("yes");
		no = new JButton("no");
		confirm = new JLabel("Delete selected rows ?");
		label = new JPanel();
		temp = new JPanel();
		con = super.getContentPane();
		button = new JPanel(new CardLayout());
		
	}

	private void add() {

		temp.add(yes);
		temp.add(no);
		label.add(confirm);
		button.add(temp);
		con.add(button, BorderLayout.CENTER);
		con.add(label, BorderLayout.PAGE_START);

	}

	class ButtonClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == yes)
				try {
					delete(ret);
					for(int i = 0; i<ret.size(); i++)
						tableM.deleteOneRow(ret.get(i));
					tableM.fireTableRowsDeleted(0, tableM.getRowCount()-1);
					dispose();
			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				dispose();
		}
	}

	private void delete(List<Integer> ret) throws IOException {

		for (Integer index: ret)
			dealer.deleteCar(tableM.getCars().get(index).getID());
	}

	public static void main(String[] args) {

	}

}