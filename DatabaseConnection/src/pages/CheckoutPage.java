package pages;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import main.Database;
import main.Item;

public class CheckoutPage {

	Database DB = Database.getInstance();
    public JFrame CheckoutPage;
    private JList<Item> cartList;
    private List<Item> cart;

    public CheckoutPage(List<Item> cart) {
        this.cart = cart;
        initialize();
    }

    private void initialize() {
        CheckoutPage = new JFrame();
        CheckoutPage.setBounds(100, 100, 450, 300);
        CheckoutPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CheckoutPage.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Checkout Page");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel.setBounds(10, 10, 200, 40);
        CheckoutPage.getContentPane().add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 60, 400, 150);
        CheckoutPage.getContentPane().add(scrollPane);

        cartList = new JList<>(cart.toArray(new Item[0]));
        scrollPane.setViewportView(cartList);

        JButton confirmButton = new JButton("Confirm Purchase");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmPurchase();
            }
        });
        confirmButton.setBounds(10, 220, 150, 30);
        CheckoutPage.getContentPane().add(confirmButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShoppingPage shoppingPage = new ShoppingPage(cart);
                shoppingPage.ShoppingPage.setVisible(true);
                CheckoutPage.dispose();
            }
        });
        backButton.setBounds(170, 220, 100, 30);
        CheckoutPage.getContentPane().add(backButton);
    }

    private void confirmPurchase() {
    	if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(CheckoutPage, "Cart is empty!");
        } else {
            int totalItems = 0;
            for (Item item : cart) {
                totalItems += item.getQuantity();
                DB.pendingAddItem(item.getName(), item.getQuantity(), item.getID());
            }
            JOptionPane.showMessageDialog(CheckoutPage, "Your order for " + totalItems + " items was completed successfully.");
            cart.clear();
            cartList.setListData(cart.toArray(new Item[0]));
        }
    }
}
