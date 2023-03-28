package seedu.internship.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.internship.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.internship.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.internship.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.internship.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.internship.commons.core.Messages;
import seedu.internship.model.Model;
import seedu.internship.model.internship.InternshipContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all internships whose company names contain any"
            + " of the specified name, role, status and tag keywords and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_COMPANY_NAME + "NAME_KEYWORD]... "
            + "[" + PREFIX_ROLE + "ROLE_KEYWORD]... "
            + "[" + PREFIX_STATUS + "STATUS_KEYWORD]... "
            + "[" + PREFIX_TAG + "TAG_KEYWORD]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_COMPANY_NAME + "apple "
            + PREFIX_COMPANY_NAME + "google "
            + PREFIX_ROLE + "software "
            + PREFIX_ROLE + "developer "
            + PREFIX_STATUS + "applied "
            + PREFIX_STATUS + "offered "
            + PREFIX_TAG + "python "
            + PREFIX_TAG + "java";

    private final InternshipContainsKeywordsPredicate predicate;

    public FindCommand(InternshipContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredInternshipList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_INTERNSHIP_LISTED_OVERVIEW, model.getFilteredInternshipList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
