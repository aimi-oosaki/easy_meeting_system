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
public class TodoView {
    /**
     * id
     */
    private Integer id;

    /**
     * 担当者
     */
    private EmployeeView employee;

    /**
     * TODOの会議
     */
    private MeetingView meeting;

    /**
     * TODOのチーム
     */
    private TeamView team;

    /**
     * 何を
     */
    private String what;

    /**
     * 締切日
     */
    private LocalDate deadline;

    /**
     * 完了・未完
     */
    private Integer status;

    /**
     * 結果
     */
    private String consequence;

}
