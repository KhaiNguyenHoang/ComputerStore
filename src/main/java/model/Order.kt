package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "Orders")
open class Order {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "OrderID", nullable = false)
    open var id: UUID? = null

    @Nationalized
    @Column(name = "OrderNumber", nullable = false, length = 50)
    open var orderNumber: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @Nationalized
    @ColumnDefault("'Pending'")
    @Column(name = "OrderStatus", nullable = false, length = 50)
    open var orderStatus: String? = null

    @Nationalized
    @ColumnDefault("'Pending'")
    @Column(name = "PaymentStatus", nullable = false, length = 50)
    open var paymentStatus: String? = null

    @Nationalized
    @Column(name = "PaymentMethod", length = 50)
    open var paymentMethod: String? = null

    @Nationalized
    @Column(name = "PaymentTransactionID")
    open var paymentTransactionID: String? = null

    @Column(name = "SubtotalAmount", nullable = false, precision = 18, scale = 2)
    open var subtotalAmount: BigDecimal? = null

    @ColumnDefault("0")
    @Column(name = "TaxAmount", precision = 18, scale = 2)
    open var taxAmount: BigDecimal? = null

    @ColumnDefault("0")
    @Column(name = "ShippingAmount", precision = 18, scale = 2)
    open var shippingAmount: BigDecimal? = null

    @ColumnDefault("0")
    @Column(name = "DiscountAmount", precision = 18, scale = 2)
    open var discountAmount: BigDecimal? = null

    @Column(name = "TotalAmount", nullable = false, precision = 18, scale = 2)
    open var totalAmount: BigDecimal? = null

    @Nationalized
    @ColumnDefault("'USD'")
    @Column(name = "CurrencyCode", length = 3)
    open var currencyCode: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ShippingAddressID")
    open var shippingAddressID: UserAddress? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BillingAddressID")
    open var billingAddressID: UserAddress? = null

    @Nationalized
    @Column(name = "ShippingTrackingNumber", length = 100)
    open var shippingTrackingNumber: String? = null

    @Nationalized
    @Column(name = "ShippingCarrier", length = 100)
    open var shippingCarrier: String? = null

    @Nationalized
    @Lob
    @Column(name = "Notes")
    open var notes: String? = null

    @ColumnDefault("getdate()")
    @Column(name = "OrderDate")
    open var orderDate: Instant? = null

    @Column(name = "ShippedDate")
    open var shippedDate: Instant? = null

    @Column(name = "DeliveredDate")
    open var deliveredDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null
}