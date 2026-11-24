package com.trash2cash.model;

import java.util.List;

public class AnalysisResult {
    private int rowNumber;                  // 1 to 5 (maps to #choose1..#choose5)
    private boolean analysisFailed;         // true if photo couldn't be identified
    private String errorMessage;            // Shown when analysisFailed is true

    // Item & AI Detection Info
    private String itemName;                // e.g., "Clear PET Plastic Bottle"
    private String materialCategory;        // "PLASTIC", "PAPER", "GLASS", "METAL", or "OTHER"
    private String condition;               // "GOOD", "FAIR", "DAMAGED", or "BROKEN"
    private String photoDataUri;            // "data:image/jpeg;base64,..." for .photo-box img

    // Recyclability Mismatch Warning (triggers .warning box in style.css)
    private boolean showRecyclableWarning;
    private String warningMessage;

    // Guidance from WasteKnowledgeService
    private String primaryAction;           // e.g., "Upcycle", "Recycle", "Sell", "Dispose properly"
    private String recyclingSteps;          // Preparing and dropping off steps
    private String diyUpcycleIdea;          // Creative DIY suggestion & sellable product idea
    private String disposalGuidance;        // Safe disposal instructions as a last step
    private String dataSourceNote;          // Source note ("Guidance from Trash2Cash data")

    // Pricing & Profit Calculator Defaults
    private double estimatedMinValue;       // Low end of estimated market value
    private double estimatedMaxValue;       // High end of estimated market value
    private double suggestedMaterialCost;   // Pre-fill for .calc profit calculator
    private double suggestedSellingPrice;   // Suggested selling price for upcycled product

    // Upcycling Gallery Images (3 slots for .gallery in results.html)
    private List<String> upcycleGalleryUrls;

    public AnalysisResult() {}

    /**
     * Formats the estimated value range for the .value display (e.g., "₱15 – ₱25" or "$1.50 – $3.00").
     */
    public String getFormattedValueRange(String currencySymbol) {
        if (estimatedMaxValue <= 0) {
            return currencySymbol + "0.00";
        }
        return String.format("%s%.2f – %s%.2f", currencySymbol, estimatedMinValue, currencySymbol, estimatedMaxValue);
    }

    // Getters and Setters
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public boolean isAnalysisFailed() { return analysisFailed; }
    public void setAnalysisFailed(boolean analysisFailed) { this.analysisFailed = analysisFailed; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getMaterialCategory() { return materialCategory; }
    public void setMaterialCategory(String materialCategory) { this.materialCategory = materialCategory; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getPhotoDataUri() { return photoDataUri; }
    public void setPhotoDataUri(String photoDataUri) { this.photoDataUri = photoDataUri; }

    public boolean isShowRecyclableWarning() { return showRecyclableWarning; }
    public void setShowRecyclableWarning(boolean showRecyclableWarning) { this.showRecyclableWarning = showRecyclableWarning; }

    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }

    public String getPrimaryAction() { return primaryAction; }
    public void setPrimaryAction(String primaryAction) { this.primaryAction = primaryAction; }

    public String getRecyclingSteps() { return recyclingSteps; }
    public void setRecyclingSteps(String recyclingSteps) { this.recyclingSteps = recyclingSteps; }

    public String getDiyUpcycleIdea() { return diyUpcycleIdea; }
    public void setDiyUpcycleIdea(String diyUpcycleIdea) { this.diyUpcycleIdea = diyUpcycleIdea; }

    public String getDisposalGuidance() { return disposalGuidance; }
    public void setDisposalGuidance(String disposalGuidance) { this.disposalGuidance = disposalGuidance; }

    public String getDataSourceNote() { return dataSourceNote; }
    public void setDataSourceNote(String dataSourceNote) { this.dataSourceNote = dataSourceNote; }

    public double getEstimatedMinValue() { return estimatedMinValue; }
    public void setEstimatedMinValue(double estimatedMinValue) { this.estimatedMinValue = estimatedMinValue; }

    public double getEstimatedMaxValue() { return estimatedMaxValue; }
    public void setEstimatedMaxValue(double estimatedMaxValue) { this.estimatedMaxValue = estimatedMaxValue; }

    public double getSuggestedMaterialCost() { return suggestedMaterialCost; }
    public void setSuggestedMaterialCost(double suggestedMaterialCost) { this.suggestedMaterialCost = suggestedMaterialCost; }

    public double getSuggestedSellingPrice() { return suggestedSellingPrice; }
    public void setSuggestedSellingPrice(double suggestedSellingPrice) { this.suggestedSellingPrice = suggestedSellingPrice; }

    public List<String> getUpcycleGalleryUrls() { return upcycleGalleryUrls; }
    public void setUpcycleGalleryUrls(List<String> upcycleGalleryUrls) { this.upcycleGalleryUrls = upcycleGalleryUrls; }
}