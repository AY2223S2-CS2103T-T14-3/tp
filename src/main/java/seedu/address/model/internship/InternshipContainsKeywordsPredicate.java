package seedu.address.model.internship;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Internship}'s {@code CompanyName} matches any of the keywords given.
 */
public class InternshipContainsKeywordsPredicate implements Predicate<Internship> {
    private final List<String> nameKeywords;
    private final List<String> statusKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a {@code CompanyNameContainsKeywordsPredicate} instance.
     *
     * @param nameKeywords The keywords to check for in the Internship's company name.
     */
    public InternshipContainsKeywordsPredicate(List<String> nameKeywords, List<String> statusKeywords,
                                               List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.statusKeywords = statusKeywords;
        this.tagKeywords = tagKeywords;
    }

    /**
     * Tests if an Internship's company name matches any of the keywords.
     *
     * @param internship the input Internship whose name will be checked.
     * @return true if there is a match with any of the keyword, else false.
     */
    @Override
    public boolean test(Internship internship) {
        boolean nameCheck = true;
        boolean statusCheck = true;
        boolean tagCheck = true;

        if (!this.nameKeywords.isEmpty()) {
            nameCheck = this.nameKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            internship.getCompanyName().fullCompanyName, keyword));
        }

        if (!this.statusKeywords.isEmpty()) {
            statusCheck = this.statusKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(
                            internship.getStatus().fullStatus, keyword));
        }

        if (!this.tagKeywords.isEmpty()) {
            tagCheck = this.tagKeywords.stream()
                    .anyMatch(keyword -> internship.getTags().stream()
                            .map(tag -> tag.tagName)
                            .anyMatch(tagName -> StringUtil.containsWordIgnoreCase(keyword, tagName)));
        }

        return nameCheck && statusCheck && tagCheck;
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
                || (other instanceof InternshipContainsKeywordsPredicate // instanceof handles nulls
                && nameKeywords.equals(((InternshipContainsKeywordsPredicate) other).nameKeywords) // state check
                && statusKeywords.equals(((InternshipContainsKeywordsPredicate) other).statusKeywords)
                && tagKeywords.equals(((InternshipContainsKeywordsPredicate) other).tagKeywords));
    }

}
