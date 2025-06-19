package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "order_number", nullable = false, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ColumnDefault("getdate()")
    @Column(name = "order_date")
    private Instant orderDate;

    @Column(name = "required_date")
    private Instant requiredDate;

    @Column(name = "shipped_date")
    private Instant shippedDate;

    @Nationalized
    @ColumnDefault("N'Pending'")
    @Column(name = "order_status", length = 20)
    private String orderStatus;

    @Nationalized
    @ColumnDefault("N'Unpaid'")
    @Column(name = "payment_status", length = 20)
    private String paymentStatus;

    @Nationalized
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @ColumnDefault("0")
    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @ColumnDefault("0")
    @Column(name = "tax_amount", precision = 15, scale = 2)
    private BigDecimal taxAmount;

    @ColumnDefault("0")
    @Column(name = "shipping_fee", precision = 15, scale = 2)
    private BigDecimal shippingFee;

    @ColumnDefault("0")
    @Column(name = "discount_amount", precision = 15, scale = 2)
    private BigDecimal discountAmount;

    @ColumnDefault("0")
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Nationalized
    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @Nationalized
    @Column(name = "shipping_ward", length = 100)
    private String shippingWard;

    @Nationalized
    @Column(name = "shipping_district", length = 100)
    private String shippingDistrict;

    @Nationalized
    @Column(name = "shipping_city", length = 100)
    private String shippingCity;

    @Nationalized
    @Lob
    @Column(name = "notes")
    private String notes;

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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Instant getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Instant requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Instant getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Instant shippedDate) {
        this.shippedDate = shippedDate;
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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingWard() {
        return shippingWard;
    }

    public void setShippingWard(String shippingWard) {
        this.shippingWard = shippingWard;
    }

    public String getShippingDistrict() {
        return shippingDistrict;
    }

    public void setShippingDistrict(String shippingDistrict) {
        this.shippingDistrict = shippingDistrict;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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