package controllers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import utils.DBUtil;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // _tokenの読み込み
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {

            // データベース接続のための変数作成
            EntityManager em = DBUtil.createEntityManager();

            // タスクのインスタンスを作成
            Task t = new Task();


            // tの各フィールドにデータを入力
            String content = request.getParameter("content");
            t.setContent(content);    //タスク

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            t.setCreated_at(currentTime);   //作成日時
            t.setUpdated_at(currentTime);   //更新日時


            // データベースへ接続、データを保存、切断
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            em.close();

            // フラッシュメッセージをリクエストスコープへ格納
            request.getSession().setAttribute("flush", "タスクを登録しました！ がんばりましょう！");

            // 遷移先のファイルを指定して開く(リダイレクト)
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
}
