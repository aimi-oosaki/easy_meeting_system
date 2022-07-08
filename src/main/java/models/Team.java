package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * チームデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TEA)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TEA_GET_ALL,
            query = JpaConst.Q_TEA_GET_ALL_DEF
            )
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TEA_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どの会社のチームか
     */
    @Column(name = JpaConst.TEA_COL_COM, nullable = false)
    private Integer company_id;

    /**
     * チーム名
     */
    @Column(name = JpaConst.TEA_COL_NAME, nullable = false)
    private String name;

    /**
     * 削除フラグ
     */
    @Column(name = JpaConst.MEE_COL_DELETE_FLAG, nullable = false)
    private Integer delete_flag;
}
