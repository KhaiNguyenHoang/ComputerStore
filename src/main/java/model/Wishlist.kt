package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.Instant
import java.util.*

@Entity
@Table(name = "Wishlists")
open class Wishlist {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "WishlistID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @ColumnDefault("getdate()")
    @Column(name = "AddedDate")
    open var addedDate: Instant? = null
}