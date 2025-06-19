package model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "Coupons")
open class Coupon {
    @Id
    @Column(name = "CouponID", nullable = false)
    open var id: Int? = null

    @Nationalized
    @Column(name = "CouponCode", nullable = false, length = 50)
    open var couponCode: String? = null

    @Nationalized
    @Column(name = "CouponName", nullable = false)
    open var couponName: String? = null

    @Nationalized
    @Column(name = "Description", length = 500)
    open var description: String? = null

    @Nationalized
    @Column(name = "DiscountType", nullable = false, length = 20)
    open var discountType: String? = null

    @Column(name = "DiscountValue", nullable = false, precision = 18, scale = 2)
    open var discountValue: BigDecimal? = null

    @ColumnDefault("0")
    @Column(name = "MinOrderAmount", precision = 18, scale = 2)
    open var minOrderAmount: BigDecimal? = null

    @Column(name = "MaxDiscountAmount", precision = 18, scale = 2)
    open var maxDiscountAmount: BigDecimal? = null

    @Column(name = "UsageLimit")
    open var usageLimit: Int? = null

    @ColumnDefault("0")
    @Column(name = "UsedCount")
    open var usedCount: Int? = null

    @ColumnDefault("1")
    @Column(name = "IsActive")
    open var isActive: Boolean? = null

    @Column(name = "StartDate", nullable = false)
    open var startDate: Instant? = null

    @Column(name = "EndDate", nullable = false)
    open var endDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null
}