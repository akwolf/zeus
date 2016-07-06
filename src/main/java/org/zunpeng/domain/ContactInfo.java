package org.zunpeng.domain;

/**
 * 联系人信息
 * Created by dapeng on 6/7/16.
 */
public class ContactInfo extends AbstractEntity {

	private static final long serialVersionUID = -7003139150578907026L;

	private String name;

	private String telephone;

	private String position;

	private int sequence = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
