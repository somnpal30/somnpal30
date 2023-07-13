package com.sample.springrest;

import com.sample.springrest.security.RSAUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTTokenGenerator {

    public static String generateToken(PrivateKey privateKey) {
        String token = null;
        try {
            Date currentDate = new Date();
            token = Jwts.builder()
                    .setHeader(header())
                    .setClaims(claims())
                    .setExpiration(new Date(System.currentTimeMillis() + 36000000))
                    .signWith(SignatureAlgorithm.RS256, privateKey).compact();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public static void main(String[] args) {
        RSAUtil rsaUtil = new RSAUtil();
        String token = generateToken(rsaUtil.getPrivateKey(rsaUtil.getPrivateKey()));
        System.out.println(token);

        //validateToken(token,rsaUtil);
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SignatureAlgorithm.HS256, rsaUtil.getPublicKey(rsaUtil.getPublicKey()));
        System.out.println(validator.isValid(token, token.split(".")[2]));
    }

    /*private static Map<String,Object>  validateToken(String token,RSAUtil rsaUtil) {
        Map<String,Object> jwtClaims = null;
        String publicKeyPEM= rsaUtil.getPublicKey();

        PublicKey publicKey = null;
        try {
            publicKey = rsaUtil.getPublicKey(publicKeyPEM);

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setVerificationKey(publicKey)
                    .build();
            // validate and decode the jwt
            JwtClaims jwtDecoded = jwtConsumer.processToClaims(token);
            jwtClaims= jwtDecoded.getClaimsMap();

        } catch (JoseException e) {
            LOG.error("invalid token",e);
        } catch (InvalidKeySpecException e) {
            LOG.error("invalid token",e);
        } catch (InvalidJwtException e) {
            LOG.error("invalid token",e);
        }
        LOG.info("jwtclaims"+jwtClaims);
        return jwtClaims;
    }*/


    public static Map<String, Object> claims() {
        Map<String, Object> claims = new HashMap();
        claims.put("serviceRequestId", "81f8d42c-21c5-4d6a-8d85-6415a3ee894e");
        claims.put("user_name", "81f8d42c-21c5-4d6a-8d85-6415a3ee894e");
        claims.put("authorizationProfileCode", "NetadminDefault");
        claims.put("identifierValue", "sysadmin1234");
        claims.put("identifierType", "LOGINID");
        claims.put("categoryCode", "NWADM");
        claims.put("userId", "US.7192723095828188");
        claims.put("deviceId", null);
        claims.put("client_id", "CoreWeb");
        claims.put("name", "yahoo gahoo");
        claims.put("bearerCode", "WEB");
        claims.put("jti", "83e48574-c996-450e-b6fa-8ed0390d5517");
        //claims.put("exp", "1752232730");
        return claims;
    }

    private static Map<String, Object> header() {
        Map<String, Object> headerMap = new HashMap<>();
        //headerMap.put("alg",new String("RS256"));
        headerMap.put("typ", new String("JWT"));
        headerMap.put("kid", new String("jws-key-public-1"));
        return headerMap;
    }
}
