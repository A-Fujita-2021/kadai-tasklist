package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // 該当IDのタスク1件のデータをDBから取得
        Task t = em.find(Task.class, Integer.parseInt(request.getParameter("id")));

        // DB切断
        em.close();

        // タスク情報とセッションIDをリクエストスコープに登録
        request.setAttribute("task", t);
        request.setAttribute("_token", request.getSession().getId());

        // タスクデータが存在しているときのみタスクIDをセッションスコープに登録
        if(t != null){
            request.getSession().setAttribute("message_id", t.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/tasks/edit.jsp");
        rd.forward(request, response);
    }

}