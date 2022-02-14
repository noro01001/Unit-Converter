/**
 * This is a unit conversion class definition for use with ConversionForm.java.
 * 
 * @author  James Greterman, Norik Movsesyan
 * @version 1.0
 */

 public class UnitConversion
 {
    // Conversion constants
    /* 
     * Note these are all static so they may be used/referenced by other static entities.
     * 
     * Also note that, for efficiency, only bigger-to-smaller format is given; the reciprocal
     * will be used for smaller-to-bigger conversion (e.g., (1 / TABLESPOON_TO_TEASPOON)).
     */
    final static private double TABLESPOON_TO_TEASPOON = 3;
    final static private double OUNCE_TO_TABLESPOON = 2;
    final static private double CUP_TO_OUNCE = 8;
    final static private double PINT_TO_CUP = 2;
    final static private double QUART_TO_PINT = 2;
    final static private double GALLON_TO_QUART = 4;

    /* 
     * The above constants are then put in a static array; this allows later processing
     * to convert "up" or "down" by referencing index numbers.
     */
    final static private double[] conversionsArray = {
        TABLESPOON_TO_TEASPOON,                             // Index 0
        OUNCE_TO_TABLESPOON,                                // 1
        CUP_TO_OUNCE,                                       // 2
        PINT_TO_CUP,                                        // 3
        QUART_TO_PINT,                                      // 4
        GALLON_TO_QUART                                     // 5
    };

    /*
     * A second static array is used to store the name of each unit type.
     * 
     * This array complements the above conversionsArray by letting
     * ConvertFromTo know which conversionsArray indices to start and end at.
     */
    final static private String[] units = {"Teaspoon(s)",    // Index 0
                                           "Tablespoon(s)",  // 1
                                           "Ounce(s)",       // 2
                                           "Cup(s)",         // 3
                                           "Pint(s)",        // 4
                                           "Quart(s)",       // 5
                                           "Gallon(s)"};     // 6

    /**
     * Static method for converting up or down between the various US volume units.
     * 
     * @param   amountFrom      Datatype: double. This value is pulled from textFieldFromAmount in the ConversionForm GUI. 
     *                          (Example: 3.5)
     * @param   startingUnit    Datatype: String. This value is pulled from choiceBoxFrom in the ConversionForm GUI. 
     *                          (Example: "Tablespoon(s)")
     * @param   targetUnit      Datatype: String. This value is pulled from choiceBoxTo in the ConversionForm GUI. 
     *                          (Example: "Gallon(s)")
     * @return  amountTo        Datatype: double. This represents the final converted value, in targetUnit units, after processing is complete.
     *                          (Example: 5.0)
     */
    public static double ConvertFromTo(double amountFrom, String startingUnit, String targetUnit)
    {
        // Declare local variables for use within ConvertFromTo.
        int startingIndex = -1;     // Initialize at -1 for starters
        int targetIndex = -1;       // Same as above
        double amountTo;

        // Set startingIndex's value based on the static array units[], so the method knows its starting point.
        for (int i = 0; i < units.length; i++) 
        {
            if (units[i].equals(startingUnit)) 
            {
                startingIndex = i;
                break;
            }
        }

        // Set targetIndex's value so the method knows its end goal index within static array units[].
        for (int i = 0; i < units.length; i++) {
            if (units[i].equals(targetUnit)) 
            {
                targetIndex = i;
                break;
            }
        }

        // Set amountTo equal to amountFrom prior to processing so the processing knows what value to begin working with
        amountTo = amountFrom;

        // If statement examines the selected starting unit type, the desired unit type, and modifies amountTo accordingly
        if (startingIndex == targetIndex) // Selected units are the same (e.g., teaspoons to teaspoons)
        {
            // When selected units are identical, no processing is needed.
        }
        else if (startingIndex < targetIndex) // Converting from smaller unit to bigger unit (e.g., pint to quart)
        {
            for (int i = startingIndex; i < targetIndex; i++) 
            {
                /* 
                 * Because the conversion constants are only given in bigger-to-smaller format, 
                 * converting "down" requires multiplying by each constant's reciprocal (i.e., (1 / x)).
                 */
                amountTo = amountTo * (1 / conversionsArray[i]);
            }
        }
        else if (startingIndex > targetIndex) // Converting from bigger unit to smaller unit (e.g., quart to pint)
        {
            for (int i = startingIndex; i > targetIndex; i--) 
            {
                amountTo = amountTo * conversionsArray[i - 1];
            }
        }

        /* 
         * Finally, return the value of the processed amountTo double
         * For the sake of practicality, this double is shortened to 2 decimal places (e.g., 3.14).
         */
        return Double.parseDouble(String.format("%.2f", amountTo));
    } // END ConvertFromTo
 } // END UnitConversion