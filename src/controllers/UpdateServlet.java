package controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Task;
import models.validators.TaskValidator;
import utils.DBUtil;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null){
           EntityManager em = DBUtil.createEntityManager();

           // セッションスコープからタスクIDを取得して
           // 該当するタスク1件のみをDBから取得
           Task t = em.find(Task.class, (Integer)(request.getSession().getAttribute("message_id")));

           // フォームの内容を各フィールドに上書き
           String content =request.getParameter("content");
           t.setContent(content);

           Timestamp currentTime = new Timestamp(System.currentTimeMillis());
           t.setUpdated_at(currentTime);

           // バリデーションを実行してエラーがあったら編集画面のフォームに戻る
           List<String> errors = TaskValidator.validate(t);
           if(errors.size() >0){
               em.close();

               // フォームに初期値を設定してエラーメッセージを送る
               request.setAttribute("_token", request.getSession().getId());
               request.setAttribute("task", t);
               request.setAttribute("errors",  errors);

               RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp");
               rd.forward(request, response);

           // エラーがなければDBを更新して切断
           } else {

               em.getTransaction().begin();
               em.getTransaction().commit();
               em.close();

               // フラッシュメッセージをセッションスコープへ格納
               request.getSession().setAttribute("flush", "タスクを変更しました！ 休憩も大事！");

               // セッションスコープ上の不要になったデータを削除
               request.getSession().removeAttribute("message_id");

               // 一覧ページへリダイレクト
               response.sendRedirect(request.getContextPath() + "/index");
           }
        }
    }
}
