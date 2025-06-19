package model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant

@Entity
@Table(name = "Brands")
open class Brand {
    @Id
    @Column(name = "BrandID", nullable = false)
    open var id: Int? = null

    @Nationalized
    @Column(name = "BrandName", nullable = false, length = 100)
    open var brandName: String? = null

    @Nationalized
    @Column(name = "Description", length = 500)
    open var description: String? = null

    @Nationalized
    @Column(name = "LogoURL")
    open var logoURL: String? = null

    @Nationalized
    @Column(name = "Website")
    open var website: String? = null

    @ColumnDefault("1")
    @Column(name = "IsActive")
    open var isActive: Boolean? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null
}