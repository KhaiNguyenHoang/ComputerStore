package model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant
import java.util.*

@Entity
@Table(name = "EmailVerificationTokens")
open class EmailVerificationToken {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "TokenID", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UserID", nullable = false)
    open var userID: User? = null

    @ColumnDefault("newid()")
    @Column(name = "Token")
    open var token: UUID? = null

    @Nationalized
    @Column(name = "TokenType", nullable = false, length = 50)
    open var tokenType: String? = null

    @Column(name = "ExpiryDate", nullable = false)
    open var expiryDate: Instant? = null

    @ColumnDefault("0")
    @Column(name = "IsUsed")
    open var isUsed: Boolean? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @Column(name = "UsedDate")
    open var usedDate: Instant? = null
}