package org.ray.crud.base.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 基础实体类，所有业务实体类的父类
 * @see 当业务上需要把属性为null的值过滤掉时，可以考虑注解{@link JsonInclude}，如：{@code @JsonInclude(Include.NON_NULL) }；
 * 但是禁止该注解使用到本类上，因为这样会导致所有实体返回结果都会过滤掉空值，前端会因取不到该属性而出错。
 * @author Ray
 * @date Created by Ray on 2018/1/24.
 */
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = -6359421782323471791L;

	@Id
    private String id;

	@Column
    private Integer status;

	@Column
    private String createUser;

	@Column
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

	@Column
    private String updateUser;

    @Column
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
    	if(status == null){
    		status = 1;
    	}
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
}
