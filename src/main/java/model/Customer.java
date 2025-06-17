package model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "customer_code", nullable = false, length = 20)
    private String customerCode;

    @Nationalized
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Nationalized
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Nationalized
    @ColumnDefault("[last_name]+' '+[first_name]")
    @Column(name = "full_name", nullable = false, length = 101)
    private String fullName;

    @Nationalized
    @Column(name = "email", length = 100)
    private String email;

    @Nationalized
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Nationalized
    @Column(name = "gender", length = 10)
    private String gender;

    @Nationalized
    @Column(name = "address", length = 500)
    private String address;

    @Nationalized
    @Column(name = "ward", length = 100)
    private String ward;

    @Nationalized
    @Column(name = "district", length = 100)
    private String district;

    @Nationalized
    @Column(name = "city", length = 100)
    private String city;

    @Nationalized
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Nationalized
    @ColumnDefault("N'Lẻ'")
    @Column(name = "customer_type", length = 20)
    private String customerType;

    @ColumnDefault("0")
    @Column(name = "credit_limit", precision = 15, scale = 2)
    private BigDecimal creditLimit;

    @ColumnDefault("0")
    @Column(name = "total_spent", precision = 15, scale = 2)
    private BigDecimal totalSpent;

    @ColumnDefault("0")
    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @ColumnDefault("getdate()")
    @Column(name = "registration_date")
    private Instant registrationDate;

    @Column(name = "last_purchase_date")
    private Instant lastPurchaseDate;

    @ColumnDefault("1")
    @Column(name = "is_active")
    private Boolean isActive;

    @Nationalized
    @Lob
    @Column(name = "notes")
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Instant lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}