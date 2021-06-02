package controllers;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // 開くページ数を取得（デフォは1ページ目）
        int page = 1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e){}


        // 最大件数と位置を指定してメッセージを取得
        List <Task> tasks = em.createNamedQuery("getAllTasks", Task.class)
                              .setFirstResult(15 * (page - 1))
                              .setMaxResults(15)
                              .getResultList();

        // 全件数をDBから取得、DBを閉じる
        long tasks_count = (long)em.createNamedQuery("getTasksCount", Long.class)
                                    .getSingleResult();
        em.close();

        // リクエストスコープへ格納
        request.setAttribute("tasks", tasks);
        request.setAttribute("tasks_count", tasks_count);   //全件数
        request.setAttribute("page", page);                 //ページ数

        // index.jsp の呼び出し
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/index.jsp");
        rd.forward(request, response);

        // フラッシュメッセージをリクエストスコープへ格納し、セッションスコープから削除
        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));;
            request.getSession().removeAttribute("flush");
        }
    }
}