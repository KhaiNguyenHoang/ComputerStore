package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.util.UUID;

@Entity
@Table(name = "ProductSpecifications")
public class ProductSpecification {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "SpecID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product productID;

    @Nationalized
    @Column(name = "SpecName", nullable = false, length = 100)
    private String specName;

    @Nationalized
    @Column(name = "SpecValue", nullable = false)
    private String specValue;

    @Nationalized
    @Column(name = "SpecGroup", length = 100)
    private String specGroup;

    @ColumnDefault("0")
    @Column(name = "DisplayOrder")
    private Integer displayOrder;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public String getSpecGroup() {
        return specGroup;
    }

    public void setSpecGroup(String specGroup) {
        this.specGroup = specGroup;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}