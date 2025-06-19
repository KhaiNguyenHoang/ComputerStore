package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.Instant
import java.util.*

@Entity
open class UserCouponUsage {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "UsageID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CouponID", nullable = false)
    open var couponID: Coupon? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrderID", nullable = false)
    open var orderID: Order? = null

    @ColumnDefault("getdate()")
    @Column(name = "UsedDate")
    open var usedDate: Instant? = null
}