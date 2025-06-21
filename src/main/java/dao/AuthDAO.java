package dao;

import dto.LoginRequestDTO;
import dto.RegisterRequestDTO;
import io.github.cdimascio.dotenv.Dotenv;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import util.DBContext;
import util.EmailService;
import util.FileService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class AuthDAO extends DBContext {
    private static final Logger logger = Logger.getLogger(AuthDAO.class.getName());
    private final EmailService emailService;

    public AuthDAO(EmailService emailService) {
        super();
        this.emailService = emailService;
        logger.info("AuthDAO initialized with EmailService: " + (emailService != null ? "OK" : "NULL"));
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String currentPassword, String hashedPassword) {
        return BCrypt.checkpw(currentPassword, hashedPassword);
    }

    public boolean checkExistEmail(String email) {
        String sql = "SELECT 1 FROM Users WHERE Email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.warning("Failed to check if email exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistPhoneNumber(String phoneNumber) {
        String sql = "SELECT 1 FROM Users WHERE PhoneNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.warning("Failed to check if phone number exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistUsername(String username) {
        String sql = "SELECT 1 FROM Users WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.warning("Failed to check if username exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkDateOfBirth(Date dob) {
        return dob.toLocalDate().isBefore(LocalDate.now().minusYears(18));
    }

    public boolean checkGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"));
    }

    public User login(LoginRequestDTO loginRequest) throws SQLException {
        String sql = "SELECT UserID, Username, Email, PasswordHash, Salt, FirstName, LastName, IsEmailVerified, IsActive, Role, CreatedDate, ModifiedDate, LastLoginDate " +
                "FROM Users WHERE Username = ? AND IsActive = 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loginRequest.getUsername());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.warning("No active user found for username: " + loginRequest.getUsername());
                    throw new SQLException("Username not found or account is inactive");
                }

                String storedPasswordHash = resultSet.getString("PasswordHash");
                if (!checkPassword(loginRequest.getPassword(), storedPasswordHash)) {
                    logger.warning("Invalid password for username: " + loginRequest.getUsername());
                    throw new SQLException("Invalid password");
                }

                if (!resultSet.getBoolean("IsEmailVerified")) {
                    logger.warning("Email not verified for username: " + loginRequest.getUsername());
                    throw new SQLException("Email not verified");
                }

                var user = new User();
                user.setUserID(UUID.fromString(resultSet.getString("UserID")));
                user.setUsername(resultSet.getString("Username"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(storedPasswordHash);
                user.setSalt(resultSet.getString("Salt"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmailVerified(resultSet.getBoolean("IsEmailVerified"));
                user.setActive(resultSet.getBoolean("IsActive"));
                user.setRole(resultSet.getString("Role"));
                user.setCreatedDate(resultSet.getTimestamp("CreatedDate").toLocalDateTime());
                user.setModifiedDate(resultSet.getTimestamp("ModifiedDate").toLocalDateTime());
                Timestamp lastLogin = resultSet.getTimestamp("LastLoginDate");
                if (lastLogin != null) {
                    user.setLastLoginDate(lastLogin.toLocalDateTime());
                }

                String updateSql = "UPDATE Users SET LastLoginDate = CURRENT_TIMESTAMP WHERE UserID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, user.getUserID().toString());
                    updateStmt.executeUpdate();
                }

                logger.info("User logged in successfully: " + loginRequest.getUsername());
                return user;
            }
        } catch (SQLException e) {
            logger.warning("Failed to login for username: " + loginRequest.getUsername() + " - " + e.getMessage());
            throw e;
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

        String userSql = "INSERT INTO Users (UserID, Username, Email, PasswordHash, Salt, FirstName, LastName, PhoneNumber, DateOfBirth, Gender, Role, CreatedDate, ModifiedDate, IsActive) " +
                "OUTPUT INSERTED.UserID VALUES (NEWID(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?)";
        try (PreparedStatement userStmt = connection.prepareStatement(userSql)) {
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
            userStmt.setBoolean(11, true);

            boolean hasResult = userStmt.execute();
            if (!hasResult) {
                logger.severe("No result set returned from INSERT");
                return false;
            }

            try (ResultSet rs = userStmt.getResultSet()) {
                if (!rs.next()) {
                    logger.severe("Failed to retrieve generated UserID");
                    return false;
                }
                String userIdStr = rs.getString("UserID");
                if (userIdStr == null) {
                    logger.severe("Generated UserID is null");
                    return false;
                }
                UUID userId = UUID.fromString(userIdStr);
                logger.info("Generated UserID: " + userId);

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
                String appBaseUrl = dotenv.get("APP_BASE_URL", "http://localhost:8080/ComputerStore");
                try {
                    HashMap<String, Object> variables = new HashMap<>();
                    variables.put("firstName", registerRequest.getFirstName());
                    variables.put("verificationLink", appBaseUrl + "/verify?token=" + token.toString());
                    logger.info("Sending verification email to: " + registerRequest.getEmail());
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
            }
        } catch (SQLException e) {
            logger.severe("Failed to register user: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyEmail(String token) {
        String sql = "SELECT TokenID, UserID, ExpiryDate, IsUsed FROM EmailVerificationTokens WHERE Token = ? AND TokenType = 'EMAIL_VERIFICATION'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
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

                String updateTokenSql = "UPDATE EmailVerificationTokens SET IsUsed = 1, UsedDate = CURRENT_TIMESTAMP WHERE TokenID = ?";
                try (PreparedStatement updateTokenStmt = connection.prepareStatement(updateTokenSql)) {
                    updateTokenStmt.setString(1, tokenId.toString());
                    updateTokenStmt.executeUpdate();
                }

                String updateUserSql = "UPDATE Users SET IsEmailVerified = 1, ModifiedDate = CURRENT_TIMESTAMP WHERE UserID = ?";
                try (PreparedStatement updateUserStmt = connection.prepareStatement(updateUserSql)) {
                    updateUserStmt.setString(1, userId.toString());
                    updateUserStmt.executeUpdate();
                }

                return true;
            }
        } catch (SQLException e) {
            logger.severe("Failed to verify email: " + e.getMessage());
            return false;
        }
    }

    public boolean forgotPassword(String email) {
        String sql = "SELECT UserID, FirstName FROM Users WHERE Email = ? AND IsActive = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    logger.warning("Email not found or inactive: " + email);
                    return false;
                }

                UUID userId = UUID.fromString(rs.getString("UserID"));
                String firstName = rs.getString("FirstName");

                UUID token = UUID.randomUUID();
                LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
                String tokenSql = "INSERT INTO EmailVerificationTokens (UserID, Token, TokenType, ExpiryDate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement tokenStmt = connection.prepareStatement(tokenSql)) {
                    tokenStmt.setString(1, userId.toString());
                    tokenStmt.setString(2, token.toString());
                    tokenStmt.setString(3, "PASSWORD_RESET");
                    tokenStmt.setTimestamp(4, Timestamp.valueOf(expiryDate));
                    tokenStmt.executeUpdate();
                }

                Dotenv dotenv = Dotenv.configure().filename("save.env").ignoreIfMissing().load();
                String appBaseUrl = dotenv.get("APP_BASE_URL", "http://localhost:8080/ComputerStore");
                HashMap<String, Object> variables = new HashMap<>();
                variables.put("firstName", firstName);
                variables.put("resetLink", appBaseUrl + "/reset-password?token=" + token.toString());
                logger.info("Sending password reset email to: " + email);
                emailService.sendEmail(
                        email,
                        "Reset Your Password",
                        "password-reset",
                        variables,
                        null
                );

                return true;
            }
        } catch (Exception e) {
            logger.severe("Failed to process forgot password: " + e.getMessage());
            return false;
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        String sql = "SELECT TokenID, UserID, ExpiryDate, IsUsed FROM EmailVerificationTokens WHERE Token = ? AND TokenType = 'PASSWORD_RESET'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    logger.warning("Invalid or non-existent reset token: " + token);
                    return false;
                }

                UUID tokenId = UUID.fromString(rs.getString("TokenID"));
                UUID userId = UUID.fromString(rs.getString("UserID"));
                LocalDateTime expiryDate = rs.getTimestamp("ExpiryDate").toLocalDateTime();
                boolean isUsed = rs.getBoolean("IsUsed");

                if (isUsed) {
                    logger.warning("Reset token already used: " + token);
                    return false;
                }
                if (expiryDate.isBefore(LocalDateTime.now())) {
                    logger.warning("Reset token expired: " + token);
                    return false;
                }

                String passwordHash = hashPassword(newPassword);
                String salt = BCrypt.gensalt();
                String updateUserSql = "UPDATE Users SET PasswordHash = ?, Salt = ?, ModifiedDate = CURRENT_TIMESTAMP WHERE UserID = ?";
                try (PreparedStatement updateUserStmt = connection.prepareStatement(updateUserSql)) {
                    updateUserStmt.setString(1, passwordHash);
                    updateUserStmt.setString(2, salt);
                    updateUserStmt.setString(3, userId.toString());
                    updateUserStmt.executeUpdate();
                }

                String updateTokenSql = "UPDATE EmailVerificationTokens SET IsUsed = 1, UsedDate = CURRENT_TIMESTAMP WHERE TokenID = ?";
                try (PreparedStatement updateTokenStmt = connection.prepareStatement(updateTokenSql)) {
                    updateTokenStmt.setString(1, tokenId.toString());
                    updateTokenStmt.executeUpdate();
                }

                String emailSql = "SELECT Email, FirstName FROM Users WHERE UserID = ?";
                try (PreparedStatement emailStmt = connection.prepareStatement(emailSql)) {
                    emailStmt.setString(1, userId.toString());
                    try (ResultSet emailRs = emailStmt.executeQuery()) {
                        if (emailRs.next()) {
                            String email = emailRs.getString("Email");
                            String firstName = emailRs.getString("FirstName");
                            HashMap<String, Object> variables = new HashMap<>();
                            variables.put("firstName", firstName);
                            logger.info("Sending password changed email to: " + email);
                            emailService.sendEmail(
                                    email,
                                    "Your Password Has Been Changed",
                                    "password-changed",
                                    variables,
                                    null
                            );
                        }
                    }
                }

                return true;
            }
        } catch (Exception e) {
            logger.severe("Failed to reset password: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        AuthDAO authDAO = new AuthDAO(new EmailService(new FileService()));
        if (authDAO.resetPassword("C37A3AF5-1208-4355-AD31-8918B884DDA1","Khaideptrai1234sd#"))
        {
            System.out.println("Password reset successful");
        } else {
            System.out.println("Password reset failed");
        }
    }
}