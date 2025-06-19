package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.util.*

@Entity
@Table(name = "ProductSpecifications")
open class ProductSpecification {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "SpecID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @Nationalized
    @Column(name = "SpecName", nullable = false, length = 100)
    open var specName: String? = null

    @Nationalized
    @Column(name = "SpecValue", nullable = false)
    open var specValue: String? = null

    @Nationalized
    @Column(name = "SpecGroup", length = 100)
    open var specGroup: String? = null

    @ColumnDefault("0")
    @Column(name = "DisplayOrder")
    open var displayOrder: Int? = null
}