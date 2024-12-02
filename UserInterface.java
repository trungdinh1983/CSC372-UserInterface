import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserInterface extends JFrame implements ActionListener {
  private JTextArea textArea;
  private JMenuItem dateTimeItem, saveItem, bgColorItem, exitItem;
  private Random random;

  public UserInterface() {
    setTitle("User Interface");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(400, 300));

    // Create menu bar and menu
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Options");

    // Create menu items
    dateTimeItem = new JMenuItem("Print Date/Time");
    saveItem = new JMenuItem("Save to File");
    bgColorItem = new JMenuItem("Change Background Color");
    exitItem = new JMenuItem("Exit");

    // Add action listeners to menu items
    dateTimeItem.addActionListener(this);
    saveItem.addActionListener(this);
    bgColorItem.addActionListener(this);
    exitItem.addActionListener(this);

    // Add menu items to menu
    menu.add(dateTimeItem);
    menu.add(saveItem);
    menu.add(bgColorItem);
    menu.add(exitItem);

    // Add menu to menu bar
    menuBar.add(menu);
    setJMenuBar(menuBar);

    // Create text area
    textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    random = new Random();

    pack();
    setLocationRelativeTo(null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == dateTimeItem) {
      LocalDateTime now = LocalDateTime.now();
      String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      textArea.append("Current Date and Time: " + formattedDateTime + "\n");
    } else if (e.getSource() == saveItem) {
      try {
        FileWriter writer = new FileWriter("log.txt");
        writer.write(textArea.getText());
        writer.close();
        JOptionPane.showMessageDialog(this, "Text saved to log.txt");
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
      }
    } else if (e.getSource() == bgColorItem) {
      float hue = random.nextFloat();
      Color randomGreen = Color.getHSBColor(hue, 1.0f, 1.0f);
      getContentPane().setBackground(randomGreen);
      bgColorItem.setText("Change Background Color (Current Hue: " + String.format("%.2f", hue) + ")");
    } else if (e.getSource() == exitItem) {
      System.exit(0);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      UserInterface ui = new UserInterface();
      ui.setVisible(true);
    });
  }
}