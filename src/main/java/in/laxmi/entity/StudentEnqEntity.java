package in.laxmi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "AIT_STUDENT_ENQUIRIES")
@Data
public class StudentEnqEntity implements Serializable
 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Long phno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	@CreationTimestamp
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private UserDetailsEntity userDetailsEntity;
}
