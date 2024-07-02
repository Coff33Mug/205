package pages;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

import main.Database;
import main.Item;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.List;
import java.util.ArrayList;

public class ShoppingPage {

    public JFrame ShoppingPage;
    public JButton ManagerPageButton;
    private JTextField searchField;
    private JTextField quantityField;
    private JList<Item> itemList;
    private JList<Item> cartList;
    private List<Item> items;
    private List<Item> cart;
    Database DB = Database.getInstance();

    /**
     * Launch the application.
     */
    public void Launch() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShoppingPage window = new ShoppingPage();
                    window.ShoppingPage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ShoppingPage() {
    	
    	DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
    	items = new ArrayList<>();
        cart = new ArrayList<>();
    	
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
        ShoppingPage = new JFrame();
        ShoppingPage.setBounds(100, 100, 1000, 1000);
        ShoppingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShoppingPage.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Shopping Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
        lblNewLabel.setBounds(10, -4, 500, 90);
        ShoppingPage.getContentPane().add(lblNewLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginPage LP = new LoginPage();
                LP.LoginPage.setVisible(true);
                ShoppingPage.dispose();
            }
        });
        logoutButton.setBounds(885, 11, 89, 23);
        ShoppingPage.getContentPane().add(logoutButton);

        ManagerPageButton = new JButton("Manage");
        ManagerPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerPage MP = new ManagerPage();
                MP.ManagerPage.setVisible(true);
                ShoppingPage.dispose();
            }
        });
        ManagerPageButton.setBounds(885, 45, 89, 23);
        ShoppingPage.getContentPane().add(ManagerPageButton);

        searchField = new JTextField();
        searchField.setBounds(10, 100, 200, 25);
        ShoppingPage.getContentPane().add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(220, 100, 100, 25);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchItems(searchField.getText());
            }
        });
        ShoppingPage.getContentPane().add(searchButton);

        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setBounds(10, 150, 60, 25);
        ShoppingPage.getContentPane().add(lblQuantity);

        quantityField = new JTextField();
        quantityField.setBounds(80, 150, 50, 25);
        ShoppingPage.getContentPane().add(quantityField);

        JButton addButton = new JButton("Add to Cart");
        addButton.setBounds(140, 150, 120, 25);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemToCart();
            }
        });
        ShoppingPage.getContentPane().add(addButton);

        JLabel lblItems = new JLabel("Items:");
        lblItems.setBounds(10, 200, 50, 25);
        ShoppingPage.getContentPane().add(lblItems);

        JScrollPane itemScrollPane = new JScrollPane();
        itemScrollPane.setBounds(10, 230, 310, 200);
        ShoppingPage.getContentPane().add(itemScrollPane);

        itemList = new JList<>(items.toArray(new Item[0]));
        itemScrollPane.setViewportView(itemList);

        JLabel lblCart = new JLabel("Cart:");
        lblCart.setBounds(350, 200, 50, 25);
        ShoppingPage.getContentPane().add(lblCart);

        JScrollPane cartScrollPane = new JScrollPane();
        cartScrollPane.setBounds(350, 230, 310, 200);
        ShoppingPage.getContentPane().add(cartScrollPane);

        cartList = new JList<>(cart.toArray(new Item[0]));
        cartScrollPane.setViewportView(cartList);

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.setBounds(350, 450, 100, 25);
        viewCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });
        ShoppingPage.getContentPane().add(viewCartButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(460, 450, 100, 25);
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        ShoppingPage.getContentPane().add(checkoutButton);
        
        JButton clearCartButton = new JButton("Clear Cart");
        clearCartButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(ShoppingPage, "Bro your cart is empty.");
                } else {
                	cart.clear();
            		cartList.setListData(cart.toArray(new Item[0]));
            		JOptionPane.showMessageDialog(ShoppingPage, "Cart cleared.");
                }
        	}
        });
        clearCartButton.setBounds(270, 151, 120, 25);
        ShoppingPage.getContentPane().add(clearCartButton);
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

    private void addItemToCart() {
        Item selectedItem = itemList.getSelectedValue();
        if (selectedItem != null) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0) {
                    cart.add(new Item(selectedItem.getName(), quantity, selectedItem.getPrice(), 0));
                    cartList.setListData(cart.toArray(new Item[0]));
                    JOptionPane.showMessageDialog(ShoppingPage, "Item added to cart!");
                } else {
                    JOptionPane.showMessageDialog(ShoppingPage, "Quantity must be greater than 0!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(ShoppingPage, "Please enter a valid quantity!");
            }
        } else {
            JOptionPane.showMessageDialog(ShoppingPage, "Please select an item to add to cart!");
        }
    }

    private void viewCart() {
        cartList.setListData(cart.toArray(new Item[0]));
        JOptionPane.showMessageDialog(ShoppingPage, "Viewing cart!");
    }

    private void checkout() {
    	
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(ShoppingPage, "Cart is empty!");
        } else {
            int totalItems = 0;
            for (Item item : cart) {
                totalItems += item.getQuantity();
                DB.checkoutAddItem(item.getName(), item.getQuantity(), item.getID());
            }
            JOptionPane.showMessageDialog(ShoppingPage, "Your order for " + totalItems + " items was completed successfully.");
            cart.clear();
            cartList.setListData(cart.toArray(new Item[0]));
        }
    }
}
