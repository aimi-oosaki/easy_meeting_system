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
 * 議題データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_AGE)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_AGE_GET_ALL,
            query= JpaConst.Q_AGE_GET_ALL_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agenda {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.AGE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どの会議の議題か
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.AGE_COL_MET, nullable = false)
    private Meeting meeting;

    /**
     * 議題
     */
    @Column(name = JpaConst.AGE_COL_TITLE, length = 250, nullable = false)
    private String title;

    /**
     * 会議で話した内容
     */
    @Column(name = JpaConst.AGE_COL_SUMMARY)
    private String summary;
}
