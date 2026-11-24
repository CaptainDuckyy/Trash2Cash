package com.trash2cash.model;

import java.util.Base64;

public class WasteItemInput {
    private int rowNumber;                 
    private byte[] photoBytes;           
    private String mimeType;             
    private String description;           
    private String condition;             
    private boolean userMarkedRecyclable; 
    private double weightKg;               
    private int quantity;                  

    public WasteItemInput() {
        this.quantity = 1;
    }

    public WasteItemInput(int rowNumber, byte[] photoBytes, String mimeType, String description,
                          String condition, boolean userMarkedRecyclable, double weightKg, int quantity) {
        this.rowNumber = rowNumber;
        this.photoBytes = photoBytes;
        this.mimeType = mimeType;
        this.description = description;
        this.condition = condition;
        this.userMarkedRecyclable = userMarkedRecyclable;
        this.weightKg = Math.max(0.0, weightKg);
        this.quantity = Math.max(1, quantity);
    }

    /**
     * Checks if this row was left empty by the user so it can be skipped.
     */
    public boolean isEmpty() {
        boolean hasPhoto = photoBytes != null && photoBytes.length > 0;
        boolean hasDescription = description != null && !description.trim().isEmpty();
        return !hasPhoto && !hasDescription;
    }


    public String getPhotoBase64() {
        if (photoBytes == null || photoBytes.length == 0) {
            return "";
        }
        return Base64.getEncoder().encodeToString(photoBytes);
    }

    // Getters and Setters
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public byte[] getPhotoBytes() { return photoBytes; }
    public void setPhotoBytes(byte[] photoBytes) { this.photoBytes = photoBytes; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public boolean isUserMarkedRecyclable() { return userMarkedRecyclable; }
    public void setUserMarkedRecyclable(boolean userMarkedRecyclable) { this.userMarkedRecyclable = userMarkedRecyclable; }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = Math.max(0.0, weightKg); }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = Math.max(1, quantity); }
}
