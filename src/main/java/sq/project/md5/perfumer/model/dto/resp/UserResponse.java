package sq.project.md5.perfumer.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import sq.project.md5.perfumer.model.entity.Users;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String fullName;
    private String status;
    private String avatar;
    private String phone;
    private String address;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    private String isDeleted;

    public UserResponse(Users users) {
        this.username = users.getUsername();
        this.email = users.getEmail();
        this.fullName = users.getFullName();
        this.address = users.getAddress();
        this.phone = users.getPhone();
        this.createdAt = users.getCreatedAt();
        this.updatedAt = users.getUpdatedAt();
        this.isDeleted = users.getIsDeleted().toString();
        this.avatar = users.getAvatar();
        this.status = users.getStatus().toString();
    }
}
