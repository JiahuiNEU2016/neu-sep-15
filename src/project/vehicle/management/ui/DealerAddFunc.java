package project.vehicle.management.ui;
/**
 * Author: Xin DING
 * Team 3: DealerScreen
 */
import javax.swing.*;

import project.vehicle.management.data.Car;
import project.vehicle.management.data.access.CarManagerFactory;
import project.vehicle.management.data.access.CarManagerImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
public class DealerAddFunc extends JFrame{
	JLabel noteInformation,Model,DealerID,Trim;
	JLabel Category,Year, Type, Price;
	JTextField textModel,textDealerID, textTrim,textMake,textID,textPrice;
	JRadioButton NewType,OldType,Type1, Type2, Type3, Type4,Type5;
	JComboBox year;
	JButton submit,cancel;

	
	GridBagLayout g = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	public project.vehicle.management.data.Category category;
	DealerAddFunc(String str)
	{
		super(str);
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(g);
		//use functions
		addComponent();
		setVisible(true);
		addListeners();
		//display in the middle
		setLocationRelativeTo(null);
	}
	
	
	private void addListeners() {
		ButtonClick bc = new ButtonClick();
		submit.addActionListener(bc);
		cancel.addActionListener(bc);
	}

	class ButtonClick implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//event for cancel
			if(e.getSource()==cancel){
				dispose();
	        }
			//event for submit
			if(e.getSource()==submit){
				String id=textID.getText();
				String did=textDealerID.getText();
				String nt=NewType.getText();
				String ot=OldType.getText();
				Integer y=(Integer) year.getSelectedItem();
				String m=textMake.getText();
				String mo=textModel.getText();
				String t=textTrim.getText();
				String t1=Type1.getText();
				String t2=Type2.getText();
				String t3=Type3.getText();
				String t4=Type4.getText();
				String t5=Type5.getText();
				String p=textPrice.getText();
				float pf = Float.parseFloat(p); 
				if(nt==null){
					String category = ot;
				}
				else{
					String category = nt;
				}
				String type;
				if(t1 != null){
					type = t1;
				}
				else if(t2 != null){
					type = t2;
				}
				else if(t3 != null){
					type = t3;
				}
				else if(t4 != null){
					type = t4;
				}
				else {
					type = t5;
				}
				Car addcar = new Car(id, did, category, y, m, mo, t, type, pf);
				try {
					new CarManagerFactory().getCarManager(did).addCar(addcar);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				
				
			}
			
		}
		

	//Add all components
	public void addComponent(){
		//Title
		noteInformation=new JLabel("Add A Car!");
		add(g,c,noteInformation,0,0,1,1);
		//ID
		Model=new JLabel("ID:");
		add(g,c,Model,0,1,1,1);
		//Input ID
		textModel=new JTextField(10);
		add(g,c,textModel,1,1,2,1);
		//DealerID
		DealerID=new JLabel("Dealer ID:");
		add(g,c,DealerID,0,2,1,1);
		//Input Dealer ID
		textDealerID=new JTextField(10);
		add(g,c,textDealerID,1,2,2,1);
		//Category
		Category=new JLabel("Category:");
		add(g,c,Category,0,3,1,1);
		//Choose from the new and old
		NewType=new JRadioButton("New");
		OldType=new JRadioButton("Used");
		ButtonGroup group=new ButtonGroup();
		group.add(NewType);
		group.add(OldType);
		add(g,c,NewType,1,3,1,1);
		add(g,c,OldType,2,3,1,1);
		//Year
		Year=new JLabel("Year:");
		add(g,c,Year,0,4,1,1);
		//content of year
		String[] YEARS=new String[16];
		for(int i=2000,k=0;i<=2015;i++,k++)
		{
		YEARS[k]=i+"";
		}
		year=new JComboBox(YEARS);
		add(g,c,year,1,4,1,1);
		//Make
		Model=new JLabel("Make:");
		add(g,c,Model,0,5,1,1);
		//Input Make
		textModel=new JTextField(10);
		add(g,c,textModel,1,5,2,1);
		//Model
		Model=new JLabel("Model:");
		add(g,c,Model,0,6,1,1);
		//Input Model
		textModel=new JTextField(10);
		add(g,c,textModel,1,6,2,1);
		//Trim
		Trim=new JLabel("Trim:");
		add(g,c,Trim,0,7,1,1);
		//Input Trim
		textTrim=new JTextField(20);
		add(g,c,textTrim,1,7,2,1);
		//Type
		Type=new JLabel("Type:");
		add(g,c,Type,0,8,1,1);
		//Choose from the types
		Type1=new JRadioButton("CAR");
		add(g,c,Type1,1,8,1,1);
		Type2=new JRadioButton("TRUCK");
		add(g,c,Type2,2,8,1,1);
		Type3=new JRadioButton("WAGON");
		add(g,c,Type3,1,10,1,1);
		Type4=new JRadioButton("VAN");
		add(g,c,Type4,1,9,1,1);
		Type5=new JRadioButton("SUV");
		add(g,c,Type5,2,9,1,1);

		group.add(Type1);
		group.add(Type2);
		group.add(Type3);
		group.add(Type4);
		group.add(Type5);
		//Price
		Price=new JLabel("Price:");
		add(g,c,Price,0,11,1,1);
		//Input Price
		textPrice=new JTextField(10);
		add(g,c,textPrice,1,11,2,1);
		
		
		//Submit button
		submit=new JButton("Submit");
		add(g,c,submit,1,13,1,1);
		//Cancel button
		cancel = new JButton("Cancel");
		add(g, c, cancel, 2, 13, 1, 1);
		
		JLabel head = new JLabel(new ImageIcon("pictures/DealerScreen.jpg"));
		add(g, c, head, 0, 0, 0, 0);
	}
	public void actionPerformed(ActionEvent e){
		
		
	}   
		

	public void add(GridBagLayout g,GridBagConstraints c,JComponent jc,int x ,int y,int gw,int gh){
		
		c.gridx=x;
		c.gridy=y;
		c.anchor=GridBagConstraints.WEST;
		c.gridwidth=gw;
		c.gridheight=gh;
		g.setConstraints(jc,c);
		add(jc);
	}
	public static void main(String args[]){
		
		new DealerAddFunc("Add Function");
	}


	
		
	
		
	
	
}
