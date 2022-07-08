package actions.views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * チーム情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamView {
    /**
     * id
     */
    private Integer id;

    /**
     * 会社ID
     */
    private Integer company_id;

    /**
     * 名前
     */
    private String name;

    /**
     * 削除フラグ
     */
    private Integer delete_flag;

}
