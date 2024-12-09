import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UserInterface extends JFrame implements ActionListener {
  private JTextArea textArea;
  private JMenuItem dateTimeItem, saveItem, bgColorItem, exitItem, fontItem;
  private Random random;
  private JPanel contentPanel;
  private static final String[] FONT_FAMILIES = {
      "Arial", "Times New Roman", "Courier New", "Verdana", "Georgia"
  };
  private static final Integer[] FONT_SIZES = {
      8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48
  };

  public UserInterface() {
    setTitle("User Interface");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(400, 300));

    // Create menu bar and menus
    JMenuBar menuBar = new JMenuBar();
    JMenu optionsMenu = new JMenu("Options");
    JMenu formatMenu = new JMenu("Format");

    // Create menu items
    dateTimeItem = new JMenuItem("Print Date/Time");
    saveItem = new JMenuItem("Save to File");
    bgColorItem = new JMenuItem("Change Background to Random Green");
    exitItem = new JMenuItem("Exit");
    fontItem = new JMenuItem("Customize Font...");

    // Add action listeners to menu items
    dateTimeItem.addActionListener(this);
    saveItem.addActionListener(this);
    bgColorItem.addActionListener(this);
    exitItem.addActionListener(this);
    fontItem.addActionListener(this);

    // Add menu items to menus
    optionsMenu.add(dateTimeItem);
    optionsMenu.add(saveItem);
    optionsMenu.add(bgColorItem);
    optionsMenu.add(exitItem);
    formatMenu.add(fontItem);

    // Add menus to menu bar
    menuBar.add(optionsMenu);
    menuBar.add(formatMenu);
    setJMenuBar(menuBar);

    // Create content panel
    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(Color.WHITE);

    // Create a sub-panel for the text area with padding
    JPanel textAreaPanel = new JPanel(new BorderLayout());
    textAreaPanel.setBackground(Color.WHITE);
    textAreaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Create text area
    textArea = new JTextArea();
    textArea.setFont(new Font("Arial", Font.PLAIN, 12)); // Default font
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

  private void showFontDialog() {
    JDialog fontDialog = new JDialog(this, "Font Settings", true);
    fontDialog.setLayout(new BorderLayout());

    // Create font selection panel
    JPanel selectionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    selectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Font family combo box
    JComboBox<String> fontFamilyCombo = new JComboBox<>(FONT_FAMILIES);
    fontFamilyCombo.setSelectedItem(textArea.getFont().getFamily());

    // Font size combo box
    JComboBox<Integer> fontSizeCombo = new JComboBox<>(FONT_SIZES);
    fontSizeCombo.setSelectedItem(textArea.getFont().getSize());

    // Preview panel
    JTextArea previewArea = new JTextArea("Preview Text\nABCDEfghijk");
    previewArea.setPreferredSize(new Dimension(200, 100));
    previewArea.setEditable(false);
    previewArea.setBorder(BorderFactory.createTitledBorder("Preview"));

    // Labels
    selectionPanel.add(new JLabel("Font Family:"));
    selectionPanel.add(fontFamilyCombo);
    selectionPanel.add(new JLabel("Font Size:"));
    selectionPanel.add(fontSizeCombo);

    // Update preview when selection changes
    ItemListener previewUpdater = e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        String family = (String) fontFamilyCombo.getSelectedItem();
        int size = (Integer) fontSizeCombo.getSelectedItem();
        Font newFont = new Font(family, Font.PLAIN, size);
        previewArea.setFont(newFont);
      }
    };

    fontFamilyCombo.addItemListener(previewUpdater);
    fontSizeCombo.addItemListener(previewUpdater);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancel");

    okButton.addActionListener(e -> {
      String family = (String) fontFamilyCombo.getSelectedItem();
      int size = (Integer) fontSizeCombo.getSelectedItem();
      textArea.setFont(new Font(family, Font.PLAIN, size));
      fontDialog.dispose();
    });

    cancelButton.addActionListener(e -> fontDialog.dispose());

    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    // Add all components to dialog
    fontDialog.add(selectionPanel, BorderLayout.NORTH);
    fontDialog.add(previewArea, BorderLayout.CENTER);
    fontDialog.add(buttonPanel, BorderLayout.SOUTH);

    // Set dialog properties
    fontDialog.pack();
    fontDialog.setLocationRelativeTo(this);
    fontDialog.setVisible(true);
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
      int green = random.nextInt(256);
      int red = random.nextInt(100);
      int blue = random.nextInt(100);
      Color randomGreen = new Color(red, green, blue);
      contentPanel.setBackground(randomGreen);
      textArea.setBackground(randomGreen);
      bgColorItem.setText("Random Green (RGB: " + red + ", " + green + ", " + blue + ")");
    } else if (e.getSource() == exitItem) {
      // Exit the program
      System.exit(0);
    } else if (e.getSource() == fontItem) {
      showFontDialog();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      UserInterface ui = new UserInterface();
      ui.setVisible(true);
    });
  }
}