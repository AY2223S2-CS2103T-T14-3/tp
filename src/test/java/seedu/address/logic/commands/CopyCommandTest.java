package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.address.testutil.TypicalInternships.getTypicalInternBuddy;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.internship.Internship;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CopyCommand}.
 */
public class CopyCommandTest {



    private Model model = new ModelManager(getTypicalInternBuddy(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Internship internshipToCopy = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        CopyCommand copyCommand = new CopyCommand(INDEX_FIRST_INTERNSHIP);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_INTERNSHIP_SUCCESS, internshipToCopy);

        ModelManager expectedModel = new ModelManager(model.getInternBuddy(), new UserPrefs());
        expectedModel.copyInternship(internshipToCopy);

        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Internship internshipToCopy = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        CopyCommand copyCommand = new CopyCommand(INDEX_FIRST_INTERNSHIP);

        String expectedMessage = String.format(CopyCommand.MESSAGE_COPY_INTERNSHIP_SUCCESS, internshipToCopy);


        Model expectedModel = new ModelManager(model.getInternBuddy(), new UserPrefs());
        showInternshipAtIndex(expectedModel, INDEX_FIRST_INTERNSHIP);
        expectedModel.copyInternship(internshipToCopy);

        assertCommandSuccess(copyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP;
        // ensures that outOfBoundIndex is still in bounds of internship list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternBuddy().getInternshipList().size());

        CopyCommand copyCommand = new CopyCommand(outOfBoundIndex);

        assertCommandFailure(copyCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

}
