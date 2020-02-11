import editor.TextEditor;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.hyperskill.hstest.v6.stage.SwingTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;

import java.util.List;
import java.util.function.Supplier;

class TestClue {

    public Supplier<Boolean> function;
    public String feedback;

    public TestClue(String feedback, Supplier<Boolean> function) {
        this.function = function;
        this.feedback = feedback;
    }

}


public class EditorTest extends SwingTest<TestClue> {

    public EditorTest() throws Exception {
        super(new TextEditor());
    }

    private JTextComponentFixture textArea;

    @Override
    public List<TestCase<TestClue>> generate() {
        return List.of(
            new TestCase<TestClue>().setAttach(new TestClue(
                "Title is empty.",
                () -> frame.getTitle().length() > 0)),

            new TestCase<TestClue>().setAttach(new TestClue(
                "Window is not visible.",
                () -> frame.isVisible())),

            new TestCase<TestClue>().setAttach(new TestClue(
                "There is no text component with name TextArea.",
                () -> checkExistence(() -> {
                    textArea = window.textBox("TextArea");
                    return textArea;
                }))),

            new TestCase<TestClue>().setAttach(new TestClue(
                "TextArea should be editable.",
                () -> {
                    textArea.requireEditable();
                    return true;
                })),

            new TestCase<TestClue>().setAttach(new TestClue(
                "TextArea should be empty at the start of the program.",
                () -> {
                    textArea.requireEmpty();
                    return true;
                })),

            new TestCase<TestClue>().setAttach(new TestClue(
                "Can't enter multiline text in TextArea.",
                () -> {
                    String text = "Basic text editor\nType here too\nHere also";
                    textArea.setText(text);
                    textArea.requireText(text);
                    return true;
                }))
            );
    }

    @Override
    public CheckResult check(String reply, TestClue clue) {
        try {
            return new CheckResult(clue.function.get(), clue.feedback);
        }
        catch (AssertionError ex) {
            return new CheckResult(false, clue.feedback);
        }
    }
}
