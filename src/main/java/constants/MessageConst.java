package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //認証
    I_LOGINED("ログインしました"),
    GUEST_LOGINED("ゲストとしてログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //バリデーション
    E_NONAME("氏名を入力してください。"),
    E_NOCOMNAME("会社CDを入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOEMP_CODE("社員番号を入力してください。"),
    E_EMP_CODE_EXIST("入力された社員番号の情報は既に存在しています。"),
    E_NOTITLE("タイトルを入力してください。"),
    E_NOProject("募集を入力してください。"),
    E_NOMEETING("会議を入力してください。"),
    E_NOCONTENT("内容を入力してください。"),
    E_NONUMBER("番号を入力してください。"),
    E_NOEMPLOYEE("入力者を入力してください。"),
    E_NODATE("日付を入力してください。"),
    E_NOWHO("担当者を入力してください。"),
    E_NOWHAT("やることを入力してください。"),
    E_NODEADLINE("期限を入力してください。"),
    E_NOTEAM("チームを入力してください。"),
    E_NOTASK("プロジェクト名を入力してください。"),
    E_NOREP("担当者名を入力してください。"),
    E_NODUEDATE("募集期限を入力してください。");


    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }
}