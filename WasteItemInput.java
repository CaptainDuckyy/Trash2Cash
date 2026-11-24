package com.trash2cash.model;

import java.util.Base64;

public class WasteItemInput {
    private int rowNumber;                  // 1 to 5
    private byte[] photoBytes;              // Uploaded JPEG, PNG, or WebP bytes
    private String mimeType;                // "image/jpeg", "image/png", or "image/webp"
    private String description;             // Optional user hint (max 120 chars)
    private String condition;               // "GOOD", "FAIR", "DAMAGED", or "BROKEN"
    private boolean userMarkedRecyclable;   // true if radio = "yes", false if "no"
    private double weightKg;                // Optional weight in kg (0.0 if left blank)
    private int quantity;                   // Quantity (defaults to 1)

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

    /**
     * Helper to convert uploaded image bytes to Base64 for the Gemini API
     * or for displaying the photo preview on results.html.
     */
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