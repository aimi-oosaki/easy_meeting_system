package actions.views;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 会議情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingView {
    /**
     * id
     */
    private Integer id;

    /**
     * アイデア募集中の会議
     */
    private TeamView team;

    /**
     * 名前
     */
    private String name;

    /**
     * 開催日
     */
    private LocalDate date;

    /**
     * 削除フラグ
     */
    private Integer delete_flag;
}
