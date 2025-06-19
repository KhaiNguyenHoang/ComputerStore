package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant

@Entity
@Table(name = "Categories")
open class Category {
    @Id
    @Column(name = "CategoryID", nullable = false)
    open var id: Int? = null

    @Nationalized
    @Column(name = "CategoryName", nullable = false, length = 100)
    open var categoryName: String? = null

    @Nationalized
    @Column(name = "Description", length = 500)
    open var description: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentCategoryID")
    open var parentCategoryID: Category? = null

    @ColumnDefault("1")
    @Column(name = "IsActive")
    open var isActive: Boolean? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null
}