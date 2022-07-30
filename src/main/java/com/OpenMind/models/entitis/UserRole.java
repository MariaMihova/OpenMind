package com.OpenMind.models.entitis;



import com.OpenMind.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {


    private Role role;

    public UserRole(){}
    public UserRole(Role role){
        this.role = role;
    }


    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
