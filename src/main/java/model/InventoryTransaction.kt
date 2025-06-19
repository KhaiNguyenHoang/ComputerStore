package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant
import java.util.*

@Entity
@Table(name = "InventoryTransactions")
open class InventoryTransaction {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "TransactionID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    open var productID: Product? = null

    @Nationalized
    @Column(name = "TransactionType", nullable = false, length = 50)
    open var transactionType: String? = null

    @Column(name = "Quantity", nullable = false)
    open var quantity: Int? = null

    @Nationalized
    @Column(name = "ReferenceType", length = 50)
    open var referenceType: String? = null

    @Column(name = "ReferenceID")
    open var referenceID: UUID? = null

    @Nationalized
    @Column(name = "Notes", length = 500)
    open var notes: String? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedBy")
    open var createdBy: User? = null
}