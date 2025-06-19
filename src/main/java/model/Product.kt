package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "Products")
open class Product {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "ProductID", nullable = false)
    open var id: UUID? = null

    @Nationalized
    @Column(name = "ProductName", nullable = false)
    open var productName: String? = null

    @Nationalized
    @Column(name = "SKU", nullable = false, length = 100)
    open var sku: String? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CategoryID", nullable = false)
    open var categoryID: Category? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BrandID", nullable = false)
    open var brandID: Brand? = null

    @Nationalized
    @Lob
    @Column(name = "Description")
    open var description: String? = null

    @Nationalized
    @Column(name = "ShortDescription", length = 500)
    open var shortDescription: String? = null

    @Column(name = "Price", nullable = false, precision = 18, scale = 2)
    open var price: BigDecimal? = null

    @Column(name = "ComparePrice", precision = 18, scale = 2)
    open var comparePrice: BigDecimal? = null

    @Column(name = "CostPrice", precision = 18, scale = 2)
    open var costPrice: BigDecimal? = null

    @Column(name = "Weight", precision = 10, scale = 2)
    open var weight: BigDecimal? = null

    @Nationalized
    @Column(name = "Dimensions", length = 100)
    open var dimensions: String? = null

    @ColumnDefault("0")
    @Column(name = "StockQuantity", nullable = false)
    open var stockQuantity: Int? = null

    @ColumnDefault("5")
    @Column(name = "MinStockLevel")
    open var minStockLevel: Int? = null

    @ColumnDefault("1000")
    @Column(name = "MaxStockLevel")
    open var maxStockLevel: Int? = null

    @ColumnDefault("1")
    @Column(name = "IsActive")
    open var isActive: Boolean? = null

    @ColumnDefault("0")
    @Column(name = "IsFeatured")
    open var isFeatured: Boolean? = null

    @ColumnDefault("0")
    @Column(name = "ViewCount")
    open var viewCount: Int? = null

    @ColumnDefault("0")
    @Column(name = "SalesCount")
    open var salesCount: Int? = null

    @ColumnDefault("0")
    @Column(name = "AverageRating", precision = 3, scale = 2)
    open var averageRating: BigDecimal? = null

    @ColumnDefault("0")
    @Column(name = "ReviewCount")
    open var reviewCount: Int? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null
}