package org.zunpeng.core.mybatis;

import com.google.common.collect.Lists;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dapeng on 7/18/16.
 */
public class Criteria implements Serializable {

	private static final long serialVersionUID = -1681208907470792693L;

	private Pageable pageable;

	private List<Criterion> criterionList;

	public Criteria(Pageable pageable){
		this.pageable = pageable;
		this.criterionList = Lists.newArrayList();
	}

	public Criteria(Pageable pageable, List<Criterion> criterionList){
		this.pageable = pageable;
		this.criterionList = criterionList;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public List<Criterion> getCriterionList() {
		return criterionList;
	}

	public void setCriterionList(List<Criterion> criterionList) {
		this.criterionList = criterionList;
	}
}
