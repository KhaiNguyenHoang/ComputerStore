package dao;

import dto.LoginRequestDTO;
import dto.RegisterRequestDTO;
import io.github.cdimascio.dotenv.Dotenv;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import util.DBContext;
import util.EmailService;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.HashMap;

public class AuthDAO extends DBContext {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AuthDAO.class.getName());
    private final EmailService emailService;

    public AuthDAO(EmailService emailService) {
        super();
        this.emailService = emailService;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String currentPassword, String hashedPassword) {
        return BCrypt.checkpw(currentPassword, hashedPassword);
    }

    public boolean checkExistEmail(String email) {
        String sql = "SELECT * "+"FROM Users WHERE Email = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warning("Failed to check if email exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistPhoneNumber(String phoneNumber) {
        String sql = "SELECT * "+"FROM Users WHERE PhoneNumber = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warning("Failed to check if phone number exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistUsername(String username) {
        String sql = "SELECT * "+"FROM Users WHERE Username = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.warning("Failed to check if username exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkDateOfBirth(Date dob) {
        return dob.toLocalDate().isBefore(java.time.LocalDate.now().minusYears(18));
    }

    public boolean checkGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"));
    }

    public User login(LoginRequestDTO loginRequest) {
        String sql = "SELECT UserID, Username, Email, PasswordHash, Salt, FirstName, LastName, PhoneNumber, DateOfBirth, Gender, IsEmailVerified, IsActive, Role, CreatedDate, ModifiedDate, LastLoginDate FROM Users WHERE Username = ? AND IsActive = 1";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, loginRequest.getUsername());
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPasswordHash = resultSet.getString("PasswordHash");
                if (checkPassword(loginRequest.getPassword(), storedPasswordHash)) {
                    User user = new User();
                    user.setId((UUID) resultSet.getObject("UserID"));
                    user.setUsername(resultSet.getString("Username"));
                    user.setEmail(resultSet.getString("Email"));
                    user.setPasswordHash(storedPasswordHash);
                    user.setSalt(resultSet.getString("Salt"));
                    user.setFirstName(resultSet.getString("FirstName"));
                    user.setLastName(resultSet.getString("LastName"));
                    user.setPhoneNumber(resultSet.getString("PhoneNumber"));
                    Date dob = resultSet.getDate("DateOfBirth");
                    if (dob != null) {
                        user.setDateOfBirth(dob.toLocalDate());
                    }
                    user.setGender(resultSet.getString("Gender"));
                    user.setIsEmailVerified(resultSet.getBoolean("IsEmailVerified"));
                    user.setIsActive(resultSet.getBoolean("IsActive"));
                    user.setRole(resultSet.getString("Role"));
                    user.setCreatedDate(Instant.from(resultSet.getTimestamp("CreatedDate").toLocalDateTime()));
                    user.setModifiedDate(Instant.from(resultSet.getTimestamp("ModifiedDate").toLocalDateTime()));
                    if (resultSet.getTimestamp("LastLoginDate") != null) {
                        user.setLastLoginDate(Instant.from(resultSet.getTimestamp("LastLoginDate").toLocalDateTime()));
                    }

                    String updateSql = "UPDATE Users SET LastLoginDate = GETDATE() WHERE UserID = ?";
                    try (var updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setString(1, user.getId().toString());
                        updateStmt.executeUpdate();
                    }

                    return user;
                }
            }
            return null;
        } catch (SQLException e) {
            logger.warning("Failed to login: " + e.getMessage());
            return null;
        }
    }

    public boolean register(RegisterRequestDTO registerRequest) {
        if (checkExistUsername(registerRequest.getUsername())) {
            logger.warning("Username already exists: " + registerRequest.getUsername());
            return false;
        }
        if (checkExistEmail(registerRequest.getEmail())) {
            logger.warning("Email already exists: " + registerRequest.getEmail());
            return false;
        }
        if (registerRequest.getPhoneNumber() != null && checkExistPhoneNumber(registerRequest.getPhoneNumber())) {
            logger.warning("Phone number already exists: " + registerRequest.getPhoneNumber());
            return false;
        }
        if (registerRequest.getDateOfBirth() != null && !checkDateOfBirth(Date.valueOf(registerRequest.getDateOfBirth()))) {
            logger.warning("User must be at least 18 years old");
            return false;
        }
        if (registerRequest.getGender() != null && !checkGender(registerRequest.getGender())) {
            logger.warning("Invalid gender: " + registerRequest.getGender());
            return false;
        }

        String passwordHash = hashPassword(registerRequest.getPassword());
        String salt = BCrypt.gensalt();

        String userSql = "INSERT INTO Users (Username, Email, PasswordHash, Salt, FirstName, LastName, PhoneNumber, DateOfBirth, Gender, Role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement userStmt = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
            userStmt.setString(1, registerRequest.getUsername());
            userStmt.setString(2, registerRequest.getEmail());
            userStmt.setString(3, passwordHash);
            userStmt.setString(4, salt);
            userStmt.setString(5, registerRequest.getFirstName());
            userStmt.setString(6, registerRequest.getLastName());
            userStmt.setString(7, registerRequest.getPhoneNumber());
            if (registerRequest.getDateOfBirth() != null) {
                userStmt.setDate(8, Date.valueOf(registerRequest.getDateOfBirth()));
            } else {
                userStmt.setNull(8, Types.DATE);
            }
            userStmt.setString(9, registerRequest.getGender());
            userStmt.setString(10, "Customer");
            userStmt.executeUpdate();

            ResultSet rs = userStmt.getGeneratedKeys();
            if (!rs.next()) {
                logger.severe("Failed to retrieve generated UserID");
                return false;
            }
            UUID userId = UUID.fromString(rs.getString(1));

            UUID token = UUID.randomUUID();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);
            String tokenSql = "INSERT INTO EmailVerificationTokens (UserID, Token, TokenType, ExpiryDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement tokenStmt = connection.prepareStatement(tokenSql)) {
                tokenStmt.setString(1, userId.toString());
                tokenStmt.setString(2, token.toString());
                tokenStmt.setString(3, "EMAIL_VERIFICATION");
                tokenStmt.setTimestamp(4, Timestamp.valueOf(expiryDate));
                tokenStmt.executeUpdate();
            }

            Dotenv dotenv = Dotenv.configure().filename("save.env").ignoreIfMissing().load();
            String getAppBaseUrl = dotenv.get("APP_BASE_URL", "http://localhost:8080/fgear");
            try{
                HashMap<String, Object> variables = new HashMap<>();
                variables.put("firstName", registerRequest.getFirstName());
                variables.put("verificationLink", getAppBaseUrl + "/verify?token=" + token.toString());
                emailService.sendEmail(
                        registerRequest.getEmail(),
                        "Verify Your Email Address",
                        "email-verification",
                        variables,
                        null
                );
            } catch (Exception e) {
                logger.severe("Failed to send verification email: " + e.getMessage());
                String deleteSql = "DELETE FROM Users WHERE UserID = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, userId.toString());
                    deleteStmt.executeUpdate();
                }
                return false;
            }

            return true;
        } catch (SQLException e) {
            logger.severe("Failed to register user: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyEmail(String token) {
        String sql = "SELECT TokenID, UserID, ExpiryDate, IsUsed FROM EmailVerificationTokens WHERE Token = ? AND TokenType = 'EMAIL_VERIFICATION'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                logger.warning("Invalid or non-existent verification token: " + token);
                return false;
            }

            UUID tokenId = UUID.fromString(rs.getString("TokenID"));
            UUID userId = UUID.fromString(rs.getString("UserID"));
            LocalDateTime expiryDate = rs.getTimestamp("ExpiryDate").toLocalDateTime();
            boolean isUsed = rs.getBoolean("IsUsed");

            if (isUsed) {
                logger.warning("Verification token already used: " + token);
                return false;
            }
            if (expiryDate.isBefore(LocalDateTime.now())) {
                logger.warning("Verification token expired: " + token);
                return false;
            }

            // Update token as used
            String updateTokenSql = "UPDATE EmailVerificationTokens SET IsUsed = 1, UsedDate = GETDATE() WHERE TokenID = ?";
            try (PreparedStatement updateTokenStmt = connection.prepareStatement(updateTokenSql)) {
                updateTokenStmt.setString(1, tokenId.toString());
                updateTokenStmt.executeUpdate();
            }

            // Update user as verified
            String updateUserSql = "UPDATE Users SET IsEmailVerified = 1, ModifiedDate = GETDATE() WHERE UserID = ?";
            try (PreparedStatement updateUserStmt = connection.prepareStatement(updateUserSql)) {
                updateUserStmt.setString(1, userId.toString());
                updateUserStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            logger.severe("Failed to verify email: " + e.getMessage());
            return false;
        }
    }
}