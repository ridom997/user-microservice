package com.pragma.powerup.usermicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtProvider;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoIdUserFoundInTokenException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoRoleFoundInTokenException;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenValidationsPort;
import com.pragma.powerup.usermicroservice.domain.validations.ArgumentValidations;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.TOKEN_MESSAGE;

@AllArgsConstructor
@Component
public class TokenValidationSpringAdapter implements ITokenValidationsPort {

    private final JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(TokenValidationSpringAdapter.class);

    @Override
    public Long findIdUserFromToken(String token) {
        ArgumentValidations.validateString(token,TOKEN_MESSAGE);
        Long idUserFromToken = getIdUserFromToken(token);
        if (idUserFromToken == null)
            throw new NoIdUserFoundInTokenException();
        return idUserFromToken;
    }

    @Override
    public void verifyRoleInToken(String token, String roleName) {
        ArgumentValidations.validateString(token,TOKEN_MESSAGE);
        List<String> rolesFromToken = getRolesFromToken(token);
        if(rolesFromToken.isEmpty())
            throw new NoRoleFoundInTokenException("Token provided doesn't have roles");
        if(rolesFromToken.stream().noneMatch(roleName::equals))
            throw new NoRoleFoundInTokenException("User doesn't have the required role in token");
    }

    private Long getIdUserFromToken(String token){
        Claims claimsFromToken = jwtProvider.getClaimsFromToken(token);
        try{
            return ((Integer) claimsFromToken.get("idUser")).longValue();
        } catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    private List<String> getRolesFromToken(String token){
        Claims claimsFromToken = jwtProvider.getClaimsFromToken(token);
        try{
            return (List<String>) claimsFromToken.get("roles");
        } catch (Exception e){
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
