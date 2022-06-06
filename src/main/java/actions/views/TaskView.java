package actions.views;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 募集情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskView {
    /**
     * id
     */
    private Integer id;

    /**
     * プロジェクトのチーム
     */
    private TeamView team;

    /**
     * 担当者
     */
    private EmployeeView employee;

    /**
     * プロジェクト名
     */
    private String title;

    /**
     * 進捗状況
     */
    private String progress;

    /**
     * 問題点
     */
    private String problem;

    /**
     * 解決策
     */
    private String solution;

    /**
     * 期日
     */
    private LocalDate due_date;

    /**
     * 完了・未完了
     */
    private Integer status;
}
