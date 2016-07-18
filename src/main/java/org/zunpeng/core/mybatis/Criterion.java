package org.zunpeng.core.mybatis;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dapeng on 7/17/16.
 */
public class Criterion implements Serializable {

	private static final long serialVersionUID = -8432726318302153501L;

	private boolean initialized = false;

	private String property;

	private List<Object> valueList = Lists.newArrayList();

	private String logicalOperator;

	private Object value;

	private Object secondValue;

	private String condition;

	private boolean noValue;

	private boolean singleValue;

	private boolean betweenValue;

	private boolean listValue;

	private boolean likeValue;

	public Criterion(String property, String logicalOperator, Object... valueArray){
		this.property = property;
		this.logicalOperator = logicalOperator;
		this.valueList = Arrays.asList(valueArray);
		init();
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public List<Object> getValueList() {
		return valueList;
	}

	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}

	public String getLogicalOperator() {
		return logicalOperator;
	}

	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}

	public Object getValue() {
		return value;
	}

	public Object getSecondValue() {
		return secondValue;
	}

	public String getCondition() {
		return condition;
	}

	public boolean isNoValue() {
		return noValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public boolean isListValue() {
		return listValue;
	}

	public boolean isLikeValue() {
		return likeValue;
	}

	private void init(){
		if(this.initialized){
			return;
		}

		int size = valueList.size();

		if(size > 0){
			this.value = valueList.get(0);
		}

		if(size > 1){
			this.secondValue = valueList.get(1);
		}

		if("eq".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " = ";
		} else if("gt".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " > ";
		} else if("ge".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " >= ";
		} else if("lt".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " < ";
		} else if("le".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " <= ";
		} else if("like".equals(this.logicalOperator)){
			this.likeValue = true;
			this.condition = this.property + " like ";
		} else if("nlike".equals(this.logicalOperator)){
			this.likeValue = true;
			this.condition = this.property + " not like ";
		} else if("bt".equals(this.logicalOperator)){
			this.betweenValue = true;
			this.condition = this.property + " between ";
		} else if("neq".equals(this.logicalOperator)){
			this.singleValue = true;
			this.condition = this.property + " <> ";
		} else if("in".equals(this.logicalOperator)) {
			this.listValue = true;
			this.condition = this.property + " in ";
		} else if("nin".equals(this.logicalOperator)) {
			this.listValue = true;
			this.condition = this.property + " not in ";
		} else if("isnull".equals(this.logicalOperator)) {
			this.noValue = true;
			this.condition = this.property + " is null ";
		} else if("notnull".equals(this.logicalOperator)) {
			this.noValue = true;
			this.condition = this.property + " is not null ";
		}

		this.initialized = true;
	}
}
