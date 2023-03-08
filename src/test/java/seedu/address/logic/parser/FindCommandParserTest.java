package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.internship.*;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new InternshipContainsKeywordsPredicate(Arrays.asList("Google"),
                        Arrays.asList("applied"),
                        Arrays.asList("frontend")));

        assertParseSuccess(parser, COMPANY_NAME_DESC_GOOGLE + STATUS_DESC_GOOGLE + TAG_DESC_FRONT,
                expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + COMPANY_NAME_DESC_GOOGLE + " \n \t "
                        + STATUS_DESC_GOOGLE + "  \t" + TAG_DESC_FRONT + "   \n\t", expectedFindCommand);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new InternshipContainsKeywordsPredicate(Arrays.asList("Google"),
                        Arrays.asList("applied"),
                        Arrays.asList("frontend", "backend")));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COMPANY_NAME_DESC_GOOGLE +
                STATUS_DESC_GOOGLE + TAG_DESC_FRONT, expectedFindCommand);

        // multiple company names - last company name accepted
        assertParseSuccess(parser, COMPANY_NAME_DESC_APPLE + COMPANY_NAME_DESC_GOOGLE
                + STATUS_DESC_GOOGLE + TAG_DESC_FRONT, expectedFindCommand);

        // multiple statuses - last status accepted
        assertParseSuccess(parser, COMPANY_NAME_DESC_GOOGLE + STATUS_DESC_APPLE + STATUS_DESC_GOOGLE
                + TAG_DESC_FRONT, expectedFindCommand);

        // multiple tags - all accepted
        assertParseSuccess(parser, COMPANY_NAME_DESC_GOOGLE + STATUS_DESC_GOOGLE + TAG_DESC_FRONT
                + TAG_DESC_BACK, expectedFindCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new InternshipContainsKeywordsPredicate(Collections.emptyList(),
                        Arrays.asList("interview"),
                        Arrays.asList("frontend")));;

        // no name
        assertParseSuccess(parser, STATUS_DESC_APPLE + TAG_DESC_FRONT, expectedFindCommand);

        // no status
        expectedFindCommand =
                new FindCommand(new InternshipContainsKeywordsPredicate(Arrays.asList("Apple"),
                        Collections.emptyList(),
                        Arrays.asList("frontend")));

        assertParseSuccess(parser, COMPANY_NAME_DESC_APPLE + TAG_DESC_FRONT, expectedFindCommand);

        // zero tags
        expectedFindCommand =
                new FindCommand(new InternshipContainsKeywordsPredicate(Arrays.asList("Apple"),
                        Arrays.asList("interview"),
                        Collections.emptyList()));

        assertParseSuccess(parser, COMPANY_NAME_DESC_APPLE + STATUS_DESC_APPLE, expectedFindCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

        // no fields at all
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid company name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + STATUS_DESC_GOOGLE + TAG_DESC_BACK
                + TAG_DESC_FRONT, CompanyName.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, COMPANY_NAME_DESC_GOOGLE + INVALID_STATUS_DESC + TAG_DESC_BACK
                + TAG_DESC_FRONT, Status.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, COMPANY_NAME_DESC_GOOGLE + STATUS_DESC_GOOGLE + INVALID_TAG_DESC
                + VALID_TAG_FRONT, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + STATUS_DESC_GOOGLE + INVALID_TAG_DESC,
                CompanyName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + COMPANY_NAME_DESC_GOOGLE + STATUS_DESC_GOOGLE
                        + TAG_DESC_BACK + TAG_DESC_FRONT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
