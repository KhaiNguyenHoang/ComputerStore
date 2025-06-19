package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.Instant
import java.util.*

@Entity
open class ShoppingCart {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "CartID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @ColumnDefault("1")
    @Column(name = "Quantity", nullable = false)
    open var quantity: Int? = null

    @ColumnDefault("getdate()")
    @Column(name = "AddedDate")
    open var addedDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null
}