package pages;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.Database;
import main.Item;
import javax.swing.JLabel;
import java.awt.Font;

public class EmpItemPage {

	public JFrame EmpItemPage;
	private JList<Item> itemList;
	private List<Item> items;
	private JTextField searchField;
    Database DB = Database.getInstance();
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTextField inputNameField;
    private JTextField inputPriceField;
    private JLabel NameLabel;
    private JLabel priceLabel;
    private JButton changePriceButton;
    private JTextField inputChangePriceField;
    private JLabel newItemPriceLabel;
    private JButton refreshButton;
    private JButton logoutButton;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpItemPage window = new EmpItemPage();
					window.EmpItemPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmpItemPage() {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
    	items = new ArrayList<>();
    	
		try {
			Statement stmt = DB.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, price FROM public.items;");
			
			while (rs.next()) {
				int itemID = rs.getInt("id");
				String itemName = rs.getString("name");
				double itemPrice = rs.getDouble("price");
				
				items.add(new Item(itemName, 1, itemPrice, itemID));
			}
		} catch (SQLException e) {
			System.out.println("Shopping list error.");
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EmpItemPage = new JFrame();
		EmpItemPage.setBounds(100, 100, 800, 600);
		EmpItemPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EmpItemPage.getContentPane().setLayout(null);
        
        searchField = new JTextField();
        searchField.setBounds(26, 94, 284, 25);
        EmpItemPage.getContentPane().add(searchField);
        
        JScrollPane itemScrollPane = new JScrollPane();
        itemScrollPane.setBounds(26, 130, 400, 400);
        EmpItemPage.getContentPane().add(itemScrollPane);

        itemList = new JList<>(items.toArray(new Item[0]));
        itemScrollPane.setViewportView(itemList);
        
        inputNameField = new JTextField();
        inputNameField.setBounds(568, 95, 178, 23);
        EmpItemPage.getContentPane().add(inputNameField);
        inputNameField.setColumns(10);
        
        inputPriceField = new JTextField();
        inputPriceField.setColumns(10);
        inputPriceField.setBounds(568, 158, 178, 23);
        EmpItemPage.getContentPane().add(inputPriceField);
        
        
        
        
        inputChangePriceField = new JTextField();
        inputChangePriceField.setColumns(10);
        inputChangePriceField.setBounds(568, 259, 178, 23);
        EmpItemPage.getContentPane().add(inputChangePriceField);
        
        changePriceButton = new JButton("Change price");
        changePriceButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Item selectedItem = itemList.getSelectedValue();
        		try {
					DB.updateItemPrice(selectedItem.getName(), selectedItem.getID(), Double.parseDouble(inputChangePriceField.getText()));
					JOptionPane.showMessageDialog(EmpItemPage, "Item price updated");
					refreshItemList();
					itemList.setListData(items.toArray(new Item[0]));
				} catch (NumberFormatException e1) {
					System.out.println("Item not found");
					JOptionPane.showMessageDialog(EmpItemPage, "Item could not be found.");
					e1.printStackTrace();
				}
        	}
        });
        changePriceButton.setBounds(567, 293, 100, 23);
        EmpItemPage.getContentPane().add(changePriceButton);
        
        newItemPriceLabel = new JLabel("New Item Price");
        newItemPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        newItemPriceLabel.setBounds(568, 240, 116, 14);
        EmpItemPage.getContentPane().add(newItemPriceLabel);
		
		JButton searchButton = new JButton("Search");
        searchButton.setBounds(320, 94, 106, 25);
        EmpItemPage.getContentPane().add(searchButton);
        
        JLabel lblNewLabel = new JLabel("Item Modification Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 34));
        lblNewLabel.setBounds(26, 29, 362, 41);
        EmpItemPage.getContentPane().add(lblNewLabel);
        
        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					DB.addItem(inputNameField.getText(), Double.parseDouble(inputPriceField.getText()));
					items.add(new Item(inputNameField.getText(), 1, Double.parseDouble(inputPriceField.getText())));
					JOptionPane.showMessageDialog(EmpItemPage, "Item added, please refresh.");
					itemList.setListData(items.toArray(new Item[0]));
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					System.out.print("Could not add item in EmpItemPage");
					e1.printStackTrace();
				}
        	}
        });
        addItemButton.setBounds(568, 192, 89, 23);
        EmpItemPage.getContentPane().add(addItemButton);
        
        deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Item selectedItem = itemList.getSelectedValue();
        		if (selectedItem != null) {
        			try {
						DB.deleteItem(selectedItem.getName(), selectedItem.getID());
						JOptionPane.showMessageDialog(EmpItemPage, "Item deleted, please refresh.");
						refreshItemList();
						itemList.setListData(items.toArray(new Item[0]));
					} catch (SQLException e1) {
						System.out.print("Could not find item");
						e1.printStackTrace();
					}
        		}
        	}
        });
        deleteItemButton.setBounds(436, 95, 100, 23);
        EmpItemPage.getContentPane().add(deleteItemButton);
        
        
        NameLabel = new JLabel("Item Name");
        NameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        NameLabel.setBounds(568, 78, 74, 14);
        EmpItemPage.getContentPane().add(NameLabel);
        
        priceLabel = new JLabel("Item Price");
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        priceLabel.setBounds(568, 142, 74, 14);
        EmpItemPage.getContentPane().add(priceLabel);
        
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EmpItemPage.dispose();
        		EmpItemPage EMP = new EmpItemPage();
        		EMP.EmpItemPage.setVisible(true);
        	}
        });
        refreshButton.setBounds(685, 11, 89, 23);
        EmpItemPage.getContentPane().add(refreshButton);
        
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LoginPage LP = new LoginPage();
        		LP.LoginPage.setVisible(true);
        		EmpItemPage.dispose();
        	}
        });
        logoutButton.setBounds(587, 11, 89, 23);
        EmpItemPage.getContentPane().add(logoutButton);
        
        
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchItems(searchField.getText());
            }
        });
	}
	
	private void searchItems(String query) {
        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        itemList.setListData(filteredItems.toArray(new Item[0]));
    }
	
	public void refreshItemList() {
		DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
    	items.clear();
    	
		try {
			Statement stmt = DB.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, price FROM public.items;");
			
			while (rs.next()) {
				int itemID = rs.getInt("id");
				String itemName = rs.getString("name");
				double itemPrice = rs.getDouble("price");
				
				items.add(new Item(itemName, 1, itemPrice, itemID));
			}
		} catch (SQLException e) {
			System.out.println("EmpItemPage error.");
			e.printStackTrace();
		}
		initialize();
	}
}
