package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JTextField filenameField;

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        setLayout(new BorderLayout(20, 20));
        initMenu();
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initMenu() {
        JMenuItem menuLoad = new JMenuItem("Load");
        menuLoad.setName("MenuLoad");
        menuLoad.addActionListener(getLoadListener());
        JMenuItem menuSave = new JMenuItem("Save");
        menuSave.setName("MenuSave");
        menuSave.addActionListener(getSaveListener());
        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");
        menuExit.addActionListener(getExitListener());
        JMenu menuFile = new JMenu("File");
        menuFile.setName("MenuFile");
        menuFile.add(menuLoad);
        menuFile.add(menuSave);
        menuFile.addSeparator();
        menuFile.add(menuExit);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuFile);
        setJMenuBar(menuBar);
    }

    private void initComponents() {
        JPanel topPanel = new JPanel();

        filenameField = new JTextField();
        filenameField.setName("FilenameField");
        filenameField.setPreferredSize(new Dimension(120, 30));
        topPanel.add(filenameField);

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener(getSaveListener());
        topPanel.add(saveButton);

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener(getLoadListener());
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setSize(150, 150);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        add(scrollPane, BorderLayout.CENTER);
    }

    private ActionListener getSaveListener() {
        return e -> {
            String filename = filenameField.getText();
            if (!filename.isBlank()) {
                String text = textArea.getText();
                save(filename, text);
            }
        };
    }

    private void save(String filename, String text) {
        File file = new File(filename);
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().mkdirs()) {
                throw new IllegalStateException("Failed to create folder: " + file.getParentFile().getAbsolutePath());
            }
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("Failed to create file: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create file: " + file.getAbsolutePath(), e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ActionListener getLoadListener() {
        return e -> {
            String filename = filenameField.getText();
            if (!filename.isBlank()) {
                String text = load(filename);
                textArea.setText(text);
            }
        };
    }

    private String load(String filename) {
        StringBuilder builder = new StringBuilder();
        File file = new File(filename);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                char[] buffer = new char[1024];
                int count;
                while ((count = reader.read(buffer)) != -1) {
                    String text = new String(buffer, 0, count);
                    builder.append(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new IllegalArgumentException("File does not exist: " + file.getAbsolutePath()).printStackTrace();
        }
        return builder.toString();
    }

    private ActionListener getExitListener() {
        return e -> {
            System.exit(0);
        };
    }
}
