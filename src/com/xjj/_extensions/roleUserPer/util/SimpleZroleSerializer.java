package com.xjj._extensions.roleUserPer.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xjj._extensions.roleUserPer.model.Zrole;

public class SimpleZroleSerializer extends JsonSerializer<Set<Zrole>> {
	
	public void serialize(final Set<Zrole> zroles, final JsonGenerator generator,
        final SerializerProvider provider) throws IOException, JsonProcessingException {
        final Set<SimpleZrole> simpleZroles = new HashSet<SimpleZrole>();
        for (final Zrole zrole : zroles) {
        	simpleZroles.add(new SimpleZrole(zrole.getId(),zrole.getName(),zrole.getCode(),zrole.getPri(),zrole.getRemarks(),zrole.getCreateTime()));                
        }
        generator.writeObject(simpleZroles);
    }

    static class SimpleZrole {

        public SimpleZrole(String id, String name ,String code, Integer pri , String remarks , Date createTime) {
        	this.id = id;
        	this.code = code;
        	this.name = name;
        	this.pri = pri;
        	this.remarks = remarks;
        	this.createTime = createTime;
		}
		private String id;

        private String name;

        private String code;//角色代码
        
        private Integer pri;//排序
        
    	private String remarks;//备注
    	
    	private Date createTime;//创建时间

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getPri() {
			return pri;
		}

		public void setPri(Integer pri) {
			this.pri = pri;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
    }
}
