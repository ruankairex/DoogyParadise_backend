package com.ispan.dogland.model.entity;

import jakarta.persistence.*;

@Table
@Entity
public class EmployeePermissions {
    @Id
    private String dbAuthority;


    private Integer permissionId;

    private String department;

    private String title;

    public EmployeePermissions(){
    }

    public String getDbAuthority() {
        return dbAuthority;
    }

    public void setDbAuthority(String dbAuthority) {
        this.dbAuthority = dbAuthority;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("EmployeePermissions{");
        sb.append("dbAuthority='").append(dbAuthority).append('\'');
        sb.append(", permissionId=").append(permissionId);
        sb.append(", department='").append(department).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
