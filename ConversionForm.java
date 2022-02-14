/**
 * This is a basic GUI form for converting between various units of measurement.
 * 
 * Actual processing (i.e., converting between units) is handled by referencing 
 * static assets in UnitConversion.java.
 * 
 * ConversionForm extends the Application object.
 * 
 * @author  James Greterman, Norik Movsesyan
 * @version 1.0
 */

// Import statements
import javafx.application.Application;
import javafx.collections.*; 
import javafx.event.*; // Used for ActionEvent class and EventHandler interface
import javafx.scene.control.*; // Used for Button, Label, etc.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*; 
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.JOptionPane; // Borrow from Swing for pop-up message window (for exception handling)

public class ConversionForm extends Application
{
    // Global declarations for stage/scene and controls
    private Stage window;
    private Scene scene;
    private Button buttonConvert;
    private Label labelConvertFrom;
    private Label labelConvertTo;
    private Label labelResult;
    private TextField textFieldFromAmount;
    private TextField textFieldToAmount;
    
    public static void main(String[] args)
	{
		launch(args);
	} // END main

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // BEGIN CREATING GUI ELEMENTS
        // Stage
        window = primaryStage;
        window.setTitle("Unit Conversion");

        // Buttons
        buttonConvert = new Button("Convert");

        // Labels
        labelConvertFrom = new Label("Convert");
        labelConvertTo = new Label("to");
        labelResult = new Label ("Result:");

        // TextFields
        textFieldFromAmount = new TextField();
        textFieldFromAmount.setText("0");
        textFieldToAmount = new TextField();
    
        // ChoiceBoxes
        ChoiceBox<String> choiceBoxFrom = new ChoiceBox<>();
        choiceBoxFrom.getItems().addAll("Teaspoon(s)", 
                                        "Tablespoon(s)",
                                        "Ounce(s)",
                                        "Cup(s)",
                                        "Pint(s)",
                                        "Quart(s)",
                                        "Gallon(s)");
        choiceBoxFrom.getSelectionModel().select(0); // Default selection for this ChoiceBox (Teaspoon(s))

        ChoiceBox<String> choiceBoxTo = new ChoiceBox<>();
        choiceBoxTo.getItems().addAll("Teaspoon(s)", 
                                      "Tablespoon(s)",
                                      "Ounce(s)",
                                      "Cup(s)",
                                      "Pint(s)",
                                      "Quart(s)",
                                      "Gallon(s)");
        choiceBoxTo.getSelectionModel().select(1); // Default selection for this ChoiceBox (Tablespoon(s))

        // Images
        Image imageMeasuringCup = new Image("MeasuringCup.png"); // Free clipart of a measuring cup
        ImageView ivMeasuringCup = new ImageView(); // ImageView for the measuring cup

        Image imageWhisk = new Image("Whisk.png"); // Free clipart of a whisk and bowl
        ImageView ivWhisk = new ImageView(); // ImageView for the whisk and bowl
        // END CREATING GUI ELEMENTS

        // BEGIN LAYOUT OF CONTROLS (labels, buttons, boxes, etc.)
        // Labels
        labelConvertFrom.setLayoutX(10);
        labelConvertFrom.setLayoutY(10);
        labelConvertFrom.setStyle("-fx-font-size: 20px");

        labelConvertTo.setLayoutX(270);
        labelConvertTo.setLayoutY(10);
        labelConvertTo.setStyle("-fx-font-size: 20px");

        labelResult.setLayoutX(139);
        labelResult.setLayoutY(96);
        labelResult.setStyle("-fx-font-size: 20px");

        // TextFields
        textFieldFromAmount.setLayoutX(90);
        textFieldFromAmount.setLayoutY(14);
        textFieldFromAmount.setPrefColumnCount(3);

        textFieldToAmount.setLayoutX(209);
        textFieldToAmount.setLayoutY(100);
        textFieldToAmount.setPrefColumnCount(5);
        textFieldToAmount.setEditable(false);

        // Buttons
        buttonConvert.setLayoutX(167);
        buttonConvert.setLayoutY(54);
        buttonConvert.setStyle("-fx-font-weight: bold;" + 
                               "-fx-font-size: 15px");
        // When the "Convert" button is pressed, pass both ChoiceBoxes to getChoice method
        buttonConvert.setOnAction(e -> {getChoice(choiceBoxFrom, choiceBoxTo);});

        // ChoiceBoxes
        choiceBoxFrom.setLayoutX(150);
        choiceBoxFrom.setLayoutY(14);

        choiceBoxTo.setLayoutX(300);
        choiceBoxTo.setLayoutY(14);

        // Images
        ivMeasuringCup.setImage(imageMeasuringCup); // Assign frog image to an ImageView
        ivMeasuringCup.setFitWidth(80);
        ivMeasuringCup.setPreserveRatio(true); // No stretching/deformation
        ivMeasuringCup.setX(30);
        ivMeasuringCup.setY(65);

        ivWhisk.setImage(imageWhisk); // Assign frog image to an ImageView
        ivWhisk.setFitWidth(70);
        ivWhisk.setPreserveRatio(true); // No stretching/deformation
        ivWhisk.setX(309);
        ivWhisk.setY(50);
        // END LAYOUT OF CONTROLS (labels, buttons, boxes, etc.)

        // BEGIN LAYOUT OF WINDOW ITSELF (pane, scene, stage)
        Pane layout = new Pane();
        layout.getChildren().addAll(buttonConvert,
                                    labelConvertFrom,
                                    labelConvertTo,
                                    labelResult,
                                    textFieldFromAmount,
                                    textFieldToAmount,
                                    choiceBoxFrom,
                                    choiceBoxTo,
                                    ivMeasuringCup,
                                    ivWhisk);

        // Finally, set the layout on the scene and set the scene on the stage.
        scene = new Scene(layout, 420, 139);
        window.setScene(scene);
        // END LAYOUT OF WINDOW ITSELF (pane, scene, stage)

        // "Lift the curtain!"
        window.show();
    } // END start

    /**
     * Method for getting ChoiceBox choices
     *
     * @param   choiceBoxFrom   Datatype: ChoiceBox<String>. Selected string value from first ChoiceBox (the "from" unit).
     * @param   choiceBoxTo     Datatype: ChoiceBox<String>. Selected string value from second ChoiceBox (the "to" unit).
     * 
     * @throws  IllegalArgumentException    Thrown when user inputs a non-double value in textFieldFromAmount in the ConversionForm GUI.
     */
    private void getChoice(ChoiceBox<String> choiceBoxFrom, 
                           ChoiceBox<String> choiceBoxTo)
    {
        String choiceFrom = choiceBoxFrom.getValue();
        // System.out.println("FROM = " + choiceFrom);
        String choiceTo = choiceBoxTo.getValue();
        // System.out.println("TO = " + choiceTo);

        // Exception handling to validate that amountFrom is a number.
        try
        {
            double amountFrom = Double.parseDouble(textFieldFromAmount.getText());
            
            // Make sure amountFrom is non-negative...
            if (amountFrom < 0)
            {
                // DATA VALIDATION: The user has entered a non-number in textFieldFromAmount.
                JOptionPane.showMessageDialog(null, "Invalid unit amount.\nGiven amount must be non-negative.");
                return; // Return to prevent further processing until valid data is given by user.
            }

            // By this point, the data is valid!

            // Pass amountFrom, choiceFrom, and choiceTo to UnitConversion's ConvertFromTo for processing.
            double finalResult = UnitConversion.ConvertFromTo(amountFrom, choiceFrom, choiceTo);

            // Finally, display the processed amount in textFieldToAmount.
            textFieldToAmount.setText(String.valueOf(finalResult));
        }
        catch (IllegalArgumentException iae)
        {
            // DATA VALIDATION: The user has entered a non-number in textFieldFromAmount.
            JOptionPane.showMessageDialog(null, "Invalid unit amount.\nGiven amount must be a number.");
            return; // Return to prevent further processing until valid data is given by user.
        } // END try...catch
    } // END getChoice method
} // END ConversionForm
