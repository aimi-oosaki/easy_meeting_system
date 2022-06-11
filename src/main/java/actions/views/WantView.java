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
public class WantView {
    /**
     * id
     */
    private Integer id;

    /**
     * アイデア募集中の会議
     */
    private MeetingView meeting;

    /**
     * アイデア募集中のチーム
     */
    private TeamView team;

    /**
     * タイトル
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 締切日
     */
    private LocalDate due_date;
}
