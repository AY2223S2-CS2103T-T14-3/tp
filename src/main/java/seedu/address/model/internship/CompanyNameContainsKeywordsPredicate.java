package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Internship}'s {@code CompanyName} matches any of the keywords given.
 */
public class CompanyNameContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> keywords;

    /**
     * Constructs a {@code CompanyNameContainsKeywordsPredicate} instance.
     *
     * @param keywords The keywords to check for in the Internship's company name.
     */
    public CompanyNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if an Internship's company name matches any of the keywords.
     *
     * @param internship the input Internship whose name will be checked.
     * @return true if there is a match with any of the keyword, else false.
     */
    @Override
    public boolean test(Internship internship) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                        internship.getCompanyName().fullCompanyName, keyword));
    }

    /**
     * Determines if an object is equal to the current {@code CompanyNameContainsKeywordsPredicate} instance.
     *
     * @param other The other object to compare with
     * @return true if both are {@code CompanyNameContainsKeywordsPredicate} instances with the same set
     *         of keywords to check for.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompanyNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CompanyNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
