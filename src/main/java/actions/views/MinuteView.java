package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 議事録情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinuteView {
    /**
     * id
     */
    private Integer id;

    /**
     * 議事録の会議
     */
    private MeetingView meeting;

    /**
     * 書記
     */
    private EmployeeView employee;

    /**
     * 参加者
     */
    private String attendees;

    /**
     * 決定事項
     */
    private String decided;

    /**
     * 保留事項
     */
    private String pending;
}
