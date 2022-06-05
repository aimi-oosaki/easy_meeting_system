package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * アイデア情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaView {
    /**
     * id
     */
    private Integer id;

    /**
     * アイデア募集中の募集
     */
    private WantView want;

    /**
     * アイデアを投稿した社員
     */
    private EmployeeView employee;

    /**
     * 内容
     */
    private String content;
}
