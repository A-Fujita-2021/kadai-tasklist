package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

// DTO(DB内データと変数名の照合)、テーブルの作成

@Entity
@NamedQueries({     // 一覧表示するデータを取得するためのJPQL
    @NamedQuery(
        name = "getAllTasks",
        query = "SELECT t From Task AS t ORDER BY t.id"
    ),
    @NamedQuery(
        name = "getTasksCount",
        query = "SELECT COUNT(t) FROM Task AS t"
    )
})


@Table(name = "tasks")
public class Task {

    // ID
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // タスク名
    @Column(name = "content", length = 100, nullable = false)
    private String content;

    // タスクの作成日時
    @Column(name="created_at", nullable = false)
    private Timestamp created_at;

    // タスクの更新日時
    @Column(name="updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
