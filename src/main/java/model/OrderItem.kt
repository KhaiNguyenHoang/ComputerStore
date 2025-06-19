package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "OrderItems")
open class OrderItem {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "OrderItemID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrderID", nullable = false)
    open var orderID: Order? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @Nationalized
    @Column(name = "ProductName", nullable = false)
    open var productName: String? = null

    @Nationalized
    @Column(name = "SKU", nullable = false, length = 100)
    open var sku: String? = null

    @Column(name = "Quantity", nullable = false)
    open var quantity: Int? = null

    @Column(name = "UnitPrice", nullable = false, precision = 18, scale = 2)
    open var unitPrice: BigDecimal? = null

    @Column(name = "TotalPrice", nullable = false, precision = 18, scale = 2)
    open var totalPrice: BigDecimal? = null
}