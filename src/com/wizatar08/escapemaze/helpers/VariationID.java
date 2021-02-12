package com.wizatar08.escapemaze.helpers;

public class VariationID {
    public static int TYPE_ID;
    public static String GROUP_ID;
    public static String VARIATION_ID;

    /**
     * Creation of an objects ID. Unique to each object.
     * @param typeId Must be 1 digit long
     * @param groupID Must be 3 digits long
     * @param variationID Must be 2 digits long
     */
    public VariationID(int typeId, String groupID, String variationID) {
        TYPE_ID = typeId;
        GROUP_ID = groupID;
        VARIATION_ID = variationID;
    }

    /**
     * Creates a null variation ID.
     */
    public VariationID(int typeId) {
        new VariationID(typeId, "000", "00");
    }

    /**
     * Creates a completely null variation ID.
     */
    public VariationID() {
        new VariationID(0, "000", "00");
    }

    public String getFullId() {
        return TYPE_ID + GROUP_ID + VARIATION_ID;
    }
}
