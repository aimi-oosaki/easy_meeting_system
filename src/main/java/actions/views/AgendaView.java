package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 議題情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaView {
    /**
     * id
     */
    private Integer id;

    /**
     * 議題の会議
     */
    private MeetingView meeting;

    /**
     * タイトル
     */
    private String title;

    /**
     * 話し合った内容
     */
    private String summary;
}
