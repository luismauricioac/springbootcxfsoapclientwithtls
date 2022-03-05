# springbootcxfsoapclientwithtls

Springboot CXF SOAP Client though TLSv1.2 and Digital Sign example

## Getting Started

This is a artifact example that I made for remember how the hell is it implemented a CXF SOAP CLient though TLS and Digital Sign

I'm using the webservice soap endpoint https://www.dataaccess.com/webservicesserver/NumberConversion.wso?wsdl 

## Instructions

### Get webservice cert
```
echo "" | openssl s_client -connect  www.dataaccess.com:443 -showcerts 2>/dev/null | openssl x509 -out certfile.txt
```

### Generate truststore file and trust in the webservice cert
```
keytool -import -alias numberconversion -keystore truststore.ts -file numberconversion.txt
```

### Generate auto-signed cert to make digital sign on the soap message
```
keytool -genkey -alias luismalamoc -keyalg RSA -keysize 2048 -keystore luismalamoc.jks -dname "CN=luismalamoc, OU=luismalamoc, O=luismalamoc, L=Santiago, ST=RM, C=CL" -validity 365000
```

## Notes

- Autogenerate classes for webservice Client were generated with wsdl2java task added to build.gradle file
- This is an implementation example, the webservice from www.dataaccess.com is not requiring TLSv1.2 TWO WAY with DIGITAL SIGN, 
because you need to provide the auto-signed cert file to the counterpart for the handshake protocol
