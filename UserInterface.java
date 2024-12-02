import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UserInterface extends JFrame implements ActionListener {
  private JTextArea textArea;
  private JMenuItem dateTimeItem, saveItem, bgColorItem, exitItem;
  private Random random;
  private JPanel contentPanel;

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
    bgColorItem = new JMenuItem("Change Background to Random Green");
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

    // Create content panel
    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(Color.WHITE);

    // Create a sub-panel for the text area with padding
    JPanel textAreaPanel = new JPanel(new BorderLayout());
    textAreaPanel.setBackground(Color.WHITE);
    textAreaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

    // Create text area
    textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    textAreaPanel.add(scrollPane, BorderLayout.CENTER);

    // Add the text area panel to the content panel
    contentPanel.add(textAreaPanel, BorderLayout.CENTER);

    // Set the main content pane
    setContentPane(contentPanel);

    random = new Random();
    pack();
    setLocationRelativeTo(null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == dateTimeItem) {
      // Print date and time to text area
      LocalDateTime now = LocalDateTime.now();
      String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      textArea.append("Current Date and Time: " + formattedDateTime + "\n");
    } else if (e.getSource() == saveItem) {
      // Save text area content to a file
      try {
        FileWriter writer = new FileWriter("log.txt");
        writer.write(textArea.getText());
        writer.close();
        JOptionPane.showMessageDialog(this, "Text saved to log.txt");
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
      }
    } else if (e.getSource() == bgColorItem) {
      // Change background color to a random green hue
      int green = random.nextInt(256); // Random green value
      int red = random.nextInt(100); // Low red value for green dominance
      int blue = random.nextInt(100); // Low blue value for green dominance
      Color randomGreen = new Color(red, green, blue);
      contentPanel.setBackground(randomGreen); // Change the panel's background
      textArea.setBackground(randomGreen); // Sync text area with the panel
      bgColorItem.setText("Random Green (RGB: " + red + ", " + green + ", " + blue + ")");
    } else if (e.getSource() == exitItem) {
      // Exit the program
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
