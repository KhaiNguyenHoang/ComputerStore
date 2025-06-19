package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "product_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "product_code", nullable = false, length = 50)
    private String productCode;

    @Nationalized
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Nationalized
    @Lob
    @Column(name = "description")
    private String description;

    @Nationalized
    @Lob
    @Column(name = "specifications")
    private String specifications;

    @Nationalized
    @ColumnDefault("N'Cái'")
    @Column(name = "unit", length = 20)
    private String unit;

    @ColumnDefault("0")
    @Column(name = "cost_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal costPrice;

    @ColumnDefault("0")
    @Column(name = "selling_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal sellingPrice;

    @ColumnDefault("0")
    @Column(name = "discount_percent", precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @ColumnDefault("12")
    @Column(name = "warranty_months")
    private Integer warrantyMonths;

    @Column(name = "weight", precision = 8, scale = 2)
    private BigDecimal weight;

    @Nationalized
    @Column(name = "dimensions", length = 100)
    private String dimensions;

    @Nationalized
    @Column(name = "color", length = 50)
    private String color;

    @Nationalized
    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "year_released")
    private Integer yearReleased;

    @Nationalized
    @Column(name = "energy_rating", length = 10)
    private String energyRating;

    @Nationalized
    @Lob
    @Column(name = "compatibility")
    private String compatibility;

    @ColumnDefault("0")
    @Column(name = "is_featured")
    private Boolean isFeatured;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @Nationalized
    @Column(name = "meta_title", length = 200)
    private String metaTitle;

    @Nationalized
    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Nationalized
    @Column(name = "slug", length = 200)
    private String slug;

    @ColumnDefault("getdate()")
    @Column(name = "created_date")
    private Instant createdDate;

    @ColumnDefault("getdate()")
    @Column(name = "updated_date")
    private Instant updatedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(Integer yearReleased) {
        this.yearReleased = yearReleased;
    }

    public String getEnergyRating() {
        return energyRating;
    }

    public void setEnergyRating(String energyRating) {
        this.energyRating = energyRating;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

}