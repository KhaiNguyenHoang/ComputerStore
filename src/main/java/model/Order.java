package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "OrderID", nullable = false)
    private UUID id;

    @Nationalized
    @Column(name = "OrderNumber", nullable = false, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    private User userID;

    @Nationalized
    @ColumnDefault("'Pending'")
    @Column(name = "OrderStatus", nullable = false, length = 50)
    private String orderStatus;

    @Nationalized
    @ColumnDefault("'Pending'")
    @Column(name = "PaymentStatus", nullable = false, length = 50)
    private String paymentStatus;

    @Nationalized
    @Column(name = "PaymentMethod", length = 50)
    private String paymentMethod;

    @Nationalized
    @Column(name = "PaymentTransactionID")
    private String paymentTransactionID;

    @Column(name = "SubtotalAmount", nullable = false, precision = 18, scale = 2)
    private BigDecimal subtotalAmount;

    @ColumnDefault("0")
    @Column(name = "TaxAmount", precision = 18, scale = 2)
    private BigDecimal taxAmount;

    @ColumnDefault("0")
    @Column(name = "ShippingAmount", precision = 18, scale = 2)
    private BigDecimal shippingAmount;

    @ColumnDefault("0")
    @Column(name = "DiscountAmount", precision = 18, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "TotalAmount", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalAmount;

    @Nationalized
    @ColumnDefault("'USD'")
    @Column(name = "CurrencyCode", length = 3)
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingAddressID")
    private UserAddress shippingAddressID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BillingAddressID")
    private UserAddress billingAddressID;

    @Nationalized
    @Column(name = "ShippingTrackingNumber", length = 100)
    private String shippingTrackingNumber;

    @Nationalized
    @Column(name = "ShippingCarrier", length = 100)
    private String shippingCarrier;

    @Nationalized
    @Lob
    @Column(name = "Notes")
    private String notes;

    @ColumnDefault("getdate()")
    @Column(name = "OrderDate")
    private Instant orderDate;

    @Column(name = "ShippedDate")
    private Instant shippedDate;

    @Column(name = "DeliveredDate")
    private Instant deliveredDate;

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    private Instant modifiedDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentTransactionID() {
        return paymentTransactionID;
    }

    public void setPaymentTransactionID(String paymentTransactionID) {
        this.paymentTransactionID = paymentTransactionID;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public UserAddress getShippingAddressID() {
        return shippingAddressID;
    }

    public void setShippingAddressID(UserAddress shippingAddressID) {
        this.shippingAddressID = shippingAddressID;
    }

    public UserAddress getBillingAddressID() {
        return billingAddressID;
    }

    public void setBillingAddressID(UserAddress billingAddressID) {
        this.billingAddressID = billingAddressID;
    }

    public String getShippingTrackingNumber() {
        return shippingTrackingNumber;
    }

    public void setShippingTrackingNumber(String shippingTrackingNumber) {
        this.shippingTrackingNumber = shippingTrackingNumber;
    }

    public String getShippingCarrier() {
        return shippingCarrier;
    }

    public void setShippingCarrier(String shippingCarrier) {
        this.shippingCarrier = shippingCarrier;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Instant shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Instant getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Instant deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}