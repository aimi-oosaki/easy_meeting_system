package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 従業員情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeView {
    /**
     * id
     */
    private Integer id;

    /**
     * 社員番号
     */
    private String code;

    /**
     * 従業員の会社
     */
    private Integer company_id;

    /**
     * 従業員のチーム
     */
    private TeamView team;


    /**
     * 氏名
     */
    private String name;

    /**
     * パスワード
     */
    private String password;

    /**
     * 管理者権限があるかどうか（一般：0、管理者：1）
     */
    private Integer adminFlag;

    /**
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    private Integer deleteFlag;

}
