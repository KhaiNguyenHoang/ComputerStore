package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant
import java.util.*

@Entity
@Table(name = "UserAddresses")
open class UserAddress {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "AddressID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @Nationalized
    @Column(name = "AddressType", nullable = false, length = 20)
    open var addressType: String? = null

    @Nationalized
    @Column(name = "AddressLine1", nullable = false)
    open var addressLine1: String? = null

    @Nationalized
    @Column(name = "AddressLine2")
    open var addressLine2: String? = null

    @Nationalized
    @Column(name = "City", nullable = false, length = 100)
    open var city: String? = null

    @Nationalized
    @Column(name = "State", nullable = false, length = 100)
    open var state: String? = null

    @Nationalized
    @Column(name = "PostalCode", nullable = false, length = 20)
    open var postalCode: String? = null

    @Nationalized
    @Column(name = "Country", nullable = false, length = 100)
    open var country: String? = null

    @ColumnDefault("0")
    @Column(name = "IsDefault")
    open var isDefault: Boolean? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null
}