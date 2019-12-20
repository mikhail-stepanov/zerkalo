package zerkalo.msclient.auth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignUpRequest {

    @NotNull
    private String login;

    @NotNull
    private String password;
}
