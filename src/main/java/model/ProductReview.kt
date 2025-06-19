package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant
import java.util.*

@Entity
@Table(name = "ProductReviews")
open class ProductReview {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "ReviewID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @Column(name = "Rating", nullable = false)
    open var rating: Int? = null

    @Nationalized
    @Column(name = "Title")
    open var title: String? = null

    @Nationalized
    @Lob
    @Column(name = "ReviewText")
    open var reviewText: String? = null

    @ColumnDefault("0")
    @Column(name = "IsVerifiedPurchase")
    open var isVerifiedPurchase: Boolean? = null

    @ColumnDefault("1")
    @Column(name = "IsPublished")
    open var isPublished: Boolean? = null

    @ColumnDefault("0")
    @Column(name = "HelpfulCount")
    open var helpfulCount: Int? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null
}