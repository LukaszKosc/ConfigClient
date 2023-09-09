Sources:

https://www.baeldung.com/spring-cloud-config-without-git

https://www.baeldung.com/spring-cloud-configuration

https://www.baeldung.com/jce-enable-unlimited-strength

https://stackoverflow.com/questions/65063402/why-bootstrap-properties-is-ignored-by-spring-cloud-starter-config

https://stackoverflow.com/questions/56364824/spring-cloud-config-client-doesnt-get-values-from-server


Call ConfigClient to get configuration stored in git:

````
curl -s http://localhost:8080/whoami/Lukasz
````

Expected output:
````
Hello! Your 'username' is 'Lukasz' and you'll become a(n) 'Developer', your password decoded from file stored in git 'hello'
````
