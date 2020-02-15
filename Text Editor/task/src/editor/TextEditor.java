package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JTextField searchField;
    private int nextIndex;
    private int previousIndex;
    private JCheckBox regexCheckBox;
    private JFileChooser fileChooser;

    public TextEditor() {
        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
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
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = initFileMenu();
        menuBar.add(fileMenu);

        JMenu searchMenu = initSearchMenu();
        menuBar.add(searchMenu);

        setJMenuBar(menuBar);
    }

    private JMenu initFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem loadMenuItem = initOpenMenuItem();
        fileMenu.add(loadMenuItem);

        JMenuItem saveMenuItem = initSaveMenuItem();
        fileMenu.add(saveMenuItem);

        fileMenu.addSeparator();

        JMenuItem exitMenuItem = initExitMenuItem();
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenuItem initOpenMenuItem() {
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        openMenuItem.addActionListener(getLoadListener());
        return openMenuItem;
    }

    private JMenuItem initSaveMenuItem() {
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(getSaveListener());
        return saveMenuItem;
    }

    private JMenuItem initExitMenuItem() {
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(getExitListener());
        return exitMenuItem;
    }

    private JMenu initSearchMenu() {
        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem startSearchMenuItem = initStartSearchMenuItem();
        searchMenu.add(startSearchMenuItem);

        JMenuItem previousMatchMenuItem = initPreviousMatchMenuItem();
        searchMenu.add(previousMatchMenuItem);

        JMenuItem nextMatchMenuItem = initNextMatchMenuItem();
        searchMenu.add(nextMatchMenuItem);

        JMenuItem useRegexpMenuItem = initUseRegexpMenuItem();
        searchMenu.add(useRegexpMenuItem);

        return searchMenu;
    }

    private JMenuItem initStartSearchMenuItem() {
        JMenuItem startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        return startSearchMenuItem;
    }

    private JMenuItem initPreviousMatchMenuItem() {
        JMenuItem previousMatchMenuItem = new JMenuItem("Previous match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        return previousMatchMenuItem;
    }

    private JMenuItem initNextMatchMenuItem() {
        JMenuItem nextSearchMenuItem = new JMenuItem("Next match");
        nextSearchMenuItem.setName("MenuNextMatch");
        return nextSearchMenuItem;
    }

    private JMenuItem initUseRegexpMenuItem() {
        JMenuItem useRegExpMenuItem = new JMenuItem("Use regular expression");
        useRegExpMenuItem.setName("MenuUseRegExp");
        return useRegExpMenuItem;
    }

    private void initComponents() {
        JPanel topPanel = new JPanel();

        Component openButton = initOpenButton();
        topPanel.add(openButton);

        Component saveButton = initSaveButton();
        topPanel.add(saveButton);

        Component searchField = initSearchField();
        topPanel.add(searchField);

        Component searchButton = initSearchButton();
        topPanel.add(searchButton);

        Component previousButton = initPreviousButton();
        topPanel.add(previousButton);

        Component nextButton = initNextButton();
        topPanel.add(nextButton);

        Component regexCheckBox = initRegexCheckBox();
        topPanel.add(regexCheckBox);

        add(topPanel, BorderLayout.NORTH);

        Component textArea = initTextArea();
        add(textArea, BorderLayout.CENTER);

        Component fileChooser = initFileChooser();
        add(fileChooser, BorderLayout.SOUTH);
    }

    private Component initOpenButton() {
        JButton openButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("icons/folder.png")));
        openButton.setName("OpenButton");
        openButton.setPreferredSize(new Dimension(32, 32));
        openButton.addActionListener(getLoadListener());
        return openButton;
    }

    private Component initSaveButton() {
        JButton saveButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("icons/floppy-disk.png")));
        saveButton.setName("SaveButton");
        saveButton.setPreferredSize(new Dimension(32, 32));
        saveButton.addActionListener(getSaveListener());
        return saveButton;
    }

    private Component initSearchField() {
        JTextField searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setPreferredSize(new Dimension(120, 30));
        this.searchField = searchField;
        return searchField;
    }

    private Component initSearchButton() {
        JButton searchButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("icons/search.png")));
        searchButton.setName("StartSearchButton");
        searchButton.setPreferredSize(new Dimension(32, 32));
        searchButton.addActionListener(getStartSearchListener());
        return searchButton;
    }

    private Component initPreviousButton() {
        JButton previousButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("icons/chevron.png")));
        previousButton.setName("PreviousMatchButton");
        previousButton.setPreferredSize(new Dimension(32, 32));
        previousButton.addActionListener(getPreviousMatchListener());
        return previousButton;
    }

    private Component initNextButton() {
        JButton nextButton = new JButton(new ImageIcon(ClassLoader.getSystemResource("icons/right-chevron.png")));
        nextButton.setName("NextMatchButton");
        nextButton.setPreferredSize(new Dimension(32, 32));
        nextButton.addActionListener(getNextMatchListener());
        return nextButton;
    }

    private Component initTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setSize(150, 150);
        this.textArea = textArea;
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        return scrollPane;
    }

    private Component initRegexCheckBox() {
        JCheckBox regexCheckBox = new JCheckBox("Use regex");
        regexCheckBox.setName("UseRegExCheckbox");
        this.regexCheckBox = regexCheckBox;
        return regexCheckBox;
    }

    private Component initFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
//        fileChooser.setVisible(false);
        this.fileChooser = fileChooser;
        return fileChooser;
    }

    private ActionListener getSaveListener() {
        return e -> {
            fileChooser.setVisible(true);
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String text = textArea.getText();
                save(file, text);
            }
        };
    }

    private void save(File file, String text) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ActionListener getLoadListener() {
        return e -> {
            fileChooser.setVisible(true);
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String text = load(file);
                textArea.setText(text);
            }
        };
    }

    private String load(File file) {
        StringBuilder builder = new StringBuilder();
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
        return e -> System.exit(0);
    }

    private ActionListener getStartSearchListener() {
        return e -> {
            nextIndex = -1;
            previousIndex = -1;
            nextMatch();
        };
    }

    private ActionListener getNextMatchListener() {
        return e -> nextMatch();
    }

    private void nextMatch() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            return;
        }
        String text = textArea.getText();
        int begin;
        int end;
        if (!regexCheckBox.isSelected()) {
            int index = text.indexOf(query, nextIndex);
            if (index != -1) {
                begin = index;
            } else if ((index = text.indexOf(query)) != -1) {
                begin = index;
            } else {
                return;
            }
            end = begin + query.length();
        } else {
            Pattern regex = Pattern.compile(query);
            Matcher matcher = regex.matcher(text);
            String line;
            if (nextIndex > 0 && matcher.find(nextIndex)) {
                begin = matcher.start();
                line = matcher.group();
            } else if (matcher.find(0)) {
                begin = matcher.start();
                line = matcher.group();
            } else {
                return;
            }
            end = begin + line.length();
        }
        selectText(begin, end);
        previousIndex = nextIndex;
        nextIndex = end;
    }

    private ActionListener getPreviousMatchListener() {
        return e -> previousMatch();
    }

    private void previousMatch() {
        String query = searchField.getText();
        if (query.isEmpty()) {
            return;
        }
        String text = textArea.getText();
        int begin;
        int end;
        String line;
        if (!regexCheckBox.isSelected()) {
            int index = text.lastIndexOf(query, previousIndex);
            if (index != -1) {
                begin = index;
            } else if ((index = text.lastIndexOf(query)) != -1) {
                begin = index;
            } else {
                return;
            }
            end = begin + query.length();
            line = query;
        } else {
            Pattern regex = Pattern.compile(query);
            Matcher matcher = regex.matcher(text);
            if (previousIndex > 0 && matcher.find(previousIndex)) {
                begin = matcher.start();
                line = matcher.group();
            } else if (matcher.find(text.length())) {
                begin = matcher.start();
                line = matcher.group();
            } else {
                return;
            }
            end = begin + line.length();
        }
        selectText(begin, end);
        nextIndex = previousIndex;
        previousIndex = begin - line.length();
        System.out.println("next: " + nextIndex + ", prev: " + previousIndex);
    }

    private void selectText(int begin, int end) {
        textArea.setCaretPosition(end);
        textArea.select(begin, end);
        textArea.grabFocus();
    }
}
