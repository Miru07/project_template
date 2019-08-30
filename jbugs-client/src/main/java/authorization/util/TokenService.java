package authorization.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ro.msg.edu.jbugs.dto.LoginResponseUserDTO;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.manager.remote.UserManagerRemote;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenService {

    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
    private static String ISSUER = "teamC";
    private static long EXPIRATION_TIME = 300000 * 6; // 5 min * 6

    public static String generateLoginToken(LoginResponseUserDTO loginResponseUserDTO){
        return generateNewJbugsToken(loginResponseUserDTO.getId(),
                loginResponseUserDTO.getUsername());
    }

    public static String generateNewJbugsToken(Integer userID, String username){
        return generateJWT(userID.toString(), ISSUER,
                username, EXPIRATION_TIME);
    }

    @SuppressWarnings( "deprecation" )
    private static String generateJWT(String id, String issuer, String subject, long ttlMillis) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**************************if token is expired, all responses should *****************************
     * **************************have as an effect LOGOUT*********************************************
     * @param token
     * @return
     */
    public static boolean isTokenExpired(String token){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expirationDateOfToken = decodeJWT(token).getExpiration();

        // 0 if the argument Date is equal to this Date
        // !--a value less than 0 if this Date is before the Date argument--!
        // and a value greater than 0 if this Date is after the Date argument.
        return (expirationDateOfToken.compareTo(now) < 0);
    }
    public static boolean currentUserHasPermission(UserManagerRemote var_userManager, String token, PermissionType permission) {
        Integer userID = Integer.parseInt(decodeJWT(token).getId());
        return var_userManager.userHasPermission(userID, permission);
    }
    public static Integer getCurrentUserID(String token){
        return Integer.parseInt(decodeJWT(token).getId());
    }
    public static String getCurrentUserUSERNAME(String token){
        return decodeJWT(token).getSubject();
    }
}
