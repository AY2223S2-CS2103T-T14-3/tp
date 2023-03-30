package seedu.internship.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.internship.model.InternBuddy;
import seedu.internship.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "InternBuddy data has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setInternBuddy(new InternBuddy());
        model.updateSelectedInternship(null);
        model.commitInternBuddy();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
