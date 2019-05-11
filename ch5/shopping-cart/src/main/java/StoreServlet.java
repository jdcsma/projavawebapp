import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "storeServlet",
        urlPatterns = "/shop",
        loadOnStartup = 1)
public class StoreServlet extends HttpServlet {

    private final Map<Integer, String> products = new HashMap<>();

    public StoreServlet() {
        this.products.put(1, "Sandpaper");
        this.products.put(2, "Nails");
        this.products.put(3, "Glue");
        this.products.put(4, "Paint");
        this.products.put(5, "Tape");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            action = "browse";
        }

        switch (action) {
            case "addToCart":
                this.addToCart(req, resp);
                break;
            case "viewCart":
                this.viewCart(req, resp);
                break;
            case "browse":
            default:
                this.browse(req, resp);
                break;
        }
    }

    private void addToCart(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        int productId;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("shop");
            return;
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Integer, Integer>());
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart =
                (Map<Integer, Integer>) session.getAttribute("cart");

        if (!cart.containsKey(productId)) {
            cart.put(productId, 0);
        }
        cart.put(productId, cart.get(productId) + 1);

        response.sendRedirect("shop?action=viewCart");
    }

    private void viewCart(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("products", this.products);
        request.getRequestDispatcher("WEB-INF/jsp/view/viewCart.jsp")
                .forward(request, response);
    }

    private void browse(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", this.products);
        request.getRequestDispatcher("WEB-INF/jsp/view/browse.jsp")
                .forward(request, response);
    }
}
