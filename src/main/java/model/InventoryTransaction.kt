package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "InventoryTransactions")
public class InventoryTransaction {
    @Id
    @Column(name = "transaction_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Nationalized
    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    @Nationalized
    @Column(name = "reference_type", length = 20)
    private String referenceType;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @Column(name = "quantity_before", nullable = false)
    private Integer quantityBefore;

    @Column(name = "quantity_after", nullable = false)
    private Integer quantityAfter;

    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_value", precision = 15, scale = 2)
    private BigDecimal totalValue;

    @Nationalized
    @Column(name = "notes", length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ColumnDefault("getdate()")
    @Column(name = "transaction_date")
    private Instant transactionDate;

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(Integer quantityChange) {
        this.quantityChange = quantityChange;
    }

    public Integer getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityBefore(Integer quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    public Integer getQuantityAfter() {
        return quantityAfter;
    }

    public void setQuantityAfter(Integer quantityAfter) {
        this.quantityAfter = quantityAfter;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

}