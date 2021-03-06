package edu.onlinetests.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the Test_Result database table.
 * 
 */
@Entity
@Table(name="Test_Result")
public class TestResult implements Serializable, Comparable<TestResult> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private float score;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="categoryId", nullable=false, insertable=true, updatable=false)
	private Category category;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId", nullable=false, insertable=true, updatable=false)
	private User user;

	public TestResult() {
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(TestResult o) {
		return Float.compare(score, o.getScore());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : ((Integer)category.getCId()).hashCode());
		result = prime * result + Float.floatToIntBits(score);
		result = prime * result + ((user == null) ? 0 : ((Integer)user.getUserId()).hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestResult other = (TestResult) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (category.getCId() != other.category.getCId())
			return false;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (user.getUserId() != other.user.getUserId())
			return false;
		return true;
	}
	
	
	
	

}