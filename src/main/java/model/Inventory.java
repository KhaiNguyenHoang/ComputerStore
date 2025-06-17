package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;

@Entity
public class Inventory {
    @Id
    @Column(name = "inventory_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Nationalized
    @ColumnDefault("N'Kho Chính'")
    @Column(name = "warehouse_location", length = 100)
    private String warehouseLocation;

    @ColumnDefault("0")
    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @ColumnDefault("0")
    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved;

    @ColumnDefault("[quantity_in_stock]-[quantity_reserved]")
    @Column(name = "quantity_available")
    private Integer quantityAvailable;

    @ColumnDefault("10")
    @Column(name = "min_stock_level")
    private Integer minStockLevel;

    @ColumnDefault("1000")
    @Column(name = "max_stock_level")
    private Integer maxStockLevel;

    @ColumnDefault("20")
    @Column(name = "reorder_point")
    private Integer reorderPoint;

    @Column(name = "last_restocked_date")
    private Instant lastRestockedDate;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Nationalized
    @Column(name = "batch_number", length = 50)
    private String batchNumber;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public Integer getMaxStockLevel() {
        return maxStockLevel;
    }

    public void setMaxStockLevel(Integer maxStockLevel) {
        this.maxStockLevel = maxStockLevel;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Instant getLastRestockedDate() {
        return lastRestockedDate;
    }

    public void setLastRestockedDate(Instant lastRestockedDate) {
        this.lastRestockedDate = lastRestockedDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
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