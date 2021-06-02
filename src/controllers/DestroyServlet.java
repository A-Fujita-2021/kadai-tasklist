package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class DestroyServlet
 */
@WebServlet("/destroy")
public class DestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null){
            EntityManager em = DBUtil.createEntityManager();

            // セッションスコープからタスクのIDを取得して、該当IDのタスク1件のみをDBから取得
            Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("message_id")));

            // DBへ接続、タスクを削除、切断
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
            em.close();

            // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("message_id");

            // フラッシュメッセージをセッションスコープへ格納
            request.getSession().setAttribute("flush", "タスクを完了しました！ おつかれさまです！！");

            // 一覧ページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}
