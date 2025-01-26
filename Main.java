

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Main {
    Main() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ניהול חנות");
        frame.setDefaultCloseOperation(3);
        final int WIDTH = 300;
        int HEIGHT = 300;
        frame.setSize(WIDTH, HEIGHT);
        final List<Product> products = new ArrayList();
        products.add(new Product("גזר", 2, 4.0, 50, true));
        products.add(new Product("תפוח אדמה", 2, 4.0, 20, true));
        products.add(new Product("מחשב", 3000, 5000.0, 5, true));
        products.add(new Product("תפוח", 3, 4.0, 100, true));
        products.add(new Product("מברשת שיניים", 1, 1.5, 50, true));
        final CardLayout cardLayout = new CardLayout();
        final JPanel mainPanel = new JPanel(cardLayout);
        JPanel menuPanel = new JPanel((LayoutManager)null);
        final JPanel salesPanel = new JPanel((LayoutManager)null);
        final JPanel summaryPanel = new JPanel((LayoutManager)null);
        final JPanel stockPanel = new JPanel((LayoutManager)null);
        JPanel closePanel = new JPanel((LayoutManager)null);
        final JLabel menuLabel = new JLabel(globalValues.money + "$");
        JLabel menuLabel2 = new JLabel("ניהול חנות");
        menuLabel.setBounds(20, 0, 100, 30);
        menuLabel2.setBounds(frame.getWidth() / 2 - 30, 50, 100, 30);
        menuLabel.setForeground(Color.green.darker());
        menuPanel.add(menuLabel);
        menuPanel.add(menuLabel2);
        String[] buttonTexts = new String[]{"מכירה חדשה", "סיכום מכירות", "בדיקת מלאי", "יציאה"};
        String[] var15 = buttonTexts;
        int var16 = buttonTexts.length;

        for(int var17 = 0; var17 < var16; ++var17) {
            final String text = var15[var17];
            JButton button = new JButton(text);
            button.setBounds(75, 50 + 30 * (menuPanel.getComponentCount() - 1), 150, 30);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JLabel CashDisplay;
                    ArrayList plist;
                    JButton backToMenu2;
                    JLabel CashDisplayxx;
                    switch (text) {
                        case "מכירה חדשה":
                            cardLayout.show(mainPanel, "Sales");
                            salesPanel.removeAll();
                            CashDisplay = new JLabel("מכירה חדשה");
                            backToMenu2 = new JButton("חזרה לתפריט");
                            JLabel ErrorDisplay = new JLabel("");
                            JCheckBox save_info_box = new JCheckBox();
                            CashDisplayxx = new JLabel("שמור פרטי מוצר לאחר אישור:");
                            TextField saleProductField = new TextField("");
                            TextField saleAmountField = new TextField("");
                            JLabel salesProductLabel = new JLabel("מוצר: ");
                            JLabel salesAmountLabel = new JLabel("כמות: ");
                            JButton saleConfirmation = new JButton("אישור");
                            JLabel CashDisplayx = new JLabel(globalValues.money + "$");
                            ErrorDisplay.setBounds(50, 240, 250, 30);
                            CashDisplay.setBounds(110, 10, 100, 30);
                            backToMenu2.setBounds(200, 10, 100, 30);
                            CashDisplayx.setBounds(20, 0, 100, 30);
                            saleProductField.setBounds(100, 50, 100, 30);
                            saleAmountField.setBounds(100, 80, 100, 30);
                            CashDisplayxx.setBounds(100, 120, 300, 20);
                            save_info_box.setBounds(70, 120, 30, 20);
                            saleConfirmation.setBounds(200, 200, 100, 30);
                            salesProductLabel.setBounds(200, 50, 100, 30);
                            salesAmountLabel.setBounds(200, 80, 100, 30);
                            ErrorDisplay.setForeground(Color.red);
                            CashDisplayx.setForeground(Color.green.darker());
                            backToMenu2.addActionListener((ea) -> {
                                cardLayout.show(mainPanel, "Menu");
                            });
                            saleConfirmation.addActionListener((eb) -> {
                                HashMap<String, Boolean> returnedHash = Main.confirmSale(saleProductField, saleAmountField, products, ErrorDisplay);
                                boolean confirmed = false;
                                Product confirmedProduct = new Product("null", 1, 1.0, 1, false);
                                Iterator var11 = returnedHash.keySet().iterator();

                                while(true) {
                                    String key;
                                    do {
                                        if (!var11.hasNext()) {
                                            if (confirmed) {
                                                int amountSold = Integer.parseInt(saleAmountField.getText());
                                                confirmedProduct.stock = confirmedProduct.stock - amountSold;
                                                boolean save_info = save_info_box.isSelected();
                                                globalValues.money += confirmedProduct.sellPrice * (double)amountSold;
                                                CashDisplayx.setText(String.valueOf(globalValues.money) + "$");
                                                if (!save_info) {
                                                    saleAmountField.setText("");
                                                    saleProductField.setText("");
                                                }

                                                int amount_to_add = 0;
                                                int total_sold = amountSold;
                                                Iterator var15 = globalValues.log.iterator();

                                                while(var15.hasNext()) {
                                                    String s = (String)var15.next();
                                                    if (s.contains(confirmedProduct.name)) {
                                                        String regex = "\\d+";
                                                        Pattern pat = Pattern.compile(regex);
                                                        Matcher matcher = pat.matcher(s);
                                                        if (matcher.find()) {
                                                            String n_str = matcher.group();
                                                            amount_to_add = Integer.parseInt(n_str);
                                                        }

                                                        total_sold = amountSold + amount_to_add;
                                                        globalValues.log.remove(s);
                                                        break;
                                                    }
                                                }

                                                globalValues.log.add(String.format("מכרת %d מהמוצר %s", total_sold, confirmedProduct.name));
                                                CashDisplayx.setText(globalValues.money + "$");
                                                menuLabel.setText(globalValues.money + "$");
                                            }

                                            return;
                                        }

                                        key = (String)var11.next();
                                    } while(!key.equals(saleProductField.getText()));

                                    confirmed = (Boolean)returnedHash.get(key);
                                    Iterator var13 = products.iterator();

                                    while(var13.hasNext()) {
                                        Product p = (Product)var13.next();
                                        if (p.name.equals(key)) {
                                            confirmedProduct = p;
                                        }
                                    }
                                }
                            });
                            salesPanel.add(ErrorDisplay);
                            salesPanel.add(CashDisplayx);
                            salesPanel.add(CashDisplay);
                            salesPanel.add(backToMenu2);
                            salesPanel.add(saleProductField);
                            salesPanel.add(saleAmountField);
                            salesPanel.add(saleConfirmation);
                            salesPanel.add(salesProductLabel);
                            salesPanel.add(salesAmountLabel);
                            salesPanel.add(save_info_box);
                            salesPanel.add(CashDisplayxx);
                            break;
                        case "סיכום מכירות":
                            cardLayout.show(mainPanel, "Summary");
                            summaryPanel.removeAll();
                            CashDisplay = new JLabel("סיכום מכירות");
                            CashDisplay.setBounds(110, 10, 100, 30);
                            summaryPanel.add(CashDisplay);
                            backToMenu2 = new JButton("חזרה לתפריט");
                            backToMenu2.addActionListener((b) -> {
                                cardLayout.show(mainPanel, "Menu");
                            });
                            backToMenu2.setBounds(200, 10, 100, 30);
                            summaryPanel.add(backToMenu2);
                            int i = 0;
                            plist = new ArrayList();

                            for(Iterator var20 = globalValues.log.reversed().iterator(); var20.hasNext(); ++i) {
                                String s = (String)var20.next();
                                JLabel current_label = new JLabel(s);
                                current_label.setBounds(20, 50 + 20 * i, 300, 20);
                                plist.add(current_label);
                                summaryPanel.add(current_label);
                            }

                            CashDisplayxx = new JLabel(String.valueOf(globalValues.money) + "$");
                            CashDisplayxx.setBounds(20, 0, 100, 30);
                            CashDisplayxx.setText(globalValues.money + "$");
                            CashDisplayxx.setForeground(Color.green.darker());
                            menuLabel.setText(globalValues.money + "$");
                            summaryPanel.add(CashDisplayxx);
                            break;
                        case "בדיקת מלאי":
                            cardLayout.show(mainPanel, "Stock");
                            stockPanel.removeAll();
                            CashDisplay = new JLabel(String.valueOf(globalValues.money) + "$");
                            CashDisplay.setBounds(20, 0, 100, 30);
                            CashDisplay.setForeground(Color.green.darker());
                            JLabel stockLabel = new JLabel("בדיקת מלאי");
                            stockLabel.setBounds(110, 10, 100, 30);
                            stockPanel.add(stockLabel);
                            JButton backToMenu3 = new JButton("חזרה לתפריט");
                            backToMenu3.addActionListener((o) -> {
                                cardLayout.show(mainPanel, "Menu");
                            });
                            backToMenu3.setBounds(200, 10, 100, 30);
                            stockPanel.add(backToMenu3);
                            plist = new ArrayList();
                            HashMap<String, JButton> pRestockButtons = new HashMap();
                            int pIndex = 0;

                            for(Iterator var10 = products.iterator(); var10.hasNext(); ++pIndex) {
                                Product p = (Product)var10.next();
                                String str = "(%s) %s : %d / %d";
                                p.stock_percent = (double)p.stock / (double)p.maxStock * 100.0;
                                String str_res = String.format(str, String.valueOf(p.stock_percent) + "%", p.name, p.maxStock, p.stock);
                                JButton current_button = new JButton("RESTOCK");
                                current_button.setForeground(Color.green.darker());
                                plist.add(new JLabel(str_res));
                                JLabel current_labelx = (JLabel)plist.get(pIndex);
                                current_labelx.setBounds(WIDTH / 4 + 20, 50 + 20 * pIndex, 200, 20);
                                current_button.setBounds(WIDTH / 4 - 60, 50 + 20 * pIndex, 70, 20);
                                current_button.addActionListener((r) -> {
                                    do {
                                        if (globalValues.money > (double)p.price && p.stock < p.maxStock) {
                                            p.stock = p.stock + 1;
                                            globalValues.money -= (double)p.price;
                                        }
                                    } while(globalValues.money > (double)p.price && p.stock < p.maxStock);

                                    if (p.stock > p.maxStock) {
                                        p.stock = p.maxStock;
                                    }

                                    String stri = "(%s) %s : %d / %d";
                                    p.stock_percent = (double)p.stock / (double)p.maxStock * 100.0;
                                    System.out.println(p.stock_percent);
                                    System.out.println(p.stock);
                                    String stri_res = String.format(stri, p.stock_percent + "%", p.name, p.maxStock, p.stock);
                                    current_labelx.setText(stri_res);
                                    CashDisplay.setText(globalValues.money + "$");
                                    menuLabel.setText(globalValues.money + "$");
                                });
                                pRestockButtons.put(p.name, current_button);
                                stockPanel.add(current_button);
                                stockPanel.add(CashDisplay);
                                stockPanel.add(current_labelx);
                            }

                            return;
                        case "סגירת קופה":
                            cardLayout.show(mainPanel, "Close");
                            break;
                        case "יציאה":
                            System.exit(0);
                    }

                }
            });
            menuPanel.add(button);
        }

        JLabel closeLabel = new JLabel("סגירת קופה");
        closeLabel.setBounds(110, 10, 100, 30);
        closePanel.add(closeLabel);
        JButton backToMenu4 = new JButton("חזרה לתפריט");
        backToMenu4.addActionListener((e) -> {
            cardLayout.show(mainPanel, "Menu");
        });
        backToMenu4.setBounds(200, 10, 100, 30);
        closePanel.add(backToMenu4);
        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(salesPanel, "Sales");
        mainPanel.add(summaryPanel, "Summary");
        mainPanel.add(stockPanel, "Stock");
        mainPanel.add(closePanel, "Close");
        cardLayout.show(mainPanel, "Menu");
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static HashMap<String, Boolean> confirmSale(TextField productField, TextField amountField, List<Product> plist, JLabel ErrorDisplay) {
        boolean validAmount = false;
        boolean amountAvailable = false;
        boolean validProduct = false;
        boolean withinStock = false;

        int amount;
        try {
            validAmount = true;
            amount = Integer.valueOf(amountField.getText());
        } catch (NumberFormatException var12) {
            validAmount = false;
            amount = 9999999;
        }

        Product product = new Product("null", 0, 0.0, 1, false);
        Iterator var10 = plist.iterator();

        while(var10.hasNext()) {
            Product p = (Product)var10.next();
            if (p.name.equals(productField.getText())) {
                validProduct = true;
                product = p;
                amountAvailable = p.stock >= amount;
                break;
            }
        }

        withinStock = amountAvailable;
        if (!validProduct) {
            ErrorDisplay.setText("שם המוצר שהוזן אינו קיים במלאי!");
            ErrorDisplay.setForeground(Color.red);
        } else if (!validAmount) {
            ErrorDisplay.setText("הכמות שהוזנה אינה שלמה או מספרית!");
            ErrorDisplay.setForeground(Color.red);
        } else if (!withinStock) {
            ErrorDisplay.setText("הכמות שהוזנה מעל כמות המוצר במלאי!");
            ErrorDisplay.setForeground(Color.red);
        } else {
            ErrorDisplay.setText("המוצר נמכר בהצלחה!");
            ErrorDisplay.setForeground(Color.green.darker());
        }

        boolean valid = validProduct && validAmount && withinStock;
        HashMap<String, Boolean> returnHash = new HashMap();
        returnHash.put(product.name, valid);
        return returnHash;
    }
}
