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
 * 会議データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_MEE)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_MEE_GET_ALL,
            query = JpaConst.Q_MEE_GET_ALL_DEF
            )
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meeting {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.MEE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会議を開催するチーム
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.MEE_COL_TEA, nullable = true)
    private Team team;

    /**
     * 会議名
     */
    @Column(name = JpaConst.MEE_COL_NAME, nullable = false)
    private String name;

    /**
     * 開催日
     */
    @Column(name = JpaConst.MEE_COL_DATE, nullable = false)
    private LocalDate date;

    @Column(name = JpaConst.MEE_COL_DELETE_FLAG, nullable = false)
    private Integer delete_flag;
}
