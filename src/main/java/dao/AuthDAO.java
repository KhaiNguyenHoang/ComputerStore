package dao;

import org.mindrot.jbcrypt.BCrypt;
import util.DBContext;

import java.sql.Date;
import java.util.logging.Logger;

public class AuthDAO extends DBContext {
    private static final long serialVersionUID = 1L;
    private final Logger logger = Logger.getLogger(AuthDAO.class.getName());
    public AuthDAO() {
        super();
    }

    public String hashPassword (String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword (String currentPassword, String userPassword)
    {
        return BCrypt.checkpw(currentPassword, userPassword);
    }

    public boolean checkExistEmail (String email)
    {
        String sql = "SELECT * "+ " FROM Users WHERE Email LIKE ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            logger.warning("Failed to check if email exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistPhoneNumber (String phoneNumber)
    {
        String sql = "SELECT * "+ " FROM Users WHERE PhoneNumber LIKE ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            logger.warning("Failed to check if phone number exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkExistUsername (String username)
    {
        String sql = "SELECT * "+ " FROM Users WHERE Username LIKE ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            logger.warning("Failed to check if username exists: " + e.getMessage());
            return false;
        }
    }

    public boolean checkDateOfBirth (Date dob)
    {
        return dob.toLocalDate().isBefore(java.time.LocalDate.now().minusYears(18));
    }

    public boolean checkGender (String gender)
    {
        return gender.equals("Male") || gender.equals("Female");
    }
}
