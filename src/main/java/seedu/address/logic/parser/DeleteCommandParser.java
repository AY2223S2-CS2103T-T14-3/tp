package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE,
                        PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isEmpty()
                && argMultimap.getValue(PREFIX_ROLE).isEmpty()
                && argMultimap.getValue(PREFIX_STATUS).isEmpty()
                && argMultimap.getValue(PREFIX_DATE).isEmpty()
                && argMultimap.getValue(PREFIX_TAG).isEmpty()
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        List<String> nameList = this.parseCompanyNames(argMultimap.getAllValues(PREFIX_COMPANY_NAME));
        List<String> roleList = this.parseRoles(argMultimap.getAllValues(PREFIX_ROLE));
        List<String> statusList = this.parseStatuses(argMultimap.getAllValues(PREFIX_STATUS));
        List<String> dateList = this.parseDates(argMultimap.getAllValues(PREFIX_DATE));
        List<String> tagList = this.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        InternshipContainsKeywordsPredicate newPredicate = new InternshipContainsKeywordsPredicate(nameList,
                roleList, statusList, dateList, tagList);


        try {
            List<Index> indexes = ParserUtil.parseIndexes(argMultimap.getPreamble());
            return new DeleteCommand(indexes, newPredicate);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    private List<String> parseCompanyNames(List<String> unparsedNames) throws ParseException {
        List<String> parsedNames = ParserUtil.parseCompanyNames(unparsedNames).stream()
                .map(name -> name.fullCompanyName)
                .collect(Collectors.toList());
        return parsedNames;
    }

    private List<String> parseRoles(List<String> unparsedRoles) throws ParseException {
        List<String> parsedRoles = ParserUtil.parseRoles(unparsedRoles).stream()
                .map(role -> role.fullRole)
                .collect(Collectors.toList());
        return parsedRoles;
    }

    private List<String> parseStatuses(List<String> unparsedStatuses) throws ParseException {
        List<String> parsedStatuses = ParserUtil.parseStatuses(unparsedStatuses).stream()
                .map(status -> status.fullStatus)
                .collect(Collectors.toList());
        return parsedStatuses;
    }

    private List<String> parseDates(List<String> unparsedDates) throws ParseException {
        List<String> parsedDates = ParserUtil.parseDates(unparsedDates).stream()
                .map(date -> date.fullDate)
                .collect(Collectors.toList());
        return parsedDates;
    }

    private List<String> parseTags(List<String> unparsedTags) throws ParseException {
        List<String> parsedTags = ParserUtil.parseTags(unparsedTags).stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
        return parsedTags;
    }

}
