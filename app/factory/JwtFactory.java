package factory;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import play.Configuration;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Created by abdoulbou on 05/05/17.
 */
@Singleton
public class JwtFactory {

    @Inject
    private Configuration configuration;

    private Long ttlMillis = 8640000l;

    public String createJWT(String id, String issuer, String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(configuration.getString("play.crypto.secret"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public boolean isValidJWT(String jwt, String userInfos) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(configuration.getString("play.crypto.secret")))
                    .parseClaimsJws(jwt).getBody();
            if (StringUtils.isBlank(userInfos) || !userInfos.equals(claims.getId()) || !userInfos.equals(claims.getSubject()) || !userInfos.equals(claims.getIssuer())) {
                return false;
            }

            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
