package jun.projavawebapp.servlet;

import jun.projavawebapp.pojo.BaseUser;
import jun.projavawebapp.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CollectionServlet", urlPatterns = "/collections")
public class CollectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<BaseUser> users = new ArrayList<>();

        users.add(new BaseUser(19384L, "Coder314", "John", "Smith"));
        users.add(new BaseUser(19383L, "geek12", "Joe", "Smith"));
        users.add(new BaseUser(19382L, "jack123", "Jack", "Johnson"));
        users.add(new BaseUser(19385L, "farmer-dude", "Adam", "Fisher"));

        req.setAttribute("users", users);
        req.getRequestDispatcher("WEB-INF/jsp/view/collections.jsp")
                .forward(req, resp);
    }
}
