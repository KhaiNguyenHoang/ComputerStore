package model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.Nationalized
import java.time.Instant
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "Users")
open class User {
    @Id
    @ColumnDefault("newid()")
    @Column(name = "UserID", nullable = false)
    open var id: UUID? = null

    @Nationalized
    @Column(name = "Username", nullable = false, length = 50)
    open var username: String? = null

    @Nationalized
    @Column(name = "Email", nullable = false)
    open var email: String? = null

    @Nationalized
    @Column(name = "PasswordHash", nullable = false)
    open var passwordHash: String? = null

    @Nationalized
    @Column(name = "Salt", nullable = false)
    open var salt: String? = null

    @Nationalized
    @Column(name = "FirstName", nullable = false, length = 100)
    open var firstName: String? = null

    @Nationalized
    @Column(name = "LastName", nullable = false, length = 100)
    open var lastName: String? = null

    @Nationalized
    @Column(name = "PhoneNumber", length = 20)
    open var phoneNumber: String? = null

    @Column(name = "DateOfBirth")
    open var dateOfBirth: LocalDate? = null

    @Nationalized
    @Column(name = "Gender", length = 10)
    open var gender: String? = null

    @ColumnDefault("0")
    @Column(name = "IsEmailVerified")
    open var isEmailVerified: Boolean? = null

    @ColumnDefault("1")
    @Column(name = "IsActive")
    open var isActive: Boolean? = null

    @Nationalized
    @ColumnDefault("'Customer'")
    @Column(name = "Role", length = 20)
    open var role: String? = null

    @ColumnDefault("getdate()")
    @Column(name = "CreatedDate")
    open var createdDate: Instant? = null

    @ColumnDefault("getdate()")
    @Column(name = "ModifiedDate")
    open var modifiedDate: Instant? = null

    @Column(name = "LastLoginDate")
    open var lastLoginDate: Instant? = null
}