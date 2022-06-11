package models;

import java.time.LocalDate;

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
 * 募集データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_WAN)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_WAN_GET_BY_MEETING,
            query= JpaConst.Q_WAN_GET_BY_MEETING_DEF),
    @NamedQuery(
            name = JpaConst.Q_WAN_GET_ALL,
            query= JpaConst.Q_WAN_GET_ALL_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Want {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.WAN_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どの会議の募集か
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.WAN_COL_MET, nullable = false)
    private Meeting meeting;

    /**
     * どのチームの募集か
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.WAN_COL_TEA, nullable = false)
    private Team team;

    /**
     * タイトル
     */
    @Column(name = JpaConst.WAN_COL_TITLE, length = 50, nullable = false)
    private String title;

    /**
     * 内容
     */
    @Column(name = JpaConst.WAN_COL_CONTENT, length = 250, nullable = false)
    private String content;

    @Column(name = JpaConst.WAN_COL_DUE_DATE, nullable = false)
    private LocalDate due_date;
}
