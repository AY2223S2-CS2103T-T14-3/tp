package seedu.internship.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internship.logic.commands.CommandTestUtil.NON_EMPTY_INDEXLIST;
import static seedu.internship.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.internship.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.internship.logic.commands.CommandTestUtil.showInternshipAtIndex;
import static seedu.internship.logic.commands.DeleteIndexCommand.MESSAGE_DELETE_INTERNSHIP_SUCCESS;
import static seedu.internship.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP;
import static seedu.internship.testutil.TypicalIndexes.INDEX_SECOND_INTERNSHIP;
import static seedu.internship.testutil.TypicalInternships.getTypicalInternBuddy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.internship.commons.core.Messages;
import seedu.internship.commons.core.index.Index;
import seedu.internship.model.Model;
import seedu.internship.model.ModelManager;
import seedu.internship.model.UserPrefs;
import seedu.internship.model.internship.Internship;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteIndexCommandTest {
    private Model model = new ModelManager(getTypicalInternBuddy(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Internship internshipToDelete = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(NON_EMPTY_INDEXLIST);
        String expectedMessage = String.format(MESSAGE_DELETE_INTERNSHIP_SUCCESS, 1);

        ModelManager expectedModel = new ModelManager(model.getInternBuddy(), new UserPrefs());
        expectedModel.deleteInternship(internshipToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInternshipList().size() + 1);
        List<Index> indexList = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(indexList);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Internship internshipToDelete = model.getFilteredInternshipList().get(INDEX_FIRST_INTERNSHIP.getZeroBased());
        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(NON_EMPTY_INDEXLIST);

        String expectedMessage = String.format(MESSAGE_DELETE_INTERNSHIP_SUCCESS, 1);

        Model expectedModel = new ModelManager(model.getInternBuddy(), new UserPrefs());
        expectedModel.deleteInternship(internshipToDelete);
        showNoInternship(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInternshipAtIndex(model, INDEX_FIRST_INTERNSHIP);

        Index outOfBoundIndex = INDEX_SECOND_INTERNSHIP;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInternBuddy().getInternshipList().size());

        List<Index> indexList = new ArrayList<>(Arrays.asList(outOfBoundIndex));

        DeleteIndexCommand deleteCommand = new DeleteIndexCommand(indexList);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteIndexCommand deleteFirstCommand = new DeleteIndexCommand(NON_EMPTY_INDEXLIST);
        List<Index> indexList = new ArrayList<>(Arrays.asList(Index.fromZeroBased(1)));
        DeleteIndexCommand deleteSecondCommand = new DeleteIndexCommand(indexList);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteIndexCommand deleteFirstCommandCopy = new DeleteIndexCommand(NON_EMPTY_INDEXLIST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different internship -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no internship entry.
     */
    private void showNoInternship(Model model) {
        model.updateFilteredInternshipList(p -> false);

        assertTrue(model.getFilteredInternshipList().isEmpty());
    }
}
