package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * アイデアデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_IDE)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_IDE_GET_ALL,
            query= JpaConst.Q_IDE_GET_ALL_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Idea {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.IDE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どの募集のアイデアか
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.IDE_COL_WAN, nullable = false)
    private Want want;

    /**
     * どのチームの募集か
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.IDE_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * 内容
     */
    @Column(name = JpaConst.IDE_COL_CONTENT, length = 500, nullable = false)
    private String content;
}
