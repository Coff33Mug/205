package pages;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.Database;
import main.Item;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import java.awt.Button;

public class PendingOrdersPage {

	public JFrame PendingOrderPage;
	private JList<Item> pendingJList;
    private JList<Item> completeJList;
    private List<Item> pendingList;
    private List<Item> completeList;
    Database DB = Database.getInstance();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PendingOrdersPage window = new PendingOrdersPage();
					window.PendingOrderPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PendingOrdersPage() {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		pendingList = new ArrayList<>();
        completeList = new ArrayList<>();
        
        
        try {
			Statement stmt = DB.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT itemname, quantity, price, orderfilled FROM public.pending;");
			
			while (rs.next()) {
				Boolean orderFilled = rs.getBoolean("orderfilled");
				
				if (orderFilled == false) {
						String itemName = rs.getString("itemname");
						int quantity = rs.getInt("quantity");
						double price = rs.getDouble("price");
						pendingList.add(new Item(itemName, quantity, price, DB.getItemID(itemName)));
				} else {
					String itemName = rs.getString("itemname");
					int quantity = rs.getInt("quantity");
					double price = rs.getDouble("price");
					completeList.add(new Item(itemName, quantity, price, DB.getItemID(itemName)));
				}
			}
		} catch (SQLException e) {
			System.out.println("PendingOrdersPage list error.");
			e.printStackTrace();
		}
        
        try {
			DB.getConnection().close();
		} catch (SQLException e) {
			System.out.print("Connection could not be closed");
			e.printStackTrace();
		}
        
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		PendingOrderPage = new JFrame();
		PendingOrderPage.setBounds(100, 100, 1000, 1000);
		PendingOrderPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PendingOrderPage.getContentPane().setLayout(null);
		
		JScrollPane pendingScrollPanel = new JScrollPane();
		pendingScrollPanel.setBounds(39, 68, 392, 472);
		PendingOrderPage.getContentPane().add(pendingScrollPanel);
		
		pendingJList = new JList<>(pendingList.toArray(new Item[0]));
		pendingScrollPanel.setViewportView(pendingJList);
		
		JScrollPane completeScrollPanel = new JScrollPane();
		completeScrollPanel.setBounds(482, 68, 392, 472);
		PendingOrderPage.getContentPane().add(completeScrollPanel);
        
        completeJList = new JList<>(completeList.toArray(new Item[0]));
        completeScrollPanel.setViewportView(completeJList);
        
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        searchField.setText("Search orders by ID or Item name");
        searchField.setBounds(39, 570, 392, 43);
        PendingOrderPage.getContentPane().add(searchField);
        searchField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				searchField.setText("");
			}
        });
        JLabel pendingOrdersLabel = new JLabel("Pending Orders");
        pendingOrdersLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
        pendingOrdersLabel.setBounds(40, 21, 221, 43);
        PendingOrderPage.getContentPane().add(pendingOrdersLabel);
        
        JLabel completeOrderLabel = new JLabel("Complete Orders");
        completeOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
        completeOrderLabel.setBounds(483, 21, 221, 43);
        PendingOrderPage.getContentPane().add(completeOrderLabel);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(39, 624, 100, 36);
        PendingOrderPage.getContentPane().add(searchButton);
        
        JButton completeButton = new JButton("Complete");
        completeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		completeItem();
        	}
        });
        completeButton.setBounds(149, 624, 100, 36);
        PendingOrderPage.getContentPane().add(completeButton);
        
        JButton unCompleteButton = new JButton("Pending ");
        unCompleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		unCompleteItem();
        	}
        });
        unCompleteButton.setBounds(259, 624, 100, 36);
        PendingOrderPage.getContentPane().add(unCompleteButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchItems(searchField.getText());
            }
        });
		
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		PendingOrderPage.dispose();
        		PendingOrdersPage POP = new PendingOrdersPage();
        		POP.PendingOrderPage.setVisible(true);
        	}
        });
        refreshButton.setBounds(714, 34, 89, 23);
        PendingOrderPage.getContentPane().add(refreshButton);
        
        JButton logoutButton = new JButton("logout");
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginPage LP = new LoginPage();
        		LP.LoginPage.setVisible(true);
        		PendingOrderPage.dispose();
        	}
        });
        logoutButton.setBounds(810, 34, 89, 23);
        PendingOrderPage.getContentPane().add(logoutButton);
		
	}
	
	private void searchItems(String query) {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
        List<Item> filteredPendingItems = new ArrayList<>();
        List<Item> filteredCompleteItems = new ArrayList<>();
        
        try {
			Statement stmt = DB.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT itemname, userid, orderid, orderfilled, quantity, price FROM public.pending;");
			
			while (rs.next()) {
				String itemName = rs.getString("itemname");
				int UserID = rs.getInt("userid");
				int orderID = rs.getInt("orderid");
				boolean isFilled = rs.getBoolean("orderfilled");
				
				if (UserID == Integer.parseInt(query) || orderID == Integer.parseInt(query) ) {
					double price = rs.getDouble("price");
					int quantity = rs.getInt("quantity");
					if (isFilled == false) {
						filteredPendingItems.add(new Item(itemName, quantity, price, UserID));
					} else {
						filteredCompleteItems.add(new Item(itemName, quantity, price, UserID));
					}
				}
				
			}
			
			pendingJList.setListData(filteredPendingItems.toArray(new Item[0]));
			completeJList.setListData(filteredCompleteItems.toArray(new Item[0]));
		} catch (SQLException e) {
			System.out.println("PendingOrdersPage list error.");
			e.printStackTrace();
		}
        // Disconnect connection
        try {
			DB.getConnection().close();
		} catch (SQLException e) {
			System.out.print("Connection could not be closed");
			e.printStackTrace();
		}
    }
	
	private void completeItem() {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		Item selectedItem = pendingJList.getSelectedValue();
		System.out.println(selectedItem.getID());
		if (selectedItem != null) {
			try {
				PreparedStatement stmt = DB.getConnection().prepareStatement("UPDATE public.pending SET orderfilled = true WHERE orderid =? OR userid =?");
				stmt.setInt(1, selectedItem.getID());
				stmt.setInt(2, selectedItem.getID());
				stmt.executeUpdate();
				System.out.println("item completed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Disconnect connection
        try {
			DB.getConnection().close();
		} catch (SQLException e) {
			System.out.print("Connection could not be closed");
			e.printStackTrace();
		}
	}
	
	private void unCompleteItem() {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		Item selectedItem = completeJList.getSelectedValue();
		System.out.println(selectedItem.getID());
		if (selectedItem != null) {
			try {
				PreparedStatement stmt = DB.getConnection().prepareStatement("UPDATE public.pending SET orderfilled = false WHERE orderid =? OR userid =?");
				stmt.setInt(1, selectedItem.getID());
				stmt.setInt(2, selectedItem.getID());
				stmt.executeUpdate();
				System.out.println("item pending");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Disconnect connection
        try {
			DB.getConnection().close();
		} catch (SQLException e) {
			System.out.print("Connection could not be closed");
			e.printStackTrace();
		}
	}
}
