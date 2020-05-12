package com.cyc.demo1.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.cyc.demo1.validation.ValidationField;

/**
 * @author chenyuchuan
 */
public class User2 {
    @NotNull
    private String id1;

    @Length(min = 0, max = 3, groups = ValidationField.class)
    private String userName1;

    @Email(regexp = ".*@qq\\.com")
    private String email1;

    private Date date1;

    public User2() {}

    public @NotNull String getId1() {
        return this.id1;
    }

    public @Length(min = 0, max = 3, groups = ValidationField.class) String getUserName1() {
        return this.userName1;
    }

    public @Email(regexp = ".*@qq\\.com") String getEmail1() {
        return this.email1;
    }

    public Date getDate1() {
        return this.date1;
    }

    public void setId1(@NotNull String id1) {
        this.id1 = id1;
    }

    public void setUserName1(@Length(min = 0, max = 3, groups = ValidationField.class) String userName1) {
        this.userName1 = userName1;
    }

    public void setEmail1(@Email(regexp = ".*@qq\\.com") String email1) {
        this.email1 = email1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User2))
            return false;
        final User2 other = (User2)o;
        if (!other.canEqual((Object)this))
            return false;
        final Object this$id1 = this.getId1();
        final Object other$id1 = other.getId1();
        if (this$id1 == null ? other$id1 != null : !this$id1.equals(other$id1))
            return false;
        final Object this$userName1 = this.getUserName1();
        final Object other$userName1 = other.getUserName1();
        if (this$userName1 == null ? other$userName1 != null : !this$userName1.equals(other$userName1))
            return false;
        final Object this$email1 = this.getEmail1();
        final Object other$email1 = other.getEmail1();
        if (this$email1 == null ? other$email1 != null : !this$email1.equals(other$email1))
            return false;
        final Object this$date1 = this.getDate1();
        final Object other$date1 = other.getDate1();
        if (this$date1 == null ? other$date1 != null : !this$date1.equals(other$date1))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id1 = this.getId1();
        result = result * PRIME + ($id1 == null ? 43 : $id1.hashCode());
        final Object $userName1 = this.getUserName1();
        result = result * PRIME + ($userName1 == null ? 43 : $userName1.hashCode());
        final Object $email1 = this.getEmail1();
        result = result * PRIME + ($email1 == null ? 43 : $email1.hashCode());
        final Object $date1 = this.getDate1();
        result = result * PRIME + ($date1 == null ? 43 : $date1.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof User2;
    }

    public String toString() {
        return "User2(id1=" + this.getId1() + ", userName1=" + this.getUserName1() + ", email1=" + this.getEmail1()
            + ", date1=" + this.getDate1() + ")";
    }
}
