package com.ssp.closet.dto;

import java.io.Serializable;
//import java.sql.Date;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.Getter;
import lombok.Setter;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "PRODUCT")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE") // 하위 테이블의 구분 컬럼 생성(default = DTYPE)
@Data
public class Product implements Serializable {

	/* Private Fields */
	@Id
	@SequenceGenerator(name = "PRODUCT_SEQ_GENERATOR", sequenceName="PRODUCT_SEQ", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PRODUCT_SEQ_GENERATOR")
	@Column(name="PRODUCTID")
	private int productId;
	@Column(name="CATEGORYID")
	private String categoryId; // 의류 카테고리
	@Column(name="PNAME")
	private String name;
	@Column(name="PDESCRIPTION")
	private String description; // 상품 설명
	@Column(name="STATUS")
	private int status; // 판매 상태 (판매중/판매종료)
	@Column(name="REGISTERDATE")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate; // 등록 날짜
	@Column(name="ENDDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate; //종료 날짜
	@Column(name="COLOR")
	private String color;
	@Column(name="PSIZE")
	private String size;
	@Column(name="PICTURE1")
	private String picture1;
	@Column(name="PICTURE2")
	private String picture2;
	@Column(name="PICTURE3")
	private String picture3;
	@Column(name="PICTURE4")
	private String picture4;
	@Column(name="PRICE")
	private Integer price;
	@Column(name = "DTYPE", insertable=false, updatable=false)
	private String DTYPE;
	
	@ManyToOne
	@JoinColumn(name = "USERID", referencedColumnName = "USERID")
	private Account account;

	@Transient
	private int likeCount;
}