package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Status;

import static seedu.address.model.internship.Status.NEW;
import static seedu.address.model.internship.Status.APPLIED;
import static seedu.address.model.internship.Status.ASSESSMENT;
import static seedu.address.model.internship.Status.INTERVIEW;
import static seedu.address.model.internship.Status.OFFERED;
import static seedu.address.model.internship.Status.REJECTED;

/**
 * An UI component that displays information of a {@code Internship}.
 */
public class InternshipCard extends UiPart<Region> {

    private static final String FXML = "InternshipListCard.fxml";

    public static final String ROLE_LABEL = "Role: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Internship internship;

    private ArrayList<String> internshipCardInformation = new ArrayList<String>();


    @FXML
    private HBox cardPane;
    @FXML
    private Label companyName;
    @FXML
    private Label id;
    @FXML
    private Label role;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Label statusLabel;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public InternshipCard(Internship internship, int displayedIndex) {
        super(FXML);
        this.internship = internship;
        //Add Id
        id.setText(displayedIndex + ". ");
        internshipCardInformation.add(id.getText());

        //Add Company Name
        companyName.setText(internship.getCompanyName().fullCompanyName);
        internshipCardInformation.add(companyName.getText());

        //Add Role
        role.setText(ROLE_LABEL + internship.getRole().fullRole);
        internshipCardInformation.add(role.getText());

        //Add Date
        String dateLabel = getDateLabel(internship.getStatus());
        date.setText(dateLabel + internship.getDate().fullDate);
        internshipCardInformation.add(date.getText());

        //Add Tags
        internship.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        ObservableList<Node> listNodes = tags.getChildren();
        listNodes.forEach(node -> internshipCardInformation.add(((Label) node).getText()));


        //Set up status label
        String statusString = internship.getStatus().toString();
        HashMap<String, Color> colorMap = setupColours();
        Color statusColor = colorMap.get(statusString);
        statusLabel.setText(statusString.toUpperCase());
        statusLabel.setBackground(new Background(new BackgroundFill(
                statusColor, new CornerRadii(10), new Insets(-5))));
        internshipCardInformation.add(statusLabel.getText());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InternshipCard)) {
            return false;
        }

        // state check
        InternshipCard card = (InternshipCard) other;
        return id.getText().equals(card.id.getText())
                && internship.equals(card.internship);
    }


    /**
     * Initialises the colours associated with the status label.
     *
     * @return a hashmap containing the colors associated with each status type
     */
    public HashMap<String, Color> setupColours() {
        //Hashmap that stores the colours associated with each status
        HashMap<String, Color> colorMap = new HashMap<String, Color>();
        colorMap.put(NEW, Color.rgb(250, 155, 68, 1.0));
        colorMap.put(APPLIED, Color.rgb(68, 170, 250, 1.0));
        colorMap.put(ASSESSMENT, Color.rgb(250, 68, 155, 1.0));
        colorMap.put(INTERVIEW, Color.rgb(126, 68, 250, 1.0));
        colorMap.put(OFFERED, Color.rgb(42, 174, 79, 1.0));
        colorMap.put(REJECTED, Color.rgb(250, 68, 68, 1.0));
        return colorMap;
    }


    /**
     * Gets the list that stores the string information of the internship card
     */
    public ArrayList<String> getInternshipCardInformation() {
        return this.internshipCardInformation;
    }

    public String getDateLabel(Status status) {
        String dateLabel;
        switch (status.toString()) {
        case NEW:
            dateLabel = "Date Added: ";
            break;
        case APPLIED:
            dateLabel = "Date Applied: ";
            break;
        case ASSESSMENT:
            dateLabel = "Date of Assessment: ";
            break;
        case INTERVIEW:
            dateLabel = "Date of Interview: ";
            break;
        case OFFERED:
            dateLabel = "Date of Notice of Offer: ";
            break;
        case REJECTED:
            dateLabel = "Date of Notice of Rejection: ";
            break;
        default:
            dateLabel = "Date: ";
        }
        return dateLabel;
    }
}
