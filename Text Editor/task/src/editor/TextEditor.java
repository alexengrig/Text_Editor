package editor;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(null);
        setTitle("The first stage");
        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setSize(150, 150);
        add(textArea);
    }
}
