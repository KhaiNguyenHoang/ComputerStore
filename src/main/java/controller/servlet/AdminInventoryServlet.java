package controller.servlet;

import dao.InventoryTransactionDAO;
import model.InventoryTransaction;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/admin/inventory"})
public class AdminInventoryServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AdminInventoryServlet.class.getName());
    private InventoryTransactionDAO inventoryTransactionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        inventoryTransactionDAO = new InventoryTransactionDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request, response)) return; // Kiểm tra quyền admin

        listInventoryTransactions(request, response);
    }

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        if (currentUser == null || !"Admin".equalsIgnoreCase(currentUser.getRole())) {
            LOGGER.warning("Unauthorized access attempt to admin inventory management by user: " + (currentUser != null ? currentUser.getUsername() : "Guest"));
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này.");
            return false;
        }
        return true;
    }

    private void listInventoryTransactions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<InventoryTransaction> transactions = inventoryTransactionDAO.getAllTransactions();
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/admin-inventory-transactions.jsp").forward(request, response);
    }
}
